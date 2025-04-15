package cn.mikulink.rabbitbot.service;


import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author MikuLink
 * @date 2022/9/25 0:17
 * for the Reisen
 */
@Slf4j
@Service
public class MirlKoiService {

    @Value("${mirlkoi.setu.url:}")
    private String mirlkoiSetuUrl;

    /**
     * 获取指定数量的随机色图
     * 微博图片可直接给NT，不用防盗链
     */
    public List<String> getSetus(Integer count) throws IOException {
        String body = null;
        String url = mirlkoiSetuUrl + "&num=" + count;
        if (mirlkoiSetuUrl.startsWith("https")) {
            byte[] response = HttpsUtil.doGet(url);
            body = new String(response);
        } else {
            body = HttpUtil.get(url);
        }

        Map<String, String> bodyMap = JSON.parseObject(body, HashMap.class);
        return JSON.parseArray(String.valueOf(bodyMap.get("pic")), String.class);
    }

    /**
     * 获取一张色图
     */
    public String getASetu() throws IOException {
        List<String> setuList = this.getSetus(1);
        return CollectionUtil.isEmpty(setuList) ? null : setuList.get(0);
    }
}
