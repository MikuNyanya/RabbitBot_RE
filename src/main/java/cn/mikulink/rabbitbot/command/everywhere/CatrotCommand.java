package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.TarotInfo;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.TarotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
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
import java.util.HashMap;
import java.util.Map;


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

    //操作间隔 账号，操作时间戳
    public static Map<Long, Long> PIXIV_TAG_SPLIT_MAP = new HashMap<>();
    //操作间隔
    public static final Long PIXIV_TAG_SPLIT_TIME = 1000L * 60;
    public static final String PIXIV_TAG_SPLIT_ERROR = "[%s]%s秒后可以使用猫罗牌";

    @Autowired
    private TarotService tarotService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Override
    public CommandProperties properties() {
        return new CommandProperties("catrot", "猫罗牌");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //操作间隔判断
        String timeCheck = rabbitBotService.commandTimeSplitCheck(PIXIV_TAG_SPLIT_MAP, sender.getId(), sender.getNick(), PIXIV_TAG_SPLIT_TIME, PIXIV_TAG_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return new PlainText(timeCheck);
        }
        //刷新操作间隔
        PIXIV_TAG_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());

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
