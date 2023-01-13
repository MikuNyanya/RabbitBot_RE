package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.qrcodes.QRCodeUtil;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * created by MikuNyanya on 2021/12/6 11:44
 * For the Reisen
 */
public class QRCodeUtilTest {

    @Test
    public void createQr() {
        try {
            BufferedImage qrImg = QRCodeUtil.createGradientColorQRCode("https://github.com/MikuNyanya/RabbitBot_RE",
                    500, 500,
                    new Color(Integer.parseInt("FF96A0", 16)), new Color(Integer.parseInt("FF69B4", 16)),
                    new Color(Integer.parseInt("FFFFFF", 16)),
                    "https://i1.hdslb.com/bfs/face/d973f5f34a9b102a9a0d6f216f806c225d66912f.jpg");

            ImageIO.write(qrImg, "png", new File("E:\\qrImage.png"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void decoderQr() {
        try {
            String filePath = "E:\\qrcode.png";
            String qrStr = QRCodeUtil.decoder(filePath);
            System.out.println("解码结果文本:" + qrStr);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
