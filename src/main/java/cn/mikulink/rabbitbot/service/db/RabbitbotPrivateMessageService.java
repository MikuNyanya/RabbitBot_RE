package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.PrivateMessageInfo;
import cn.mikulink.rabbitbot.mapper.RabbitbotPrivateMessageMapper;
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
public class RabbitbotPrivateMessageService {
    @Autowired
    private RabbitbotPrivateMessageMapper mapper;

    //创建
    public int create(RabbitbotPrivateMessageInfo info) {
        return mapper.create(info);
    }

    /**
     * 创建
     * 接收报文对象转化并保存消息
     *
     * @param privateMessageInfo 上报的消息对象
     * @return 受影响行数
     */
    public int create(PrivateMessageInfo privateMessageInfo) {
        RabbitbotPrivateMessageInfo info = new RabbitbotPrivateMessageInfo();
        info.setMessageId(privateMessageInfo.getMessageId());
        info.setUserId(privateMessageInfo.getUserId());
        info.setTargetId(privateMessageInfo.getTargetId());
        info.setRawMessage(privateMessageInfo.getRawMessage());
        info.setSenderJson(JSON.toJSONString(privateMessageInfo.getSender()));
        info.setMessageJson(JSON.toJSONString(privateMessageInfo.getMessage()));
        info.setMessageType(privateMessageInfo.getMessageType());
        info.setMessageFormat(privateMessageInfo.getMessageFormat());
        info.setRecall(0);
        return mapper.create(info);
    }

    /**
     * 获取指定目标的历史聊天记录记录 默认50条
     *
     * @param targetId 私聊对象id
     * @return 聊天记录列表，按照时间降序
     */
    public List<RabbitbotPrivateMessageInfo> getHistoryByTargetId(Long targetId) {
        return this.getHistoryByTargetId(targetId, 50);
    }

    /**
     * 获取指定目标指定数量的历史聊天记录记录
     *
     * @param targetId     私聊对象id
     * @param historyCount 获取聊天记录条数
     * @return 聊天记录列表，按照时间降序
     */
    public List<RabbitbotPrivateMessageInfo> getHistoryByTargetId(Long targetId, int historyCount) {
        List<RabbitbotPrivateMessageInfo> resultList = mapper.getHistoryByTargetId(targetId, historyCount);
        resultList.sort(Comparator.comparing(RabbitbotPrivateMessageInfo::getCreateTime));
        return resultList;
    }


    public RabbitbotPrivateMessageInfo getLastBotHistoryByTargetId(Long targetId,Long botId) {
        return mapper.getLastBotHistoryByTargetId(targetId, botId);
    }
}
