package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.bilibili.BilibiliDynamicSvrGet;
import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.bilibili.*;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.*;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author MikuLink
 * @date 2021/05/19 17:10
 * for the Reisen
 * B站相关服务
 */
@Service
public class BilibiliService {
    private static final Logger logger = LoggerFactory.getLogger(BilibiliService.class);

    //B站cookie
    @Value("${bilibili.cookie:0}")
    private String bilibiliCookie;
    /**
     * 最后一次推送的视频DynamicId
     */
    private Long lastDynamicId = -1L;

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private ConfigService configService;


    /**
     * 执行视频动态推送
     *
     * @throws IOException api异常
     */
    public void doDynamicSvrPush() throws IOException {
        //获取b站视频动态列表
        BilibiliDynamicSvrGet request = new BilibiliDynamicSvrGet();
        request.getHeader().put("cookie", bilibiliCookie);
        request.doRequest();
        BilibiliDynamicSvrResponseInfo responseInfo = request.parseResponseInfo();
        if (null == responseInfo) {
            return;
        }
        BilibiliDynamicSvrInfo dynamicSvrInfo = responseInfo.getData();
        if (null == dynamicSvrInfo || CollectionUtil.isEmpty(dynamicSvrInfo.getCards())) {
            return;
        }

        //获取lastDynamicId配置
        if (lastDynamicId < 0) {
            lastDynamicId = NumberUtil.toLong(ConstantCommon.common_config.get("lastDynamicId"));
            if (null == lastDynamicId) {
                lastDynamicId = 0L;
            }
        }

        //如果本次没有新视频则结束推送流程
        if (dynamicSvrInfo.getMaxDynamicId() <= lastDynamicId) {
            return;
        }

        List<BilibiliDynamicSvrCardsInfo> dynamicSvrCardsInfoList = dynamicSvrInfo.getCards();

        for (BilibiliDynamicSvrCardsInfo dynamicSvrCardsInfo : dynamicSvrCardsInfoList) {
            //忽略已推送内容
            if (dynamicSvrCardsInfo.getDesc().getDynamicId() <= lastDynamicId) {
                continue;
            }
            //转化并推送
            groupDynamicSvrPush(dynamicSvrCardsInfo);
        }

        //刷新lastDynamicId
        this.refreshLastDynamicId(String.valueOf(dynamicSvrInfo.getMaxDynamicId()));
    }

    /**
     * 解析内容并推送到群
     */
    private void groupDynamicSvrPush(BilibiliDynamicSvrCardsInfo dynamicSvrCardsInfo) {
        try {
            //up主uid
            Long uid = dynamicSvrCardsInfo.getDesc().getUid();

            //组装消息 懒加载
            MessageChain msgChain = null;

            //给每个群推送消息
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "biliVideo");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                //检查该群是否订阅了这个账号
                if (!configService.checkBiliPushId(groupInfo.getId(), uid)) {
                    continue;
                }

                //懒加载
                if (null == msgChain) {
                    msgChain = parseDynamicSvrBody(dynamicSvrCardsInfo);
                }
                if (null == msgChain) {
                    continue;
                }

                try {
                    groupInfo.sendMessage(msgChain);
                } catch (kotlinx.coroutines.TimeoutCancellationException ex) {
                    logger.warn("B站视频动态消息mirai发送超时");
                }

                //每个群之间间隔半秒意思一下
                Thread.sleep(500);
            }
            Thread.sleep(1000L * 5);
        } catch (Exception ioEx) {
            logger.error("BilibiliService groupDynamicSvrPush error,dynamicSvrCardsInfo:{}", JSONObject.toJSONString(dynamicSvrCardsInfo), ioEx);
        }
    }

    public MessageChain parseDynamicSvrBody(BilibiliDynamicSvrCardsInfo dynamicSvrCardsInfo) throws IOException {
        MessageChain result = null;

        BilibiliDynamicSvrCardsDescInfo descInfo = dynamicSvrCardsInfo.getDesc();
        BilibiliDynamicSvrCardInfo cardInfo = JSONObject.parseObject(dynamicSvrCardsInfo.getCard(), BilibiliDynamicSvrCardInfo.class);
        //视频动态类型
        Integer type = descInfo.getType();

        if (type == 8) {
            //普通视频动态处理

            //用户信息
            BilibiliUserProfileInfoInfo userInfo = descInfo.getUserProfile().getInfo();
            //视频图片
            Image videoImgInfo = parseUserPic(cardInfo.getPic(), null, null);
            //头像
            Image userImgInfo = parseUserPic(userInfo.getFace(), 50, 50);

            //转化为messageChain
            result = parseDynamicSvrMessageChain(cardInfo.getTitle(),
                    videoImgInfo,
                    cardInfo.getDesc(),
                    userImgInfo,
                    userInfo.getUname(),
                    userInfo.getUid(),
                    new Date(descInfo.getTimestamp() * 1000L),
                    cardInfo.getShortLink());
        } else if (type == 512) {
            //追番视频动态
            BilibiliDynamicSvrCardApiSeasonInfo apiSeasonInfo = cardInfo.getApiSeasonInfo();

            //视频图片
            Image videoImgInfo = parseUserPic(cardInfo.getCover(), null, null);
            //头像
            Image userImgInfo = parseUserPic(apiSeasonInfo.getCover(), 50, 50);

            //转化为messageChain
            result = parseDynamicSvrMessageChain(cardInfo.getNewDesc(),
                    videoImgInfo,
                    null,
                    userImgInfo,
                    apiSeasonInfo.getTitle(),
                    apiSeasonInfo.getSeasonId(),
                    new Date(descInfo.getTimestamp() * 1000L),
                    cardInfo.getUrl());
        }

        return result;
    }


    private MessageChain parseDynamicSvrMessageChain(String title,
                                                     Image videoImgInfo,
                                                     String desc,
                                                     Image userImgInfo,
                                                     String uName,
                                                     Long uid,
                                                     Date createTime,
                                                     String link) {
        MessageChain result = MessageUtils.newChain();
        //正文
        result = result.plus(title);

        //视频图片
        if (null != videoImgInfo) {
            result = result.plus("").plus(videoImgInfo);
        } else {
            result = result.plus("[封面下载失败]");
        }

        //视频简介 最多展示50个字符
        if (StringUtil.isNotEmpty(desc)) {
            if (desc.length() > 50) {
                desc = desc.substring(0, 50);
                desc = desc + "......";
            }
            result = result.plus(desc);
        }

        result = result.plus("\n=======[BiliBili]=======\n");

        //头像
        if (null != userImgInfo) {
            result = result.plus("").plus(userImgInfo);
        } else {
            result = result.plus("[头像下载失败]");
        }

        //up主名称
        result = result.plus("[" + uName + "]\n");
        //uid
        result = result.plus("[" + uid + "]\n");
        //投稿时间
        result = result.plus("[" + DateUtil.toString(createTime) + "]\n");
        //短连接
        result = result.plus("[" + link + "]");

        return result;
    }

    //处理头像图片大小
    private Image parseUserPic(String imgUrl, Integer width, Integer height) throws IOException {
        //先把图片下载下来
        String localImageUrl = ImageUtil.downloadImage(imgUrl, ConstantImage.IMAGE_BILIBILI_SAVE_PATH, null);
        if (StringUtil.isEmpty(localImageUrl)) {
            return null;
        }
        String scaleImgPath = localImageUrl;
        if (null != width && null != height) {
            //压制到指定大小
            scaleImgPath = imageService.thumbnailsOfSize(localImageUrl, width, height);
        }

        //然后上传到服务器，获取imageId
        return rabbitBotService.uploadMiraiImage(scaleImgPath);
    }


    /**
     * 刷新dynamicId
     */
    public void refreshLastDynamicId(String dynamicId) {
        logger.info(String.format("BiliBili DynamicId刷新：[%s]->[%s]", lastDynamicId, dynamicId));
        lastDynamicId = NumberUtil.toLong(dynamicId);
        //覆写lastDynamicId配置
        ConstantCommon.common_config.put("lastDynamicId", dynamicId);
        //更新配置文件
        configService.refreshConfigFile();
    }

}
