package cn.mikulink.rabbitbot.modules.pixiv.api;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * create by MikuLink on 2020/12/17 22:10
 * for the Reisen
 * 根据p站图片id获取插画图片信息 只能在未登录的情况下使用
 * 登录后页面数据结构会变化，应使用另一个api获取详情信息更为全面方便
 * https://www.pixiv.net/artworks/82343475
 */
@Setter
@Getter
public class PixivIllustGetNoLogin extends BaseRequest {
    //根据pid查看图片的页面，get请求，最后需要追加p站图片id
    private static final String URL = "https://www.pixiv.net/artworks/";

    //就一个参数，直接构造里传入
    public PixivIllustGetNoLogin(Long pixivImgId) {
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
        //获取pid图片详情信息
        byte[] resultBytes = HttpsUtil.doGet(URL + pixivId, header, proxy);
        body = new String(resultBytes);
        //使用jsoup解析html
        Document document = Jsoup.parse(body);

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
