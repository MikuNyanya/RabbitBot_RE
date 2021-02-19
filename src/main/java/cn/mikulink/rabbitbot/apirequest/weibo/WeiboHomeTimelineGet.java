package cn.mikulink.rabbitbot.apirequest.weibo;


import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
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
 * create by MikuLink on 2020/1/8 18:38
 * for the Reisen
 * 获取当前登录用户及其所关注（授权）用户的最新微博
 * https://open.weibo.com/wiki/2/statuses/home_timeline
 */
@Getter
@Setter
public class WeiboHomeTimelineGet extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(WeiboHomeTimelineGet.class);
    private static final String URL = "https://api.weibo.com/2/statuses/home_timeline.json";

    /**
     * 页数
     * 非必填
     * 默认为1
     */
    private Integer page;
    /**
     * 每页大小
     * 非必填
     * 默认为20
     */
    private Integer count;
    /**
     * 若指定此参数，则返回ID比since_id大的微博（即比since_id时间晚的微博）
     * 非必填
     * 默认为0
     * <p>
     * 据观察，该参数类似于时间戳，同样的请求结果，值是固定的，应该是取返回数据里最晚的时间来算出来的值
     */
    private Long since_id;
    /**
     * 过滤类型ID
     * 0：全部、1：原创、2：图片、3：视频、4：音乐
     * 非必填
     * 默认为0
     */
    private Integer feature;
    /**
     * 返回值中user字段开关
     * 0：返回完整user字段、1：user字段仅返回user_id
     * 非必填
     * 默认为0
     */
    private Integer trim_user;


    //执行接口请求
    public void doRequest() throws IOException {
        //拼装参数
        this.addParam();
        //请求

        byte[] responseBytes = HttpsUtil.doGet(URL + HttpUtil.parseUrlEncode(param));
        body = new String(responseBytes);

        //记录接口请求与返回日志
        logger.info(String.format("Api Request WeiboHomeTimelineGet,param:%s,resultBody:%s", JSONObject.toJSONString(param), body));
    }

    //拼装参数
    private void addParam() {
        param.put("access_token", accessToken);
        param.put("page", page);
        param.put("count", count);
        param.put("since_id", since_id);
        param.put("feature", feature);
        param.put("trim_user", trim_user);
    }

    //获取解析后的结果对象
    public InfoWeiboHomeTimeline getEntity() {
        if (StringUtil.isEmpty(body)) {
            return null;
        }
        return JSONObject.parseObject(body, InfoWeiboHomeTimeline.class);
    }
}
