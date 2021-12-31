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
import java.awt.geom.Ellipse2D;
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
    public static final int QRCODE_WIDTH_DEFAULE = 300;
    public static final int QRCODE_HEIGHT_DEFAULE = 300;
    //默认二维码颜色
    public static final Color ONCOLOR_DEFAULT = new Color(0xFF000001);
    public static final Color OFFCOLOR_DEFAULT = new Color(0xFFFFFFFF);

    /**
     * 生成一个二维码
     * 只有这么普通了
     *
     * @param content 二维码内容
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content) throws WriterException, IOException {
        return createQRCode(content, QRCODE_WIDTH_DEFAULE, QRCODE_HEIGHT_DEFAULE);
    }

    /**
     * 生成一个二维码
     *
     * @param content 二维码内容
     * @param logoUrl 中心logo
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, String logoUrl) throws WriterException, IOException {
        return createQRCode(content, QRCODE_WIDTH_DEFAULE, QRCODE_HEIGHT_DEFAULE, ONCOLOR_DEFAULT, OFFCOLOR_DEFAULT, logoUrl);
    }

    /**
     * 生成一个二维码
     *
     * @param width   图片长度
     * @param height  图片高度
     * @param content 二维码内容
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, int width, int height) throws WriterException, IOException {
        return createQRCode(content, width, height, ONCOLOR_DEFAULT, OFFCOLOR_DEFAULT, null);
    }

    /**
     * 生成一个二维码图片
     *
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, Color onColor, Color offColor) throws WriterException, IOException {
        return createQRCode(content, QRCODE_WIDTH_DEFAULE, QRCODE_HEIGHT_DEFAULE, onColor, offColor, null);
    }

    /**
     * 生成一个二维码图片
     *
     * @param width    图片长度
     * @param height   图片高度
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @param logoUrl  网络图片链接
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, int width, int height, Color onColor, Color offColor, String logoUrl) throws WriterException, IOException {
        //颜色默认
        //https://www.iteye.com/blog/ququjioulai-2254382
        //如果使用的是默认的空格白0xFFFFFFFF,二维码黑0xFF000000,就会使用BufferedImage.TYPE_BYTE_BINARY类型,即,只有黑白二色的位图
        //这会导致logo也变成黑白的
        //所以前景色改为不是黑色，但跟黑色差不多的颜色
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

        return bufferedImage;
    }

    /**
     * 生成一个渐变色二维码图片
     *
     * @param content      二维码内容
     * @param onColorStart 二维码前景渐变色 开始
     * @param onColorEnd   二维码前景渐变色 结束
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createGradientColorQRCode(String content, Color onColorStart, Color onColorEnd) throws WriterException, IOException {
        return createGradientColorQRCode(content, QRCODE_WIDTH_DEFAULE, QRCODE_HEIGHT_DEFAULE, onColorStart, onColorEnd, OFFCOLOR_DEFAULT, null);
    }

    /**
     * 生成一个渐变色二维码图片
     *
     * @param content      二维码内容
     * @param onColorStart 二维码前景渐变色 开始
     * @param onColorEnd   二维码前景渐变色 结束
     * @param logoUrl      中心logo
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createGradientColorQRCode(String content, Color onColorStart, Color onColorEnd, String logoUrl) throws WriterException, IOException {
        return createGradientColorQRCode(content, QRCODE_WIDTH_DEFAULE, QRCODE_HEIGHT_DEFAULE, onColorStart, onColorEnd, OFFCOLOR_DEFAULT, logoUrl);
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
     * @param logoUrl      中心logo
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createGradientColorQRCode(String content, int width, int height, Color onColorStart, Color onColorEnd, Color offColor, String logoUrl) throws WriterException, IOException {
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

    public static byte[] createQRCodeByte(String content, Color onColor, Color offColor) throws WriterException, IOException {
        return createQRCodeByte(content, 0, 0, onColor, offColor, null);
    }

    public static byte[] createQRCodeByte(String content, Color onColor, Color offColor, String logoPath) throws WriterException, IOException {
        return createQRCodeByte(content, 0, 0, onColor, offColor, logoPath);
    }

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

    public static byte[] createGradientColorQRCodeByte(String content, Color onColorStart, Color offColorEnd, String logoPath) throws WriterException, IOException {
        return createGradientColorQRCodeByte(content, 0, 0, onColorStart, offColorEnd, logoPath);
    }

    /**
     * 生成一个二维码图片
     *
     * @param width        图片长度
     * @param height       图片高度
     * @param content      二维码内容
     * @param onColorStart 二维码前景色渐变开始
     * @param offColorEnd  二维码背景色渐变结束
     * @param logoPath     二维码中心logo路径
     * @return 二维码图片 byte[]
     * @throws WriterException
     * @throws IOException
     */
    public static byte[] createGradientColorQRCodeByte(String content, int width, int height, Color onColorStart, Color offColorEnd, String logoPath) throws WriterException, IOException {
        BufferedImage bufferedImage = createGradientColorQRCode(content, width, height, onColorStart, offColorEnd, null, logoPath);

        // 位矩阵对象转流对象
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", os);
        return os.toByteArray();
    }

    //生成BitMatrix
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
    private static BufferedImage qrLogoDraw(BufferedImage qrImg, BufferedImage logoImg, Color bgColor) {
        //读取二维码图片，并构建绘图对象
        Graphics2D g2 = qrImg.createGraphics();

        int matrixWidth = qrImg.getWidth();
        int matrixHeigh = qrImg.getHeight();

        //绘制坐标 使其居中
        int x = matrixWidth / 5 * 2;
        int y = matrixHeigh / 5 * 2;

        //logo尺寸
        int logoWidth = matrixWidth / 5;
        int logoHeigh = matrixHeigh / 5;

        //从角落圆弧的宽度
        int arcw = 10;
        //从角落圆弧的高度
        int arch = 10;

        //圆形logo
//        arcw = logoWidth;
//        arch = logoHeigh;
//        Ellipse2D.Double shape = new Ellipse2D.Double(x, y, arcw, arch);
//        g2.setClip(shape);
        //不使用锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //开始绘制图片
        g2.drawImage(logoImg, x, y, logoWidth, logoHeigh, null);//绘制
        //开始绘制周围边框
        //默认为白色周边背景
        if (null == bgColor) {
            bgColor = OFFCOLOR_DEFAULT;
        }
        //白色边框宽度
        int roundW = 8;

        BasicStroke stroke = new BasicStroke(roundW, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke);// 设置笔画对象
        //指定弧度的圆角矩形
        RoundRectangle2D.Float round = new RoundRectangle2D.Float(x, y, logoWidth, logoHeigh, arcw, arch);
        g2.setColor(bgColor);
        g2.draw(round);// 绘制圆弧矩形

        //绘制logo灰色线条边框
        //线条粗细
        int borderWidth = 1;
        //线条修正数值，由于存在周边留白，想要使线条贴着logo，需要根据上线的白色边框进行修正
        int borderFix = roundW / 2;

        BasicStroke stroke2 = new BasicStroke(borderWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2.setStroke(stroke2);// 设置笔画对象
        RoundRectangle2D.Float border = new RoundRectangle2D.Float(x + borderFix, y + borderFix, logoWidth - (borderFix * 2), logoHeigh - (borderFix * 2), arcw, arch);
        g2.setColor(new Color(128, 128, 128));
        g2.draw(border);// 绘制圆弧矩形

        g2.dispose();
        qrImg.flush();
        return qrImg;
    }
}
