package cn.mikulink.rabbitbot.service.db;

import cn.mikulink.rabbitbot.entity.db.RabbitbotSendRecordInfo;
import cn.mikulink.rabbitbot.mapper.RabbitbotSendRecordMapper;
import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * MikuLink created in 2025/4/8 20:08
 * For the Reisen
 * 群消息
 */
@Slf4j
@Service
public class RabbitbotSendRecordService {
    @Autowired
    private RabbitbotSendRecordMapper mapper;

    public int create(RabbitbotSendRecordInfo info) {
        return mapper.create(info);
    }

    /**
     * 保存兔叽的发送记录
     *
     * @param url      请求的链接
     * @param bodyJson 发送的消息json
     * @return 落库后的主键id
     */
    public Long create(String url, String bodyJson) {
        //解析groupId和userId
        JSONObject jsonObject = JSONObject.parseObject(bodyJson);
        Long groupId = null;
        if (null != jsonObject.get("group_id")) {
            groupId = Long.valueOf(jsonObject.get("group_id").toString());
        }
        Long userId = null;
        if (null != jsonObject.get("user_id")) {
            userId = Long.valueOf(jsonObject.get("user_id").toString());
        }

        RabbitbotSendRecordInfo info = new RabbitbotSendRecordInfo();
        info.setGroupId(groupId);
        info.setUserId(userId);
        info.setUrl(url);
        info.setRequestJson(bodyJson);
        mapper.create(info);

        return info.getId();
    }

    /**
     * 更新发送记录的返回报文
     *
     * @param recordId     发送记录主键id
     * @param responseBody 返回报文
     */
    public void updateResponseBody(Long recordId, String responseBody) {
        if (null == recordId) {
            log.error("准备更新发送返回报文，但是没有记录id,responseBody:{}", responseBody);
            return;
        }
        //解析返回报文，获取messageId
        JSONObject jsonObject = JSONObject.parseObject(responseBody);
        Long messageId = null;
        if (null != jsonObject.get("data")) {
            JSONObject data = JSONObject.parseObject(jsonObject.get("data").toString());
            if (null != data.get("message_id")) {
                messageId = Long.valueOf(data.get("message_id").toString());
            }
        }
        RabbitbotSendRecordInfo info = new RabbitbotSendRecordInfo();
        info.setId(recordId);
        info.setMessageId(messageId);
        info.setResponseJson(responseBody);

        mapper.updateResponseBody(info);
    }

}
