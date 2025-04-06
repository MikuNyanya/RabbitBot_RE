package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:57
 * For the Reisen
 * 消息发送人
 */
@Getter
@Setter
public class SenderInfo {
    //qq号
    @JsonProperty("user_id")
    private Long userId;
    //昵称
    private String nickname;
    //群备注
    private String card;
    //角色 owner 或 admin 或 member
    private String role;
    //专属头衔
    private String title;
}
