package cn.mikulink.rabbitbot.apirequest.bilibili;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.bilibili.BilibiliDynamicSvrResponseInfo;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.Setter;

import java.io.IOException;

/**
 * create by MikuLink on 2021/05/19 16:39
 * for the Reisen
 * B站获取视频动态
 * <p>
 * https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr/dynamic_history?uid=127879&type_list=8,512,4097,4098,4099,4100,4101&offset_dynamic_id=524978607124101592
 */
public class BilibiliDynamicSvrGet extends BaseRequest {
    private static final String URL = "https://api.vc.bilibili.com/dynamic_svr/v1/dynamic_svr";

    /**
     * 用户uid
     * 似乎不传也行，应该是从cookie判别身份的
     */
    @Setter
    private Long uid;
    /**
     * 最后一个动态的dynamic_id
     * 传入此参数会获取dynamic_id不高于该值的动态
     * 不传的话，会获取最新的一页
     * 一页20个
     */
    @Setter
    private Long offsetDynamicId;

    private static final String typeList = "8,512,4097,4098,4099,4100,4101";

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //拼装url
        String requestUrl = URL;
        if (null == offsetDynamicId) {
            requestUrl = URL + "/dynamic_new";
        } else {
            requestUrl = URL + "/dynamic_history";
        }
        requestUrl = requestUrl + HttpUtil.parseUrlEncode(param);
        //获取视频动态
        byte[] resultBytes = HttpsUtil.doGet(requestUrl, this.getHeader(), null);
        body = new String(resultBytes);
    }

    /**
     * 解析返回报文
     */
    public BilibiliDynamicSvrResponseInfo parseResponseInfo() {
        return JSONObject.parseObject(body, BilibiliDynamicSvrResponseInfo.class);
    }

    //拼装参数
    private void addParam() {
        param.put("uid", uid);
        param.put("type_list", typeList);
        param.put("offset_dynamic_id", offsetDynamicId);
    }
}
