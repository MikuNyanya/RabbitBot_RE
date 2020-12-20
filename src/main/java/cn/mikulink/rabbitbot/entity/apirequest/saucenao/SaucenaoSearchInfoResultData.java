package cn.mikulink.rabbitbot.entity.apirequest.saucenao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/19 13:19
 * for the Reisen
 */
@Setter
@Getter
public class SaucenaoSearchInfoResultData {
    /**
     * ext_urls : ["https://www.pixiv.net/member_illust.php?mode=medium&illust_id=55527150"]
     * title : いろんなの
     * pixiv_id : 55527150
     * member_name : 湯木間
     * member_id : 3251963
     */

    //P站图片标题
    private String title;
    //P站图片id
    private int pixiv_id;
    //作者名称
    private String member_name;
    //作者id
    private int member_id;
    private List<String> ext_urls;

    /**
     * danbooru_id : 2288681
     * sankaku_id : 5156484
     * creator : yushika
     * material : touhou
     * characters : reisen udongein inaba
     * source : http://i3.pixiv.net/img-original/img/2016/02/28/12/17/08/55527150
     */

    //danbooru图片id
    private Long danbooru_id;
    //danbooru图片标签
    private String characters;

    //其他来源，没用过
    private Long sankaku_id;
    private String creator;
    private String material;
    private String source;
}
