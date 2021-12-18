package cn.mikulink.rabbitbot.entity.rpg;

import lombok.Getter;
import lombok.Setter;

/**
 * created by MikuNyanya on 2021/12/17 17:49
 * For the Reisen
 * 人物属性
 */
@Getter
@Setter
public class PlayerStatistics {
    /**
     * 人物名称
     */
    private String name;
    /**
     * 力量
     */
    private int str;
    /**
     * 敏捷
     */
    private int dex;
    /**
     * 智力
     */
    private int inte;
    /**
     * 运气
     */
    private int luck;
}
