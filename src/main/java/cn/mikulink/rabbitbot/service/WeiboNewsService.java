package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.weibo.WeiboHomeTimelineGet;
import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantWeiboNews;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoPicUrl;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoStatuses;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ConfigService configService;

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
        //每次获取最新的指定数目
        request.setCount(pageSize);
        request.setSince_id(NumberUtil.toLong(ConstantCommon.common_config.get("sinceId")));

        //发送请求
        request.doRequest();
        return request.getEntity();
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
                configService.refreshConfigFile();
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
    public List<MessageChain> parseWeiboBody(InfoStatuses info) throws IOException {
        return parseWeiboBody(info, false);
    }

    /**
     * 解析微博信息
     *
     * @param info            微博信息
     * @param retweetedStatus 是否为被转发的微博
     * @return 转化好的信息对象，包含图片信息
     * @throws IOException 处理异常
     */
    public List<MessageChain> parseWeiboBody(InfoStatuses info, boolean retweetedStatus) throws IOException {
        List<MessageChain> result = new ArrayList<>();

        Long userId = info.getUser().getId();

        //如果是转发微博
        if (retweetedStatus) {
            result.add(RabbitBotMessageBuilder.parseMessageChainText("\n-----------↓转发微博↓----------\n"));
        }

        //头像
        if (1312997677 == userId) {
            result.add(RabbitBotMessageBuilder.parseMessageChainText(ConstantWeiboNews.WHAT_ASSHOLE + "\n"));
        } else {
            //解析推主头像
            String userLogoUrl = getUserLogoUrl(info.getUser().getProfile_image_url());
            if (null != userLogoUrl) {
                result.add(RabbitBotMessageBuilder.parseMessageChainText(userLogoUrl));
            }
        }
        //推主名
        result.add(RabbitBotMessageBuilder.parseMessageChainText("[" + info.getUser().getName() + "]\n"));
        //uid
        result.add(RabbitBotMessageBuilder.parseMessageChainText("[" + info.getUser().getId() + "]\n"));
        //推文时间
        result.add(RabbitBotMessageBuilder.parseMessageChainText("[" + parseWeiboDate(info.getCreated_at()) + "]\n"));
        result.add(RabbitBotMessageBuilder.parseMessageChainText("========================\n"));
        //正文
        result.add(RabbitBotMessageBuilder.parseMessageChainText(info.getText()));

        //拼接推文图片
        List<InfoPicUrl> picList = info.getPic_urls();
        if (null == picList) {
            return result;
        }
        for (InfoPicUrl picUrl : picList) {
            if (StringUtil.isEmpty(picUrl.getThumbnail_pic())) {
                continue;
            }
            //获取原图地址
            String largeImageUrl = getImgLarge(picUrl.getThumbnail_pic());
            //上传图片并获取图片id
            String userLogoUrl = getUserLogoUrl(largeImageUrl);
            if (null != userLogoUrl) {
                result.add(RabbitBotMessageBuilder.parseMessageChainText("\n" + userLogoUrl));
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

    //处理头像图片链接问题
    private String getUserLogoUrl(String imageUrl) {
        if (StringUtil.isEmpty(imageUrl)) {
            return null;
        }
        //针对头像的不标准链接特殊处理
        if (imageUrl.contains("?")) {
            imageUrl = imageUrl.substring(0, imageUrl.lastIndexOf("?"));
        }
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }

    //转化微博接口的时间字段
    private String parseWeiboDate(String dateStr) {
        try {
            Date tempDate = DateUtil.toDate(dateStr, "EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
            return DateUtil.toString(tempDate);
        } catch (ParseException parseEx) {
            logger.error("微博时间转化失败:{}", dateStr, parseEx);
            return dateStr;
        }
    }
}
