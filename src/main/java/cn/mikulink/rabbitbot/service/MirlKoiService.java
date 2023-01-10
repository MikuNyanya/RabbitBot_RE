package cn.mikulink.rabbitbot.service;


import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author MikuLink
 * @date 2022/9/25 0:17
 * for the Reisen
 */
@Service
public class MirlKoiService {
    private static final Logger logger = LoggerFactory.getLogger(MirlKoiService.class);

    @Value("${mirlkoi.setu.url:}")
    private String mirlkoiSetuUrl;

    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 获取一张随机色图
     *
     * @return 下载后的本地路径
     * @throws IOException 异常
     */
    public List<String> downloadASetu(Integer count) throws IOException {
        String body = null;
        String url = mirlkoiSetuUrl + "&num=" + count;
        if (mirlkoiSetuUrl.startsWith("https")) {
            byte[] response = HttpsUtil.doGet(url);
            body = new String(response);
        } else {
            body = HttpUtil.get(url);
        }

        Map<String, String> bodyMap = JSONObject.parseObject(body, HashMap.class);
        List<String> picUrls = JSONObject.parseArray(String.valueOf(bodyMap.get("pic")), String.class);
        List<String> returnUrls = new ArrayList<>();
        HashMap<String, String> header = new HashMap<>();
        header.put("referer", "https://weibo.com/");
        for (String picUrl : picUrls) {
            returnUrls.add(ImageUtil.downloadImage(header, picUrl, "data/images/mirlkoi", System.currentTimeMillis() + ".jpg"));
        }
        return returnUrls;
    }

    public void sendRandomSetu(Contact subject) {
        this.sendRandomSetu(subject, 1);
    }

    /**
     * 发送一张随机色图
     * 异常时重试一次
     *
     * @param subject 目标对象
     */
    public void sendRandomSetu(Contact subject, Integer count) {
        try {
            //默认为1张
            if (null == count) {
                count = 1;
            }
            List<String> pathStrs = downloadASetu(count);
            for (String pathStr : pathStrs) {
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
            logger.error("MirlKoi 色图发送异常", ex);
        }
    }
}
