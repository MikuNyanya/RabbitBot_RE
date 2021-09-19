package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantMorseCode;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.MorseCodeService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/02/27 09:33
 * for the Reisen
 * <p>
 * 摩斯电码
 */
@Command
public class MorseCodeCommand extends BaseEveryWhereCommand {

    @Autowired
    private MorseCodeService morseCodeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("MorseCode", "morse", "摩斯电码");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantMorseCode.MORSE_CODE_TEXT);
        }
        //校验参数
        if (args.size() < 2) {
            return new PlainText(ConstantMorseCode.INPUT_IS_EMPTY);
        }
        //第二指令
        String action = args.get(0);
        //后面所有的参数拼接起来作为传入字符串
        StringBuilder inputStr = new StringBuilder();
        for (int i = 1; i < args.size(); i++) {
            inputStr.append(args.get(i)).append(ConstantMorseCode.DEFAULT_SPLIT);
        }

        String result = "";
        switch (action) {
            case ConstantMorseCode.ENCODE:
            case ConstantMorseCode.CN_ENCODE:
                result = morseCodeService.encode(inputStr.toString(), null);
                break;
            case ConstantMorseCode.DECODE:
            case ConstantMorseCode.CN_DECODE:
                result = morseCodeService.decode(inputStr.toString(), null);
                break;
            default:
                result = ConstantMorseCode.ACTION_ERROR;
        }
        //最终结果转为小写
        return new PlainText(result.toLowerCase());
    }
}
