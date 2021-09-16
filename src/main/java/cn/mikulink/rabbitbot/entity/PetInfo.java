package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * created by MikuNyanya on 2021/9/15 17:22
 * For the Reisen
 */
@Getter
@Setter
public class PetInfo {
    /**
     * 等级
     */
    private Integer level = 0;
    /**
     * 当前经验
     */
    private Integer exp = 0;
    /**
     * 下一个等级所需经验
     * 所需经验 = 5 + 当前等级;
     * exp = 5 + level;
     */
    private Integer nextLevelExp = 0;

    public Integer getNextLevelExp() {
        return this.level + 5;
    }

    /**
     * 心情
     */
    private Integer heart = 0;
}
