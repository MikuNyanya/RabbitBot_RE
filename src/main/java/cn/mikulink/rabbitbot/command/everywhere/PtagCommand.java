package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 根据pixiv图片tag随机搜索图片
 */
@Command
public class PtagCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PtagCommand.class);
    //操作间隔 账号，操作时间戳
    public static Map<Long, Long> PIXIV_TAG_SPLIT_MAP = new HashMap<>();
    //操作间隔
    public static final Long PIXIV_TAG_SPLIT_TIME = 1000L * 60;
    public static final String PIXIV_TAG_SPLIT_ERROR = "[%s]%s秒后可以使用tag搜索";

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivImageTag", "ptag");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //操作间隔判断
//        String timeCheck = rabbitBotService.commandTimeSplitCheck(PIXIV_TAG_SPLIT_MAP, sender.getId(), sender.getNick(), PIXIV_TAG_SPLIT_TIME, PIXIV_TAG_SPLIT_ERROR);
//        if (StringUtil.isNotEmpty(timeCheck)) {
//            return new PlainText(timeCheck);
//        }
//        //刷新操作间隔
//        PIXIV_TAG_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());
//
//        if (null == args || args.size() == 0) {
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TAG_IS_EMPTY);
//        }
//        //基本输入校验
//        StringBuilder tagSB = new StringBuilder();
//        for (String param : args) {
//            tagSB.append(" ");
//            tagSB.append(param);
//        }
//        String tag = StringUtil.trim(tagSB.toString());
//        if (StringUtil.isEmpty(tag)) {
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TAG_IS_EMPTY);
//        }
//
//        try {
//            //根据tag获取接口返回信息
//            PixivImageInfo pixivImageInfo = pixivService.getPixivIllustByTag(tag);
//            pixivImageInfo.setSender(sender);
//            pixivImageInfo.setSubject(subject);
//            return pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
//        } catch (RabbitException rabEx) {
//            return new PlainText(rabEx.getMessage());
//        } catch (SocketTimeoutException stockTimeoutEx) {
//            logger.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
//        } catch (Exception ex) {
//            logger.error(ConstantPixiv.PIXIV_TAG_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
//            //异常后清除间隔允许再次操作
//            PIXIV_TAG_SPLIT_MAP.remove(sender.getId());
//            return new PlainText(ConstantPixiv.PIXIV_TAG_GET_ERROR_GROUP_MESSAGE);
//        }
        return null;
    }
}
