package cn.mikulink.rabbitbot.test.apirequest;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.*;
import cn.mikulink.rabbitbot.apirequest.weibo.WeiboHomeTimelineGet;
import cn.mikulink.rabbitbot.apirequest.weibo.WeiboImageUpload;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.ImageUploadResponseInfo;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;
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
            request.setAccessToken("token");
            request.setPage(1);
            //每次获取最近的5条
            request.setCount(30);
//            request.setSince_id(NumberUtil.toLong(ConstantCommon.common_config.get("sinceId")));

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
            String cookie = "SINAGLOBAL=9512145936146.342.1620702509418; SSOLoginState=1652426469; XSRF-TOKEN=2awcee_BDhgmiAiEhijQXXWh; WBPSESS=kWd5G1Gkgmu41uC779gXBzV0bHKT_4GUe4-OrpJSVtbsuvH45HwUiNYf5aVXSwyJ7SVM4efd-lz-mOk_Y3NeqeSmkZyfvirqnv88Xp3ugAkcoU2uUulywp-Q0gxENxqA-4cl9fhVuXaSYUwRaqzJMA==; _s_tentry=www.baidu.com; UOR=,,www.baidu.com; Apache=1883719981188.6118.1652431476072; ULV=1652431476079:38:2:1:1883719981188.6118.1652431476072:1651817591161; ALF=1683967491; SCF=Apo7cthQLBVq83acSGZfr1juT4Hz5eXTxdN3xFg9-OuvjbulDYnZy7CSqwiVjhLjNjJxEhfBKBx6jSk_EGQDYYI.; SUB=_2A25PemrUDeRhGedI7FoY9ibOyjmIHXVsDtscrDV8PUNbmtAfLVXukW9NVq7Q3ZoESkiXj0q8MP3fk8i0IQTfmaZT; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFKforM0T8bFj0KC6i9qLf55JpX5KzhUgL.Fo2cS0n4SonEeK-2dJLoIEBLxK-L1hML1-zLxK.L1-zLB-2LxK.LBKeL1KnLxKqLBonLB.2t; PC_TOKEN=b839f729ee";
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
