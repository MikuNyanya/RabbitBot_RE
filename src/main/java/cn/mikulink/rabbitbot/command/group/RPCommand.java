package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.*;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 每日人品
 */
@Command
public class RPCommand implements GroupCommand {
    public static Map<Long, Integer> MAP_RP = new HashMap<>();
    //极低人品短语<10
    public static final List<String> RP_MSGEX_WTF = Arrays.asList(
            "你这。。。",
            "啊咧，这也太低了点吧",
            "有考虑过去做反向风向标嘛",
            "真可怜，让兔叽抱抱你吧~",
            "这不是。。。兔叽的错"
            );
    //低人品短语10-40
    public static final List<String> RP_MSGEX_LOW = Arrays.asList(
            "虽然是有点低，不过还好啦",
            "其实还可以啦",
            "有点勉强喔",
            "没关系，下次一定更好"
    );
    //一般人品短语40-70
    public static final List<String> RP_MSGEX_NORMAL = Arrays.asList(
            "还是很正常的嘛",
            "中规中矩",
            "平平淡淡才是真",
            "嗯嗯，不错"
            );
    //高人品短语70-90
    public static final List<String> RP_MSGEX_HIGH = Arrays.asList(
            "看起来很不错的样子",
            "说不定真的会有好事发生哦",
            "这次很高哦"
            );
    //极高人品短语>90
    public static final List<String> RP_MSGEX_WOW = Arrays.asList(
            "有考虑过去买张彩票嘛",
            "很高啊，真好",
            "恭喜恭喜~",
            "大吉大利~"
            );

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rp", "RP", "人品");
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        //获取群员Q号
        Long groupUserId = sender.getId();
        String groupUserName = sender.getNameCard();

        //rp
        int rollNum = 0;
        if (MAP_RP.containsKey(groupUserId)) {
            rollNum = MAP_RP.get(groupUserId);
        } else {
            rollNum = RandomUtil.roll();
            MAP_RP.put(groupUserId, rollNum);
        }

        //可以随机点装饰性语句
        String msgEx = getMsgEx(rollNum);
        String resultStr = String.format("【%s】今天的人品值：%s%s", groupUserName, rollNum, msgEx);
        return new PlainText(resultStr);
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx(int rollNum) {
        //固有短语
        if (rollNum <= 0) {
            return ConstantCommon.NEXT_LINE + "这人可真是惨到家了...";
        } else if (rollNum == 9) {
            return ConstantCommon.NEXT_LINE + "baka~";
        }else if(rollNum == 42){
            return ConstantCommon.NEXT_LINE + "嗯，宇宙中任何事情的终极答案";
        }else if (rollNum == 100) {
            return ConstantCommon.NEXT_LINE + "哇，金色传说！";
        }

        //如果不属于上面的情况，则随机出现短语
        //是否附带随机短语
        if(!RandomUtil.rollBoolean(20)){
            return "";
        }

        //获取随机短语
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append(ConstantCommon.NEXT_LINE);
        if(rollNum<=10){
            stringBuilder.append(RandomUtil.rollStrFromList(RP_MSGEX_WTF));
        }else if(rollNum<=40){
            stringBuilder.append(RandomUtil.rollStrFromList(RP_MSGEX_LOW));
        }else if(rollNum<=70){
            stringBuilder.append(RandomUtil.rollStrFromList(RP_MSGEX_NORMAL));
        }else if(rollNum<=90){
            stringBuilder.append(RandomUtil.rollStrFromList(RP_MSGEX_HIGH));
        }else{
            stringBuilder.append(RandomUtil.rollStrFromList(RP_MSGEX_WOW));
        }

        return stringBuilder.toString();
    }
}
