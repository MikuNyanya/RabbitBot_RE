package cn.mikulink.rabbitbot.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/31 17:33
 * for the Reisen
 * 二维码相关
 */
@Setter
@Getter
public class ImjadQRCode {
    /**
     * url : https://api.imjad.cn/qrcode/temp/6bf63b75f138b5091a7ff02c48a59797.png
     * data : 兔子万岁！
     * logo :
     * level : Q
     * size : 200
     * msg : success
     * code : 0
     */

    //二维码网络图片链接
    private String url;
    //下面都是传入参数，无需关注
    private String data;
    private String logo;
    private String level;
    private String size;
    private String msg;
    private Integer code;
}
