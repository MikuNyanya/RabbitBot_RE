package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
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
 * 展示部分tag
 */
@Command
public class PtagsCommand extends BaseEveryWhereCommand {
    //展示的tag数量
    private static final int SHOW_TAG_QTY = 10;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivTags", "ptags");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //随机返回一定数量的tag，直接随机
        StringBuilder tagStr = new StringBuilder();
        tagStr.append("=====Pixiv tag=====");
        if (0 == ConstantPixiv.PIXIV_TAG_RABBIT_LIST.size()) {
            tagStr.append(ConstantPixiv.PIXIV_TAG_IS_EMPTY);
            tagStr.append("===================");
            return new PlainText(tagStr.toString());
        }

        int i = 0;
        List<String> tempTagList = new ArrayList<>();
        do {
            if (ConstantPixiv.PIXIV_TAG_RABBIT_LIST.size() < SHOW_TAG_QTY) {
                tempTagList.addAll(ConstantPixiv.PIXIV_TAG_RABBIT_LIST);
                break;
            }
            //防止死循环
            if (i > 50) {
                break;
            }

            //从内存里随机出一个tag
            String tag = RandomUtil.rollStrFromList(ConstantPixiv.PIXIV_TAG_RABBIT_LIST);
            //随机到重复的则再随机一次
            if (tempTagList.contains(tag)) {
                continue;
            }

            tempTagList.add(tag);
            i++;
        } while (SHOW_TAG_QTY > i);

        for (String tagTemp : tempTagList) {
            if (StringUtil.isEmpty(tagTemp)) {
                continue;
            }
            //整理后的tag以逗号分隔，前面是p站tag，后面是注释，并不是每个tag都需要注释
            String[] tagSplit = tagTemp.split(",");
            tagStr.append("\n[" + tagSplit[0] + "]");
            if (tagSplit.length >= 2) {
                tagStr.append("------");
                tagStr.append(tagSplit[1]);
            }
        }

        tagStr.append("\n===================");

        return new PlainText(tagStr.toString());
    }
}
