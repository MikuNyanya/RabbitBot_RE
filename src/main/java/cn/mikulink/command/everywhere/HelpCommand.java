package cn.mikulink.command.everywhere;

import cn.mikulink.constant.ConstantCommon;
import cn.mikulink.entity.CommandProperties;
import cn.mikulink.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 帮助列表
 */
@Command
public class HelpCommand extends BaseEveryWhereCommand {
    private static String clsMessage = null;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Help", "help");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        StringBuilder msg = new StringBuilder();
        msg.append("这里是[" + ConstantCommon.RABBIT_BOT_NAME + "(" + ConstantCommon.RABBIT_BOT_NAME_EN + ")]，一个居无定所的群机器人\n");
        msg.append("===指令列表===\n");
        msg.append("[.r] 生成一个1-100的随机数\n");
        msg.append("[.rp] 查看今天的人品,每天的人品是固定的\n");
//        msg.append("[.sj] 查看当前时间\n");
//        msg.append("[.say] 主动触发一句日常语句\n");
        msg.append("[.cls] 清屏\n");
        msg.append("[.扭蛋] 就是扭蛋\n");
        msg.append("[.扭蛋 add] 新添加一个扭蛋\n");
        msg.append("[.rpwd] 随机生成一串密码 可指定密码长度\n");
//        msg.append("[.搜图] 以图搜图，ACG向\n");
        msg.append("[.pid] 根据p站图片id获取图片以及信息\n");
        msg.append("[.ptag] 根据p站图片tag随机获取图片\n");
        msg.append("[.puser] 根据p站作者名称随机获取图片\n");
        msg.append("[.摩尔斯] 摩尔斯电码相关\n");
        msg.append("=============\n");
        return new PlainText(msg);
    }

}
