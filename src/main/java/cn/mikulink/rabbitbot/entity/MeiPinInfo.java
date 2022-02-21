package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * created by MikuNyanya on 2022/2/21 14:06
 * For the Reisen
 * 没品文章信息
 */
@Getter
@Setter
public class MeiPinInfo implements Serializable {
    public MeiPinInfo(){}
    public MeiPinInfo(String title,String url){
        this.title = title;
        this.url = url;
    }

    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章链接
     */
    private String url;
}
