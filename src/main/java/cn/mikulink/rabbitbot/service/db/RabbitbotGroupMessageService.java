package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.RabbitbotGroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.mapper.RabbitbotGroupMessageMapper;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MikuLink created in 2025/4/8 20:08
 * For the Reisen
 * 群消息
 */
@Service
public class RabbitbotGroupMessageService {
    @Autowired
    private RabbitbotGroupMessageMapper mapper;

    public int create(RabbitbotGroupMessageInfo info) {
        return mapper.create(info);
    }

    public int create(GroupMessageInfo groupMessageInfo){
        RabbitbotGroupMessageInfo info = new RabbitbotGroupMessageInfo();
        info.setMessageId(groupMessageInfo.getMessageId());
        info.setGroupId(groupMessageInfo.getGroupId());
        info.setUserId(groupMessageInfo.getUserId());
        info.setRawMessage(groupMessageInfo.getRawMessage());
        info.setSenderJson(JSON.toJSONString(groupMessageInfo.getSender()));
        info.setMessageJson(JSON.toJSONString(groupMessageInfo.getMessage()));
        info.setMessageType(groupMessageInfo.getMessageType());
        info.setMessageFormat(groupMessageInfo.getMessageFormat());
        info.setRecall(0);
        return mapper.create(info);
    }

}
