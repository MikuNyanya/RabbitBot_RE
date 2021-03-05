package cn.mikulink.rabbitbot.apirequest.news;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.lolicon.LoliconImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.news.NewsTodayEntity;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NewsTodayGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(NewsTodayGet.class);
    private static final String URL = "https://news.topurl.cn/api";

    @Setter
    @Getter
    private NewsTodayEntity newsTodayEntity;

    public void doRequest() throws IOException {
        byte[] responseBytes = HttpsUtil.doGet(URL);
        body = new String(responseBytes);

    }

    public NewsTodayEntity getNewsTodayEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, NewsTodayEntity.class);
    }

}
