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
public class ImjadPixivUser {
    /**
     * id : 3251963
     * account : kitunegumo
     * name : 湯木間
     * is_following : false
     * is_follower : false
     * is_friend : false
     * is_premium : null
     * profile_image_urls : {"px_50x50":"https://i.pximg.net/user-profile/img/2012/12/27/20/20/23/5596289_d8b0b48bbe2c130f5636981358d0e9b1_50.jpg"}
     * stats : null
     * profile : null
     */

    //用户id
    private int id;
    //用户账号
    private String account;
    //用户名称
    private String name;
    //是否已关注
    private boolean is_following;
    //是否被该用户关注
    private boolean is_follower;
    //是否为好友
    private boolean is_friend;
    //未知字段，返回的null
//    private Object is_premium;
//    private Object stats;
//    private Object profile;
    private ImjadPixivUserProfileImageUrls profile_image_urls;
}
