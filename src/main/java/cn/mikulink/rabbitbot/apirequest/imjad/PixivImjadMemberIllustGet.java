package cn.mikulink.rabbitbot.apirequest.imjad;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.imjad.ImjadPixivResult;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2020/04/29 13:38
 * for the Reisen
 * 根据p站用户id获取作品列表
 * https://api.imjad.cn/
 * https://zhuanlan.zhihu.com/p/35243511
 */
@Getter
@Setter
public class PixivImjadMemberIllustGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(PixivImjadMemberIllustGet.class);
    private static final String URL = "https://api.imjad.cn/pixiv/v1/";

    /**
     * p站用户id
     */
    private Long memberId;
    /**
     * 页数
     * 默认为1
     */
    private int page = 1;
    /**
     * 每页大小
     * 接口默认30，设置10条够用了
     */
    private int pageSize = 10;

    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //请求
        byte[] resultBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param), null);
        body = new String(resultBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request PixivImjadMemberIllustGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        //类型：用户插画
        param.put("type", "member_illust");
        param.put("id", memberId);
        param.put("page", page);
        param.put("per_page", pageSize);
    }

    //获取解析后的结果对象
    public ImjadPixivResult getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, ImjadPixivResult.class);
    }

    public boolean isSuccess() {
        if (StringUtil.isEmpty(body)) {
            return false;
        }
        Map<?, ?> rootMap = JSONObject.parseObject(body, HashMap.class);
        if (null == rootMap
                || !rootMap.containsKey("status")
                || !"success".equalsIgnoreCase(String.valueOf(rootMap.get("status")))) {
            return false;
        }
        return true;
    }
}
