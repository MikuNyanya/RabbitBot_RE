package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.RegexUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 随机密码
 * 可自定义长度，默认长度为6
 * 密码组合至少包含2个特殊字符和两个字母
 */
@Command
public class PwdCommand extends BaseEveryWhereCommand {
    private static final String OVER_SIZE = "密码长度必须在1-20之间";
    //密码字符列表 定死算了
    private static final List<String> PWD_STRS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
            "^", "@", ".", "_");
    private static final String PWD_REGEX = "\\S*[@^._]{1,}\\S*[@^._]{1,}\\S*";

    //97-122 小写字母
    //60-90 大写字母
    //48-57 数字
    //特殊字符直接使用

    @Override
    public CommandProperties properties() {
        return new CommandProperties("RandomPwd", "rpwd", "随机密码");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //参数可选，获取第一个作为长度，进行校验,最常不超过20位
        Integer pwdSize = 6;
        if (null != args && args.size() > 0) {
            String arg = args.get(0);
            if (!NumberUtil.isNumberOnly(arg)) {
                return new PlainText(ConstantCommon.GAME_PARAM_NUMBER_ONLY);
            }
            pwdSize = NumberUtil.toInt(arg);
            if (pwdSize > 20 || pwdSize <= 0) {
                return new PlainText(OVER_SIZE);
            }
        }

        //生成随机密码
        return new PlainText(randPwd(pwdSize));
    }

    /**
     * 生成密码，如果不符合条件进行递归，直到符合条件
     * 理论上不会递归到炸还搞不到一个符合条件的吧
     * 懒死算了
     *
     * @param pwdSize 密码长度
     * @return 符合条件的密码字符串
     */
    private String randPwd(Integer pwdSize) {
        StringBuilder pwdSb = new StringBuilder();
        for (int i = 0; i < pwdSize; i++) {
            pwdSb.append(RandomUtil.rollStrFromList(PWD_STRS));
        }

        String pwd = pwdSb.toString();
        if (!RegexUtil.regex(pwd, PWD_REGEX)) {
            pwd = randPwd(pwdSize);
        }
        return pwd;
    }
}
