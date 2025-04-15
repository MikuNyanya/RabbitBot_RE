package cn.mikulink.rabbitbot.command.everywhere.rpg;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.SenderInfo;
import cn.mikulink.rabbitbot.entity.rpg.PlayerCharacterStats;
import cn.mikulink.rabbitbot.service.rpg.CharacterStatsService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * roll点，与属性相关
 */
@Command
public class RollCommand extends EverywhereCommand {
    @Autowired
    private CharacterStatsService characterStatsService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("roll", "roll");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        ArrayList<String> args = super.getArgs(messageInfo.getRawMessage());
        SenderInfo sender = messageInfo.getSender();

        //返回说明
        if (args.size() <= 0) {
            return RabbitBotMessageBuilder.createMessageText(ConstantRPG.EXPLAIN);
        }
        //发送人名称
        String userName = sender.getGroupCardOrUserNick();
        //输入的指令
        String commandParam = args.get(0).toUpperCase();
        PlayerCharacterStats userStat = characterStatsService.parseCharacterStatsList(userName);
        //属性值
        Integer statsValue = 0;
        //运气 null 代表没有使用运气修正
        Integer luckValue = null;
        //根据传入的指令来计算对应的
        //先暴力列举吧，实现为上
        switch (commandParam) {
            case ConstantRPG.STR:
                statsValue = userStat.getStr();
                break;
            case ConstantRPG.DEX:
                statsValue = userStat.getDex();
                break;
            case ConstantRPG.INTE:
                statsValue = userStat.getInte();
                break;
            case ConstantRPG.LSTR:
                statsValue = userStat.getStr();
                luckValue = userStat.getLuck();
                break;
            case ConstantRPG.LDEX:
                statsValue = userStat.getDex();
                luckValue = userStat.getLuck();
                break;
            case ConstantRPG.LINTE:
                statsValue = userStat.getInte();
                luckValue = userStat.getLuck();
                break;
            default:
                return RabbitBotMessageBuilder.createMessageText(ConstantRPG.COMMON_PARAM_ERROR);
        }
        int roll = characterStatsService.rollD(statsValue, luckValue);

        //附加文本
        StringBuilder exText = new StringBuilder();
        if (args.size() >= 2) {
            for (int i = 1; i < args.size(); i++) {
                if (exText.length() <= 0) {
                    exText.append(" ");
                }
                exText.append(args.get(i));
            }
        }

        //【群员名称】附加文本 roll[属性(属性值 运气)]=roll结果
        String resultStr = String.format("[%s] %s roll %s(%s) = %s (%s)",
                userName,
                exText,
                commandParam,
                null == luckValue ? statsValue : statsValue + " " + luckValue,
                roll,
                parseSuccessText(roll));
        return RabbitBotMessageBuilder.createMessageText(resultStr);
    }

    //生成成功与否文本
    private String parseSuccessText(int roll) {
        if (roll >= 97) {
            return "大成功";
        }
        if (roll >= 60) {
            return "成功";
        }
        if (roll >= 4) {
            return "失败";
        }
        return "大失败";
    }
}
