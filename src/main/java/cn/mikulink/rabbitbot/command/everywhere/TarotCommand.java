package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.TarotInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.TarotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/12/22 15:50
 * for the Reisen
 * <p>
 * 塔罗牌
 * https://www.23luke.com/daaerkanapai/1059.html
 */
@Command
public class TarotCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(TarotCommand.class);

    @Autowired
    private TarotService tarotService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("tarot", "塔罗牌");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        String userNick = rabbitBotService.getUserName(subject, sender);
//        MessageChain result = MessageUtils.newChain();
//        try {
//            //抽牌
//            TarotInfo tarotInfo = tarotService.getTarot();
//            //正常塔罗牌
//            tarotInfo.setCat(false);
//            //拼装信息
//            MessageChain tarotMsg = tarotService.parseTarotMessage(tarotInfo);
//            //请求人昵称
//            result = result.plus("[" + userNick + "]\n");
//            //拼接所有信息
//            result = result.plus(tarotMsg);
//            return result;
//        } catch (Exception ex) {
//            logger.error(ConstantTarot.TAROT_ERROR_GROUP_MESSAGE + ex.toString(), ex);
//            return new PlainText(ConstantTarot.TAROT_ERROR_GROUP_MESSAGE);
//        }
        return null;
    }
}
