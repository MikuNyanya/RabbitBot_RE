package cn.mikulink.rabbitbot.entity.apirequest.saucenao;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 13:16
 * for the Reisen
 */
@Getter
@Setter
public class SaucenaoSearchInfoHeader {
    /**
     * user_id : 34804
     * account_type : 1
     * short_limit : 6
     * long_limit : 200
     * long_remaining : 193
     * short_remaining : 5
     * status : 0
     * results_requested : 3
     * index : {"0":{"status":0,"parent_id":0,"id":0,"results":3},"2":{"status":0,"parent_id":2,"id":2,"results":3},"5":{"status":0,"parent_id":5,"id":5,"results":3},"51":{"status":0,"parent_id":5,"id":51,"results":3},"52":{"status":0,"parent_id":5,"id":52,"results":3},"53":{"status":0,"parent_id":5,"id":53,"results":3},"6":{"status":0,"parent_id":6,"id":6,"results":3},"8":{"status":0,"parent_id":8,"id":8,"results":3},"9":{"status":0,"parent_id":9,"id":9,"results":24},"10":{"status":0,"parent_id":10,"id":10,"results":3},"11":{"status":0,"parent_id":11,"id":11,"results":3},"12":{"status":1,"parent_id":9,"id":12},"16":{"status":0,"parent_id":16,"id":16,"results":3},"18":{"status":0,"parent_id":18,"id":18,"results":3},"19":{"status":0,"parent_id":19,"id":19,"results":3},"20":{"status":0,"parent_id":20,"id":20,"results":3},"21":{"status":0,"parent_id":21,"id":21,"results":3},"211":{"status":0,"parent_id":21,"id":211,"results":3},"22":{"status":0,"parent_id":22,"id":22,"results":3},"23":{"status":0,"parent_id":23,"id":23,"results":3},"24":{"status":0,"parent_id":24,"id":24,"results":3},"25":{"status":1,"parent_id":9,"id":25},"26":{"status":1,"parent_id":9,"id":26},"27":{"status":1,"parent_id":9,"id":27},"28":{"status":1,"parent_id":9,"id":28},"29":{"status":1,"parent_id":9,"id":29},"30":{"status":1,"parent_id":9,"id":30},"31":{"status":0,"parent_id":31,"id":31,"results":3},"32":{"status":0,"parent_id":32,"id":32,"results":3},"33":{"status":0,"parent_id":33,"id":33,"results":3},"34":{"status":0,"parent_id":34,"id":34,"results":3},"35":{"status":0,"parent_id":35,"id":35,"results":3},"36":{"status":0,"parent_id":36,"id":36,"results":3},"37":{"status":0,"parent_id":37,"id":37,"results":3}}
     * search_depth : 128
     * minimum_similarity : 55
     * query_image_display : userdata/xRwkcf75y.jpg.png
     * query_image : xRwkcf75y.jpg
     * results_returned : 3
     */

    private String user_id;
    private String account_type;
    private String short_limit;
    private String long_limit;
    private int long_remaining;
    private int short_remaining;
    private int status;
    private int results_requested;
    private String search_depth;
    private int minimum_similarity;
    private String query_image_display;
    private String query_image;
    private int results_returned;
}
