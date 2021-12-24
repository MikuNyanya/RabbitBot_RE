package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rpg.KickingASSInfo;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.rpg.KickASSService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.NormalMember;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/12/24 11:30
 * for the Reisen
 * <p>
 * 群友互殴指令
 * RPG相关功能
 */
@Command
@Slf4j
public class KickASSCommand implements GroupCommand {
    @Autowired
    private KickASSService kickASSService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("KickASS", "vs", "kickass");
    }

    @Override
    public Message permissionCheck(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        return null;
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantRPG.KICKASS_EXPLAIN);
        }

        Long groupId = subject.getId();
        Long senderId = sender.getId();
        String senderName = sender.getNameCard();
        if (StringUtil.isEmpty(senderName)) {
            senderName = sender.getNick();
        }

        //二级指令
        String arg = args.get(0);
        //at群友则尝试发起互殴
        if (arg.startsWith("@")) {
            Long playIdTwo = null;
            String playNameTwo = null;

            try {
                arg = arg.substring(1);
                NormalMember targetMember = subject.getMembers().get(NumberUtil.toLong(arg));
                if (null == targetMember) {
                    log.warn("KickASSCommand 互殴目标群员不存在,arg:{}", args.get(0));
                    return new PlainText(ConstantRPG.KICKASS_TARGET_404);
                }
                playIdTwo = targetMember.getId();
                playNameTwo = targetMember.getNameCard();
                if (StringUtil.isEmpty(playNameTwo)) {
                    playNameTwo = targetMember.getNick();
                }

            } catch (Exception ex) {
                log.warn("KickASSCommand 获取互殴目标群员失败,arg:{}", args.get(0), ex);
                return new PlainText(ConstantRPG.KICKASS_TARGET_ERROR);
            }

            KickingASSInfo kickInfo = new KickingASSInfo();
            kickInfo.setGroupId(groupId);
            kickInfo.setPlayIdOne(senderId);
            kickInfo.setPlayNameOne(senderName);
            kickInfo.setPlayIdTwo(playIdTwo);
            kickInfo.setPlayNameTwo(playNameTwo);

            //开启群友互殴
            return kickASSService.kickASSStart(kickInfo);
        }

        //临时终结用代码
        if(arg.equalsIgnoreCase("clear")){
            KickASSService.ROUND_NOW.remove(groupId);
            return new PlainText("互殴已恢复初始化");
        }

        //如果已经开启了互殴，检查当前指令发送者和当前互殴开启者是不是一个人
        //todo 淦，代码可真乱，下次摸鱼得整理一下
        if (!rabbitBotService.isMaster(senderId)) {
            if (KickASSService.ROUND_NOW.containsKey(groupId) && null != KickASSService.ROUND_NOW.get(groupId)) {
                KickingASSInfo kickingASSInfo = KickASSService.ROUND_NOW.get(groupId);
                if (!kickingASSInfo.getPlayIdOne().equals(senderId)) {
                    return new PlainText("这个不是这场互殴的发起者，不让操作");
                }
            }
        }

        MessageChain result = MessageUtils.newChain();
        result = result.plus(arg).plus("\n");

        //互殴指令匹配列表
        switch (arg) {
            case ConstantRPG.KICKASS_ACTION_TUNKHEAD_NO:
            case ConstantRPG.KICKASS_ACTION_TUNKHEAD:
            case ConstantRPG.KICKASS_ACTION_PUNCH_NO:
            case ConstantRPG.KICKASS_ACTION_PUNCH:
                result = result.plus(kickASSService.actionSTR(groupId));
                break;
            case ConstantRPG.KICKASS_ACTION_NYAPUNCH_NO:
            case ConstantRPG.KICKASS_ACTION_NYAPUNCH:
            case ConstantRPG.KICKASS_ACTION_JUMPKICK_NO:
            case ConstantRPG.KICKASS_ACTION_JUMPKICK:
                result = result.plus(kickASSService.actionDEX(groupId));
                break;
            case ConstantRPG.KICKASS_ACTION_PLANB_NO:
            case ConstantRPG.KICKASS_ACTION_PLANB:
            case ConstantRPG.KICKASS_ACTION_TALK_NO:
            case ConstantRPG.KICKASS_ACTION_TALK:
                result = result.plus(kickASSService.actionINTE(groupId));
                break;
            default:
                return new PlainText(ConstantRPG.PARAM_ERROR);
        }

        return result;
    }
}
