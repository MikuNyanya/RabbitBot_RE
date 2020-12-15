package cn.mikulink.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/5 15:24
 * for the Reisen
 * 图片参数
 */
@Setter
@Getter
public class ImageInfo {
    /**
     * 图片名称（文件名称）
     */
    private String name;
    /**
     * 图片类型（后缀)
     */
    private String type;
    /**
     * 图片大小
     */
    private Long size;
    /**
     * 图片长度
     */
    private Integer width;
    /**
     * 图片高度
     */
    private Integer height;
    /**
     * 本地路径
     */
    private String localPath;
    /**
     * 网络连接
     */
    private String imgUrl;
}
