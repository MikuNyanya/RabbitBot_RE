package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.NeteaseCloudService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author MikuLink
 * @date 2022/9/26 14:13
 * for the Reisen
 * 点歌
 */
@Command
@Slf4j
public class ChooseMusicCommand extends EverywhereCommand {
    @Autowired
    private NeteaseCloudService neteaseCloudService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("ChooseMusic", "点歌");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//         方便测试
//        {
//            "type": "music",
//                "data": {
//            "type": "custom",
//                    "url": "https://music.163.com/#/song?id=2673305131",
//                    "audio": "http://music.163.com/song/media/outer/url?id=2673305131.mp3",
//                    "title": "优雅失败指南",
//                    "image": "https://p2.music.126.net/1rnT9pQUxZGALlaM3MpaAA==/109951170465881791.jpg",
//                    "content": "演唱者xxx"
//        }
//        }

        try {
            List<String> args = getArgs(messageInfo.getRawMessage());
            //获取输入关键词
            if (CollectionUtil.isEmpty(args)) {
                return RabbitBotMessageBuilder.createMessageText("需要关键词才能点歌");
            }

            //所有入参拼接一起作为关键词
            StringBuilder keyWords = new StringBuilder();
            args.forEach(str -> keyWords.append(str).append(" "));

            //自建api
//            NeteaseCloudSearchResponse.ResultBean.SongsBean songInfo = neteaseCloudService.searchByKeywords(keyWords.toString().trim());
//            MessageChain messageChain = neteaseCloudService.parseMessage(songInfo);
            //oiapi
            MessageChain messageChain = neteaseCloudService.searchKeywordsByOiapi(keyWords.toString().trim());
            return new MessageInfo(List.of(messageChain));
        } catch (Exception ex) {
            log.error("点歌异常", ex);
            return RabbitBotMessageBuilder.createMessageText("点歌系统炸了");
        }
    }
}
