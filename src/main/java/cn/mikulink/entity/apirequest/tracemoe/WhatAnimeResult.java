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
public class WhatAnimeResult {

    /**
     * RawDocsCount : 15463445
     * RawDocsSearchTime : 29570
     * ReRankSearchTime : 3679
     * CacheHit : false
     * trial : 1
     * docs : [{"from":121.08,"to":121.08,"anilist_id":108617,"at":121.08,"season":"2020-01","anime":"索瑪麗與森林之神","filename":"索玛丽与森林之神 第02集.mp4","episode":2,"tokenthumb":"4BDgSKUXNi2_fyEAWL97NQ","similarity":0.9613629669484373,"title":"ソマリと森の神様","title_native":"ソマリと森の神様","title_chinese":"索瑪麗與森林之神","title_english":"Somali and the Forest Spirit","title_romaji":"Somali to Mori no Kamisama","mal_id":39575,"synonyms":["Somari and the Guardian of the Forest"],"synonyms_chinese":[],"is_adult":false}]
     * limit : 9
     * limit_ttl : 60
     * quota : 149
     * quota_ttl : 86400
     */

    private Long RawDocsCount;
    private Long RawDocsSearchTime;
    private Long ReRankSearchTime;
    private Boolean CacheHit;
    private Long trial;
    private Long limit;
    private Long limit_ttl;
    private Long quota;
    private Long quota_ttl;
    private List<WhatAnimeDoc> docs;
}
