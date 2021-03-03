package cn.mikulink.rabbitbot.apirequest.weixin;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.entity.apirequest.weixin.WeiXinAppMsgInfo;
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
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2021/3/3 09:05
 * for the Reisen
 * 获取微信公众号信息
 * https://mp.weixin.qq.com/cgi-bin/appmsg?action=list_ex&begin=0&count=5&fakeid=MzI4NzcwNjY4NQ==&type=9&query=&token=token&lang=zh_CN&f=json&ajax=1
 */
@Getter
@Setter
public class WeixinAppMsgGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(WeixinAppMsgGet.class);
    private static final String URL = "https://mp.weixin.qq.com/cgi-bin/appmsg";

    /**
     * 表明是获取列表的操作
     */
    private String action = "list_ex";
    /**
     * 获取数量相关
     * 不知道如何计算的，用这两个参数返回了20条
     */
    private Integer begin = 0;
    private Integer count = 5;
    /**
     * 公众号id
     * 易即今日 MzI4NzcwNjY4NQ==
     */
    private String fakeid;
    /**
     * (未知参数)
     */
    private String type = "9";
    /**
     * 语言种类
     */
    private String lang = "zh_CN";
    /**
     * 数据格式
     */
    private String f = "json";
    /**
     * (未知参数)
     */
    private String ajax = "1";
    /**
     * cookie
     */
    private String cookie;


    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        header.put("cookie", cookie);
        //请求
        byte[] responseBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), header, null);
        body = new String(responseBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request WeixinAppMsgGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("action", action);
        param.put("begin", begin);
        param.put("count", count);
        param.put("fakeid", fakeid);
        param.put("type", type);
        param.put("token", accessToken);
        param.put("lang", lang);
        param.put("f", f);
        param.put("ajax", ajax);
    }

    //获取解析后的结果对象
    public List<WeiXinAppMsgInfo> getMsgList() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap || !rootMap.containsKey("app_msg_list")) {
            logger.warn("Api WeixinAppMsgGet getEntity warn,no app_msg_list,body:{}", body);
            return null;
        }
        String appMsgListStr = JSONObject.toJSONString(rootMap.get("app_msg_list"));
        return JSONObject.parseArray(appMsgListStr,WeiXinAppMsgInfo.class);
    }
}
