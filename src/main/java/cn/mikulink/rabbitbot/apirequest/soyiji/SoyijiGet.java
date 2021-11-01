package cn.mikulink.rabbitbot.apirequest.soyiji;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2021/11/1 13:48
 * for the Reisen
 * 易即今日api
 * https://shimo.im/docs/kRy9yp6PdJD89ppV/read
 */
@Getter
@Setter
public class SoyijiGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(SoyijiGet.class);
    private static final String URL = "http://api.soyiji.com//news_jpg";

    //执行接口请求
    public void doRequest() throws IOException {
        //请求
        //response:{"url": "http://news.soyiji.com/29641-2021-11-01.jpg"}
        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param), header, null);
    }

    //获取图片链接
    public String getImageUrl() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap || !rootMap.containsKey("url")) {
            logger.warn("Api SoyijiGet getEntity warn,body:{}", body);
            return null;
        }
        return String.valueOf(rootMap.get("url"));
    }
}
