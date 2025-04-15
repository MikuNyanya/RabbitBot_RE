package cn.mikulink.rabbitbot.apirequest.deepseek;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.deepseek.MessageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.deepseek.ResponseBodyInfo;
import cn.mikulink.rabbitbot.entity.apirequest.deepseek.ResponseFormatInfo;
import com.alibaba.fastjson2.JSONObject;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MikuLink created in 2025/3/31 22:33
 * For the Reisen
 * deepseek的对话补全接口
 * <a href="https://api-docs.deepseek.com/zh-cn/api/create-chat-completion">DeepSeek接口文档</a>
 */
public class ChatCompletionsRequest extends BaseRequest {
    @Setter
    private String url = "https://api.deepseek.com/chat/completions";
    //消息列表
    @Setter
    private List<MessageInfo> messagList;
    //系统级提示词
    @Setter
    private String systemTag;
    /**
     * 模型
     * deepseek-chat 为 DeepSeek-V3
     * deepseek-reasoner 为推理模型 DeepSeek-R1
     */
    @Setter
    private String model = "deepseek-chat";
    /**
     * 最大token影响输出长度
     * 普通聊天的时候200足矣，避免输出太多，体验不好也浪费钱
     * 但比较专业的问答时，或者压缩历史对话时，总之就是需要输出长篇大论的时候，建议直接拉满8000
     */
    @Setter
    private int maxTokens = 200;

    private void parseParam() {
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer " + accessToken);

        param.put("model", model);
        param.put("frequency_penalty", 0);
        param.put("max_tokens", maxTokens);
        param.put("presence_penalty", 0);
        ResponseFormatInfo formatInfo = new ResponseFormatInfo();
        formatInfo.setType("text");
        param.put("response_format", formatInfo);
        param.put("stop", null);
        param.put("stream", false);
        param.put("stream_options", null);
        //温度
        param.put("temperature", 0.6);
        param.put("top_p", 0.8);
        param.put("tools", null);
        param.put("tool_choice", "none");
        param.put("logprobs", false);
        param.put("top_logprobs", null);

        List<MessageInfo> messages = new ArrayList<>();
        messages.add(new MessageInfo("system", systemTag));

        messages.addAll(messagList);

        param.put("messages", messages);
    }


    public String doRequest() throws IOException {
        //填充参数
        parseParam();

        HttpRequest httpRequest = HttpUtil.createPost(url);
        httpRequest.contentType(ContentType.JSON.getValue());
        httpRequest.headerMap(header, true);

        HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).body(JSONObject.toJSONString(param)).execute();
        String responseBody = response.body();

        ResponseBodyInfo respInfo = JSONObject.parseObject(responseBody, ResponseBodyInfo.class);
        return respInfo.getChoices().get(0).getMessage().getContent();
    }
}
