package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 根据pixiv图片id搜索图片
 */
@Slf4j
@Command
public class PidCommand extends EverywhereCommand {

    @Autowired
    private PixivService pixivService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivImageId", "pid");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        //todo 研究代理 https://pixiv.cat/
//        List<String> args = getArgs(messageInfo.getRawMessage());
//        if (null == args || args.size() == 0) {
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
//        }
//        //基本输入校验
//        String pid = args.get(0);
//        if (StringUtil.isEmpty(pid)) {
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
//        }
//        if (!NumberUtil.isNumberOnly(pid)) {
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_NUMBER_ONLY);
//        }
//
//        try {
//            PixivImageInfo pixivImageInfo = pixivService.getPixivImgInfoById(NumberUtil.toLong(pid));
//            pixivImageInfo.setSender(sender);
//            pixivImageInfo.setSubject(subject);
//            return pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
//        } catch (FileNotFoundException fileNotFoundEx) {
//            logger.warn(ConstantPixiv.PIXIV_IMAGE_DELETE + fileNotFoundEx.toString());
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_DELETE);
//        } catch (SocketTimeoutException stockTimeoutEx) {
//            logger.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
//        } catch (Exception ex) {
//            logger.error(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
//            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE);
//        }
        return null;
    }
}
