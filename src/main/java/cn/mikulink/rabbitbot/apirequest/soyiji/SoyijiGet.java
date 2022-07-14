package cn.mikulink.rabbitbot.apirequest.soyiji;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    //    private static final String URL = "http://api.soyiji.com//news_jpg";
    private static final String URL = "http://118.31.18.68:8080/news/api/news-file/get";

    //执行接口请求
    public void doRequest() throws IOException {
        //请求
        //response:{"url": "http://news.soyiji.com/29641-2021-11-01.jpg"}
        //{"success":true,"message":"查询成功","code":200,"result":["http://118.31.18.68:8080/yiji/f40d2dd3489f42968fb5fe8168caab9c.jpg"],"timestamp":1657793459253}
        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param), header, null);
    }

    //获取图片链接
    public String getImageUrl() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap || !rootMap.containsKey("result")) {
            logger.warn("Api SoyijiGet getImageUrl warn,body:{}", body);
            return null;
        }
        List<String> results = JSONObject.parseArray(String.valueOf(rootMap.get("result")),String.class);
        if (CollectionUtil.isEmpty(results)) {
            logger.warn("Api SoyijiGet results is empty,body:{}", body);
            return null;
        }

        return results.get(0);
    }
}
