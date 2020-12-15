package cn.mikulink.entity.apirequest.tracemoe;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/21 13:09
 * for the Reisen
 */
@Setter
@Getter
public class WhatAnimeDoc {
    /**
     * from : 121.08
     * to : 121.08
     * anilist_id : 108617
     * at : 121.08
     * season : 2020-01
     * anime : 索瑪麗與森林之神
     * filename : 索玛丽与森林之神 第02集.mp4
     * episode : 2
     * tokenthumb : 4BDgSKUXNi2_fyEAWL97NQ
     * similarity : 0.9613629669484373
     * title : ソマリと森の神様
     * title_native : ソマリと森の神様
     * title_chinese : 索瑪麗與森林之神
     * title_english : Somali and the Forest Spirit
     * title_romaji : Somali to Mori no Kamisama
     * mal_id : 39575
     * synonyms : ["Somari and the Guardian of the Forest"]
     * synonyms_chinese : []
     * is_adult : false
     */

    //开始位置，单位秒，保留2位小数
    private Double from;
    //截止位置，单位秒，保留2位小数
    private Double to;
    //传入图片位置
    private Double at;
    //AniList网站相关字段
    private Long anilist_id;
    //MyAnimeList网站相关字段
    private Long mal_id;
    //档期，什么时间的番
    private String season;
    //番名
    private String anime;
    //文件名
    private String filename;
    //第几集 不过说是从文件名中提取出来的
    private String episode;
    private String tokenthumb;
    //相似度
    private Double similarity;
    //番名
    private String title;
    //番名_日文
    private String title_native;
    //番名_中文
    private String title_chinese;
    //番名_英文
    private String title_english;
    //番名_罗马音
    private String title_romaji;
    private Boolean is_adult;
    //其他英文名
    private List<String> synonyms;
    //其他中文名
    private List<String> synonyms_chinese;

}
