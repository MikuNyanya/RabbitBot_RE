package cn.mikulink.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * create by MikuLink on 2020/2/19 15:06
 * for the Reisen
 * 用户信息
 */
@Setter
@Getter
public class ImjadPixivMetadata {
    //图片列表
    private List<ImjadPixivMetadataPage> pages;
}
