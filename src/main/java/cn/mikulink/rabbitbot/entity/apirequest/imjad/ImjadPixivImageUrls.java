package cn.mikulink.rabbitbot.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 14:55
 * for the Reisen
 * P站图片链接，有不同规格的
 */
@Setter
@Getter
public class ImjadPixivImageUrls {
    /**
     * px_128x128 : https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg
     * px_480mw : https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg
     * small : https://i.pximg.net/c/150x150/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg
     * medium : https://i.pximg.net/c/600x600/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg
     * large : https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png
     */

    //180像素方图
    private String px_128x128;
    //480p
    private String px_480mw;
    //小图
    private String small;
    //不大不小的图
    private String medium;
    //大图，也是原图
    private String large;
}
