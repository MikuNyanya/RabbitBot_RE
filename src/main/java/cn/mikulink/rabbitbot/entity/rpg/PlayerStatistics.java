package cn.mikulink.rabbitbot.entity.rpg;

import lombok.Getter;
import lombok.Setter;

/**
 * created by MikuNyanya on 2021/12/17 17:49
 * For the Reisen
 * 人物属性
 * 设定所有属性均衡值为 MAX/2
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
     * 一般是随机的
     */
    private int luck;
    /**
     * 血量
     * 一般由基础属性计算而来
     */
    private int health;
}
