package cn.mikulink.rabbitbot.bot.penguincenter;

import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.bot.penguincenter.entity.NotcieInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMemberInfo;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import cn.mikulink.rabbitbot.service.FreeTimeService;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * MikuLink created in 2025/4/2 11:31
 * For the Reisen
 * notice相关业务 Notice是啥
 */
@Slf4j
@Component
public class NoticeHandle {

    //用于戳一戳的固定回复
    private static final List<String> pokeResult = List.of(
            "?", "戳我干嘛！", "诶嘿，好痒", "噗~", "噗了个噗", "啵~啵~啵~", "有事嘛？", "唔姆", "好啦，不要闹了"
    );

    @Autowired
    private DeepSeekService deepSeekService;
    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private FreeTimeService freeTimeService;
    @Autowired
    private NapCatApi napCatApi;

    public void noticeHandle(String messageBody) {
        NotcieInfo notcieInfo = JSON.parseObject(messageBody, NotcieInfo.class);

        if(notcieInfo.getNoticeType().equals("notify")){
            switch (notcieInfo.getSubType()) {
                case "poke":
                    //戳一戳
                    doPokeBiz(notcieInfo);
                    break;
        }

        if(notcieInfo.getNoticeType().equals("group_recall")){
            //群消息撤回
        }


        }
    }

    /**
     * 戳一戳相关业务
     *
     * @param notcieInfo notcie信息
     */
    public void doPokeBiz(NotcieInfo notcieInfo) {
        //谁戳的
        Long pokerId = notcieInfo.getUserId();
        //戳的谁
        Long targetId = notcieInfo.getTargetId();
        //在哪个群戳的
        Long groupId = notcieInfo.getGroupId();
        //而我踏马是谁
        Long selfId = notcieInfo.getSelfId();

        //戳的兔叽
        if (targetId.equals(selfId)) {
            //概率发生以下随机事件 0.回复一条日常语句 1.AI回复 2.固定随机回复
            int randomNum = RandomUtil.roll(2);
            String resultStr = switch (randomNum) {
                case 0 -> freeTimeService.randomMsg();
                case 1 -> pokeGetAIRelust(groupId, pokerId);
                case 2 -> RandomUtil.rollStrFromList(pokeResult);
                default -> null;
            };

            //保底回复，一般不会出现
            if (null == resultStr) {
                resultStr = "啊这，戳一戳好像出问题了";
            }

            //发送消息
            rabbitBotSender.sendGroupMessageCQ(groupId, resultStr);

            //结束本次处理
            return;
        }

        //戳别人的

    }

    /**
     * ai回复戳一戳业务
     *
     * @param groupId 群号
     * @param pokerId 谁戳的
     * @return ai回复
     */
    private String pokeGetAIRelust(Long groupId, Long pokerId) {
        try {
            //获取戳一戳人信息
            GroupMemberInfo groupMemberInfo = napCatApi.getGroupMemberInfo(groupId, pokerId);
            String userName = groupMemberInfo.getCard();
            if (StringUtil.isEmpty(userName)) {
                userName = groupMemberInfo.getNickname();
            }
            return deepSeekService.aiPoke(userName);
        } catch (Exception ex) {
            log.error("ai回复戳一戳业务异常 pokeGetAIRelust", ex);
            return null;
        }
    }

}
