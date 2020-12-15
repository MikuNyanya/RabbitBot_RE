package cn.mikulink.apirequest.imjad;

import cn.mikulink.apirequest.BaseRequest;
import cn.mikulink.entity.apirequest.imjad.ImjadPixivResult;
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
 * 根据tag获取信息
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadIllustSearch extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(PixivImjadIllustSearch.class);
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
     * 搜索类型
     * tag 非精准tag
     * exact_tag 精准tag
     * text 标题和描述
     */
    private String mode;
    /**
     * 关键词
     */
    private String word;

    //构造
    public PixivImjadIllustSearch() {
    }

    //构造
    public PixivImjadIllustSearch(String mode, String word, int page, int pageSize) {
        this.mode = mode;
        this.word = word;
        this.page = page;
        this.pageSize = pageSize;
    }

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), null);
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request PixivImjadIllustSearch,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：搜索
        param.put("type", "search");
        param.put("page", page);
        param.put("per_page", pageSize);
        param.put("mode", mode);
        param.put("word", word);
    }

    //获取解析后的结果对象
    public ImjadPixivResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivResult.class);
    }
}
