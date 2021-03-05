package cn.mikulink.rabbitbot.service;


import cn.mikulink.rabbitbot.apirequest.stock.StockGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;

import cn.mikulink.rabbitbot.utils.ImageUtil;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);
    @Autowired
    private RabbitBotService rabbitBotService;


    public MessageChain parseStockImgInfo( String code ) throws IOException {

        StockGet request = new StockGet(code);
        request.packageUrl();
        Image miraiImage = rabbitBotService.uploadMiraiImage(
                this.parseImages(request.getSendUrl(),request.getCode()+".jpg"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rabbitBotService.parseMsgChainByImg(miraiImage);

    }

    private String  parseImages(String pixivUrl,String fileName) throws IOException {
        return ImageUtil.downloadImage( pixivUrl, ConstantImage.IMAGE_STOCK_SAVE_PATH ,
                fileName);
    }

}
