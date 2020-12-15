package cn.mikulink.command.everywhere;

import cn.mikulink.constant.ConstantConfig;
import cn.mikulink.constant.ConstantFile;
import cn.mikulink.entity.CommandProperties;
import cn.mikulink.entity.ReString;
import cn.mikulink.filemanage.FileManagerConfig;
import cn.mikulink.service.RabbitBotService;
import cn.mikulink.sys.annotate.Command;
import cn.mikulink.utils.RandomUtil;
import cn.mikulink.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
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
 * 兔叽配置
 */
@Command
public class ConfigCommand extends BaseEveryWhereCommand {
    private static String clsMessage = null;

    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Config", "config");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() < 1) {
            return new PlainText(ConstantConfig.ARGS_ERROR);
        }
        //解析副指令
        String action = args.get(0);
        //解析配置key
        String configName = null;
        if (args.size() >= 2) {
            configName = args.get(1);
        }
        //解析配置值
        String configValue = null;
        if (args.size() >= 3) {
            configValue = args.get(2);
        }

        ReString reString = accessCheck(sender.getId(), configName);
        if (!reString.isSuccess()) {
            return new PlainText(reString.getMessage());
        }

        String resultMsg = "";
        switch (action) {
            case ConstantConfig.SET:
                resultMsg = setConfig(configName, configValue);
                break;
            case ConstantConfig.DELETE:
            case ConstantConfig.DEL:
                resultMsg = delConfig(configName);
                break;
        }
        return new PlainText(resultMsg);
    }

    /**
     * 设置参数
     *
     * @param configName  参数名称
     * @param configValue 参数值
     * @return 响应信息
     */
    private String setConfig(String configName, String configValue) {
        if (StringUtil.isEmpty(configName)) {
            return ConstantConfig.CONFIG_NAME_EMPTY;
        }
        if (StringUtil.isEmpty(configValue)) {
            return ConstantConfig.CONFIG_VALUE_EMPTY;
        }
        //设置配置文件
        ConstantConfig.common_config.put(configName, configValue);
        //更新配置文件
        FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
        return ConstantConfig.CONFIG_SET_SUCCESS;
    }

    /**
     * 删除配置
     *
     * @param configName 参数名称
     * @return 响应信息
     */
    private String delConfig(String configName) {
        if (StringUtil.isEmpty(configName)) {
            return ConstantConfig.CONFIG_NAME_EMPTY;
        }
        if (!ConstantConfig.common_config.containsKey(configName)) {
            return ConstantConfig.CONFIG_NOT_FOUND;
        }
        //删除配置文件
        ConstantConfig.common_config.remove(configName);
        //更新配置文件
        FileManagerConfig.doCommand(ConstantFile.FILE_COMMAND_WRITE);
        return ConstantConfig.CONFIG_SET_SUCCESS;
    }

    /**
     * 权限检查
     * 以后有页面了做成页面配置
     */
    private ReString accessCheck(Long qq, String configName) {
        if (rabbitBotService.isRabbitAdmin(qq) && ConstantConfig.CONFIG_R18.equalsIgnoreCase(configName)) {
            return new ReString(true);
        }

        if (!rabbitBotService.isMaster(qq)) {
            return new ReString(false, RandomUtil.rollStrFromList(ConstantConfig.COMMAND_MASTER_ONLY));
        }

        return new ReString(true);
    }
}
