package cn.mikulink.rabbitbot.apirequest.stock;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.apirequest.loliconApp.LoliconAppImageGet;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 查询股市指数
 */
public class StockGet  extends BaseRequest {

    public StockGet(String code){
        this.code = code;
        addParam();
    }
    public static final String url = "https://imgnode.gtimg.cn/hq_img";

    private static final Logger logger = LoggerFactory.getLogger(LoliconAppImageGet.class);
    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private int size;
    @Getter
    @Setter
    private String proj;
    @Getter
    @Setter
    private String sendUrl;




    public void doRequest() throws IOException {
        addParam();
        //调用
        body = new String(HttpsUtil.doGet(url+ HttpUtil.parseUrlEncode(param)));
    }

    public void packageUrl() throws IOException{
        addParam();
        sendUrl = url+ HttpUtil.parseUrlEncode(param);
        System.out.println(sendUrl);
    }

    //拼装参数
    private void addParam() {
        param.put("code", code);
        param.put("type", "minute");
        param.put("size", "3");
        param.put("proj", "news");

    }


}
