package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.soyiji.SoyijiGet;
import cn.mikulink.rabbitbot.apirequest.weixin.WeixinAppMsgGet;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantWeiXin;
import cn.mikulink.rabbitbot.entity.apirequest.weixin.WeiXinAppMsgInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * create by MikuLink on 2021/3/3 09:55
 * for the Reisen
 * 微信公众号相关服务
 */
@Service
public class WeiXinAppMsgService {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinAppMsgService.class);

    private String weiXinAppMsgToken = null;
    private String weiXinAppMsgCookie = null;

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ConfigService configService;

    /**
     * 获取今日简报
     *
     * @return 今日简报公众号文章信息
     * @throws IOException     接口请求异常
     * @throws RabbitException 业务异常
     */
    public WeiXinAppMsgInfo getNewsTodayMsg() throws IOException, RabbitException {
        //检查授权码
        tokenCheck();

        if (StringUtil.isEmpty(weiXinAppMsgToken) || StringUtil.isEmpty(weiXinAppMsgCookie)) {
            throw new RabbitException(ConstantWeiXin.NO_ACCESSTOKEN);
        }

        WeixinAppMsgGet request = new WeixinAppMsgGet();
        //易即今日的公众号id
        request.setFakeid("MzI4NzcwNjY4NQ==");
        request.setAccessToken(weiXinAppMsgToken);
        request.setCookie(weiXinAppMsgCookie);

        request.doRequest();
        List<WeiXinAppMsgInfo> list = request.getMsgList();

        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        //找到第一个标题包含"今日简报"的即可
        for (WeiXinAppMsgInfo info : list) {
            if (null == info || StringUtil.isEmpty(info.getTitle())) {
                continue;
            }
            if (info.getTitle().contains("今日简报")) {
                return info;
            }
        }
        return null;
    }

    /**
     * 格式化今日简报消息
     *
     * @param weiXinAppMsgInfo 从api获取的今日简报信息对象
     * @return 转化后的今日简报消息链
     * @throws IOException        get请求异常
     * @throws RabbitApiException 业务异常
     */
    public MessageChain parseNewsToday(WeiXinAppMsgInfo weiXinAppMsgInfo) throws IOException, RabbitApiException {
        MessageChain result = MessageUtils.newChain();
        //获取文章主体链接
        String url = weiXinAppMsgInfo.getLink();
        //拿到的是http，需要更换为https
        url = url.replace("http", "https");

        //请求链接，拿到网页图片列表里的第一张图片作为内容返回
        byte[] responseBytes = HttpsUtil.doGet(url);
        String htmlStr = new String(responseBytes);
        //使用jsoup解析html
        Document document = Jsoup.parse(htmlStr);
        //选择目标节点，类似于JS的选择器
        Element element = document.getElementById("img_list");
        if (CollectionUtil.isEmpty(element.childNodes())) {
            throw new RabbitApiException(ConstantWeiXin.WEIXIN_APPMSG_PARSE_FAIL);
        }
        String imageUrl = element.getElementsByAttribute("src").get(0).attr("src");

        //下载图片
        String fileName = imageUrl.substring(imageUrl.indexOf("mmbiz_jpg/") + "mmbiz_jpg/".length(), imageUrl.lastIndexOf("/")) + ".jpg";
        String localUrl = ImageUtil.downloadImage(imageUrl, ConstantImage.IMAGE_WEIXIN_SAVE_PATH, fileName);
        if (StringUtil.isEmpty(localUrl)) {
            throw new RabbitApiException(ConstantWeiXin.WEIXIN_IMAGE_DOWNLOAD_FAIL);
        }

        result = result.plus(rabbitBotService.uploadMiraiImage(localUrl));
        return result;
    }

    /**
     * 刷新cookie有效期
     * 使用当前授权调用一次接口即可
     */
    public void refreshCookie() {
        tokenCheck();
        try {
            WeixinAppMsgGet request = new WeixinAppMsgGet();
            //易即今日的公众号id
            request.setFakeid("MzI4NzcwNjY4NQ==");
            request.setAccessToken(weiXinAppMsgToken);
            request.setCookie(weiXinAppMsgCookie);
            request.doRequest();

            boolean isInvalidSession = request.isInvalidSessionError();
            //刷新失败私聊通知到最高权限账号
            if (isInvalidSession) {
                MessageChain messageChain = MessageUtils.newChain();
                messageChain = messageChain.plus("微信公众平台cookie刷新失败:" + request.getBody());
                rabbitBotService.sendMasterMessage(messageChain);
            }
            logger.info("微信cookie刷新{}", isInvalidSession ? "失败" : "成功");
        } catch (Exception ex) {
            //异常只记录不处理，这个业务不重要
            logger.warn("微信cookie刷新失败!", ex);
        }
    }

    /**
     * 覆盖当前授权和cookie
     *
     * @param token  微信公众平台token
     * @param cookie 微信公众平台cookie
     */
    public void reTokenCookie(String token, String cookie) {
        if (StringUtil.isNotEmpty(token)) {
            this.weiXinAppMsgToken = token;
        }
        if (StringUtil.isNotEmpty(cookie)) {
            this.weiXinAppMsgCookie = cookie;
        }
        //覆写SINCEID配置
        ConstantCommon.common_config.put("weixinAppmsgToken", token);
        ConstantCommon.common_config.put("weixinAppmsgCookie", cookie);
        //更新配置文件
        configService.refreshConfigFile();
    }

    private void tokenCheck() {
        //检查授权码
        if (StringUtil.isEmpty(weiXinAppMsgToken)) {
            weiXinAppMsgToken = ConstantCommon.common_config.get("weixinAppmsgToken");
        }
        if (StringUtil.isEmpty(weiXinAppMsgCookie)) {
            weiXinAppMsgCookie = ConstantCommon.common_config.get("weixinAppmsgCookie");
        }
    }

    /**
     * 通过api获取每日新闻
     */
    public MessageChain getSoyijiNews() {
        MessageChain result = MessageUtils.newChain();
        try {
            SoyijiGet request = new SoyijiGet();
            request.doRequest();
            String imageUrl = request.getImageUrl();

            //下载图片
            String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            //下载图片
            String localUrl = ImageUtil.downloadImage(null, imageUrl, ConstantImage.IMAGE_WEIXIN_SAVE_PATH, fileName, null);
            if (StringUtil.isEmpty(localUrl)) {
                throw new RabbitApiException(ConstantWeiXin.WEIXIN_IMAGE_DOWNLOAD_FAIL);
            }
            result = result.plus(rabbitBotService.uploadMiraiImage(localUrl));
        } catch (Exception ex) {
            logger.error("每日新闻图片获取异常", ex);
        }
        return result;
    }
}
