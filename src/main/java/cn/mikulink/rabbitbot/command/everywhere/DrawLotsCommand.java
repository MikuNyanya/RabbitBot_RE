package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/03/13 14:25
 * for the Reisen
 * <p>
 * 抽签，自己做的图片
 */
@Command
public class DrawLotsCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(DrawLotsCommand.class);
    //签的图片存放路径
    public static final String IMAGE_DRAWLOTS_SAVE_PATH = "data/images/drawlots";
    //标记签的总数
    private static final int DRAWLOTS_MAX_COUNT = 6;

    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("DrawLots", "抽签");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        
        String userNick = sender.getNick();
        MessageChain result = MessageUtils.newChain();
        try {
            //基于签的最大数量抽取一个数字作为索引
            int randNum = RandomUtil.roll(DRAWLOTS_MAX_COUNT - 1);
            //图片处理
            String imageName = randNum + ".png";
            Image miraiImage = rabbitBotService.uploadMiraiImage(IMAGE_DRAWLOTS_SAVE_PATH + File.separator + imageName);
            //拼接请求人信息
            result = result.plus("[" + userNick + "]\n");
            //拼接图片
            MessageChain imageMsg = rabbitBotService.parseMsgChainByImg(miraiImage);
            result = result.plus(imageMsg);
            return result;
        } catch (Exception ex) {
            logger.error("DrawLotsCommand 抽签功能异常", ex);
            return new PlainText("抽签功能出错啦");
        }
    }
}
