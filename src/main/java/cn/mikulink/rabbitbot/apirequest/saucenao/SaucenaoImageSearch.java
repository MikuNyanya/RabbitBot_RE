package cn.mikulink.rabbitbot.apirequest.saucenao;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchResult;
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
 * create by MikuLink on 2020/2/19 12:38
 * for the Reisen
 * saucenao的图片搜索接口
 * https://saucenao.com/user.php?page=search-api
 */
@Getter
@Setter
public class SaucenaoImageSearch extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(SaucenaoImageSearch.class);
    private static final String URL = "https://saucenao.com/search.php";

    /**
     * 看不懂，大概是特别的搜索条件
     * search a specific index number or all without needing to generate a bitmask.
     */
    private Integer db;
    /**
     * 接口数据格式，一般用json
     * 0=normal html
     * 1=xml api(not implemented)
     * 2=json api
     */
    private Integer output_type = 2;
    /**
     * 搜索结果数目，一般一两个就行，网页上默认6个
     * Change the number of results requested.
     */
    private Integer numres;
    /**
     * 网络图片链接
     */
    private String url;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param));
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request SaucenaoImageSearch,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("api_key", accessToken);
        param.put("db", db);
        param.put("output_type", output_type);
        param.put("numres", numres);
        param.put("url", url);
    }

    //获取解析后的结果对象
    public SaucenaoSearchResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, SaucenaoSearchResult.class);
    }
}
