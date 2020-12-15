package cn.mikulink.constant;

/**
 * create by MikuLink on 2020/3/31 19:51
 * for the Reisen
 * <p>
 * 二维码相关
 */
public class ConstantQRCode extends ConstantCommon {
    //提示信息
    public static final String EXPLAIN = "[二维码/qrcode]" +
            "\n.二维码 [内容] [背景色] [前景色] [logo]" +
            "\n颜色需要十六进制代码，logo需要在50x50像素左右大小" +
            "\n示例：" +
            "\n.二维码 兔子万岁！" +
            "\n.qrcode 兔子万岁！ #D8BFD8 #FF69B4";
    public static String QRCODE_API_FAIL = "二维码转换接口挂了=A=";
    public static String QRCODE_ERROR = "二维码生成，它挂了";
    public static final String QRCODE_LOGO_NOT_IMAGE = "logo这块你得给我一张图";

}
