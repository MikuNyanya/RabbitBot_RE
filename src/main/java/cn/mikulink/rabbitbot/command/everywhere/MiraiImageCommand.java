package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2021/09/24 16:10
 * for the Reisen
 * <p>
 * 小工具
 * 传入mirai的图片id，回复对应图片
 */
@Command
public class MiraiImageCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(MiraiImageCommand.class);

    @Override
    public CommandProperties properties() {
        return new CommandProperties("MiraiImage", "imageId", "imgId");
    }

    public Message permissionCheck(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        return null;
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText("请传入mirai图片id，比如{54718158-C227-5410-5235-FB49323E97B7}.jpg");
        }

        return Image.fromId(args.get(0));
    }
}
