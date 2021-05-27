package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SwitchEntity {
    public boolean pixivRank = false;
    private boolean weibo = false;
    private boolean pixivDay = false;
    private boolean sj = false;
    private boolean r18 = false;
    private boolean say = false;

    //{"pixivRank":"off","weibo":"off","setu":"off","setuday":"off","sj":"off",
    // "pixivR18":"off","say":"off","loliconApp":"on","historyToday":"on"}
}
