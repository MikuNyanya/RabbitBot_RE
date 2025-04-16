package cn.mikulink.rabbitbot.test.apirequest;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.weibo.WeiboHomeTimelineGet;
import cn.mikulink.rabbitbot.apirequest.weibo.WeiboImageUpload;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.ImageUploadResponseInfo;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import com.alibaba.fastjson2.JSONObject;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * created by MikuNyanya on 2022/2/23 15:43
 * For the Reisen
 */
public class WeiboApiTest {
    @Test
    public void test() {
        try {

            WeiboHomeTimelineGet request = new WeiboHomeTimelineGet();
            request.setAccessToken("2.00Vfa21312asdffdd19asdfhC");
            request.setPage(1);
            //每次获取最近的5条
            request.setCount(5);
//            request.setSince_id(NumberUtil.toLong(ConstantCommon.common_config.get("4856051796541578")));

            //发送请求
            request.doRequest();
            InfoWeiboHomeTimeline response = request.getEntity();


            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void WeiboImageUploadTest() {
        try {
            String url = "https://picupload.weibo.com/interface/pic_upload.php?ori=1&mime=image%2Fjpeg&data=base64&url=0&markpos=1&logo=&nick=0&marks=1&app=miniblog";
            String cookie = "cookie";
            String localFilePath = "E://害怕2.gif";

            File imageLocal = new File(localFilePath);

            String fileSuffix = FileUtil.getFileSuffix(localFilePath).replace(".", "");
            BufferedImage image = ImageIO.read(imageLocal);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, fileSuffix, out);
            byte[] imgBytes = out.toByteArray();

            String base64Image = Base64.encode(imgBytes);


            Map<String, Object> params = new HashMap<>();
            params.put("b64_data", base64Image);

            HttpRequest httpRequest = HttpUtil.createPost(url);
            httpRequest.contentType(ContentType.MULTIPART.getValue());
            httpRequest.header("Cookie", cookie);

            HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).form(params).execute();
            String rspBody = response.body();
            System.out.println(rspBody);
            String sp_1 = "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                    "    <script type=\"text/javascript\">document.domain=\"sina.com.cn\";</script> ";

            rspBody = rspBody.substring(sp_1.length());

            //https://wx1.sinaimg.cn/mw2000/64130597ly1h26tbf94p3j20e80e80tf.jpg
            //https://tvax1.sinaimg.cn/large/64130597ly1h26vig06dbj20qc119e82.png
            ImageUploadResponseInfo rspInfo = JSONObject.parseObject(rspBody, ImageUploadResponseInfo.class);
            String imageurl = "https://tvax1.sinaimg.cn/large/" + rspInfo.getData().getPics().getPic1().getPid() + "." + fileSuffix;

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    @Test
    public void WeiboImageUploadApiTest() {
        try {

            String cookie = "cookie";
            String localFilePath = "E://9.jpg";

            File imageLocal = new File(localFilePath);

            String fileSuffix = FileUtil.getFileSuffix(localFilePath).replace(".", "");
            BufferedImage image = ImageIO.read(imageLocal);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, fileSuffix, out);
            byte[] imgBytes = out.toByteArray();

            String base64Image = Base64.encode(imgBytes);

            WeiboImageUpload request = new WeiboImageUpload();
            request.setBase64Image(base64Image);
            request.setFileSuffix(fileSuffix);
            request.getHeader().put("Cookie", cookie);

            request.doRequest();

            String body = request.getBody();
            String imageurl = request.getImageUrl();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
