package cn.mikulink.rabbitbot.apirequest.historyToday;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;

import cn.mikulink.rabbitbot.utils.HttpsUtil;


import java.io.IOException;

public class HistoryTodayGet extends BaseRequest {
    String url = "https://www.ipip5.com/today/api.php?type=txt";



    public void doRequest() throws IOException {
        byte[] responseBytes = HttpsUtil.doGet(url);
        body = new String(responseBytes);
        body = body.substring(0,body.lastIndexOf("\n"));
    }

}
