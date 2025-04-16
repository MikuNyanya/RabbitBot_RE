package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.modules.oiapi.OiApiService;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MikuLink created in 2025/4/17 4:31
 * For the Reisen
 * at相关业务
 */
@Slf4j
@Service
public class AtService {

    @Autowired
    private OiApiService oiApiService;

    public GroupMessageInfo doAtBiz(GroupMessageInfo groupMessageInfo) {
        try {
            if (null == groupMessageInfo
                    || CollectionUtil.isEmpty(groupMessageInfo.getMessage())
                    || !groupMessageInfo.getMessage().get(0).getType().equals("at")) {
                return null;
            }
            List<MessageChain> messageChainList = groupMessageInfo.getMessage();

            //at的谁
            Long atTargetId = NumberUtil.toLong(messageChainList.get(0).getData().getQq());
            //at后的内容
            String context = null;
            if (messageChainList.size() >= 2) {
                MessageChain atContextInfo = messageChainList.get(1);
                //文本内容
                if (atContextInfo.getType().equals("text")) {
                    context = atContextInfo.getData().getText();
                }
                //图片内容

            }

            //没有内容则略过at业务处理
            if (StringUtil.isEmpty(context)) {
                return null;
            }
            //at人后面会自动跟一个空格
            context = context.trim();

            GroupMessageInfo result = new GroupMessageInfo();
            result.setGroupId(groupMessageInfo.getGroupId());

            //优先进行关键词对应的业务
            MessageChain chain = switch (context) {
                case "舔" -> RabbitBotMessageBuilder.parseMessageChainImage(oiApiService.prprpr(atTargetId));
                case "顶" -> RabbitBotMessageBuilder.parseMessageChainImage(oiApiService.topUp(atTargetId));
                case "吸", "吃" -> RabbitBotMessageBuilder.parseMessageChainImage(oiApiService.suckEat(atTargetId));
                case "摸", "摸摸" -> RabbitBotMessageBuilder.parseMessageChainImage(oiApiService.petpet(atTargetId));
                case "捣" -> RabbitBotMessageBuilder.parseMessageChainImage(oiApiService.pound(atTargetId));
                default -> null;
            };

            //没有信息则表示没有匹配到对应业务
            if (null != chain) {
                result.setMessage(List.of(chain));
                return result;
            }

            //at机器人的业务
            if (groupMessageInfo.atBot()) {
                chain = switch (context) {
                    case "kfc", "KFC", "疯狂星期四" ->
                            RabbitBotMessageBuilder.parseMessageChainText(oiApiService.kfcV50());
                    default -> null;
                };
            }
            if (null != chain) {
                result.setMessage(List.of(chain));
                return result;
            }

        } catch (Exception ex) {
            log.error("at相关业务处理异常" + ex.getMessage(), ex);
        }
        return null;
    }


}
