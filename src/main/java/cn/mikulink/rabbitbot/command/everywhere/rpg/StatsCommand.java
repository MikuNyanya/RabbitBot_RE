package cn.mikulink.rabbitbot.command.everywhere.rpg;


import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChainData;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.SenderInfo;
import cn.mikulink.rabbitbot.entity.rpg.PlayerCharacterStats;
import cn.mikulink.rabbitbot.service.ImageService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.rpg.CharacterStatsService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2021/12/18 10:27
 * for the Reisen
 * <p>
 * 人物属性界面
 */
@Command
public class StatsCommand extends EverywhereCommand {
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
    public MessageInfo execute(MessageInfo messageInfo) {
        SenderInfo sender = messageInfo.getSender();

        //获取用户名，优先获取群名片
        String userNick = rabbitBotService.getUserName(sender);
        if (StringUtil.isEmpty(userNick)) {
            return RabbitBotMessageBuilder.createMessageText(ConstantRPG.NO_NAME);
        }
        //生成属性面板，最前面加头像
        PlayerCharacterStats playerStats = characterStatsService.parseCharacterStatsList(userNick);
        String msg = characterStatsService.parseStatMsg(playerStats);

        //如果没有自定义公告，则发送默认消息
        //获取头像
        String qlogoUrl = imageService.getQLogoUrl(sender.getUserId());

        //返回消息
        List<MessageChain> result = new ArrayList<>();
        result.add(new MessageChain("image", MessageChainData.createImageMessageData(qlogoUrl)));
        result.add(new MessageChain("text", new MessageChainData(msg)));
        return new MessageInfo(result);
    }

}
