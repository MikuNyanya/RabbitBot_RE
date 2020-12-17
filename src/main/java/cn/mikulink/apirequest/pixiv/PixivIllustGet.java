package cn.mikulink.apirequest.pixiv;

import cn.mikulink.apirequest.BaseRequest;
import cn.mikulink.entity.pixiv.PixivImageInfo;
import cn.mikulink.utils.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Proxy;

/**
 * create by MikuLink on 2020/12/17 22:10
 * for the Reisen
 * 根据p站图片id获取插画图片信息 尚未找到官方API，或者稳定的第三方，所以使用爬虫
 * https://www.pixiv.net/artworks/82343475
 */
@Setter
@Getter
public class PixivIllustGet extends BaseRequest {
    //根据pid查看图片的页面，get请求，最后需要追加p站图片id
    private static final String URL = "https://www.pixiv.net/artworks/";

    //就一个参数，直接构造里传入
    public PixivIllustGet(Long pixivImgId) {
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
    public void doRequest() throws IOException {
        if (null == pixivId) return;
        //代理
        Proxy proxy = HttpUtil.getProxy();
        //爬虫获取pid图片详情信息
        String pidHtmlStr = HttpUtil.get(URL + pixivId, proxy);
        //使用jsoup解析html
        Document document = Jsoup.parse(pidHtmlStr);

        //这里从页面可以获取到图片详情信息，是一个超长的json字符串
        String pidJsonStr = document.getElementById("meta-preload-data").attr("content");
        //取需要的即可，信息太多，不需要的都不解析
        JSONObject pidInfoJson = JSONObject.parseObject(pidJsonStr);
        //结构特殊，还包含动态字段名称，需要多点步骤处理
        String illustInfoStr = JSONObject.toJSONString(pidInfoJson.get("illust"));
        pidInfoJson = JSONObject.parseObject(illustInfoStr);
        String imageInfoStr = JSONObject.toJSONString(pidInfoJson.get(String.valueOf(pixivId)));

        pixivImageInfo = JSONObject.parseObject(imageInfoStr, PixivImageInfo.class);
    }
}
