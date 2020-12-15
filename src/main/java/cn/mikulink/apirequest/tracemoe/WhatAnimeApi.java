package cn.mikulink.apirequest.tracemoe;

import cn.mikulink.apirequest.BaseRequest;
import cn.mikulink.entity.apirequest.tracemoe.WhatAnimeResult;
import cn.mikulink.utils.HttpUtil;
import cn.mikulink.utils.HttpsUtil;
import cn.mikulink.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 以图搜番
 * 官网https://trace.moe/
 * 官方文档 https://soruly.github.io/trace.moe/#/
 */
@Getter
@Setter
public class WhatAnimeApi extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(WhatAnimeApi.class);
    private static final String URL = "https://trace.moe/api/search";

    /**
     * 图片链接
     */
    private String imgUrl;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param));
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request WhatAnimeApi,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("url", imgUrl);
    }

    //获取解析后的结果对象
    public WhatAnimeResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, WhatAnimeResult.class);
    }
}
