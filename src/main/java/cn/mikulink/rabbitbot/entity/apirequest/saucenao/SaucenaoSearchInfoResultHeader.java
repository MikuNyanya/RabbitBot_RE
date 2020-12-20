package cn.mikulink.rabbitbot.entity.apirequest.saucenao;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 13:19
 * for the Reisen
 */
@Setter
@Getter
public class SaucenaoSearchInfoResultHeader {
    /**
     * similarity : 89.60
     * thumbnail : https://img1.saucenao.com/res/pixiv/5552/manga/55527150_p16.jpg?auth=e1zHkxDtXQhHHV96oN7lxA&exp=1582083465
     * index_id : 5
     * index_name : Index #5: Pixiv Images - 55527150_p16.jpg
     */

    //查询结果相似度
    private String similarity;
    //saucenao查询结果缩略图
    private String thumbnail;
    //应该是在saucenao里的索引类型，5应该表示来源于p站 9代表Danbooru
    private int index_id;
    //索引文本
    private String index_name;
}
