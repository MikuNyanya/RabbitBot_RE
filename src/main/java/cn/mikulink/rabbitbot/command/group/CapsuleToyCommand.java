package cn.mikulink.rabbitbot.command.group;

import cn.mikulink.rabbitbot.command.GroupCommand;
import cn.mikulink.rabbitbot.constant.ConstantCapsuleToy;
import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.filemanage.FileManagerCapsuleToy;
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
 * 扭蛋
 */
@Command
public class CapsuleToyCommand implements GroupCommand {

    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("CapsuleToy", "扭蛋");
    }

    @Override
    public Message permissionCheck(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        return null;
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        Long userId = sender.getId();
        String userNick = sender.getNameCard();

        if (null == args || args.size() <= 0) {
            //操作间隔判断
            String timeCheck = rabbitBotService.commandTimeSplitCheck(ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAP, userId, userNick,
                    ConstantCapsuleToy.CAPSULE_TOY_SPLIT_TIME, ConstantCapsuleToy.CAPSULE_TOY_SPLIT_ERROR);
            if (StringUtil.isNotEmpty(timeCheck)) {
                return new PlainText(timeCheck);
            }
            //进行扭蛋
            String capsuleToy = capsuleToySelect();
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
     * 选择一个扭蛋
     *
     * @return 随机扭蛋
     */
    private String capsuleToySelect() {
        //集合为空时，重新加载一次扭蛋文件
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() == 0) {
            FileManagerCapsuleToy.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
        }
        //扭个蛋
        String capsuleToy = RandomUtil.rollStrFromList(ConstantCapsuleToy.MSG_CAPSULE_TOY);
        //删除这个扭蛋，实现伪随机
        ConstantCapsuleToy.MSG_CAPSULE_TOY.remove(capsuleToy);
        //元素少于1/6的时候，重新加载
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() < ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAX_SIZE / 6) {
            FileManagerCapsuleToy.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
        }
        return capsuleToy;
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
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.size() == 0) {
            FileManagerCapsuleToy.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
        }
        if (ConstantCapsuleToy.MSG_CAPSULE_TOY.contains(capsuleToy)) {
            return String.format(ConstantCapsuleToy.CAPSULE_TOY_ADD_RE, capsuleToy);
        }

        //添加扭蛋
        FileManagerCapsuleToy.doCommand(ConstantFile.FILE_COMMAND_WRITE, capsuleToy);
        return String.format(ConstantCapsuleToy.MSG_CAPSULE_TOY_ADD_SUCCESS, capsuleToy);
    }
}
