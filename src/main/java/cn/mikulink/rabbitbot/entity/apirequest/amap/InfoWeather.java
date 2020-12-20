package cn.mikulink.rabbitbot.entity.apirequest.amap;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/1/21 16:42
 * for the Reisen
 * 天气信息
 */
@Getter
@Setter
public class InfoWeather {
    /**
     * 省份名
     */
    private String province;
    /**
     * 城市名
     */
    private String city;
    /**
     * 区域编码
     */
    private String adcode;
    /**
     * 天气现象（汉字描述）
     */
    private String weather;
    /**
     * 实时气温，单位：摄氏度
     */
    private String temperature;
    /**
     * 风向描述
     */
    private String winddirection;
    /**
     * 风力级别，单位：级
     */
    private String windpower;
    /**
     * 空气湿度
     */
    private String humidity;
    /**
     * 数据发布的时间
     */
    private String reporttime;
}
