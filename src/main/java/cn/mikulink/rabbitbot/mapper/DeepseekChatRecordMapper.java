package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.DeepseekChatRecordInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 */
@Mapper
public interface DeepseekChatRecordMapper {
    int create(DeepseekChatRecordInfo info);
}