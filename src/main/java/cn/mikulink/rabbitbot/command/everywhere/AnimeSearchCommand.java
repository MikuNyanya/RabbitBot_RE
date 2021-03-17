package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantAnime;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.apirequest.tracemoe.TracemoeSearchDoc;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.TracemoeService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.internal.message.OnlineImage;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/02/19 16:10
 * for the Reisen
 * <p>
 * 以图搜番指令
 */
@Command
public class AnimeSearchCommand implements EverywhereCommand {

    @Autowired
    private TracemoeService whatAnimeService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("AnimeSearch", "搜番");
    }


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantAnime.ANIME_SEARCH_NO_IMAGE_INPUT);
        }
        //args中，图片被转为了字符串"[图片]"，这里直接判断args第一个参数是否为"[图片]",然后从messageChain中取索引为2的元素，忽略后面的图片
        if (!args.get(0).equals("[图片]")) {
            return new PlainText(ConstantImage.IMAGE_SEARCH_NO_IMAGE_INPUT);
        }

        MessageChain result = MessageUtils.newChain();

        //获取图片网络链接，gchat.qpic.cn是腾讯自家的
        //mirai中原图链接在messageChain中的图片元素下，但是需要强转
        //我看不太懂kotlin，先凑合着用吧,虽然可以运行，但代码报红，提示Usage of Kotlin internal declaration from different module
        //http://gchat.qpic.cn/gchatpic_new/455806936/3987173185-2655981981-FD4A1FC845F7A3A9FB8AC75AFE71C47E/0?term=2
        String imgUrl = ((OnlineImage) messageChain.get(2)).getOriginUrl();

        if (StringUtil.isEmpty(imgUrl)) {
            return new PlainText(ConstantAnime.ANIME_SEARCH_IMAGE_URL_PARSE_FAIL);
        }

        //搜番
        TracemoeSearchDoc doc = whatAnimeService.searchAnimeFromTracemoe(imgUrl);
        if (null == doc) {
            return new PlainText(ConstantAnime.TRACE_MOE_SEARCH_FAIL);
        }
        //下载预览图，允许异常后继续业务
        String previewImgLocalPath = whatAnimeService.imagePreviewDownload(doc);
        if (StringUtil.isNotEmpty(previewImgLocalPath)) {
            result = result.plus(rabbitBotService.uploadMiraiImage(previewImgLocalPath));
        }
        //格式化搜索结果
        result = result.plus(whatAnimeService.parseResultMsg(doc));
        return result;
    }


}
