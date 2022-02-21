package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.entity.MeiPinInfo;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * created by MikuNyanya on 2022/2/21 13:56
 * For the Reisen
 * 由Fall_ark翻译的没品+4chan笑话集
 * http://meipin.im/
 */
@Service
@Slf4j
public class MeiPinService {
    private String url = "http://meipin.im/";
    private String logo_4chan_path = "data/images/other/4chan.png";

    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 获取最后一次发布的没品文章，并转化为消息链
     */
    public MessageChain lastMeiPinMsgChain() throws IOException {
        return this.parseMsg(this.getLastMeipinInfo());
    }


    /**
     * 获取最后一次发布的没品文章信息
     */
    public MeiPinInfo getLastMeipinInfo() throws IOException {
        //由于网站服务器链接很不稳定，所以超时的时候重试一次
        String htmlStr = null;
        try {
            htmlStr = HttpUtil.get(url);
        } catch (SocketTimeoutException timeoutEx) {
            log.warn("获取没品文章，链接超时，即将进行重试");
            htmlStr = HttpUtil.get(url);
        }

        //使用jsoup解析html
        Document document = Jsoup.parse(htmlStr);
        //选择目标节点，类似于JS的选择器
        Elements elements = document.getElementsByTag("article");

        //标题
        String title = elements.get(0).getElementsByTag("a").get(0).text();
        //文章链接
        String url = elements.get(0).getElementsByTag("a").get(0).attr("href");

        return new MeiPinInfo(title, url);
    }

    /**
     * 没品文章转化为消息链
     *
     * @param meiPinInfo 没品文章信息
     */
    public MessageChain parseMsg(MeiPinInfo meiPinInfo) {
        MessageChain result = MessageUtils.newChain();

        //先放上4chan的logo
        Image image = rabbitBotService.uploadMiraiImage(logo_4chan_path);
        result = result.plus("").plus(image).plus("\n")
                //然后是标题，文章链接
                .plus(meiPinInfo.getTitle()).plus("\n")
                .plus(meiPinInfo.getUrl()).plus("\n")
                .plus("===========================\n")
                .plus("转载只图一乐，不代表任何政治立场\n")
                .plus("小盆友们请在有家长陪同的情况下观看\n");

        return result;
    }
}
