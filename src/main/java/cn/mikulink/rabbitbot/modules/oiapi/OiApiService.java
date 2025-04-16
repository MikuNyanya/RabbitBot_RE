package cn.mikulink.rabbitbot.modules.oiapi;

import cn.mikulink.rabbitbot.utils.EncodingUtil;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * MikuLink created in 2025/4/17 2:36
 * For the Reisen
 * https://oiapi.net/
 * 很有意思的一些小玩意儿
 */
@Slf4j
@Service
public class OiApiService {

    /**
     * 舔屏幕
     *
     * @param userId 用户id
     * @return 完整api请求url，也可以直接当做图片链接
     */
    public String prprpr(Long userId) {
        //有转义，直接拼接更为稳妥
        return "https://oiapi.net/API/PrPr/?url=http://q2.qlogo.cn/headimg_dl?dst_uin=" + userId + "%26spec=5";
    }

    /**
     * 顶西瓜
     */
    public String topUp(Long userId) {
        return "https://oiapi.net/API/Face_Play?QQ=" + userId;
    }

    /**
     * 吃
     */
    public String suckEat(Long userId) {
        return "https://oiapi.net/API/Face_Suck/?QQ=" + userId;
    }

    /**
     * 摸摸
     */
    public String petpet(Long userId) {
        return "https://oiapi.net/API/Face_Petpet?QQ=" + userId;
    }

    /**
     * 捣年糕
     */
    public String pound(Long userId) {
        return "https://oiapi.net/API/Face_Pound?QQ=" + userId;
    }

    /**
     * 随机的疯狂星期四文案
     *
     * @return 疯狂星期四文案
     */
    public String kfcV50() throws IOException {
        return HttpUtil.get("https://oiapi.net/API/KFC?type=text");
    }

    public String emojiMix() {
        //todo 查看上报报文决定
        String url = "https://oiapi.net/API/EmojiMix/%F0%9F%98%93/%F0%9F%98%9B?type=text";
        return null;
    }

    /**
     * 碧蓝档案字体
     *
     * @param startText 前缀部分
     * @param endText   后缀部分
     * @param x         左右坐标，默认-18
     * @param y         上下坐标，默认0
     * @param rgba      背景颜色
     * @return url，可直接当图片链接使用
     */
    public String blueArchiveImage(String startText, String endText, Integer x, Integer y, String rgba) {
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

        //背景颜色 默认white，可选16进制颜色，rgb()，rgba()，颜色英文单词
        if (StringUtil.isNotEmpty(rgba)) {
            url += "&color=" + rgba;
        }

        return url;
    }

}
