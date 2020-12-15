package cn.mikulink.command;

import cn.mikulink.command.group.*;
import cn.mikulink.command.everywhere.*;
import cn.mikulink.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MikuLink
 * @date 2020/12/14 18:50
 * for the Reisen
 * <p>
 * 指令配置
 */
@Component
public class CommandConfig {
    private static final Logger logger = LoggerFactory.getLogger(CommandConfig.class);

    //指令头 区分正常消息 和 指令消息
    private static Set<String> commandHeads = new HashSet<>();

    //已注册的指令, [指令名, 指令对象]
    public static Map<String, Command> everywhereCommands = new HashMap<>();
    public static Map<String, Command> friendCommands = new HashMap<>();
    public static Map<String, Command> groupCommands = new HashMap<>();
    public static Map<String, Command> tempMsgCommands = new HashMap<>();

    /**
     * 注册指令头，一般用.其他的!#都可以
     */
    public void registerCommandHeads() {
        String[] heads = initCommandHeads();
        for (String head : heads) {
            commandHeads.add(head);
        }
    }

    /**
     * 注册指令(批量)
     */
    public void registerCommands() {
        Command[] commands = initCommands();
        for (Command command : commands) {
            registerCommand(command);
        }
    }

    /**
     * 注册指令
     *
     * @param command 一个指令
     */
    private void registerCommand(Command command) {
        //让所有指令名称指向一个指令，不区分大小写
        Map<String, Command> tempCommans = new HashMap<>();
        tempCommans.put(command.properties().getName().toLowerCase(), command);
        command.properties().getAlias().forEach(alias -> tempCommans.put(alias.toLowerCase(), command));

        //根据事件类型分配指令，方便监听程序调用
        if (command instanceof FriendCommand) {
            friendCommands.putAll(tempCommans);
        } else if (command instanceof GroupCommand) {
            groupCommands.putAll(tempCommans);
        } else if (command instanceof TempMessageCommand) {
            tempMsgCommands.putAll(tempCommans);
        } else if (command instanceof EverywhereCommand) {
            everywhereCommands.putAll(tempCommans);
        } else {
            //未配置的监听，一般不会出现，除非以后腾讯又加了新花样
            logger.warn("发现未知指令类型[{}]，忽略该指令注册", command.properties().getName());
        }
    }

    /**
     * 判断是否是否属于指令
     *
     * @param msg 消息
     * @return 是否为指令
     */
    public boolean isCommand(String msg) {
        return commandHeads.stream().anyMatch(head -> msg.startsWith(head));
    }


    /**
     * 根据指令名称获取对应指令
     *
     * @param msg
     * @param commandMap
     * @return
     */
    public Command getCommand(String msg, Map<String, Command> commandMap) {
        String[] temp = msg.split(" ");
        // 带头的指令名称
        String headcommand = temp[0];

        //去除指令头，需要考虑指令头不止一个字符的情况
        List<String> temps = commandHeads.stream()
                .filter(head -> headcommand.startsWith(head) && StringUtil.isNotEmpty(head))
                .map(head -> headcommand.replaceFirst(head, ""))
                .collect(Collectors.toList());

        String commandStr;
        if (temps.isEmpty()) {
            commandStr = headcommand;
        } else {
            commandStr = temps.get(0);
        }

        return commandMap.getOrDefault(commandStr.toLowerCase(), null);
    }

    public String[] initCommandHeads() {
        String[] heads = new String[]{
//                "#", "$", "!", "！", ""
                "."
        };
        return heads;
    }

    public Command[] initCommands() {
        Command[] commands = new Command[]{
                new CLSCommand(),
                new CapsuleToyCommand(),
                new RollCommand(),
                new RPCommand(),
                new WeiboNewsCommand(),
                new ConfigCommand(),
                new HelpCommand(),
                new PidCommand(),
                new PixivRankCommand(),
                new SystemCommand(),
                new PtagCommand(),
                new PtagsCommand(),
                new PwdCommand(),
                new PUserIllustCommand(),
                new PusersCommand(),
                new SetuCommand(),
                new SayCommand()
        };
        return commands;
    }

}
