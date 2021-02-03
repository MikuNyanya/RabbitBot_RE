package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MikuLink
 * @date 2021/2/3 19:23
 * for the Reisen
 * 开关对象
 */
@Getter
@Setter
public class SwitchInfo {
    private String name;
    private String value;
    private String description;
}
