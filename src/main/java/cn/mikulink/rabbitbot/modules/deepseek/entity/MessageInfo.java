package cn.mikulink.rabbitbot.modules.deepseek.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * MikuLink created in 2025/3/31 12:35
 * For the Reisen
 */
@Getter
@Setter
public class MessageInfo implements Serializable {

    public MessageInfo() {
    }

    public MessageInfo(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public MessageInfo(String role, String name, String content) {
        this.role = role;
        this.name = name;
        this.content = content;
    }


    private String content;

    private String role;
    //可以选填的参与者的名称，为模型提供信息以区分相同角色的参与者。
    private String name;

}
