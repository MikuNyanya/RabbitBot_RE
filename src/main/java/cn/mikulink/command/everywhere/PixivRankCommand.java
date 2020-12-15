package cn.mikulink.command.everywhere;

import cn.mikulink.constant.ConstantCommon;
import cn.mikulink.constant.ConstantImage;
import cn.mikulink.constant.ConstantWeiboNews;
import cn.mikulink.entity.CommandProperties;
import cn.mikulink.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.service.ImageService;
import cn.mikulink.service.PixivBugService;
import cn.mikulink.service.PixivService;
import cn.mikulink.service.RabbitBotService;
import cn.mikulink.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * P站日榜
 */
@Command
public class PixivRankCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PixivRankCommand.class);

    @Autowired
    private PixivService pixivService;
    @Autowired
    private PixivBugService pixivBugService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ImageService imageService;


    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivRank", "pixivrank", "prank");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //限制其他人调用
        if (!rabbitBotService.isRabbitAdmin(sender.getId())) {
            return new PlainText(ConstantWeiboNews.COMMAND_NEED_AUTHORITY);
        }

        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = null;
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                imageList = pixivBugService.getPixivIllustRank(ConstantImage.PIXIV_IMAGE_PAGESIZE);
            } else {
                imageList = pixivService.getPixivIllustRank(1, ConstantImage.PIXIV_IMAGE_PAGESIZE);
            }
            //拼接一个发送一个，中间间隔几秒
            for (PixivRankImageInfo imageInfo : imageList) {
                //上传图片
                MessageChain resultChain = imageService.uploadMiraiImage(imageInfo.getLocalImagesPath(), subject);
                //拼接图片描述
                String resultStr = pixivService.parsePixivImgInfoToGroupMsg(imageInfo);
                resultChain = resultChain.plus("").plus(resultStr);
                //发送消息
                subject.sendMessage(resultChain);
                Thread.sleep(1000L * 2);
            }
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantImage.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantImage.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantImage.PIXIV_IMAGE_RANK_ERROR + ex.toString(), ex);
            return new PlainText(ConstantImage.PIXIV_IMAGE_RANK_ERROR);
        }
        return null;
    }


}
