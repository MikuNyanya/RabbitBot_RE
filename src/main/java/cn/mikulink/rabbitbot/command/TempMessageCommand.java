package cn.mikulink.rabbitbot.command;

import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 适用于临时会话，比如来源于群的非好友私聊
 */
public abstract class TempMessageCommand extends Command {

    /**
     * 具体业务执行入口
     * @param sender    消息发送人
     * @param args      指令追加参数
     * @param messageChain  消息对象 第一个元素一定为 [MessageSource], 存储此消息的发送人, 发送时间, 收信人, 消息 id 等数据. 随后的元素为拥有顺序的真实消息内容.
     * @param subject   消息主体
     * @return 回复的消息内容，返回null不做处理
     */
    public abstract Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Member subject);
}
