package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.DeepseekChatRecordInfo;
import cn.mikulink.rabbitbot.mapper.DeepseekChatRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MikuLink created in 2025/4/8 20:08
 * For the Reisen
 */
@Slf4j
@Service
public class DeepseekChatRecordService {
    @Autowired
    private DeepseekChatRecordMapper mapper;

    public int create(DeepseekChatRecordInfo info) {
        return mapper.create(info);
    }

}
