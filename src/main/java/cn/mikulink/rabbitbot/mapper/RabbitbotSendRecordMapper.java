package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.RabbitbotSendRecordInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 兔叽消息发送记录
 */
@Mapper
public interface RabbitbotSendRecordMapper {
    int create(RabbitbotSendRecordInfo info);

    void updateResponseBody(RabbitbotSendRecordInfo info);
}