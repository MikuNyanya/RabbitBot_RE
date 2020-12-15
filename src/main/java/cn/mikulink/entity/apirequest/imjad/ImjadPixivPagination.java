package cn.mikulink.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/4 15:24
 * for the Reisen
 */
@Setter
@Getter
public class ImjadPixivPagination {
    /**
     * previous : null
     * next : 2
     * current : 1
     * per_page : 5
     * total : 960
     * pages : 192
     */

    private int next;
    private int current;
    private int per_page;
    private int total;
    private int pages;
}
