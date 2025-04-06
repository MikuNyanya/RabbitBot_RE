package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.NeteaseCloudService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

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
//        try {
//            //音乐平台运营商枚举 MusicKind.NeteaseCloudMusic
//            //mirai文档 https://github.com/mamoe/mirai/blob/dev/mirai-core-api/src/commonMain/kotlin/message/data/MusicShare.kt
//            //MusicShare result = new MusicShare(MusicKind.NeteaseCloudMusic,"消息卡片标题","-消息卡片内容-","点击卡片跳转网页 URL","消息卡片图片 URL","音乐文件 URL","在消息列表显示");
//
//            //模板
////            MusicShare result = new MusicShare(
////                    MusicKind.NeteaseCloudMusic,
////                    "兔子要吃小铃子",
////                    "-兔子要吃小铃子-",
////                    "https://vdn6.vzuu.com/SD/d5255eee-6682-11eb-8cb5-2a44ba89f23a.mp4?pkey=AAWxYztymo2u8MTznHwt3u9qVQSNnGW0eMvOwrdmxM6agEmgKzsC7_1yJTscSZH3DVEn3DhzpD0Mo4HbKJpsZqu3&c=avc.0.0&f=mp4&pu=078babd7&bu=078babd7&expiration=1664259883&v=ks6",
////                    "https://tva1.sinaimg.cn/large/64130597ly1h6jzmz6eiwj20g40i1aav.jpg",
////                    "https://music.163.com/song?id=1952191507&userid=120831343",
////                    "[震惊]兔子要吃小铃子");
//
//            //获取输入关键词
//            if (CollectionUtil.isEmpty(args)) {
//                return new PlainText("需要关键词才能点歌");
//            }
//
//            //所有入参拼接一起作为关键词
//            StringBuilder keyWords = new StringBuilder();
//            args.forEach(str -> keyWords.append(str).append(" "));
//
//            NeteaseCloudSearchResponse.ResultBean.SongsBean songInfo = neteaseCloudService.searchByKeywords(keyWords.toString().trim());
//            return neteaseCloudService.parseMessage(songInfo);
//        } catch (Exception ex) {
//            log.error("点歌异常", ex);
//            return new PlainText("点歌系统炸了");
//        }
        return null;
    }
}
