package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.modules.oiapi.OiApiService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * @author MikuLink
 * @date 2025/4/17 06:05
 * for the Reisen
 * <p>
 * 蔚蓝档案字体
 */
@Command
public class BlueArchiveFontCommand extends EverywhereCommand {

    private final static String EXPLAIN = """
            格式，但没必要每个都填写：
            .蔚蓝档案 前部分文字(默认为Blue) 后部分文字(默认为Archive) 光环上下位置(默认-18) 光环左右位置(默认0) 背景色(支持16进制颜色，rgb()，rgba())
            举几个栗子：
            .蔚蓝档案 蔚蓝 档案
            .蔚蓝档案 蔚蓝 档案 -18 0
            .蔚蓝档案 蔚蓝 档案 -18 0 7fffd4
            """;

    @Autowired
    private OiApiService oiApiService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("BlueArchiveFont", "蔚蓝档案", "蔚蓝档案字体");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        List<String> args = getArgs(messageInfo.getRawMessage());
        if (CollectionUtil.isEmpty(args)) {
            return RabbitBotMessageBuilder.createMessageText(EXPLAIN);
        }

        String startText = null;
        String endText = null;
        Integer x = null;
        Integer y = null;
        String color = null;

        //文本内容
        if (args.size() >= 2) {
            startText = args.get(0);
            endText = args.get(1);
        }
        //光环坐标
        if (args.size() >= 4) {
            x = NumberUtil.toInt(args.get(2));
            y = NumberUtil.toInt(args.get(3));
        }
        //颜色
        if (args.size() >= 5) {
            color = args.get(4);
        }

        String imageUrl = oiApiService.blueArchiveImage(startText, endText, x, y, color);
        return RabbitBotMessageBuilder.createMessageImage(imageUrl);
    }

}
