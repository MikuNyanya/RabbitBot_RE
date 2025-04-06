package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantMorseCode;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.MorseCodeService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author MikuLink
 * @date 2020/02/27 09:33
 * for the Reisen
 * <p>
 * 摩斯电码
 */
@Command
public class MorseCodeCommand extends EverywhereCommand {

    @Autowired
    private MorseCodeService morseCodeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("MorseCode", "morse", "摩斯电码");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        List<String> args = getArgs(messageInfo.getRawMessage());

        if (null == args || args.size() == 0) {
            return RabbitBotMessageBuilder.createMessageText(ConstantMorseCode.MORSE_CODE_TEXT);
        }
        //校验参数
        if (args.size() < 2) {
            return RabbitBotMessageBuilder.createMessageText(ConstantMorseCode.INPUT_IS_EMPTY);
        }
        //第二指令
        String action = args.get(0);
        //后面所有的参数拼接起来作为传入字符串
        StringBuilder inputStr = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            inputStr.append(args.get(i)).append(ConstantMorseCode.DEFAULT_SPLIT);
        }
        String param = inputStr.substring(0,inputStr.length()-1);

        String result = switch (action) {
            case ConstantMorseCode.ENCODE, ConstantMorseCode.CN_ENCODE ->
                    morseCodeService.encode(param, null);
            case ConstantMorseCode.DECODE, ConstantMorseCode.CN_DECODE ->
                    morseCodeService.decode(param, null);
            default -> ConstantMorseCode.ACTION_ERROR;
        };
        //最终结果转为小写
        return RabbitBotMessageBuilder.createMessageText(result.toLowerCase());
    }
}
