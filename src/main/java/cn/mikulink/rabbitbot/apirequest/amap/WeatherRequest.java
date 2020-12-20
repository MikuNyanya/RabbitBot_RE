package cn.mikulink.rabbitbot.apirequest.amap;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.amap.InfoWeather;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/1/21 16:37
 * for the Reisen
 * 高德地图 天气查询接口
 * https://lbs.amap.com/api/webservice/guide/api/weatherinfo
 */
@Getter
@Setter
public class WeatherRequest extends BaseRequest {
    private static final String URL = "https://restapi.amap.com/v3/weather/weatherInfo";

    /**
     * 高德地图appkey
     */
    private String appkey;
    /**
     * 高德地图api的地区代码
     */
    private String cityAcode;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();

        body = new String(HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param)));
    }

    //拼装参数
    private void addParam() {
        param.put("city", cityAcode);
        param.put("key", appkey);
    }

    //获取解析后的结果对象
    public InfoWeather getWeather() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (!rootMap.containsKey("infocode") || !"10000".equals(String.valueOf(rootMap.get("infocode")))) {
            return null;
        }
        String livesStr = String.valueOf(rootMap.get("lives"));
        List<InfoWeather> weatherList = JSONObject.parseArray(livesStr, InfoWeather.class);
        if (null == weatherList || weatherList.size() <= 0) {
            return null;
        }
        return weatherList.get(0);
    }
}
