package cn.mikulink.rabbitbot.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * created by MikuNyanya on 2021/12/6 11:41
 * For the Reisen
 * 二维码工具
 */
public class QRCodeUtil {
    //默认二维码尺寸
    private static final int QRCODE_WIDTH_DEFAULE = 200;
    private static final int QRCODE_HEIGHT_DEFAULE = 200;
    //默认二维码颜色
    private static final Color ONCOLOR_DEFAULT = Color.BLACK;
    private static final Color OFFCOLOR_DEFAULT = Color.WHITE;

    /**
     * 生成默认尺寸，默认颜色二维码
     *
     * @param content 二维码内容
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content) throws WriterException, IOException {
        return createQRCode(content, 0, 0);
    }

    /**
     * 生成指定尺寸，默认颜色二维码
     *
     * @param width   图片长度
     * @param height  图片高度
     * @param content 二维码内容
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, int width, int height) throws WriterException, IOException {
        return createQRCode(content, width, height, null, null);
    }

    /**
     * 生成默认尺寸，指定颜色二维码
     *
     * @param content  二维码内容
     * @param onColor  二维码前景色
     * @param offColor 二维码背景色
     * @return 二维码图片
     * @throws WriterException
     * @throws IOException
     */
    public static BufferedImage createQRCode(String content, Color onColor, Color offColor) throws WriterException, IOException {
        return createQRCode(content, 0, 0, onColor, offColor);
    }

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
    public static BufferedImage createQRCode(String content, int width, int height, Color onColor, Color offColor) throws WriterException, IOException {
        //尺寸边界校验
        if (width <= 0) {
            width = QRCODE_WIDTH_DEFAULE;
        }
        if (height <= 0) {
            height = QRCODE_HEIGHT_DEFAULE;
        }
        //颜色默认
        if (null == onColor) {
            onColor = ONCOLOR_DEFAULT;
        }
        if (null == offColor) {
            offColor = OFFCOLOR_DEFAULT;
        }

        // 创建位矩阵对象
        BitMatrix bitMatrix = initBitMatrix(content, width, height);
        // 设置位矩阵转图片的参数
        MatrixToImageConfig config = new MatrixToImageConfig(onColor.getRGB(), offColor.getRGB());

        //转化为图片对象
        return MatrixToImageWriter.toBufferedImage(bitMatrix, config);
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
        return createQRCodeByte(content, 0, 0, onColor, offColor);
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
        //颜色默认
        if (null == onColor) {
            onColor = ONCOLOR_DEFAULT;
        }
        if (null == offColor) {
            offColor = OFFCOLOR_DEFAULT;
        }

        // 创建位矩阵对象
        BitMatrix bitMatrix = initBitMatrix(content, width, height);
        // 设置位矩阵转图片的参数
        MatrixToImageConfig config = new MatrixToImageConfig(onColor.getRGB(), offColor.getRGB());
        // 位矩阵对象转流对象
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", os, config);
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
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        //白边 可设置范围为0-10，但仅四个变化0 1(2) 3(4 5 6) 7(8 9 10)
        hints.put(EncodeHintType.MARGIN, 1);
        // 生成图片类型为QRCode
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        // 创建位矩阵对象
        return new MultiFormatWriter().encode(content, format, width, height, hints);
    }
}
