package cn.mikulink.rabbitbot.modules.pixiv.api;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2020/12/17 22:10
 * for the Reisen
 * 根据p站图片id获取插画图片详情信息 在最后加一段pages，会只返回图片链接
 * https://www.pixiv.net/ajax/illust/82343475/pages
 */
@Setter
@Getter
public class PixivIllustDetailGet extends BaseRequest {
    //根据pid查看多图，返回的是个常规json
    private static final String URL = "https://www.pixiv.net/ajax/illust/%s";

    //就一个参数，直接构造里传入
    public PixivIllustDetailGet(Long pixivImgId) {
        pixivId = pixivImgId;
    }

    /**
     * p站图片id
     */
    private Long pixivId;

    /**
     * pixiv图片解析后的对象
     */
    private PixivImageInfo pixivImageInfo;

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws RabbitApiException, IOException {
        if (null == pixivId) return;

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
        pixivImageInfo = JSON.parseObject(JSONObject.toJSONString(rootMap.get("body")), PixivImageInfo.class);
    }
}
