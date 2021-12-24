package cn.mikulink.rabbitbot.entity.rpg;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * created by MikuNyanya on 2021/12/24 10:08
 * For the Reisen
 * 群友互殴
 */
@Getter
@Setter
public class KickingASSInfo implements Serializable {
    /**
     * 互殴开始时间
     * 时间戳
     */
    private Long startTime;
    /**
     * 最后一次操作时间
     */
    private Long operationLastTime;
    /**
     * 哪个群的互殴
     */
    private Long groupId;


    /**
     * 玩家1的qq号
     * 也是发起互殴的账号
     */
    private Long playIdOne;
    /**
     * 玩家1的名称
     * 发起互殴的时候记录当前双方名称
     * 途中更换名字不会影响当前互殴
     */
    private String playNameOne;
    /**
     * 玩家1的血量
     * 显示剩余血量
     * 初始血量从人物属性里获取
     */
    private Integer playHealthOne;

    //玩家2数据 描述同上
    private Long playIdTwo;
    private String playNameTwo;
    private Integer playHealthTwo;
}
