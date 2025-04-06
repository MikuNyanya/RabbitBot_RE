package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.RegexUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 搜索P站作者信息指令
 */
@Command
public class PusersCommand extends EverywhereCommand {
    //展示的数量
    private static final int SHOW_COUNT = 15;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivUsers", "pusers");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        String memberName = null;
//        if (null != args && args.size() > 0) {
//            memberName = args.get(0);
//        }
//
//        List<String> tempList = new ArrayList<>();
//
//        //传入作者名称参数为空，随机返回指定数目作者信息
//        if (StringUtil.isEmpty(memberName)) {
//            int localMemberListSize = ConstantPixiv.PIXIV_MEMBER_LIST.size();
//            if (localMemberListSize <= SHOW_COUNT) {
//                tempList = ConstantPixiv.PIXIV_MEMBER_LIST;
//            } else {
//                List<Integer> randNumList = RandomUtil.roll(localMemberListSize - 1, SHOW_COUNT);
//                if (null == randNumList) {
//                    randNumList = new ArrayList<>();
//                }
//                for (Integer index : randNumList) {
//                    tempList.add(ConstantPixiv.PIXIV_MEMBER_LIST.get(index));
//                }
//            }
//        }
//
//        //传入作者不为空，去模糊搜索用户名
//        if (StringUtil.isNotEmpty(memberName)) {
//            for (String localMemberStr : ConstantPixiv.PIXIV_MEMBER_LIST) {
//                String[] memberStrs = localMemberStr.split(",");
//                if (memberStrs.length < 2) {
//                    continue;
//                }
//                boolean isRegex = RegexUtil.regex(memberStrs[1], memberName);
//                if (isRegex) {
//                    tempList.add(localMemberStr);
//                }
//            }
//        }
//
//        StringBuilder resultSb = new StringBuilder();
//        resultSb.append("========Pixiv作者信息========");
//
//        for (String memberStr : tempList) {
//            String[] memberStrs = memberStr.split(",");
//            if (memberStrs.length < 2) {
//                continue;
//            }
////            String resultMemberIdStr = memberIdStrSpace(String.format("\n作者id [%s]", memberStrs[0]));
////            resultSb.append(resultMemberIdStr);
////            resultSb.append(String.format("作者名称 [%s]", memberStrs[1]));
//
//            resultSb.append(String.format("\n作者id [%s]\t作者名称 [%s]", memberStrs[0], memberStrs[1]));
//        }
//
//        return new PlainText(resultSb.toString());
        return null;
    }
}
