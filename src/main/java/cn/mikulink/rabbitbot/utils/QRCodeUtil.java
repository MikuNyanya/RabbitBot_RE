package cn.mikulink.rabbitbot.utils;

import cn.mikulink.rabbitbot.entity.overrides.RabbitMatrixToImageConfig;
import cn.mikulink.rabbitbot.entity.overrides.RabbitMatrixToImageWriter;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * created by MikuNyanya on 2021/12/6 11:41
 * For the Reisen
 * 二维码工具
 */
public class QRCodeUtil {
    //默认二维码尺寸
    private static final int QRCODE_WIDTH_DEFAULE = 300;
    private static final int QRCODE_HEIGHT_DEFAULE = 300;
    //默认二维码颜色
    private static final Color ONCOLOR_DEFAULT = new Color(0xFF000001);
    private static final Color OFFCOLOR_DEFAULT = new Color(0xFFFFFFFF);

    /**
     * 生成一个二维码图片
     *
     * @param width    图片长度
     * @param height   图片高度
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, int width, int height, Color onColor, Color offColor, String logoUrl) throws WriterException, IOException {
        //尺寸边界校验
        if (width <= 0) {
            width = QRCODE_WIDTH_DEFAULE;
        }
        if (height <= 0) {
            height = QRCODE_HEIGHT_DEFAULE;
        }
        //颜色默认
        //https://www.iteye.com/blog/ququjioulai-2254382
        //如果使用的是默认的空格白0xFFFFFFFF,二维码黑0xFF000000,就会使用BufferedImage.TYPE_BYTE_BINARY类型,即,只有黑白二色的位图
        //这会导致logo也变成黑白的
        //所以默认背景色改为不是白色，但跟白色差不多的颜色
        if (null == onColor) {
            onColor = ONCOLOR_DEFAULT;
        }
        if (null == offColor) {
            offColor = OFFCOLOR_DEFAULT;
        }

        // 创建位矩阵对象
        BitMatrix bitMatrix = initBitMatrix(content, width, height);
        // 设置位矩阵转图片的参数
        RabbitMatrixToImageConfig config = new RabbitMatrixToImageConfig(onColor.getRGB(), offColor.getRGB());

        //转化为图片对象
        BufferedImage bufferedImage = RabbitMatrixToImageWriter.toBufferedImage(bitMatrix, config);

        //绘制中心logo
        if (StringUtil.isNotEmpty(logoUrl)) {
            //从网络连接读取图片
            URL url = new URL(logoUrl);
            InputStream inputStream = url.openStream();
            BufferedImage logoImg = ImageIO.read(inputStream);
            //绘制logo
            qrLogoDraw(bufferedImage, logoImg, offColor);
        }
//        if (FileUtil.exists(logoPath)) {
//        //读取logo
//        BufferedImage logo = ImageIO.read(logoFile);
//            qrLogoDraw(bufferedImage, new File(logoPath));
//    }


        return bufferedImage;
    }

    /**
     * 生成一个渐变色二维码图片
     *
     * @param width        图片长度
     * @param height       图片高度
     * @param content      二维码内容
     * @param onColorStart 二维码前景渐变色 开始
     * @param onColorEnd   二维码前景渐变色 结束
     * @param offColor     二维码背景色
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createGradientColorQRCode(String content, int width, int height, Color onColorStart, Color onColorEnd, Color offColor, String logoUrl) throws WriterException, IOException {
        //尺寸边界校验
        if (width <= 0) {
            width = QRCODE_WIDTH_DEFAULE;
        }
        if (height <= 0) {
            height = QRCODE_HEIGHT_DEFAULE;
        }

        if (null == onColorStart) {
            onColorStart = ONCOLOR_DEFAULT;
        }
        if (null == onColorEnd) {
            onColorEnd = ONCOLOR_DEFAULT;
        }
        if (null == offColor) {
            offColor = OFFCOLOR_DEFAULT;
        }

        // 创建位矩阵对象
        BitMatrix bitMatrix = initBitMatrix(content, width, height);
        // 设置位矩阵转图片的参数
        RabbitMatrixToImageConfig config = new RabbitMatrixToImageConfig(ONCOLOR_DEFAULT.getRGB(), offColor.getRGB());
        config.initConfigGradientColor(offColor.getRGB(), onColorStart.getRGB(), onColorEnd.getRGB());

        //转化为图片对象
        BufferedImage bufferedImage = RabbitMatrixToImageWriter.toBufferedImage(bitMatrix, config);

        //绘制中心logo
        if (StringUtil.isNotEmpty(logoUrl)) {
            //从网络连接读取图片
            URL url = new URL(logoUrl);
            InputStream inputStream = url.openStream();
            BufferedImage logoImg = ImageIO.read(inputStream);
            //绘制logo
            qrLogoDraw(bufferedImage, logoImg, offColor);
        }

        return bufferedImage;
    }

    /**
     * 生成默认尺寸，指定颜色二维码
     *
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @return 二维码图片 byte[]
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCodeByte(String content, Color onColor, Color offColor) throws WriterException, IOException {
        return createQRCodeByte(content, 0, 0, onColor, offColor, null);
    }

    /**
     * 生成默认尺寸，指定颜色二维码
     *
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @param logoPath 二维码中心logo路径
     * @return 二维码图片 byte[]
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCodeByte(String content, Color onColor, Color offColor, String logoPath) throws WriterException, IOException {
        return createQRCodeByte(content, 0, 0, onColor, offColor, logoPath);
    }

    /**
     * 生成一个二维码图片
     *
     * @param width    图片长度
     * @param height   图片高度
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @return 二维码图片 byte[]
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCodeByte(String content, int width, int height, Color onColor, Color offColor) throws WriterException, IOException {
        return createQRCodeByte(content, width, height, onColor, offColor, null);
    }

    /**
     * 生成一个二维码图片
     *
     * @param width    图片长度
     * @param height   图片高度
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @param logoPath 二维码中心logo路径
     * @return 二维码图片 byte[]
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createQRCodeByte(String content, int width, int height, Color onColor, Color offColor, String logoPath) throws WriterException, IOException {
        BufferedImage bufferedImage = createQRCode(content, width, height, onColor, offColor, logoPath);

        // 位矩阵对象转流对象
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        return os.toByteArray();
    }

    private static BitMatrix initBitMatrix(String content, int width, int height) throws WriterException {
        //尺寸边界校验
        if (width <= 0) {
            width = QRCODE_WIDTH_DEFAULE;
        }
        if (height <= 0) {
            height = QRCODE_HEIGHT_DEFAULE;
        }

        // 二维码基本参数设置
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 设置编码字符集utf-8
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 设置纠错等级L/M/Q/H,纠错等级越高越不易识别，最高等级H
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
        //白边 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10)
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        // 创建位矩阵对象
        return new MultiFormatWriter().encode(content, format, width, height, hints);
    }

    //绘制二维码中间的logo
    private static BufferedImage qrLogoDraw(BufferedImage qrImg, BufferedImage logoImg) {
        return qrLogoDraw(qrImg, logoImg, OFFCOLOR_DEFAULT);
    }

    //绘制二维码中间的logo
    private static BufferedImage qrLogoDraw(BufferedImage qrImg, BufferedImage logoImg, Color bgColor) {
        //读取二维码图片，并构建绘图对象
        Graphics2D g2 = qrImg.createGraphics();

        int matrixWidth = qrImg.getWidth();
        int matrixHeigh = qrImg.getHeight();

        //开始绘制图片
        g2.drawImage(logoImg, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);//绘制
        BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象

        //开始绘制周围边框
        //从角落圆弧的宽度
        int arcw = 10;
        //从角落圆弧的高度
        int arch = 10;

        //默认为白色周边背景
        if (null == bgColor) {
            bgColor = OFFCOLOR_DEFAULT;
        }
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, arcw, arch);
        g2.setColor(bgColor);
        g2.draw(round);// 绘制圆弧矩形

        //绘制logo灰色线条边框
        BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2, matrixWidth / 5 - 4, matrixHeigh / 5 - 4, arcw, arch);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(round2);// 绘制圆弧矩形

        g2.dispose();
        qrImg.flush();
        return qrImg;
    }
}
