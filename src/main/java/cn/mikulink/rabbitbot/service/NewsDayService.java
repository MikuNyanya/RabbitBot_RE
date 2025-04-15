package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.other.ZaobGet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * create by MikuLink on 2021/3/3 09:55
 * for the Reisen
 * 每日新闻
 */
@Slf4j
@Service
public class NewsDayService {
    /**
     * 通过api获取每日新闻图片url
     */
    public String getZaobNews() {
        try {
            ZaobGet request = new ZaobGet();
            request.doRequest();
            return request.getImageUrl();

        } catch (Exception ex) {
            log.error("每日新闻图片获取异常", ex);
            return null;
        }
    }

}
