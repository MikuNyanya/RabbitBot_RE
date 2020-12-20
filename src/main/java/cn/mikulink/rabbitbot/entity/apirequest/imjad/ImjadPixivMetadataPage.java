package cn.mikulink.rabbitbot.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 15:06
 * for the Reisen
 * 用户信息
 */
@Setter
@Getter
public class ImjadPixivMetadataPage {
    /**
     * image_urls : {"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg"}
     */

    private ImjadPixivImageUrls image_urls;
}
