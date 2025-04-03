package cn.mikulink.rabbitbot.apirequest.other;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2022/07/09 13:48
 * for the Reisen
 * 每日新闻
 * http://api.2xb.cn/zaob
 */
@Getter
@Setter
public class ZaobGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(ZaobGet.class);
    private static final String URL = "http://api.2xb.cn/zaob";

    //执行接口请求
    public void doRequest() throws IOException {
        //请求
        //response:{"code":200,"msg":"Success","imageUrl":"https://img03.sogoucdn.com/app/a/200692/621_2948_feedback_faa7dde25b26444882228dd22de6aed1.png","datatime":"2022-07-09"}
        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param), header, null);
    }

    //获取图片链接
    public String getImageUrl() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap || !rootMap.containsKey("imageUrl")) {
            logger.warn("Api ZaobGet getEntity warn,body:{}", body);
            return null;
        }
        return String.valueOf(rootMap.get("imageUrl"));
    }
}
