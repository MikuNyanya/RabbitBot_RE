package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/12/14 18:50
 * for the Reisen
 * <p>
 * EveryWhereCommand父类
 */
public abstract class BaseEveryWhereCommand implements EverywhereCommand {

    /**
     * 权限验证
     *
     * @param sender       消息发送人
     * @param args         指令追加参数
     * @param messageChain 消息对象 第一个元素一定为 [MessageSource], 存储此消息的发送人, 发送时间, 收信人, 消息 id 等数据. 随后的元素为拥有顺序的真实消息内容.
     * @param subject      消息主体
     * @return 权限验证结果，返回null表示不做校验
     */
    public Message permissionCheck(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        return null;
    }
}
