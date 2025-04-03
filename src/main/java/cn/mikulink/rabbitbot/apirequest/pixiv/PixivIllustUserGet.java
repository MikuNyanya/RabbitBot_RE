package cn.mikulink.rabbitbot.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.*;

/**
 * create by MikuLink on 2021/1/6 4:36
 * for the Reisen
 * 根据用户id获取该用户的所有插画id
 */
@Setter
@Getter
public class PixivIllustUserGet extends BaseRequest {
    //https://www.pixiv.net/ajax/user/19469841/profile/all
    //返回的是标准json
    private static final String URL = "https://www.pixiv.net/ajax/user/%s/profile/all";

    /**
     * p站用户id
     */
    private String userId;

    /**
     * 图片id列表
     */
    private List<String> responseList;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws RabbitApiException, IOException {
        if (StringUtil.isEmpty(userId) || !NumberUtil.isNumberOnly(userId)) return;

        //获取数据
        byte[] resultBytes = HttpsUtil.doGet(String.format(URL, userId), header, proxy);
        body = new String(resultBytes);

        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        //接口调用异常
        if (null == rootMap || null == rootMap.get("error") || null == rootMap.get("body")) {
            throw new RabbitApiException("报文解析失败,body:" + body);
        }
        //接口业务异常
        if (!"false".equalsIgnoreCase(rootMap.get("error").toString())) {
            throw new RabbitApiException("接口业务失败,message:" + rootMap.get("message").toString());
        }

        //解析所有pid 需要关闭fastjson针对null值的自动过滤
        Map<?, ?> bodyMap = JSONObject.parseObject(JSONObject.toJSONString(rootMap.get("body"), JSONWriter.Feature.WriteMapNullValue), HashMap.class);
        Map<String, ?> illustsMap = JSONObject.parseObject(JSONObject.toJSONString(bodyMap.get("illusts"), JSONWriter.Feature.WriteMapNullValue), HashMap.class);

        Set<String> tempSet = illustsMap.keySet();
        responseList = new ArrayList<>(tempSet);
    }
}
