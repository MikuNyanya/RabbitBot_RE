package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.hutool.http.*;
import cn.mikulink.rabbitbot.bot.penguincenter.entity.BaseRsp;
import cn.mikulink.rabbitbot.bot.penguincenter.entity.GetGroupInfo;
import cn.mikulink.rabbitbot.bot.penguincenter.entity.GetGroupMemberInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMemberInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MikuLink created in 2025/4/7 13:31
 * For the Reisen
 * NapCat组件支持的交互接口
 * <p>
 * 所有api数据转化并返回RabbitBot的本身自带对象，以方便后期换源
 */
@Slf4j
@Component
public class NapCatApi {
    //接口地址
    @Value("${bot.napcat.apiurl:}")
    private String url;

    /**
     * 调用接口
     *
     * @param url      接口完整链接
     * @param jsonBody json参数
     * @return 接口返回参数
     */
    private String httpToNapCatApi(String url, String jsonBody) {
        try {
            HttpRequest httpRequest = HttpUtil.createPost(url);
            httpRequest.contentType(ContentType.JSON.getValue());

            //todo 发送记录落库

            HttpResponse response = httpRequest.timeout(HttpGlobalConfig.getTimeout()).body(jsonBody).execute();
            String responseBody = response.body();

            //todo 返回消息落库

            return responseBody;
        } catch (Exception ex) {
            log.error("httpToNapCatApi error", ex);
            return null;
        }
    }

    /**
     * 发送群消息 消息链形式
     *
     * @param groupId       群号
     * @param messageChains 消息链
     */
    public void sendGroupMessage(Long groupId, List<MessageChain> messageChains) {
        String apiName = "/send_group_msg";

        Map<String, Object> param = new HashMap<>();
        param.put("group_id", groupId);
        param.put("message", messageChains);

        this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
    }

    /**
     * 发送群消息
     * CQ码形式
     *
     * @param groupId   群号
     * @param cqMessage CQ码消息
     */
    public void sendGroupMessageCQ(Long groupId, String cqMessage) {
        String apiName = "/send_group_msg";

        Map<String, Object> param = new HashMap<>();
        param.put("group_id", groupId);
        param.put("message", cqMessage);

        this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
    }

    /**
     * 发送私聊消息
     *
     * @param userId        q号
     * @param messageChains 消息链
     */
    public void sendPrivateMessage(Long userId, List<MessageChain> messageChains) {
        String apiName = "/send_private_msg";

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("message", messageChains);

        this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
    }

    /**
     * 发送私聊消息
     *
     * @param userId    q号
     * @param cqMessage CQ码消息
     */
    public void sendPrivateMessageCQ(Long userId, String cqMessage) {
        String apiName = "/send_private_msg";

        Map<String, Object> param = new HashMap<>();
        param.put("user_id", userId);
        param.put("message", cqMessage);

        this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
    }


    /**
     * 获取群列表
     *
     * @return 群列表
     */
    public List<GroupInfo> getGroupList() {
        return this.getGroupList(null);
    }

    /**
     * 获取群列表
     *
     * @param nextToken 下一页标识
     * @return 群列表
     */
    public List<GroupInfo> getGroupList(String nextToken) {
        String apiName = "/get_group_list";

        Map<String, Object> param = new HashMap<>();
        param.put("next_token", nextToken);

        //解析返回信息
        String body = this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
        BaseRsp baseRsp = JSON.parseObject(body, BaseRsp.class);
        if (null == baseRsp) {
            log.error("获取群列表返回为空");
            return null;
        }
        if (baseRsp.getRetcode() != 0) {
            log.error("获取群列表请求失败,message:{}", baseRsp.getMessage());
        }

        //转化为兔叽用的信息
        List<GetGroupInfo> getGroupInfoList = JSON.parseArray(baseRsp.getData(), GetGroupInfo.class);
        List<GroupInfo> result = new ArrayList<>();
        if (CollectionUtil.isEmpty(getGroupInfoList)) {
            return result;
        }
        for (GetGroupInfo apiGroupInfo : getGroupInfoList) {
            GroupInfo temp = new GroupInfo();
            BeanUtils.copyProperties(apiGroupInfo, temp);
            result.add(temp);
        }
        return result;
    }

    /**
     * 获取群员详细信息
     *
     * @param groupId 群号
     * @param userId  群员号
     * @return 群员信息
     */
    public GroupMemberInfo getGroupMemberInfo(Long groupId, Long userId) {
        String apiName = "/get_group_member_info";

        Map<String, Object> param = new HashMap<>();
        param.put("group_id", groupId);
        param.put("user_id", userId);
        param.put("no_cache", false);

        //解析返回信息
        String body = this.httpToNapCatApi(url + apiName, JSON.toJSONString(param));
        BaseRsp baseRsp = JSON.parseObject(body, BaseRsp.class);
        if (null == baseRsp) {
            log.error("获取群员信息返回为空");
            return null;
        }
        if (baseRsp.getRetcode() != 0) {
            log.error("获取群员信息请求失败,message:{}", baseRsp.getMessage());
        }

        //转化为兔叽用的信息
        GetGroupMemberInfo getGroupMemberInfo = JSON.parseObject(baseRsp.getData(), GetGroupMemberInfo.class);
        if (null == getGroupMemberInfo) {
            return null;
        }
        GroupMemberInfo groupMemberInfo = new GroupMemberInfo();
        BeanUtils.copyProperties(getGroupMemberInfo, groupMemberInfo);
        return groupMemberInfo;
    }

}
