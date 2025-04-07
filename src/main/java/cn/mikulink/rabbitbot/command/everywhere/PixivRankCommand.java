package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * P站日榜
 */
@Command
public class PixivRankCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PixivRankCommand.class);

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private SwitchService switchService;


    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivRank", "pixivrank", "prank");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //检查功能开关
//        ReString reStringSwitch = switchService.switchCheck(sender, subject, "pixivRank");
//        if (!reStringSwitch.isSuccess()) {
//            return new PlainText(reStringSwitch.getMessage());
//        }
//        //限制其他人调用
//        if (!rabbitBotService.isRabbitAdmin(sender.getId())) {
//            return new PlainText(ConstantWeiboNews.COMMAND_NEED_AUTHORITY);
//        }
//
//        try {
//            //获取日榜
//            List<PixivRankImageInfo> imageList = pixivService.getPixivIllustRank(ConstantPixiv.PIXIV_IMAGE_PAGESIZE);
//            //拼接一个发送一个，中间间隔几秒
//            for (PixivRankImageInfo imageInfo : imageList) {
//                //上传图片
//                MessageChain resultChain = pixivService.parsePixivImgInfoByApiInfo(imageInfo);
//                //发送消息
//                subject.sendMessage(resultChain);
//                Thread.sleep(1000L * 2);
//            }
//        } catch (SocketTimeoutException stockTimeoutEx) {
//            logger.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
//        } catch (Exception ex) {
//            logger.error(ConstantPixiv.PIXIV_IMAGE_RANK_ERROR + ex.toString(), ex);
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_RANK_ERROR);
//        }
        return null;
    }
}
