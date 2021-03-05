package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.loliconApp.LoliconAppImageGet;
import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustTagGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.apirequest.lolicon.LoliconDataEntity;
import cn.mikulink.rabbitbot.entity.apirequest.lolicon.LoliconImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoliconAppImageService {
    private static final Logger logger = LoggerFactory.getLogger(LoliconAppImageService.class);

    @Value("${lolicon.token}")
    private String loliconKey;

    @Autowired
    private RabbitBotService rabbitBotService;

    public LoliconImageInfo getPixivIllust(String tags, Boolean r18 ) throws  IOException {
        if (StringUtil.isEmpty(loliconKey)) {
            logger.warn("LoliconAappkey为空！");
            return null;
        }
        //1.查询这个tag下的总结果
        LoliconAppImageGet request = new LoliconAppImageGet();
        request.setAccessToken(loliconKey);
        request.setKeyword(tags);
        if(r18){
            request.setR18(1);
        }

        //查询
        request.doRequest();
        //todo 如果真要做评分，直接返回之前查询的元素即可，没必要再次请求一次
        return request.getLoliconImageInfo();
    }


    /**
     * 接口返回的图片信息拼装为群消息
     * 重载 为识图结果提供相似度参数
     *
     * @param imageInfo  接口返回对象

     * @return 群消息
     * @throws IOException api异常
     */
    public MessageChain parsePixivImgInfoByApiInfo(LoliconImageInfo imageInfo) throws IOException {

        //下载
        LoliconDataEntity pivixData = imageInfo.getData().get(0);

        Image miraiImage = rabbitBotService.uploadMiraiImage(this.parseImages(pivixData.getUrl()));
        MessageChain result = rabbitBotService.parseMsgChainByImg(miraiImage);

        StringBuilder resultStr = new StringBuilder();


        resultStr.append("\n[P站id] ").append(pivixData.getUid());
        resultStr.append("\n[标题] ").append(pivixData.getTitle());
        resultStr.append("\n[作者] ").append(pivixData.getAuthor());

        result = result.plus(resultStr.toString());
        return result;
    }

    //下载图片到本地
    private String  parseImages(String pixivUrl) throws IOException {

        return ImageUtil.downloadImage( pixivUrl, ConstantImage.IMAGE_Pixiv_SAVE_PATH , null);
    }
}
