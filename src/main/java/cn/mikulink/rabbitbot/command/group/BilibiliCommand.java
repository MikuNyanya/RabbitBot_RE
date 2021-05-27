package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantBilibili;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.BilibiliService;
import cn.mikulink.rabbitbot.service.ConfigService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
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
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * bilibili消息相关
 */
@Command
public class BilibiliCommand implements GroupCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private BilibiliService bilibiliService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Bilibili", "bili");
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        //权限限制
        if (!rabbitBotService.isMaster(sender.getId())) {
            return new PlainText(RandomUtil.rollStrFromList(ConstantCommon.COMMAND_MASTER_ONLY));
        }

        if (null == args || args.size() == 0) {
            return new PlainText("[.bili (lastDynamicId,pull,unpull)]");
        }

        Long groupId = subject.getId();

        //二级指令
        String arg = args.get(0);
        switch (arg) {
            case ConstantBilibili.LAST_DYNAMIC_ID:
                //从外部接受sinceId
                if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
                    return new PlainText(ConstantBilibili.DYNAMIC_ID_OVERRIDE_FAIL);
                }
                String dynamicId = args.get(1);
                if (!NumberUtil.isNumberOnly(dynamicId)) {
                    return new PlainText(ConstantBilibili.DYNAMIC_ID_OVERRIDE_FAIL_NOW_NUMBER);
                }
                bilibiliService.refreshLastDynamicId(dynamicId);
                return new PlainText(ConstantBilibili.DYNAMIC_ID_OVERRIDE_SUCCESS);
            case ConstantBilibili.COMMAND_KEY_PULL:
                //以群为单位订阅b站uid
                if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
                    return new PlainText(ConstantBilibili.PULL_OR_UNPULL_BILI_DYNAMICDSVR_USERID_EMPTY);
                }
                String biliUserIds = args.get(1);

                configService.pullBiliUid(groupId, biliUserIds);
                return new PlainText(ConstantBilibili.PULL_SUCCESS);
            case ConstantBilibili.COMMAND_KEY_UN_PULL:
                //以群为单位取消订阅b站uid
                if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
                    return new PlainText(ConstantBilibili.PULL_OR_UNPULL_BILI_DYNAMICDSVR_USERID_EMPTY);
                }
                String unPushBiliUserIds = args.get(1);

                configService.unpullBiliUid(groupId, unPushBiliUserIds);
                return new PlainText(ConstantBilibili.UNPULL_SUCCESS);
        }
        return null;
    }
}
