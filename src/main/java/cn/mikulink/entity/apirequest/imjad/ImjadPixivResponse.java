package cn.mikulink.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/19 14:52
 * for the Reisen
 */
@Setter
@Getter
public class ImjadPixivResponse {
    /**
     * id : 55527150
     * title : いろんなの
     * caption : 久しぶりに思い出しながら色々絵かき。
     * おもしろそうな職を見つけたので、来月から東京に転職することに。
     * 山と海に囲まれた場所を離れるのは何か寂しいけど、多くの人やものに出会えそうで楽しみ。
     * またアニメや絵描きに夢中になる日がきたらいいなとちょい思う。
     * いろいろ楽しむ!
     * tags : ["東方とかいろいろ","東方","洩矢諏訪子","古明地こいし","鈴仙・優曇華院・イナバ","ススキ","萃香にとり天子","東方Project","初音ミク","東方Project10000users入り"]
     * tools : ["CLIP STUDIO PAINT"]
     * image_urls : {"px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","small":"https://i.pximg.net/c/150x150/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","medium":"https://i.pximg.net/c/600x600/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png"}
     * width : 1521
     * height : 928
     * stats : {"scored_count":17459,"score":174061,"views_count":249453,"favorited_count":{"public":18562,"private":1279},"commented_count":143}
     * publicity : 0
     * age_limit : all-age
     * created_time : 2016-02-28 12:17:08
     * reuploaded_time : 2016-02-28 12:17:08
     * user : {"id":3251963,"account":"kitunegumo","name":"湯木間","is_following":false,"is_follower":false,"is_friend":false,"is_premium":null,"profile_image_urls":{"px_50x50":"https://i.pximg.net/user-profile/img/2012/12/27/20/20/23/5596289_d8b0b48bbe2c130f5636981358d0e9b1_50.jpg"},"stats":null,"profile":null}
     * is_manga : true
     * is_liked : false
     * favorite_id : 0
     * page_count : 22
     * book_style : none
     * type : illustration
     * metadata : {"pages":[{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p0.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p0_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p1.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p1_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p1_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p1_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p2.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p2_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p2_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p2_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p3.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p3_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p3_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p3_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p4.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p4_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p4_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p4_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p5.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p5_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p5_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p5_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p6.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p6_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p6_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p6_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p7.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p7_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p7_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p7_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p8.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p8_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p8_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p8_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p9.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p9_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p9_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p9_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p10.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p10_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p10_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p10_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p11.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p11_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p11_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p11_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p12.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p12_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p12_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p12_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p13.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p13_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p13_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p13_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p14.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p14_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p14_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p14_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p15.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p15_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p15_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p15_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p16.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p16_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p16_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p16_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p17.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p17_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p17_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p17_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p18.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p18_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p18_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p18_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p19.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p19_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p19_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p19_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p20.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p20_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p20_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p20_master1200.jpg"}},{"image_urls":{"large":"https://i.pximg.net/img-original/img/2016/02/28/12/17/08/55527150_p21.png","px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2016/02/28/12/17/08/55527150_p21_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2016/02/28/12/17/08/55527150_p21_master1200.jpg","medium":"https://i.pximg.net/img-master/img/2016/02/28/12/17/08/55527150_p21_master1200.jpg"}}]}
     * content_type : null
     */

    //p站图片id
    private long id;
    //图片标题
    private String title;
    //图片简介
    private String caption;
    //图片宽度 应该是取p0的原始大小
    private int width;
    //图片高度
    private int height;
    private int publicity;
    private String age_limit;
    //上传时间
    private String created_time;
    //修改时间？
    private String reuploaded_time;
    private boolean is_manga;
    private boolean is_liked;
    private int favorite_id;
    //pid下的总p数
    private Integer page_count;
    private String book_style;
    //illustration 代表插画，其他的不太清楚
    private String type;
    private Object content_type;
    //图片tag列表
    private List<String> tags;
    //画图工具
    private List<String> tools;
    //封面图片链接，多p取的是p0信息作为封面
    private ImjadPixivImageUrls image_urls;
    //作品数据信息
    private ImjadPixivStats stats;
    //作者信息
    private ImjadPixivUser user;
    //图片列表，只有pid下是多p的时候才会有值，也可以用page_count字段是否=1来判断是不是多p
    private ImjadPixivMetadata metadata;
}
