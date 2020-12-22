package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/12/22 14:24
 * for the Reisen
 * 塔罗牌对象
 */
@Getter
@Setter
public class TarotInfo {
    /**
     * 塔罗牌名称
     */
    private String name;
    /**
     * true 正位
     * false 逆位
     */
    private boolean status;
    /**
     * 图片名称
     */
    private String imgName;
    /**
     * 正位描述
     */
    private String normalDes;
    /**
     * 逆位描述
     */
    private String seDlamron;
}
