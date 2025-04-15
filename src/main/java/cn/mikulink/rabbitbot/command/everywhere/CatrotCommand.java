package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.TarotInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.TarotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2021/2/9 13:50
 * for the Reisen
 * <p>
 * 猫罗牌
 * 塔罗牌的变异品种(并不
 */
@Slf4j
@Command
public class CatrotCommand extends EverywhereCommand {

    @Autowired
    private TarotService tarotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("catrot", "猫罗牌");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        String userNick = messageInfo.getSender().getGroupCardOrUserNick();

        try {
            //抽牌
            TarotInfo tarotInfo = tarotService.getTarot();
            //标记为猫罗牌
            tarotInfo.setCat(true);
            //拼装信息
            List<MessageChain> tarotMsgChain = tarotService.parseTarotMessage(tarotInfo);
            //请求人昵称
            MessageChain userNameMsgChain = RabbitBotMessageBuilder.parseMessageChainText("[" + userNick + "]\n");
            //拼接所有信息
            List<MessageChain> messageChainList = new ArrayList<>();
            messageChainList.add(userNameMsgChain);
            messageChainList.addAll(tarotMsgChain);

            return new MessageInfo(messageChainList);
        } catch (Exception ex) {
            log.error(ConstantTarot.CATROT_ERROR_GROUP_MESSAGE, ex);
            return RabbitBotMessageBuilder.createMessageText(ConstantTarot.CATROT_ERROR_GROUP_MESSAGE);
        }
    }
}
