package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantSwitch;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.SwitchService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.internal.contact.NormalMemberImpl;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/2/3 17:00
 * for the Reisen
 * <p>
 * 开关指令
 * 这个时候，就好想有个界面来操作啊
 */
@Command
public class SwitchCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(SwitchCommand.class);

    private StringBuilder description = null;

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private SwitchService switchService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Switch", "switch", "开关");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args,
                           MessageChain messageChain, Contact subject) {
        Long qId = sender.getId();
        //查看配置是否已被最高权限强制统一接管
        if (switchService.switchFarceCheck() && !rabbitBotService.isMaster(qId)) {
            return new PlainText(RandomUtil.rollStrFromList(ConstantSwitch.SWITCH_FARCE));
        }

        Long groupId = null;

        //群消息
        if (subject instanceof Group) {
            //((NormalMemberImpl) sender).getPermission()
            //OWNER 群主 2
            //ADMINISTRATOR 管理 1
            //MEMBER 群员 0
            //常规权限检查，只允许群主和最高权限设置
            if (!rabbitBotService.isRabbitAdmin(qId) && ((NormalMemberImpl) sender).getPermission().getLevel() != 2) {
                return new PlainText(RandomUtil.rollStrFromList(ConstantSwitch.COMMAND_ADMIN_ONLY));
            }
            groupId = subject.getId();
        }

        //没有带参数，返回开关指令说明信息
        if (null == args || args.size() < 1) {
            if (null == description) {
                description = new StringBuilder(ConstantSwitch.ARGS_ERROR);
                for (String key : ConstantSwitch.SWITCH_NAME_DESCRIPTION_MAP.keySet()) {
                    description.append(String.format("\n[%s] %s", key, ConstantSwitch.SWITCH_NAME_DESCRIPTION_MAP.get(key)));
                }
            }
            return new PlainText(description);
        }

        //解析开关名称
        String switchName = args.get(0);
        //解析开关值
        String switchValue = null;
        if (args.size() >= 2) {
            switchValue = args.get(1);
        }
        //非空判断,输入限制判断
        ReString re = paramCheck(switchName, switchValue);
        if (!re.isSuccess()) {
            return new PlainText(re.getMessage());
        }

        //执行开关操作
        try {


            switchService.setSwitch(switchName, switchValue, groupId);
        } catch (Exception ex) {
            logger.error(ConstantSwitch.SWITCH_SET_ERROR + ex.toString(), ex);
            return new PlainText(ConstantSwitch.SWITCH_SET_ERROR);
        }

        return new PlainText(ConstantSwitch.SWITCH_SET_SUCCESS);
    }

    private ReString paramCheck(String switchName, String switchValue) {
        //非空判断
        if (StringUtil.isEmpty(switchName) || StringUtil.isEmpty(switchValue)) {
            return new ReString(false, RandomUtil.rollStrFromList(ConstantSwitch.SWITCH_PARAM_ERR));
        }
        //输入限制，只对可识别的开关进行操作
        if (!ConstantSwitch.SWITCH_NAME_DESCRIPTION_MAP.containsKey(switchName)) {
            return new ReString(false, RandomUtil.rollStrFromList(ConstantSwitch.SWITCH_PARAM_ERR));
        }
        //限制值的长度
        if (switchValue.length() > 10) {
            return new ReString(false, RandomUtil.rollStrFromList(ConstantSwitch.SWITCH_PARAM_ERR));
        }
        return new ReString(true);
    }
}
