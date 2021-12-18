package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCapsuleToy;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.CapsuleToyService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.contact.User;
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
 * 扭蛋
 */
@Command
@Slf4j
public class CapsuleToyCommand extends BaseEveryWhereCommand {

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private CapsuleToyService capsuleToyService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("CapsuleToy", "扭蛋");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        Long userId = sender.getId();
        String userNick = rabbitBotService.getUserName(subject, sender);

        if (null == args || args.size() <= 0) {
            //操作间隔判断
            String timeCheck = rabbitBotService.commandTimeSplitCheck(ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP, userId, userNick,
                    ConstantCapsuleToy.CAPSULE_TOY_SPLIT_TIME, ConstantCapsuleToy.CAPSULE_TOY_SPLIT_ERROR);
            if (StringUtil.isNotEmpty(timeCheck)) {
                return new PlainText(timeCheck);
            }
            //进行扭蛋
            String capsuleToy = capsuleToyService.capsuleToySelect();
            if (StringUtil.isEmpty(capsuleToy)) {
                return new PlainText(ConstantCapsuleToy.CAPSULE_TOY_HAS_NOTHING);
            }
            //如果扭到了扭蛋，就不用拦截操作了，可以直接再扭一次
            if (!"扭蛋".equalsIgnoreCase(capsuleToy)) {
                //刷新操作间隔
                ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP.put(userId, System.currentTimeMillis());
            }

            return new PlainText(String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_RESULT, userNick, capsuleToy));
        }

        //添加扭蛋部分
        //判断副指令
        String commandSecond = args.get(0);
        if (!ConstantCapsuleToy.ADD.equalsIgnoreCase(commandSecond)) {
            return new PlainText(ConstantCapsuleToy.COMMAND_SECOND_ERROR);
        }
        //判断有没有添加的信息
        if (args.size() < 2) {
            return new PlainText(ConstantCapsuleToy.EXPLAIN_ADD);
        }
        //兼容空格，后面的信息都作为扭蛋存下来
        boolean isFirst = true;
        StringBuilder sb = new StringBuilder();
        for (String capsuleToyStr : args) {
            //过滤掉二级指令
            if (isFirst) {
                isFirst = false;
                continue;
            }
            sb.append(" " + capsuleToyStr);
        }

        //添加扭蛋
        return new PlainText(capsuleToyAdd(StringUtil.trim(sb.toString())));
    }


    /**
     * 添加一个扭蛋
     *
     * @param capsuleToy 新的扭蛋
     * @return 添加结果
     */
    private String capsuleToyAdd(String capsuleToy) {
        //判空
        if (StringUtil.isEmpty(capsuleToy)) {
            return ConstantCapsuleToy.CAPSULE_TOY_ADD_EMPTY;
        }

        //判重
//        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() == 0) {
//            FileManagerCapsuleToy.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
//        }
//        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.contains(capsuleToy)) {
//            return String.format(ConstantCapsuleToy.CAPSULE_TOY_ADD_RE, capsuleToy);
//        }

        //添加扭蛋
        try {
            capsuleToyService.addCapsuleToy(capsuleToy);
        } catch (Exception ex) {
            log.error("添加扭蛋异常", ex);
            return String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_ADD_ERROR, capsuleToy);
        }
        return String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_ADD_SUCCESS, capsuleToy);
    }
}
