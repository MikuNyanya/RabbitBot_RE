package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.mikulink.rabbitbot.entity.db.QQMessagePushInfo;
import cn.mikulink.rabbitbot.service.db.QQMessagePushService;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * MikuLink created in 2025/4/1 10:35
 * For the Reisen
 */
@Slf4j
@Controller
@RequestMapping("/onebotMessage")
public class OneBotMessageController {
    @Autowired
    private QQMessagePushService qqMessagePushService;
    @Autowired
    private MessageHandle messageHandle;
    @Autowired
    private NoticeHandle noticeHandle;


    /**
     * OneBot消息推送口 需要QQNT上的OneBotHTTP上报设置到此接口上
     * OneBot所有的QQNT消息将以HTTP POST JSON形式推送至此
     * 机器人关于消息的核心业务也由此展开
     */
    @RequestMapping(value = "push", method = RequestMethod.POST)
    @ResponseBody
    public void push(HttpServletRequest request) {
        //解析消息信息
        String body = readBody(request);
        System.out.println(body);
        try {
            QQMessagePushInfo qqMessagePushInfo = new QQMessagePushInfo(body);
            //todo 异步落库
//        qqMessagePushService.create(qqMessagePushInfo);

            //解析，区分消息类型，进行对应处理
            JSONObject bodyJsonObj = JSONObject.parseObject(body);

            //todo 黑名单消息过滤
            String senderUserId = String.valueOf(bodyJsonObj.get("user_id"));

            String postType = String.valueOf(bodyJsonObj.get("post_type"));
            if (null == postType) {
                log.error("接收到未知类型的消息上报,报文id:{}", qqMessagePushInfo.getId());
                return;
            }
            //todo 异步分发任务
            switch (postType) {
                case "message":
                    //消息
                    messageHandle.messageHandle(body);
                    break;
                case "notice":
                    //戳一戳
                    noticeHandle.noticeHandle(body);
                    break;
            }
        } catch (Exception ex) {
            log.error("消息推送业务异常！", ex);
        }
    }

    private String readBody(HttpServletRequest request) {
        String body = null;
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = streamReader.readLine()) != null) {
                sb.append(line);
            }
            body = sb.toString();
        } catch (Exception ex) {
            log.warn("QQNT消息上报异常");
        }
        return body;
    }
}
