package cn.mikulink.rabbitbot.controller;

import cn.hutool.http.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * MikuLink created in 2025/1/15 20:22
 * For the Reisen
 * <p>
 * 分类
 */
@Slf4j
@Controller
@RequestMapping("/qqmsg")
public class WebDataController {

    /**
     * 获取全局设置
     */
    @RequestMapping(value = "send", method = RequestMethod.POST)
    @ResponseBody
    public void getSetting(HttpServletRequest request) {
//        String body = "";
//        try {
//            BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
//
//            StringBuilder sb = new StringBuilder();
//            String line = null;
//            while((line = streamReader.readLine()) != null){
//                sb.append(line);
//            }
//
//            body = sb.toString();
//
//            PostGroupMessage postGroupMessage = JSONObject.parseObject(body,PostGroupMessage.class);
//            String tempMsg  = postGroupMessage.getSender().getCard()+":";
//            for (MessageInfo messageInfo : postGroupMessage.getMessage()) {
//                if(messageInfo.getType().equals("text")){
//                    tempMsg += messageInfo.getData().getText();
//                }else{
//                    tempMsg += "[图片]";
//                }
//            }
//
//            System.out.println("群消息拼接："+tempMsg);
//
//            String chatRsp = ChatCompletionsRequest.test(tempMsg);
//            System.out.println("兔叽api回复："+chatRsp);
//
//            PostGroupMessage rspGroupMsg = new PostGroupMessage();
//            rspGroupMsg.setGroup_id(postGroupMessage.getGroup_id());
//            MessageInfo temp = new MessageInfo();
//            temp.setType("text");
//            MessageDataInfo messageDataInfo = new MessageDataInfo();
//            messageDataInfo.setText(chatRsp);
//            temp.setData(messageDataInfo);
//            rspGroupMsg.setMessage(Arrays.asList(temp));
//
//
//            //发送到qq
//            HttpRequest httpRequest = HttpUtil.createPost("http://localhost:31201/send_group_msg");
//            httpRequest.contentType(ContentType.JSON.getValue());
//
//            HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).body(JSONObject.toJSONString(rspGroupMsg)).execute();
//            String responseBody = response.body();
//            System.out.println("发送消息回执：" + responseBody);
//
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        System.out.println("接收到请求: "+body);
    }


}
