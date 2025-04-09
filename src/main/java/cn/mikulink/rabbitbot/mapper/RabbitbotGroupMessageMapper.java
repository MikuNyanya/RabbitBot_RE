package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.RabbitbotGroupMessageInfo;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 群消息
 */
@Mapper
public interface RabbitbotGroupMessageMapper {
    int create(RabbitbotGroupMessageInfo info);

    List<RabbitbotGroupMessageInfo> getHistoryByGroupId(Long groupId, int historyCount);
}