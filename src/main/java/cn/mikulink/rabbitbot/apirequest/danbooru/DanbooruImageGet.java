package cn.mikulink.rabbitbot.apirequest.danbooru;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.DanbooruImageInfo;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

/**
 * create by MikuLink on 2020/12/18 3:10
 * for the Reisen
 * 根据图片id，从danbooru获取图片信息
 * https://danbooru.donmai.us/posts/4239811
 * https://danbooru.donmai.us/posts/4239811.json
 */
public class DanbooruImageGet extends BaseRequest {
    //单张图片页面链接
    private static final String URL = "https://danbooru.donmai.us/posts/";

    /**
     * danbooru图片id
     */
    @Setter
    @Getter
    private String danbooruId;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        //通过请求获取到图片json数据，这是由官方提供的渠道
        HttpRequest httpRequest = HttpUtil.createGet(URL + danbooruId + ".json");
        HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).setProxy(proxy).execute();
        this.body = response.body();
    }

    /**
     * 解析图片信息
     */
    public DanbooruImageInfo parseDanbooruImageInfo() {
        if (StringUtil.isEmpty(this.body)) {
            return null;
        }
        return JSONObject.parseObject(this.body, DanbooruImageInfo.class);
    }

}
