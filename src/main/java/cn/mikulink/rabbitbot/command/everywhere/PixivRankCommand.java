package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.PixivService;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivRankImageInfo;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketTimeoutException;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * P站日榜
 */
@Slf4j
@Command
public class PixivRankCommand extends EverywhereCommand {

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private RabbitBotSender rabbitBotSender;


    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivRank", "pixivrank", "prank");
    }

    @Override
    public MessageInfo permissionCheck(MessageInfo groupMessageInfo) {
        //权限限制
        if (!rabbitBotService.isMaster(groupMessageInfo.getUserId())) {
            return RabbitBotMessageBuilder.createMessageText(RandomUtil.rollStrFromList(ConstantCommon.COMMAND_MASTER_ONLY));
        }
        return null;
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = pixivService.getPixivIllustRank(ConstantPixiv.PIXIV_IMAGE_PAGESIZE);
            //拼接一个发送一个，中间间隔几秒
            for (PixivRankImageInfo imageInfo : imageList) {
                //上传图片
                MessageInfo resultInfo = pixivService.parsePixivImgInfoByApiInfo(imageInfo);
                //发送消息
                rabbitBotSender.resultMessageToSource(messageInfo, resultInfo);

                Thread.sleep(1000L * 1);
            }
        } catch (SocketTimeoutException stockTimeoutEx) {
            log.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.getMessage());
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            log.error(ConstantPixiv.PIXIV_IMAGE_RANK_ERROR + ex.getMessage(), ex);
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_RANK_ERROR);
        }
        return null;
    }
}
