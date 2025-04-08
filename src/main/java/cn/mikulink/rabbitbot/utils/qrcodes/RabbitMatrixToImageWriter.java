package cn.mikulink.rabbitbot.utils.qrcodes;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * created by MikuNyanya on 2021/12/9 17:41
 * For the Reisen
 * 替代zxing的MatrixToImageWriter
 */
public class RabbitMatrixToImageWriter {
    private static final RabbitMatrixToImageConfig DEFAULT_CONFIG = new RabbitMatrixToImageConfig();

    private RabbitMatrixToImageWriter() {
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        return toBufferedImage(matrix, DEFAULT_CONFIG);
    }

    public static BufferedImage toBufferedImage(BitMatrix matrix, RabbitMatrixToImageConfig config) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, config.getBufferedImageColorModel());
        int colorType = config.getType();
        int onColor = config.getPixelOnColor();
        int offColor = config.getPixelOffColor();
        int onColor1 = config.getOnColor1();
        int onColor2 = config.getOnColor2();
        int[] rowPixels = new int[width];
        BitArray row = new BitArray(width);

        //如果是渐变色，需要计算出从开始到结束，每行需要渐变的r g b数值，然后在每次循环时累加，以达到渐变效果
        double increaseR = 0.0;
        double increaseG = 0.0;
        double increaseB = 0.0;
        if (colorType == 2) {
            Color color1 = new Color(onColor1);
            int color1R = color1.getRed();
            int color1G = color1.getGreen();
            int color1B = color1.getBlue();

            Color color2 = new Color(onColor2);
            int color2R = color2.getRed();
            int color2G = color2.getGreen();
            int color2B = color2.getBlue();

            //以两个颜色的差值，除以行数，以算出每行之间渐变多少
            //先来个简单粗暴的除法试试水，如果两个颜色相近，颜色可能会出现小偏差
            increaseR = (color1R - color2R) / (height * 1.0);
            increaseG = (color1G - color2G) / (height * 1.0);
            increaseB = (color1B - color2B) / (height * 1.0);
        }

        for (int y = 0; y < height; ++y) {
            row = matrix.getRow(y, row);

            //渐变色需要先确定当前行使用什么颜色
            int onColorTemp = onColor;
            if (config.getType() == 2) {
                Color colorTemp = new Color(onColor1);
                //偏移RGB
                int colorR = colorTemp.getRed() - ((int) (increaseR * y));
                if (colorR < 0) colorR = 0;
                if (colorR > 255) colorR = 255;
                int colorG = colorTemp.getGreen() - ((int) (increaseG * y));
                if (colorG < 0) colorG = 0;
                if (colorG > 255) colorG = 255;
                int colorB = colorTemp.getBlue() - ((int) (increaseB * y));
                if (colorB < 0) colorB = 0;
                if (colorB > 255) colorB = 255;

                Color gradientColor = new Color(colorR, colorG, colorB);
                onColorTemp = gradientColor.getRGB();
            }

            for (int x = 0; x < width; ++x) {
                rowPixels[x] = row.get(x) ? onColorTemp : offColor;
            }

            image.setRGB(0, y, width, 1, rowPixels, 0, width);
        }

        return image;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        writeToPath(matrix, format, file.toPath());
    }

    public static void writeToPath(BitMatrix matrix, String format, Path file) throws IOException {
        writeToPath(matrix, format, file, DEFAULT_CONFIG);
    }

    /**
     * @deprecated
     */
    @Deprecated
    public static void writeToFile(BitMatrix matrix, String format, File file, RabbitMatrixToImageConfig config) throws IOException {
        writeToPath(matrix, format, file.toPath(), config);
    }

    public static void writeToPath(BitMatrix matrix, String format, Path file, RabbitMatrixToImageConfig config) throws IOException {
        BufferedImage image = toBufferedImage(matrix, config);
        if (!ImageIO.write(image, format, file.toFile())) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
        writeToStream(matrix, format, stream, DEFAULT_CONFIG);
    }

    public static void writeToStream(BitMatrix matrix, String format, OutputStream stream, RabbitMatrixToImageConfig config) throws IOException {
        BufferedImage image = toBufferedImage(matrix, config);
        if (!ImageIO.write(image, format, stream)) {
            throw new IOException("Could not write an image of format " + format);
        }
    }

}
