package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import org.junit.Test;

/**
 * created by MikuNyanya on 2021/10/22 14:16
 * For the Reisen
 */
public class MirlKoiService {

    @Test
    public void setuApiTest(){
        try{
//            byte[] responseByte = HttpsUtil.doGet("https://iw233.cn/API/Ghs.php");
//            String body = new String(responseByte);

            String pathStr =ImageUtil.downloadImage("https://iw233.cn/API/Ghs.php","data/images/mirlkoi",System.currentTimeMillis()+".jpg");

            System.out.println("");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
