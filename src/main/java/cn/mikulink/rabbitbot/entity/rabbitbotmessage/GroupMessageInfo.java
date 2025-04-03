package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:50
 * For the Reisen
 */
@Getter
@Setter
public class GroupMessageInfo extends MessageInfo{
    //群号
    @JsonProperty("group_id")
    private Long groupId;
}
