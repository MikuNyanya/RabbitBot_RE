package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:50
 * For the Reisen
 */
@Getter
@Setter
public class GroupMessageInfo extends MessageInfo {
    //群号
    @JSONField(name = "group_id")
    private Long groupId;
}
