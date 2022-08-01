package cn.mikulink.rabbitbot.apirequest.soyiji;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.soyiji.SoyijiResponseInfo;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by MikuLink on 2021/11/1 13:48
 * for the Reisen
 * 易即今日api
 */
@Getter
@Setter
public class SoyijiGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(SoyijiGet.class);
    private static final String URL = "http://118.31.18.68:8080/news/api/news-file/get";

    //执行接口请求
    public void doRequest() throws IOException {
        //请求
        body = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param), header, null);
    }

    //获取图片链接
    public SoyijiResponseInfo getResponseInfo() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }

        return JSONObject.parseObject(body, SoyijiResponseInfo.class);
    }
}
