package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.TarotInfo;
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
 * @date 2021/2/9 13:50
 * for the Reisen
 * <p>
 * 猫罗牌
 * 塔罗牌的变异品种(并不
 */
@Command
public class CatrotCommand implements EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(CatrotCommand.class);

    @Autowired
    private TarotService tarotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("catrot", "猫罗牌");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        String userNick = sender.getNick();
        MessageChain result = MessageUtils.newChain();
        try {
            //抽牌
            TarotInfo tarotInfo = tarotService.getTarot();
            //标记为猫罗牌
            tarotInfo.setCat(true);
            //拼装信息
            MessageChain tarotMsg = tarotService.parseTarotMessage(tarotInfo);
            //请求人昵称
            result = result.plus("[" + userNick + "]\n");
            //拼接所有信息
            result = result.plus(tarotMsg);
            return result;
        } catch (Exception ex) {
            logger.error(ConstantTarot.CATROT_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            return new PlainText(ConstantTarot.CATROT_ERROR_GROUP_MESSAGE);
        }
    }
}
