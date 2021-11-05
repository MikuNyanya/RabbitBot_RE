package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantRP;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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

    @Override
    public CommandProperties properties() {
        return new CommandProperties("rp", "RP", "人品");
    }

    @Override
    public Message permissionCheck(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        return null;
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        //获取群员Q号
        Long groupUserId = sender.getId();
        String groupUserName = sender.getNameCard();
        if(StringUtil.isEmpty(groupUserName)){
            groupUserName = sender.getNick();
        }

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
        if(!RandomUtil.rollBoolean(-20)){
            return "";
        }

        //获取随机短语
        StringBuilder stringBuilder= new StringBuilder();
        stringBuilder.append(ConstantCommon.NEXT_LINE);
        if(rollNum<=10){
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_WTF));
        }else if(rollNum<=40){
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_LOW));
        }else if(rollNum<=70){
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_NORMAL));
        }else if(rollNum<=90){
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_HIGH));
        }else{
            stringBuilder.append(RandomUtil.rollStrFromList(ConstantRP.RP_MSGEX_WOW));
        }

        return stringBuilder.toString();
    }
}
