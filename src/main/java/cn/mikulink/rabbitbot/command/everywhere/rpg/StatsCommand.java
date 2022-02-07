package cn.mikulink.rabbitbot.command.everywhere.rpg;


import cn.mikulink.rabbitbot.command.everywhere.BaseEveryWhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rpg.PlayerCharacterStats;
import cn.mikulink.rabbitbot.service.ImageService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.rpg.CharacterStatsService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2021/12/18 10:27
 * for the Reisen
 * <p>
 * 人物属性界面
 */
@Command
public class StatsCommand extends BaseEveryWhereCommand {
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private CharacterStatsService characterStatsService;
    @Autowired
    private ImageService imageService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Stats", "人物属性");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //获取用户名，优先获取群名片
        String userNick = rabbitBotService.getUserName(subject, sender);
        if (StringUtil.isEmpty(userNick)) {
            return new PlainText(ConstantRPG.NO_NAME);
        }
        //生成属性面板，最前面加头像
        PlayerCharacterStats playerStats = characterStatsService.parseCharacterStatsList(userNick);
        String msg = characterStatsService.parseStatMsg(playerStats);

        //如果没有自定义公告，则发送默认消息
        //获取头像
        String qlogoLocalPath = imageService.getQLogoCq(sender.getId());
        //上传头像
        Image miraiImage = rabbitBotService.uploadMiraiImage(qlogoLocalPath);

        //返回消息
        Message result = MessageUtils.newChain();
        result = result.plus("").plus(miraiImage).plus("\n");
        result = result.plus(msg);

        return result;
    }

}
