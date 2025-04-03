package cn.mikulink.rabbitbot.apirequest.neteaseCloudMusic;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * create by MikuLink on 2022/09/26 15:20
 * for the Reisen
 * 网易云音乐 关键词搜索
 * 使用第三方项目自建api https://github.com/Binaryify/NeteaseCloudMusicApi
 */
@Slf4j
public class NeteaseCloudSearch extends BaseRequest {
    /**
     * 网易云api链接，因为自己搭建的，所以写为配置
     */
    @Setter
    private String url;
    /**
     * 关键词
     * 必填
     */
    @Setter
    private String keywords;
    /**
     * 返回数量
     * 默认30
     */
    @Setter
    private Integer limit = 1;
    /**
     * 偏移量
     * = ( 页数 -1) * limit,
     * 默认0
     */
    @Setter
    private Integer offset;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        if (StringUtil.isEmpty(url)) {
            log.warn("网易云api请求链接未配置");
        }
        //拼装参数
        addParam();
        //拼装url
        String requestUrl = String.format("%s/search", url);
        requestUrl = requestUrl + HttpUtil.parseUrlEncode(param);
        //获取请求结果
        body = HttpUtil.get(requestUrl);
        log.warn("网易云api请求结果："+body);
    }

    /**
     * 解析返回报文
     */
    public NeteaseCloudSearchResponse parseResponseInfo() {
        return JSONObject.parseObject(body, NeteaseCloudSearchResponse.class);
    }

    //拼装参数
    private void addParam() {
        param.put("keywords", keywords);
        param.put("limit", limit);
        if (null != offset) {
            param.put("offset", offset);
        }
    }
}
