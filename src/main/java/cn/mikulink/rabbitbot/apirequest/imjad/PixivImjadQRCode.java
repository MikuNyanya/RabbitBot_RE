package cn.mikulink.rabbitbot.apirequest.imjad;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.imjad.ImjadQRCode;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by MikuLink on 2020/3/31 12:38
 * for the Reisen
 * 生成二维码
 * https://api.imjad.cn/qrcode.md
 */
@Getter
@Setter
public class PixivImjadQRCode extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(PixivImjadQRCode.class);
    private static final String URL = "https://api.imjad.cn/qrcode/";

    /**
     * 二维码内容
     * 最大 512Byte
     * 需要使用utf-8字符集并进行urlencode
     */
    private String text;
    /**
     * 二维码中间logo的地址
     * 最大200KiB
     * 需要网络图片地址
     */
    private String logo;
    /**
     * 二维码大小
     * 单位px
     * 最大500
     * 默认200
     */
    private int size = 200;
    /**
     * 冗余度
     * 可用值为 L/M/Q/H
     * 默认为M
     * 就是图片复杂度
     * Q看起来比较常规
     */
    private String level;
    /**
     * 背景色
     * 十六进制
     * #FFFFFF
     */
    private String bgcolor;
    /**
     * 前景色
     * 十六进制
     * #000000
     */
    private String fgcolor;
    /**
     * 返回数据格式
     * raw	返回 MIME 类型为 image/png 的图像数据
     * json	返回 JSON 格式数据
     * js	返回函数名为 qrcode 的 JavaScript 脚本，用于同步调用
     * jsc	返回指定 CallBack 函数名的 JavaScript 脚本，可用于异步调用
     * <p>
     * 其他看不懂，选json吧
     */
    private String encode = "json";

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), null);
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request PixivImjadQrcode,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：搜索
        param.put("text", text);
        param.put("logo", logo);
        param.put("size", size);
        param.put("level", level);
        param.put("bgcolor", bgcolor);
        param.put("fgcolor", fgcolor);
        param.put("encode", encode);
    }

    //获取解析后的结果对象
    public ImjadQRCode getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadQRCode.class);
    }
}
