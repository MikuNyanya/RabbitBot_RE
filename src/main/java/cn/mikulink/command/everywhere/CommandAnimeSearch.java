package cn.mikulink.command.everywhere;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.constant.ConstantAnime;
import gugugu.service.WhatAnimeService;
import utils.StringUtil;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2020/02/19 16:10
 * for the Reisen
 * <p>
 * 以图搜番指令
 */
public class CommandAnimeSearch implements EverywhereCommand {
    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        if (null == args || args.size() == 0) {
            return ConstantAnime.ANIME_SEARCH_NO_IMAGE_INPUT;
        }
        //获取传入图片，解析CQ中的网络图片链接
        String imgCQ = args.get(0);
        if (!imgCQ.contains("[CQ:image")) {
            return ConstantAnime.ANIME_SEARCH_NO_IMAGE_INPUT;
        }
        String imgUrl = StringUtil.getCQImageUrl(imgCQ);
        if (StringUtil.isEmpty(imgUrl)) {
            return ConstantAnime.ANIME_SEARCH_IMAGE_URL_PARSE_FAIL;
        }
        //搜番，然后把图片加在最上面
        return imgCQ + "\n" + WhatAnimeService.searchAnimeFromWhatAnime(imgUrl);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("AnimeSearch", "搜番", "animesearch");
    }
}
