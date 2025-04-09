package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.QQMessagePushInfo;
import org.apache.ibatis.annotations.Mapper;
/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 * 消息推送
 */
@Mapper
public interface QQMessagePushMapper {
    int create(QQMessagePushInfo info);
}