package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantGroupNotice;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.service.GroupNoticeService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/09/14 14:20
 * for the Reisen
 * <p>
 * 设置群公告指令
 */
@Command
public class NoticeSetCommand implements GroupCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private GroupNoticeService groupNoticeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("setGroupNotice", "设置群公告");
    }

    @Override
    public Message permissionCheck(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        //权限限制 群主可以设置
        if (!rabbitBotService.isRabbitAdmin(sender.getId()) && !rabbitBotService.isGroupOwner(subject, sender)) {
            return new PlainText(RandomUtil.rollStrFromList(ConstantCommon.COMMAND_GROUP_OWNER_ONLY));
        }
        return null;
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantGroupNotice.EXPLAIN);
        }

        Long groupId = subject.getId();

        //获取公告信息
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg);
            stringBuilder.append(" ");
        }

        if (stringBuilder.length() > ConstantGroupNotice.MAX_LENGTH) {
            return new PlainText(ConstantGroupNotice.MAX_LENGTH_OVERFLOW);
        }

        //设置群公告
        ReString reString = groupNoticeService.createGroupNotice(groupId, stringBuilder.toString().trim());
        if (!reString.isSuccess()) {
            new PlainText(reString.getMessage());
        }

        return new PlainText(ConstantCommon.DONE);
    }
}
