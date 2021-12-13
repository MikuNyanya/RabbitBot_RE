package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.QRCodeUtil;
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
    public void test() {
        try {
//            byte[] b = QRCodeUtil.createQRCode(200, 200, "VPYbo7I0KKto.4@");
//            OutputStream os = new FileOutputStream("E:\\qrtest.png");
//            os.write(b);
//            os.close();

//            BufferedImage qrImg = QRCodeUtil.createQRCode("测试兔子",
//                    300, 300,
//                    new Color(Integer.parseInt("FF69B4", 16)), new Color(Integer.parseInt("FFFFFF", 16)),
//                    "https://i1.hdslb.com/bfs/face/d973f5f34a9b102a9a0d6f216f806c225d66912f.jpg");

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

}
