package cn.mikulink.rabbitbot.apirequest.loliconApp;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;

import cn.mikulink.rabbitbot.entity.apirequest.amap.InfoWeather;
import cn.mikulink.rabbitbot.entity.apirequest.lolicon.LoliconImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pixiv站图片推送
 */
@Getter
public class LoliconAppImageGet extends BaseRequest {

    private static final Logger logger = LoggerFactory.getLogger(LoliconAppImageGet.class);
    private static final String URL = "https://api.lolicon.app/setu/";

    @Getter
    @Setter
    private Integer r18 = 0;

    @Getter
    @Setter
    private Integer num = 1;

    /**
     * 请求执行
     *
     * @throws IOException
     */
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //调用
        body = new String(HttpsUtil.doGet(URL+ HttpUtil.parseUrlEncode(param)));

    }

    public LoliconImageInfo getLoliconImageInfo(){
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return  JSONObject.parseObject(body, LoliconImageInfo.class);
    }

    //拼装参数
    private void addParam() {
        param.put("apikey", accessToken);
        param.put("r18", r18);
        param.put("num", num);

    }


}
