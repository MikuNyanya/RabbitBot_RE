package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.historyToday.HistoryTodayGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class HistoryTodayService {
    private static final Logger logger = LoggerFactory.getLogger(HistoryTodayService.class);

    public String getHistoryToday() throws IOException {
        HistoryTodayGet request = new HistoryTodayGet();
        request.doRequest();
        return request.getBody();
    }
}
