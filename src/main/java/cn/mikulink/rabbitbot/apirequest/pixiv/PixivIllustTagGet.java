package cn.mikulink.rabbitbot.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/12/18 2:10
 * for the Reisen
 * 根据tag获取pixiv插图信息 尚未找到官方API，或者稳定的第三方，所以使用爬虫
 * 如果不登录，有很多功能受限
 * <p>
 * https://www.pixiv.net/ajax/search/illustrations/%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF?word=%E5%88%9D%E9%9F%B3%E3%83%9F%E3%82%AF&order=date_d&mode=all&p=2&s_mode=s_tag_full&type=illust_and_ugoira&lang=zh
 */
public class PixivIllustTagGet extends BaseRequest {
    //根据tag搜索图片
    private static final String URL = "https://www.pixiv.net/ajax/search/illustrations/";

    /**
     * 关键词
     */
    @Setter
    private String word;
    /**
     * 排序方式
     * date_d 从新到旧
     * 其他方式需要登录
     */
    private String order = "date_d";
    /**
     * 可能是涉及R18什么的
     */
    private String mode = "all";
    /**
     * 搜索模式
     */
    private String s_mode = "s_tag_full";
    /**
     * 图片类型
     * illust_and_ugoira 点击插画也是传入的这个，不知道ugoira代表的是什么
     */
    private String type = "illust_and_ugoira";
    /**
     * 第几页，默认为第一页，最大1000页，如果不登录，最大为10页
     * 页书大小不可选，因为是模拟网页访问，
     */
    @Setter
    private int p = 1;

    //排行榜解析结果
    @Getter
    private List<PixivImageInfo> responseList = new ArrayList<>();

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //代理
        Proxy proxy = HttpUtil.getProxy();
        //爬虫获取排行榜信息
        byte[] resultBytes = HttpsUtil.doGet(URL + URLEncoder.encode(word, "UTF-8") + HttpUtil.parseUrlEncode(param), proxy);
        body = new String(resultBytes);
    }

    /**
     * 解析返回报文
     */
    public List<PixivImageInfo> parseImageList() {
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);

        Map<?, ?> bodyMap = JSONObject.parseObject(JSONObject.toJSONString(rootMap.get("body")), HashMap.class);

        Map<?, ?> illustMap = JSONObject.parseObject(JSONObject.toJSONString(bodyMap.get("illust")), HashMap.class);

        //解析结果
        responseList = JSONObject.parseArray(JSONObject.toJSONString(illustMap.get("data")), PixivImageInfo.class);
        return responseList;
    }

    /**
     * 获取搜索结果总数
     */
    public int getTotal() {
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);

        Map<?, ?> bodyMap = JSONObject.parseObject(JSONObject.toJSONString(rootMap.get("body")), HashMap.class);

        Map<?, ?> illustMap = JSONObject.parseObject(JSONObject.toJSONString(bodyMap.get("illust")), HashMap.class);

        return NumberUtil.toInt(illustMap.get("total"));
    }

    //拼装参数
    private void addParam() {
        param.put("word", word);
        param.put("order", order);
        param.put("s_mode", s_mode);
        param.put("mode", mode);
        param.put("type", type);
        param.put("p", p);
    }
}
