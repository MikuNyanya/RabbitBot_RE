package cn.mikulink.rabbitbot.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageUrlInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/12/17 22:10
 * for the Reisen
 * 根据p站图片id获取插画图片信息 尚未找到官方API，或者稳定的第三方，所以使用爬虫
 * https://www.pixiv.net/ajax/illust/82343475/pages
 */
@Setter
@Getter
public class PixivIllustPagesGet extends BaseRequest {
    //根据pid查看多图，返回的是个常规json
    private static final String URL = "https://www.pixiv.net/ajax/illust/%s/pages";

    //就一个参数，直接构造里传入
    public PixivIllustPagesGet(Long pixivImgId) {
        pixivId = pixivImgId;
    }

    /**
     * p站图片id
     */
    private Long pixivId;

    /**
     * pixiv图片列表
     */
    private List<PixivImageUrlInfo> responseList;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws RabbitApiException, IOException {
        if (null == pixivId) return;
        //代理
        Proxy proxy = HttpUtil.getProxy();

        //获取pid图片列表 返回的是一个标准的json文本
        byte[] resultBytes = HttpsUtil.doGet(String.format(URL, pixivId), header, proxy);
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

        //解析结果
        responseList = new ArrayList<>();
        List<PixivImageInfo> pixivImageInfos = JSONObject.parseArray(JSONObject.toJSONString(rootMap.get("body")), PixivImageInfo.class);
        for (PixivImageInfo pixivImageInfo : pixivImageInfos) {
            responseList.add(pixivImageInfo.getUrls());
        }
    }
}
