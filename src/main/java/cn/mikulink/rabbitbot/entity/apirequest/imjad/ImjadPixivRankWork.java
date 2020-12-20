package cn.mikulink.rabbitbot.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/20 17:38
 * for the Reisen
 */
@Setter
@Getter
public class ImjadPixivRankWork {
    /**
     * rank : 1
     * previous_rank : 4
     * work : {"id":79564823,"title":"Haine","caption":null,"tags":["Fate/GrandOrder","FGO","ジャンヌ・オルタ","Fate/GO10000users入り"],"tools":null,"image_urls":{"px_128x128":"https://i.pximg.net/c/128x128/img-master/img/2020/02/18/00/00/01/79564823_p0_square1200.jpg","px_480mw":"https://i.pximg.net/c/480x960/img-master/img/2020/02/18/00/00/01/79564823_p0_master1200.jpg","large":"https://i.pximg.net/img-original/img/2020/02/18/00/00/01/79564823_p0.png"},"width":758,"height":1500,"stats":{"scored_count":3258,"score":32580,"views_count":49404,"favorited_count":{"public":null,"private":null},"commented_count":null},"publicity":0,"age_limit":"all-age","created_time":"2020-02-18 00:00:00","reuploaded_time":"2020-02-18 00:00:01","user":{"id":83739,"account":"blacklack-21","name":"lack","is_following":null,"is_follower":null,"is_friend":null,"is_premium":null,"profile_image_urls":{"px_170x170":"https://i.pximg.net/user-profile/img/2019/09/12/19/41/28/16268521_ecf42a7c286189898dbbcdbecaf3760c_170.jpg","px_50x50":"https://i.pximg.net/user-profile/img/2019/09/12/19/41/28/16268521_ecf42a7c286189898dbbcdbecaf3760c_50.jpg"},"stats":null,"profile":null},"is_manga":null,"is_liked":null,"favorite_id":null,"page_count":1,"book_style":"none","type":"illustration","metadata":null,"content_type":null,"sanity_level":"white"}
     */

    //排名
    private int rank;
    //昨日排名
    private int previous_rank;
    private ImjadPixivRankWorkWork work;
}
