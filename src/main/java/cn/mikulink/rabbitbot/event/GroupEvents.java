package cn.mikulink.rabbitbot.event;


import cn.mikulink.rabbitbot.service.GroupNoticeService;
import cn.mikulink.rabbitbot.service.ImageService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import kotlin.coroutines.CoroutineContext;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.event.events.MemberLeaveEvent;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 群相关事件
 * @author: MikuLink
 * @date: 2021/1/1 1:42
 **/
@Component
public class GroupEvents extends SimpleListenerHost {
    //新成员入群消息
    private List<String> memberJoinMsgList = Arrays.asList(
            "欢迎[%s]入群，请自觉遵守群内相关规定",
            "[%s]\n兔叽持续增员中。。。",
            "[%s]\n兔叽+1",
            "欢迎新成员[%s]入群",
            "欢迎新兔叽[%s]加入我们"
    );

    //成员离群消息
    private List<String> memberLeaveMsgList = Arrays.asList(
            "[%s]\n兔叽减员中。。。",
            "[%s]\n兔叽-1",
            "[%s]有位兔叽永远的离开了我们("
    );


    private static final Logger logger = LoggerFactory.getLogger(GroupEvents.class);

    @Autowired
    private ImageService imageService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private GroupNoticeService groupNoticeService;


    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        logger.error("RecallEvent Error:{}", exception.getMessage());
    }


    /**
     * 群成员主动加群事件
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     */
    @NotNull
    @EventHandler
    public ListeningStatus onMemberJoinGroup(@NotNull MemberJoinEvent.Active event) {
        Group group = event.getGroup();
        User sender = event.getMember();
        logger.info("{新增群员_主动加群} userId:{},userNick:{},groupId:{},groupName:{}", sender.getId(), sender.getNick(), group.getId(), group.getName());
        groupMemberJoinMsg(group, sender);
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    /**
     * 群成员被邀请加群事件
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     */
    @NotNull
    @EventHandler
    public ListeningStatus onMemberJoinGroup(@NotNull MemberJoinEvent.Invite event) {
        Group group = event.getGroup();
        User sender = event.getMember();
        logger.info("{新增群员_被邀请加群} userId:{},userNick:{},groupId:{},groupName:{}", sender.getId(), sender.getNick(), group.getId(), group.getName());
        groupMemberJoinMsg(group, sender);
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }


    //群成员主动加群，被邀请加群事件业务
    private void groupMemberJoinMsg(Group group, User sender) {
        //有人入群，发送对应群的自定义公告
        groupNoticeService.

        //如果没有自定义公告，则发送默认消息
        //获取头像
        String qlogoLocalPath = imageService.getQLogoCq(sender.getId());
        //上传头像
        Image miraiImage = rabbitBotService.uploadMiraiImage(qlogoLocalPath);

        //返回消息
        Message result = MessageUtils.newChain();
        result = result.plus("").plus(miraiImage).plus("\n");
        result = result.plus("[").plus(sender.getNick()).plus("]\n");
        String msg = RandomUtil.rollStrFromList(memberJoinMsgList);
        result = result.plus(String.format(msg, sender.getNick()));
        group.sendMessage(result);
    }


    /**
     * 群成员主动离群事件
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     */
    @NotNull
    @EventHandler
    public ListeningStatus onMemberLeaveGroup(@NotNull MemberLeaveEvent.Quit event) {
        Group group = event.getGroup();
        User sender = event.getMember();
        logger.info("{群员离群_主动离群} userId:{},userNick:{},groupId:{},groupName:{}", sender.getId(), sender.getNick(), group.getId(), group.getName());
        groupMemberLeaveMsg(group, sender);
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    /**
     * 群成员被踢出群事件
     *
     * @param event 消息事件
     * @return 监听状态 详见 ListeningStatus
     */
    @NotNull
    @EventHandler
    public ListeningStatus onMemberLeaveGroup(@NotNull MemberLeaveEvent.Kick event) {
        Group group = event.getGroup();
        User sender = event.getMember();
        logger.info("{群员离群_被踢出群} userId:{},userNick:{},groupId:{},groupName:{}", sender.getId(), sender.getNick(), group.getId(), group.getName());
        groupMemberLeaveMsg(group, sender);
        return ListeningStatus.LISTENING; // 表示继续监听事件
    }

    //群成员主动离群，被踢出群事件业务
    private void groupMemberLeaveMsg(Group group, User sender) {
        //获取头像
        String qlogoLocalPath = imageService.getQLogoCq(sender.getId());
        //上传头像
        Image miraiImage = rabbitBotService.uploadMiraiImage(qlogoLocalPath);

        //返回消息
        Message result = MessageUtils.newChain();
        result = result.plus("").plus(miraiImage).plus("\n");
        result = result.plus("[").plus(sender.getNick()).plus("]\n");
        String msg = RandomUtil.rollStrFromList(memberLeaveMsgList);
        result = result.plus(String.format(msg, sender.getNick()));
        group.sendMessage(result);
    }
}
