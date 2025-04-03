package cn.mikulink.rabbitbot.entity.apirequest.deepseek;

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

    public MessageInfo(){}

    public MessageInfo(String role,String content){
        this.role = role;
        this.content = content;
    }


    private String content;

    private String role;

}
