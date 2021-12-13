package cn.mikulink.rabbitbot.entity.overrides;

import lombok.Getter;
import lombok.Setter;

/**
 * created by MikuNyanya on 2021/12/9 17:47
 * For the Reisen
 * 替代zxing中MatrixToImageConfig
 */
public class RabbitMatrixToImageConfig {
    public static final int BLACK = -16777216;
    public static final int WHITE = -1;

    /**
     * 1.默认常规双色
     * 2.三色渐变
     */
    @Getter
    @Setter
    private int type = 1;
    /**
     * 前景色
     * 默认情况下生效
     */
    @Getter
    @Setter
    private int onColor;
    /**
     * 背景色
     * 任何情况下生效
     */
    @Getter
    @Setter
    private int offColor;

    /**
     * 渐变色列表
     */
    @Getter
    @Setter
    private int onColor1;
    @Getter
    @Setter
    private int onColor2;

    public RabbitMatrixToImageConfig() {
        this(-16777216, -1);
    }

    public RabbitMatrixToImageConfig(int onColor, int offColor) {
        this.onColor = onColor;
        this.offColor = offColor;
    }

    //渐变参数初始化
    public void initConfigGradientColor(int offColor,int onColor1, int onColor2) {
        this.type = 2;
        this.offColor = offColor;
        this.onColor1 = onColor1;
        this.onColor2 = onColor2;
    }

    public int getPixelOnColor() {
        return this.onColor;
    }

    public int getPixelOffColor() {
        return this.offColor;
    }

    int getBufferedImageColorModel() {
        //类型可于java api的BufferedImage中看到
        if (this.onColor == -16777216 && this.offColor == -1) {
            return 12;
        } else {
            return !hasTransparency(this.onColor) && !hasTransparency(this.offColor) ? 1 : 2;
        }
    }

    private static boolean hasTransparency(int argb) {
        return (argb & -16777216) != -16777216;
    }
}
