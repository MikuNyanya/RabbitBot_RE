package cn.mikulink.rabbitbot.utils.qrcodes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图像二值化，灰度处理，开运算
 * 来源网络 https://blog.csdn.net/weixin_43919656/article/details/121724681
 */
public class QRImageOptimizationUtil {

    // 阈值0-255
    public static int YZ = 150;

    /**
     * 图像二值化处理
     *
     * @param filePath       要处理的图片路径
     * @param fileOutputPath 处理后的图片输出路径
     */
    public static void binarization(String filePath, String fileOutputPath) throws IOException {
        File file = new File(filePath);
        BufferedImage bi = ImageIO.read(file);
        // 获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];

        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// 该点的灰度值
            }
        }

        // 构造一个类型为预定义图像类型,BufferedImage
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        // 和预先设置的阈值大小进行比较，大的就显示为255即白色，小的就显示为0即黑色
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (getGray(arr, i, j, w, h) > YZ) {
                    int white = new Color(255, 255, 255).getRGB();
                    bufferedImage.setRGB(i, j, white);
                } else {
                    int black = new Color(0, 0, 0).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }
            }

        }
        ImageIO.write(bufferedImage, "jpg", new File(fileOutputPath));
    }

    public static BufferedImage binarizationToBufferedImage(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedImage bi = ImageIO.read(file);
        return binarizationToBufferedImage(bi);
    }

    /**
     * 图像二值化处理
     * 仅返回BufferedImage对象
     *
     * @param bi 要处理的图片对象
     */
    public static BufferedImage binarizationToBufferedImage(BufferedImage bi) {
        // 获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];

        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// 该点的灰度值
            }
        }

        // 构造一个类型为预定义图像类型,BufferedImage
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);

        // 和预先设置的阈值大小进行比较，大的就显示为255即白色，小的就显示为0即黑色
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (getGray(arr, i, j, w, h) > YZ) {
                    int white = new Color(255, 255, 255).getRGB();
                    bufferedImage.setRGB(i, j, white);
                } else {
                    int black = new Color(0, 0, 0).getRGB();
                    bufferedImage.setRGB(i, j, black);
                }
            }
        }
        return bufferedImage;
    }

    /**
     * 图像的灰度处理
     * 利用浮点算法：Gray = R*0.3 + G*0.59 + B*0.11;
     *
     * @param rgb 该点的RGB值
     * @return 返回处理后的灰度值
     */
    private static int getImageGray(int rgb) {
        String argb = Integer.toHexString(rgb);// 将十进制的颜色值转为十六进制
        // argb分别代表透明,红,绿,蓝 分别占16进制2位
        int r = Integer.parseInt(argb.substring(2, 4), 16);// 后面参数为使用进制
        int g = Integer.parseInt(argb.substring(4, 6), 16);
        int b = Integer.parseInt(argb.substring(6, 8), 16);
        int gray = (int) (r * 0.28 + g * 0.95 + b * 0.11);
        return gray;
    }

    /**
     * 自己加周围8个灰度值再除以9，算出其相对灰度值
     *
     * @param gray
     * @param x    要计算灰度的点的横坐标
     * @param y    要计算灰度的点的纵坐标
     * @param w    图像的宽度
     * @param h    图像的高度
     * @return
     */
    public static int getGray(int gray[][], int x, int y, int w, int h) {
        int rs = gray[x][y] + (x == 0 ? 255 : gray[x - 1][y]) + (x == 0 || y == 0 ? 255 : gray[x - 1][y - 1])
                + (x == 0 || y == h - 1 ? 255 : gray[x - 1][y + 1]) + (y == 0 ? 255 : gray[x][y - 1])
                + (y == h - 1 ? 255 : gray[x][y + 1]) + (x == w - 1 ? 255 : gray[x + 1][y])
                + (x == w - 1 || y == 0 ? 255 : gray[x + 1][y - 1])
                + (x == w - 1 || y == h - 1 ? 255 : gray[x + 1][y + 1]);
        return rs / 9;
    }

    /**
     * 二值化后的图像的开运算：先腐蚀再膨胀（用于去除图像的小黑点）
     *
     * @param filePath       要处理的图片路径
     * @param fileOutputPath 处理后的图片输出路径
     * @throws IOException
     */
    public static void opening(String filePath, String fileOutputPath) throws IOException {
        File file = new File(filePath);
        BufferedImage bi = ImageIO.read(file);
        // 获取当前图片的高,宽,ARGB
        int h = bi.getHeight();
        int w = bi.getWidth();
        int arr[][] = new int[w][h];
        // 获取图片每一像素点的灰度值
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // getRGB()返回默认的RGB颜色模型(十进制)
                arr[i][j] = getImageGray(bi.getRGB(i, j));// 该点的灰度值
            }
        }

        int black = new Color(0, 0, 0).getRGB();
        int white = new Color(255, 255, 255).getRGB();
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_BINARY);
        // 临时存储腐蚀后的各个点的亮度
        int temp[][] = new int[w][h];
        // 1.先进行腐蚀操作
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                /*
                 * 为0表示改点和周围8个点都是黑，则该点腐蚀操作后为黑
                 * 由于公司图片态模糊，完全达到9个点全为黑的点太少，最后效果很差，故改为了小于30
                 * （写30的原因是，当只有一个点为白，即总共255，调用getGray方法后得到255/9 = 28）
                 */
                if (getGray(arr, i, j, w, h) < 30) {
                    temp[i][j] = 0;
                } else {
                    temp[i][j] = 255;
                }
            }
        }

        // 2.再进行膨胀操作
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                bufferedImage.setRGB(i, j, white);
            }
        }
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                // 为0表示改点和周围8个点都是黑，则该点腐蚀操作后为黑
                if (temp[i][j] == 0) {
                    bufferedImage.setRGB(i, j, black);
                    if (i > 0) {
                        bufferedImage.setRGB(i - 1, j, black);
                    }
                    if (j > 0) {
                        bufferedImage.setRGB(i, j - 1, black);
                    }
                    if (i > 0 && j > 0) {
                        bufferedImage.setRGB(i - 1, j - 1, black);
                    }
                    if (j < h - 1) {
                        bufferedImage.setRGB(i, j + 1, black);
                    }
                    if (i < w - 1) {
                        bufferedImage.setRGB(i + 1, j, black);
                    }
                    if (i < w - 1 && j > 0) {
                        bufferedImage.setRGB(i + 1, j - 1, black);
                    }
                    if (i < w - 1 && j < h - 1) {
                        bufferedImage.setRGB(i + 1, j + 1, black);
                    }
                    if (i > 0 && j < h - 1) {
                        bufferedImage.setRGB(i - 1, j + 1, black);
                    }
                }
            }
        }

        ImageIO.write(bufferedImage, "jpeg", new File(fileOutputPath));
    }
}
