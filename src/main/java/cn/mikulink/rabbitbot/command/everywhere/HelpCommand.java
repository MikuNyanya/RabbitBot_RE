package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Value;

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

    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    //RabbitBot
    @Value("${bot.name.en:RabbitBot}")
    public String rabbit_bot_name_en;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Help", "help");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        StringBuilder msg = new StringBuilder();
        msg.append("这里是[").append(rabbit_bot_name).append("(").append(rabbit_bot_name_en).append(")]，一个居无定所的群机器人\n");
        msg.append("===指令列表===\n");
        msg.append("[.r] 生成一个1-100的随机数\n");
        msg.append("[.rp] 查看今天的人品,每天的人品是固定的\n");
        msg.append("[.扭蛋] 就是扭蛋\n");
        msg.append("[.rpwd] 随机生成一串密码 可指定密码长度\n");
        msg.append("[.搜图] 以图搜图，ACG向\n");
        msg.append("[.pid] 根据p站图片id获取图片以及信息\n");
        msg.append("[.摩斯电码] 摩尔斯电码相关\n");
        msg.append("[.天气] 空格后面填写城市名称，可以查询实时天气状况\n");
        msg.append("[.塔罗牌] 抽取一张塔罗牌\n");
        msg.append("[.猫罗牌] 塔罗牌变异品种(\n");
        msg.append("===更多更详细的指令请阅读文档===\n");
        msg.append("[github.com/MikuNyanya/RabbitBot_RE]");
        return new PlainText(msg);
    }
}
