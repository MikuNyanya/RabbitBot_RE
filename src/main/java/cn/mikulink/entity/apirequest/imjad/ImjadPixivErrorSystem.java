package cn.mikulink.entity.apirequest.imjad;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 15:00
 * for the Reisen
 * api错误信息
 */
@Setter
@Getter
public class ImjadPixivErrorSystem {
    private Integer code;
    private String message;
}
