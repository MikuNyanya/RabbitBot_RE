package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.GroupNoticeService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
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
 * 群公告指令
 */
@Command
public class NoticeCommand implements GroupCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private GroupNoticeService groupNoticeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("groupNotice", "群公告");
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
        Long groupId = subject.getId();
        //获取群公告
        String groupNoticeStr = groupNoticeService.getGroupNotice(groupId);

        StringBuilder resultStr = new StringBuilder();
        resultStr.append("群主可以设置入群公告");
        resultStr.append("\n具体参照指令(.设置群公告)");
        if (StringUtil.isNotEmpty(groupNoticeStr)) {
            resultStr.append("\n============当前群公告============");
            resultStr.append("\n").append(groupNoticeStr);
        } else {
            resultStr.append("\n============当前未设置群公告============");
        }

        return new PlainText(resultStr.toString());
    }
}
