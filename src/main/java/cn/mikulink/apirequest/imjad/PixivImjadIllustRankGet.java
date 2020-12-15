package cn.mikulink.apirequest.imjad;

import cn.mikulink.apirequest.BaseRequest;
import cn.mikulink.entity.apirequest.imjad.ImjadPixivRankResult;
import cn.mikulink.utils.HttpUtil;
import cn.mikulink.utils.HttpsUtil;
import cn.mikulink.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * 获取pixiv插画排行
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadIllustRankGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(PixivImjadIllustRankGet.class);
    private static final String URL = "https://api.imjad.cn/pixiv/v1/";

    /**
     * 页数
     */
    private Integer page;
    /**
     * 每页大小
     */
    private Integer pageSize;
    /**
     * 类型 illust 代表插画
     */
    private String content;
    /**
     * 排行类型
     * daily 日榜
     * weekly 月榜
     */
    private String mode;
    /**
     * 日期
     * 每天0点-12点左右获取不到昨日的排行
     * 这时候可以获取前一天的
     * yyyy-MM-dd
     */
    private String date;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), null);
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request PixivImjadIllustRankGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：热榜
        param.put("type", "rank");
        param.put("page", page);
        param.put("per_page", pageSize);
        param.put("mode", mode);
        param.put("content", content);
        param.put("date", date);
    }

    //获取解析后的结果对象
    public ImjadPixivRankResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivRankResult.class);
    }
}
