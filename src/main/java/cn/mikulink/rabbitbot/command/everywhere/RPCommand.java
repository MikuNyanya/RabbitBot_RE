package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantRP;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.rpg.CharacterStatsService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 每日人品
 */
@Command
public class RPCommand extends EverywhereCommand {
    @Autowired
    private CharacterStatsService characterStatsService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rp", "RP", "人品");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        //获取群员信息
        String groupUserName = messageInfo.getSender().getGroupCardOrUserNick();

        //rp 与人物属性的运气值对齐 问题就是可能不会出现0和100的运气了
        int rp = characterStatsService.getPlayerLUCK(groupUserName);
        //如果是99直接当做100吧，弥补下没有100封顶的遗憾
        if (rp == 99) {
            rp = 100;
        }

        //可以随机点装饰性语句
        String msgEx = getMsgEx(rp);
        String resultStr = String.format("【%s】今天的人品值：%s%s", groupUserName, rp, msgEx);
        return RabbitBotMessageBuilder.createMessageText(resultStr);
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx(int rollNum) {
        //固有短语
        if (rollNum <= 0) {
            return ConstantCommon.NEXT_LINE + "这人可真是惨到家了...";
        } else if (rollNum == 9) {
            return ConstantCommon.NEXT_LINE + "baka~";
        } else if (rollNum == 42) {
            return ConstantCommon.NEXT_LINE + "嗯，宇宙中任何事情的终极答案";
        } else if (rollNum == 100) {
            return ConstantCommon.NEXT_LINE + "哇，金色传说！";
        }

        //如果不属于上面的情况，则随机出现短语
        //是否附带随机短语
        if (!RandomUtil.rollBoolean(-20)) {
            return "";
        }

        //获取随机短语
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ConstantCommon.NEXT_LINE);
        if (rollNum <= 10) {
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_WTF));
        } else if (rollNum <= 40) {
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_LOW));
        } else if (rollNum <= 70) {
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_NORMAL));
        } else if (rollNum <= 90) {
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_HIGH));
        } else {
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_WOW));
        }

        return stringBuilder.toString();
    }
}
