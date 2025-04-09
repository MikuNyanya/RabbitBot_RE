package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.RabbitbotGroupMessageInfo;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.mapper.RabbitbotGroupMessageMapper;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

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

    /**
     * 获取指定目标的历史聊天记录记录 默认50条
     *
     * @param groupId 群号
     * @return 聊天记录列表，按照时间降序
     */
    public List<RabbitbotGroupMessageInfo> getHistoryByTargetId(Long groupId) {
        return this.getHistoryByTargetId(groupId, 50);
    }

    /**
     * 获取指定目标指定数量的历史聊天记录记录
     *
     * @param groupId       群号
     * @param historyCount 获取聊天记录条数
     * @return 聊天记录列表，按照时间降序
     */
    public List<RabbitbotGroupMessageInfo> getHistoryByTargetId(Long groupId, int historyCount) {
        List<RabbitbotGroupMessageInfo> resultList = mapper.getHistoryByGroupId(groupId, historyCount);
        resultList.sort(Comparator.comparing(RabbitbotGroupMessageInfo::getCreateTime));
        return resultList;
    }

}
