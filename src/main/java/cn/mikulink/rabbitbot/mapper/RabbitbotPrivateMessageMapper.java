package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 私聊消息
 */
@Mapper
public interface RabbitbotPrivateMessageMapper {
    int create(RabbitbotPrivateMessageInfo info);

    List<RabbitbotPrivateMessageInfo> getHistoryByTargetId(Long targetId, int historyCount);

    RabbitbotPrivateMessageInfo getLastBotHistoryByTargetId(Long targetId,Long botId);
}