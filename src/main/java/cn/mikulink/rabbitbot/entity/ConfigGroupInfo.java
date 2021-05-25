package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MikuLink
 * @date 2021/2/3 19:23
 * for the Reisen
 * 群配置对象
 */
@Getter
@Setter
public class ConfigGroupInfo {
    /**
     * 订阅的微博id列表
     */
    List<Long> weiboPushIds = new ArrayList<>();
    /**
     * 订阅的B站视频动态uid列表
     */
    List<Long> biliPushIds = new ArrayList<>();
}
