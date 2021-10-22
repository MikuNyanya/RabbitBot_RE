package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * created by MikuNyanya on 2021/10/22 14:15
 * For the Reisen
 * MirlKoi相关
 */
@Service
public class MirlKoiService {
    private static final Logger logger = LoggerFactory.getLogger(MirlKoiService.class);

    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 获取一张随机色图
     *
     * @return 下载后的本地路径
     * @throws IOException 异常
     */
    public String downloadASetu() throws IOException {
        return ImageUtil.downloadImage("https://iw233.cn/API/Ghs.php", "data/images/mirlkoi", System.currentTimeMillis() + ".jpg");
    }

    public void sendRandomSetu(Contact subject) {
        this.sendRandomSetu(subject, 1);
    }

    public void sendRandomSetu(Contact subject, Integer count) {
        this.sendRandomSetu(subject, count, null);
    }

    /**
     * 发送一张随机色图
     * 异常时重试一次
     *
     * @param subject 目标对象
     * @param reSend  重复调用 为空则为正常调用 1.为重复调用
     */
    public void sendRandomSetu(Contact subject, Integer count, Integer reSend) {
        try {
            //默认为1张
            if (null == count) {
                count = 1;
            }

            for (int i = 0; i < count; i++) {
                String pathStr = downloadASetu();
                if (StringUtil.isNotEmpty(pathStr)) {
                    MessageChain messageChain = rabbitBotService.parseMsgChainByLocalImgs(pathStr);
                    if (null != messageChain) {
                        subject.sendMessage(messageChain);
                    }
                }

                //间隔半秒
                Thread.sleep(500);
            }
        } catch (Exception ex) {
            logger.error("MirlKoi 色图发送异常，reSend:{}", reSend, ex);
            //异常时重试一次
            if (null == reSend) {
                this.sendRandomSetu(subject, count, 1);
            }
        }
    }
}
