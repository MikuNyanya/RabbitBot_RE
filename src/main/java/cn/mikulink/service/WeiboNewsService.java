package cn.mikulink.service;

import cn.mikulink.apirequest.weibo.WeiboHomeTimelineGet;
import cn.mikulink.bot.RabbitBot;
import cn.mikulink.constant.ConstantCommon;
import cn.mikulink.constant.ConstantFile;
import cn.mikulink.constant.ConstantWeiboNews;
import cn.mikulink.entity.apirequest.weibo.InfoPicUrl;
import cn.mikulink.entity.apirequest.weibo.InfoStatuses;
import cn.mikulink.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.exceptions.RabbitException;
import cn.mikulink.filemanage.FileManagerConfig;
import cn.mikulink.utils.ImageUtil;
import cn.mikulink.utils.NumberUtil;
import cn.mikulink.utils.StringUtil;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * create by MikuLink on 2020/1/9 16:42
 * for the Reisen
 * 与微博推文交互的服务
 */
@Service
public class WeiboNewsService {
    private static final Logger logger = LoggerFactory.getLogger(WeiboNewsService.class);

    @Value("${weibo.token}")
    private String weiboToken;

    /**
     * 获取微信的最新消息
     *
     * @param pageSize 每页大小，默认为5
     * @return 微博查询接口返回的对象
     * @throws IOException 接口异常
     */
    public InfoWeiboHomeTimeline getWeiboNews(Integer pageSize) throws IOException, RabbitException {
        //检查授权码
        if (StringUtil.isEmpty(weiboToken)) {
            throw new RabbitException(ConstantWeiboNews.NO_ACCESSTOKEN);
        }

        if (null == pageSize) {
            pageSize = 5;
        }

        WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
        request.setAccessToken(weiboToken);
        request.setPage(1);
        //每次获取最近的5条
        request.setCount(pageSize);
        request.setSince_id(NumberUtil.toLong(ConstantCommon.common_config.get("sinceId")));

        //发送请求
        request.doRequest();
        return request.getEntity();
    }

    /**
     * 给给每个群推送微博
     *
     * @param statuses 从微博API获取的推文列表
     */
    public void sendWeiboNewsToEveryGroup(List<InfoStatuses> statuses) throws InterruptedException, IOException {
        if (null == statuses || statuses.size() == 0) {
            return;
        }

        //发送微博
        for (InfoStatuses info : statuses) {
            //过滤转发微博
            if (null != info.getRetweeted_status()) {
                continue;
            }
            //给每个群发送报时
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //解析微博报文
                MessageChain msgChain = parseWeiboBody(info, groupInfo);
                groupInfo.sendMessage(msgChain);

                //每个群之间间隔半秒意思一下
                Thread.sleep(500);
            }
            Thread.sleep(1000L * 5);
        }
    }

    /**
     * 执行一次微博群消息推送
     */
    public void doPushWeiboNews() throws IOException, InterruptedException, RabbitException {
        InfoWeiboHomeTimeline weiboNews = getWeiboNews(10);
        Long sinceId = weiboNews.getSince_id();
        //刷新最后推文标识，但如果一次请求中没有获取到新数据，since_id会为0
        if (0 != sinceId) {
            logger.info(String.format("微博sinceId刷新：[%s]->[%s]", ConstantCommon.common_config.get("sinceId"), sinceId));
            //刷新sinceId配置
            ConstantCommon.common_config.put("sinceId", String.valueOf(sinceId));
            //更新配置文件
            FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
        }
        sendWeiboNewsToEveryGroup(weiboNews.getStatuses());
    }

    /**
     * 更新微博sinceId
     */
    public void refreshSinceId() {
        try {
            //检查授权码
            if (StringUtil.isEmpty(weiboToken)) {
                throw new RabbitException(ConstantWeiboNews.NO_ACCESSTOKEN);
            }
            WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
            request.setAccessToken(weiboToken);
            request.setPage(1);
            request.setCount(1);

            //发送请求
            request.doRequest();
            InfoWeiboHomeTimeline weiboNews = request.getEntity();
            Long sinceId = weiboNews.getSince_id();
            //刷新最后推文标识
            if (0 != sinceId) {
                logger.info(String.format("微博sinceId刷新(主动刷新)：[%s]->[%s]", ConstantCommon.common_config.get("sinceId"), sinceId));
                //刷新sinceId配置
                ConstantCommon.common_config.put("sinceId", String.valueOf(sinceId));
                //更新配置文件
                FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
            }
        } catch (Exception ex) {
            logger.error("WeiboNewsService refreshSinceId error", ex);
        }
    }

    /**
     * 解析微博信息
     *
     * @param info 微博信息
     * @return 转化好的信息对象，包含图片信息
     * @throws IOException 处理异常
     */
    public MessageChain parseWeiboBody(InfoStatuses info, Group subject) throws IOException {
        MessageChain result = MessageUtils.newChain();
        //头像
        if (1312997677 != info.getUser().getId()) {
            //解析推主头像
            Image userImgInfo = getMiraiImageByWeiboImgUrl(info.getUser().getProfile_image_url(), subject);
            if (null != userImgInfo) {
                result = result.plus("").plus(userImgInfo);
            } else {
                result = result.plus("[头像下载失败]");
            }
        } else {
            result = result.plus(ConstantWeiboNews.WHAT_ASSHOLE).plus("\n");
        }
        //推主名
        result = result.plus("[" + info.getUser().getName() + "]\n");
        //微博id
        result = result.plus("[" + info.getUser().getId() + "]\n");
        //推文时间
        result = result.plus("[" + info.getCreated_at() + "]\n");
        result = result.plus("====================\n");
        //正文
        result = result.plus(info.getText());

        //拼接推文图片
        List<InfoPicUrl> picList = info.getPic_urls();
        if (null == picList || picList.size() <= 0) {
            return result;
        }

        for (InfoPicUrl picUrl : picList) {
            if (StringUtil.isEmpty(picUrl.getThumbnail_pic())) {
                continue;
            }
            //获取原图地址
            String largeImageUrl = getImgLarge(picUrl.getThumbnail_pic());
            //上传图片并获取图片id
            Image largeImagInfo = getMiraiImageByWeiboImgUrl(largeImageUrl, subject);
            if (null != largeImagInfo) {
                result = result.plus("\n").plus("").plus(largeImagInfo);
            }
        }
        return result;
    }

    /**
     * 可以尝试获取微博正文中的原图
     *
     * @param imageUrl 图片链接
     * @return 原图链接
     */
    private String getImgLarge(String imageUrl) {
        //一般缩略图是这样的 http://wx4.sinaimg.cn/thumbnail/006QZngZgy1gaqfywbnqlg30dw0hzu0z.gif
        //原图链接是这样的   http://wx4.sinaimg.cn/large/006QZngZgy1gaqfywbnqlg30dw0hzu0z.gif
        //发现不一样的地方只有中段，thumbnail是缩略图 bmiddle是压过的图 large是原图
        //GIF目前发现只有原图会动，所以把中部替换掉就能获取到原图链接
        return imageUrl.replace("thumbnail", "large");
    }

    //解析微博图片为miraiImage对象
    private Image getMiraiImageByWeiboImgUrl(String imageUrl, Group subject) throws IOException {
        if (StringUtil.isEmpty(imageUrl)) {
            return null;
        }
        //针对头像的不标准链接特殊处理
        if (imageUrl.contains("?")) {
            imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("?"));
        }
        String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

        //先把图片下载下来
        String localImageUrl = ImageUtil.downloadImage(imageUrl);
        if (StringUtil.isEmpty(localImageUrl)) {
            return null;
        }
        //然后上传到服务器，获取imageId
        BufferedImage image = ImageIO.read(new FileInputStream(localImageUrl));
        return subject.uploadImage(image);
    }
}
