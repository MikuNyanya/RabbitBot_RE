package cn.mikulink.rabbitbot.apirequest.weibo;


import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.ImageUploadResponseInfo;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by MikuLink on 2022/05/13 16:53
 * for the Reisen
 * 微博图床
 */
@Getter
@Setter
public class WeiboImageUpload extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(WeiboImageUpload.class);
    private static final String URL = "https://picupload.weibo.com/interface/pic_upload.php?ori=1&mime=image%2Fjpeg&data=base64&url=0&markpos=1&logo=&nick=0&marks=1&app=miniblog";

    //图片的base64编码
    private String base64Image;
    private String fileSuffix = "jpg";

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        HttpRequest httpRequest = HttpUtil.createPost(URL);
        httpRequest.contentType(ContentType.MULTIPART.getValue());
        httpRequest.header("Cookie", header.get("Cookie"));

        HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).form(param).execute();
        body = response.body();

        //记录接口返回日志
        logger.info(String.format("Api Request WeiboImageUpload,resultBody:%s", body));
    }

    //拼装参数
    private void addParam() {
        param.put("b64_data", base64Image);
    }

    //获取图片链接
    public String getImageUrl() {
        String spStr = "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <script type=\"text/javascript\">document.domain=\"sina.com.cn\";</script> ";
        String tempBody = body.substring(spStr.length());

        //https://wx1.sinaimg.cn/mw2000/64130597ly1h26tbf94p3j20e80e80tf.jpg
        //https://tvax1.sinaimg.cn/large/64130597ly1h26vig06dbj20qc119e82.png
        ImageUploadResponseInfo rspInfo = JSONObject.parseObject(tempBody, ImageUploadResponseInfo.class);
        return "https://tvax1.sinaimg.cn/large/" + rspInfo.getData().getPics().getPic1().getPid() + "." + fileSuffix;
    }
}
