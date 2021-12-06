package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.imjad.PixivImjadQRCode;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantQRCode;
import cn.mikulink.rabbitbot.entity.apirequest.imjad.ImjadQRCode;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * create by MikuLink on 2020/3/31 17:17
 * for the Reisen
 * 二维码相关服务
 */
@Service
public class QRCodeService {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeService.class);

    @Autowired
    private ImageService imageService;

    /**
     * 二维码转化接口请求
     *
     * @param text    二维码内容
     * @param logo    二维码中间的logo
     * @param bgColor 背景色
     * @param fgColor 前景色
     * @return 返回的群消息，正常情况下是返回的CQ码
     * @throws IOException 接口请求异常
     */
    public String doQRCodeRequest(String text, String bgColor, String fgColor, String logo) throws IOException {
        //请求接口
        PixivImjadQRCode request = new PixivImjadQRCode();
        request.setText(text);
        if (StringUtil.isNotEmpty(logo)) {
            request.setLogo(logo);
        }
        if (StringUtil.isNotEmpty(bgColor)) {
            request.setBgcolor(bgColor);
        }
        if (StringUtil.isNotEmpty(fgColor)) {
            request.setFgcolor(fgColor);
        }
        request.setLevel("Q");
        request.setSize(500);
        request.doRequest();

        ImjadQRCode imjadQRCode = request.getEntity();
        if (null == imjadQRCode || imjadQRCode.getCode() == null || imjadQRCode.getCode() != 0) {
            //记录api失败日志
            logger.info("二维码请求失败，body:" + request.getBody());
            return ConstantQRCode.QRCODE_FAIL;
        }
        String localUrl = ImageUtil.downloadImage(imjadQRCode.getUrl(), ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "qrcode", null);
        return imageService.scaleForceByLocalImagePath(localUrl);
    }
}
