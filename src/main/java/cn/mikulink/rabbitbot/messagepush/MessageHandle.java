package cn.mikulink.rabbitbot.messagepush;

import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MikuLink created in 2025/4/2 11:31
 * For the Reisen
 * 消息相关业务
 */
@Component
public class MessageHandle {
    @Autowired
    private DeepSeekService deepSeekService;

    public void messageHandle(String messageBody) {
        JSONObject bodyJsonObj = JSONObject.parseObject(messageBody);

        String messageType = String.valueOf(bodyJsonObj.get("message_type"));
        switch (messageType) {
            case "group":
                //群消息
                GroupMessageInfo groupMessageInfo = JSON.parseObject(messageBody, GroupMessageInfo.class);
                doMessageBiz(groupMessageInfo);
                break;
            case "private":
                //私聊消息
                break;
        }
    }

    /**
     * 群消息业务
     *
     * @param groupMessageInfo 群消息
     */
    public void doMessageBiz(GroupMessageInfo groupMessageInfo) {
        //匹配指令模式

        //进入AI响应模式
        deepSeekService.aiModeGroup(groupMessageInfo);

        //匹配关键词 (常规状态下，不是每一局都会触发AI相应的)


    }

}
