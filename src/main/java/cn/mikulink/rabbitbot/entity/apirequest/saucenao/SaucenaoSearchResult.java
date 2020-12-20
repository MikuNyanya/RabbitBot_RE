package cn.mikulink.rabbitbot.entity.apirequest.saucenao;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/19 13:14
 * for the Reisen
 */
@Setter
@Getter
public class SaucenaoSearchResult {

    /**
     * header : {"user_id":"34804","account_type":"1","short_limit":"6","long_limit":"200","long_remaining":193,"short_remaining":5,"status":0,"results_requested":3,"index":{"0":{"status":0,"parent_id":0,"id":0,"results":3},"2":{"status":0,"parent_id":2,"id":2,"results":3},"5":{"status":0,"parent_id":5,"id":5,"results":3},"51":{"status":0,"parent_id":5,"id":51,"results":3},"52":{"status":0,"parent_id":5,"id":52,"results":3},"53":{"status":0,"parent_id":5,"id":53,"results":3},"6":{"status":0,"parent_id":6,"id":6,"results":3},"8":{"status":0,"parent_id":8,"id":8,"results":3},"9":{"status":0,"parent_id":9,"id":9,"results":24},"10":{"status":0,"parent_id":10,"id":10,"results":3},"11":{"status":0,"parent_id":11,"id":11,"results":3},"12":{"status":1,"parent_id":9,"id":12},"16":{"status":0,"parent_id":16,"id":16,"results":3},"18":{"status":0,"parent_id":18,"id":18,"results":3},"19":{"status":0,"parent_id":19,"id":19,"results":3},"20":{"status":0,"parent_id":20,"id":20,"results":3},"21":{"status":0,"parent_id":21,"id":21,"results":3},"211":{"status":0,"parent_id":21,"id":211,"results":3},"22":{"status":0,"parent_id":22,"id":22,"results":3},"23":{"status":0,"parent_id":23,"id":23,"results":3},"24":{"status":0,"parent_id":24,"id":24,"results":3},"25":{"status":1,"parent_id":9,"id":25},"26":{"status":1,"parent_id":9,"id":26},"27":{"status":1,"parent_id":9,"id":27},"28":{"status":1,"parent_id":9,"id":28},"29":{"status":1,"parent_id":9,"id":29},"30":{"status":1,"parent_id":9,"id":30},"31":{"status":0,"parent_id":31,"id":31,"results":3},"32":{"status":0,"parent_id":32,"id":32,"results":3},"33":{"status":0,"parent_id":33,"id":33,"results":3},"34":{"status":0,"parent_id":34,"id":34,"results":3},"35":{"status":0,"parent_id":35,"id":35,"results":3},"36":{"status":0,"parent_id":36,"id":36,"results":3},"37":{"status":0,"parent_id":37,"id":37,"results":3}},"search_depth":"128","minimum_similarity":55,"query_image_display":"userdata/xRwkcf75y.jpg.png","query_image":"xRwkcf75y.jpg","results_returned":3}
     * results : [{"header":{"similarity":"89.60","thumbnail":"https://img1.saucenao.com/res/pixiv/5552/manga/55527150_p16.jpg?auth=e1zHkxDtXQhHHV96oN7lxA&exp=1582083465","index_id":5,"index_name":"Index #5: Pixiv Images - 55527150_p16.jpg"},"data":{"ext_urls":["https://www.pixiv.net/member_illust.php?mode=medium&illust_id=55527150"],"title":"いろんなの","pixiv_id":55527150,"member_name":"湯木間","member_id":3251963}},{"header":{"similarity":"89.9","thumbnail":"https://img3.saucenao.com/booru/a/7/a7ce6e6b779de568593465af692e4d4a_4.jpg","index_id":9,"index_name":"Index #9: Danbooru - a7ce6e6b779de568593465af692e4d4a_0.jpg"},"data":{"ext_urls":["https://danbooru.donmai.us/post/show/2288681","https://chan.sankakucomplex.com/post/show/5156484"],"danbooru_id":2288681,"sankaku_id":5156484,"creator":"yushika","material":"touhou","characters":"reisen udongein inaba","source":"http://i3.pixiv.net/img-original/img/2016/02/28/12/17/08/55527150"}},{"header":{"similarity":"88.53","thumbnail":"https://img3.saucenao.com/booru/5/a/5a3cd7effc04ed50a25ff00c5c4b6242_2.jpg","index_id":9,"index_name":"Index #9: Danbooru - 5a3cd7effc04ed50a25ff00c5c4b6242_0.jpg"},"data":{"ext_urls":["https://danbooru.donmai.us/post/show/2022703","https://gelbooru.com/index.php?page=post&s=view&id=2712279","https://chan.sankakucomplex.com/post/show/4620256"],"danbooru_id":2022703,"gelbooru_id":2712279,"sankaku_id":4620256,"creator":"yushika","material":"touhou","characters":"reisen udongein inaba","source":"https://twitter.com/yukima_Q/status/600703985356443650/photo/1"}}]
     */

    private SaucenaoSearchInfoHeader header;
    //搜索结果
    private List<SaucenaoSearchInfoResult> results;
}
