package cn.mikulink.rabbitbot.mapper;

import cn.mikulink.rabbitbot.entity.db.QQMessagePushInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QQMessagePushMapper {
    int create(QQMessagePushInfo info);
}