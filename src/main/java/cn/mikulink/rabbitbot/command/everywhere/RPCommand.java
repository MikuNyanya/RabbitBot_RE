package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantRP;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.rpg.StatisticsService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 每日人品
 */
@Command
public class RPCommand extends BaseEveryWhereCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private StatisticsService statisticsService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rp", "RP", "人品");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //获取群员信息
        String groupUserName = rabbitBotService.getUserName(subject, sender);

        //rp 与人物属性的运气值对齐 问题就是可能不会出现0和100的运气了
        int rp = statisticsService.getPlayerLUCK(groupUserName);
        //如果是99直接当做100吧，弥补下没有100封顶的遗憾
        if (rp == 99) {
            rp = 100;
        }

        //可以随机点装饰性语句
        String msgEx = getMsgEx(rp);
        String resultStr = String.format("【%s】今天的人品值：%s%s", groupUserName, rp, msgEx);
        return new PlainText(resultStr);
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
