package cn.mikulink.rabbitbot.command.everywhere;


import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantAnime;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.service.WhatAnimeService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
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
    private WhatAnimeService whatAnimeService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("AnimeSearch", "搜番");
    }


    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantAnime.ANIME_SEARCH_NO_IMAGE_INPUT);
        }
        //获取传入图片，解析CQ中的网络图片链接 todo 接入mirai
        String imgCQ = args.get(0);
        if (!imgCQ.contains("[CQ:image")) {
            return new PlainText(ConstantAnime.ANIME_SEARCH_NO_IMAGE_INPUT);
        }
        String imgUrl = StringUtil.getCQImageUrl(imgCQ);
        if (StringUtil.isEmpty(imgUrl)) {
            return new PlainText(ConstantAnime.ANIME_SEARCH_IMAGE_URL_PARSE_FAIL);
        }
        //搜番，然后把图片加在最上面
        whatAnimeService.searchAnimeFromWhatAnime(imgUrl);

        return null;
    }


}
