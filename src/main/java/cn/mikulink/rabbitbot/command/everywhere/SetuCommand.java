package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.MirlKoiService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/12/13 15:50
 * for the Reisen
 * <p>
 * 来点色图
 */
@Slf4j
@Command
public class SetuCommand extends EverywhereCommand {
    @Autowired
    private MirlKoiService mirlKoiService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("laidiansetu", "setu", "色图", "涩图", "来点色图", "来涩色图", "来份色图", "来份涩图", "来张色图", "来张涩图", "整点色图");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        List<String> args = getArgs(messageInfo.getRawMessage());

        //检查操作间隔
//        ReString reString = setuService.setuTimeCheck(userId, userNick);
//        if (!reString.isSuccess()) {
//            return RabbitBotMessageBuilder.createMessageText(reString.getMessage());
//        }

        //刷新操作间隔
//        ConstantPixiv.SETU_PID_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());

        //获取指令参数
        Integer setuCount = 1;
        if (CollectionUtil.isNotEmpty(args)) {
            //第一个指令作为色图数量，最少一个，最多5个 参数不合法的时候,使用默认值
            String setuCountStr = args.get(0);
            if (NumberUtil.isNumberOnly(setuCountStr)) {
                setuCount = NumberUtil.toInt(setuCountStr);
            }
            if (setuCount <= 0) {
                setuCount = 1;
            }
            if (setuCount > 5) {
                setuCount = 5;
            }
        }

        try {
            List<String> setuList = mirlKoiService.getSetus(setuCount);
            if (CollectionUtil.isEmpty(setuList)) {
                return RabbitBotMessageBuilder.createMessageText("色图被榨干了");
            }
            List<MessageChain> messageChains = new ArrayList<>();
            for (String setuUrl : setuList) {
                messageChains.add(RabbitBotMessageBuilder.parseMessageChainImage(setuUrl));
            }
            return new MessageInfo(messageChains);
        } catch (Exception ex) {
            log.error("色图指令异常:" + ex.getMessage(), ex);
            return RabbitBotMessageBuilder.createMessageText("已经。。。没有什么色图了。。。");
        }
    }
}
