package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2020/3/31 19:51
 * for the Reisen
 * <p>
 * 二维码相关
 */
public class ConstantQRCode extends ConstantCommon {
    //提示信息
    public static final String EXPLAIN = "[二维码/qrcode]" +
            "\n.二维码 [前景色] [背景色] [logo] [内容]" +
            "\n内容最大长度300" +
            "\n颜色需要十六进制代码" +
            "\n示例：" +
            "\n.二维码 兔子万岁！" +
            "\n.qrcode #FF69B4 #FFFFF 兔子万岁！";
    public static final Integer QRCODE_CONTEXT_MAX_LENGTH = 300;
    public static final String QRCODE_FAIL = "二维码生成失败";
    public static final String QRCODE_FAIL_OVER_LEN = "二维码生成失败，内容长度最大为300";
    public static final String QRCODE_ERROR = "二维码生成，它挂了";
    public static final String QRCODE_COLOR_ERROR = "二维码颜色参数错误，请使用十六进制颜色代码";
    public static final String QRCODE_LOGO_NOT_IMAGE = "logo这块你得给我一张图";


    //渐变提示信息
    public static final String GRADIENT_EXPLAIN = "[渐变二维码]" +
            "\n.渐变二维码 [顶部颜色] [底部颜色] [logo] [内容]" +
            "\n内容最大长度300" +
            "\n颜色需要十六进制代码" +
            "\n示例：" +
            "\n.渐变二维码 #FF96A0 #FF69B4 兔子万岁！";
    public static final String GRADIENT_QRCODE_COLOR_ERROR = "渐变二维码至少需要两种颜色和二维码内容信息";
}
