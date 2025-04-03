package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.QQMessagePushInfo;
import cn.mikulink.rabbitbot.mapper.QQMessagePushMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MikuLink created in 2025/4/1 20:08
 * For the Reisen
 *
 */
@Service
public class QQMessagePushService {
    @Autowired
    private QQMessagePushMapper mapper;

    //原始报文落库
    public int create(QQMessagePushInfo info) {
        return mapper.create(info);
    }

}
