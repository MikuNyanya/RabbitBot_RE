package cn.mikulink.rabbitbot.test.modules;

import cn.mikulink.rabbitbot.utils.EncodingUtil;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.junit.Test;

/**
 * MikuLink created in 2025/4/17 2:58
 * For the Reisen
 */
public class OiapiTest {

    @Test
    public void KFC() {
        try {
            String rsp = HttpUtil.get("https://oiapi.net/API/KFC?type=text");

            System.out.println(rsp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void blueArchiveImage() {
        try {
            String startText = "兔叽";
            String endText = "可爱";
            Integer x = null;
            Integer y = null;
            String color = null;


            String url = "https://oiapi.net/API/BlueArchive?";

            //开头文本
            if (null != startText) {
                url += "startText=" + EncodingUtil.encodeURIComponent(startText);
            }

            //后续文本
            if (null != endText) {
                url += "&endText=" + EncodingUtil.encodeURIComponent(endText);
            }

            //光环位置x 默认-18
            if (null != x) {
                x = -18;

            }
            url += "&x=" + x;

            //光环位置y 默认0
            if (null != y) {
                y = 0;
            }
            url += "&y=" + y;

            //背景颜色
            if (StringUtil.isNotEmpty(color)) {
                url += "&color=" + color;
            }


            System.out.println(url);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
