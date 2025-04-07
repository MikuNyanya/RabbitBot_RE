package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.other.ZaobGet;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantWeiXin;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * create by MikuLink on 2021/3/3 09:55
 * for the Reisen
 * 微信公众号相关服务
 */
@Service
public class WeiXinAppMsgService {
    private static final Logger logger = LoggerFactory.getLogger(WeiXinAppMsgService.class);

    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 根据配置，从指定的新闻源获取新闻
     */
    public MessageChain getNewsUseSourceConfig() {
        return getZaobNews();
    }

    /**
     * 通过api获取每日新闻
     */
    public MessageChain getZaobNews() {
        MessageChain result = MessageUtils.newChain();
        try {
            ZaobGet request = new ZaobGet();
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
