package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.mikulink.rabbitbot.constant.ConstantBlackList;
import cn.mikulink.rabbitbot.entity.db.QQMessagePushInfo;
import cn.mikulink.rabbitbot.service.db.QQMessagePushService;
import cn.mikulink.rabbitbot.utils.NumberUtil;
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
//        System.out.println("====接受报文====");
//        System.out.println(body);

        try {
            //解析，区分消息类型，进行对应处理
            JSONObject bodyJsonObj = JSONObject.parseObject(body);

            //推送原报文落库
            //输入状态报文不落库
            if (!(null != bodyJsonObj.get("sub_type") && bodyJsonObj.get("sub_type").toString().equals("input_status"))) {
                QQMessagePushInfo qqMessagePushInfo = new QQMessagePushInfo(body);
                qqMessagePushService.create(qqMessagePushInfo);
            }

            String senderUserId = String.valueOf(bodyJsonObj.get("user_id"));

            //黑名单，用来防止和其他机器人死循环响应，或者屏蔽恶意人员
            if (ConstantBlackList.BLACK_LIST.contains(NumberUtil.toLong(senderUserId))) {
                return;
            }

            String postType = String.valueOf(bodyJsonObj.get("post_type"));
            if (null == postType) {
                log.error("接收到未知类型的消息上报,bodyJsonObj:{}", body);
                return;
            }
            //按照类型分发任务
            switch (postType) {
                case "message" ->
                    //消息
                        messageHandle.messageHandle(body);
                case "message_sent" ->
                    //自己的消息
                        messageHandle.selfMessageHandle(body);
                case "notice" ->
                    //notice事件
                        noticeHandle.noticeHandle(body);
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
