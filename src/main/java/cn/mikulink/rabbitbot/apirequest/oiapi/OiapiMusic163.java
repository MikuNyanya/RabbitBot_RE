package cn.mikulink.rabbitbot.apirequest.oiapi;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.oiapi.Music163Response;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * create by MikuLink on 2025/04/07 01:20
 * for the Reisen
 * 网易云音乐 关键词搜索
 */
@Slf4j
public class OiapiMusic163 extends BaseRequest {
    private static final String url = "https://oiapi.net/API/Music_163";
    /**
     * 关键词
     */
    @Setter
    private String name;
    /**
     * 歌曲序号
     * 可视为搜索结果第几个
     * 不填写则会返回列表
     * <p>
     * 会导致返回的格式从列表变为单个对象
     * 而目前业务中并未有返回列表的需求，所以定死此参数
     */
    private static final Integer n = 1;
    /**
     * 歌曲id
     */
    @Setter
    private Long id;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //拼装url
        String requestUrl = url + HttpUtil.parseUrlEncode(param);
        //获取请求结果
        body = HttpUtil.get(requestUrl);
    }

    /**
     * 解析返回报文
     */
    public Music163Response parseResponseInfo() {
        JSONObject jsonObject = JSON.parseObject(body);
        return JSONObject.parseObject(jsonObject.get("data").toString(), Music163Response.class);
    }

    //拼装参数
    private void addParam() {
        param.put("name", name);
        param.put("n", n);
        param.put("id", id);
    }
}
