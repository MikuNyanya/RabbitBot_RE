package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:50
 * For the Reisen
 * 私聊信息对象
 */
@Getter
@Setter
public class PrivateMessageInfo extends MessageInfo {
    //私聊目标 该参数会以兔叽为视角，始终为对方的id
    @JSONField(name = "target_id")
    private Long targetId;
}
