package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.PixivService;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.util.List;


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
        List<String> args = getArgs(messageInfo.getRawMessage());
        if (null == args || args.size() == 0) {
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
        }
        //基本输入校验
        String pid = args.get(0);
        if (StringUtil.isEmpty(pid)) {
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
        }
        if (!NumberUtil.isNumberOnly(pid)) {
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_ID_IS_NUMBER_ONLY);
        }

        try {
            PixivImageInfo pixivImageInfo = pixivService.getPixivImgInfoById(NumberUtil.toLong(pid));
            return pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
        } catch (FileNotFoundException fileNotFoundEx) {
            log.warn(ConstantPixiv.PIXIV_IMAGE_DELETE + fileNotFoundEx.getMessage());
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_DELETE);
        } catch (SocketTimeoutException stockTimeoutEx) {
            log.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.getMessage());
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            log.error(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.getMessage(), ex);
            return RabbitBotMessageBuilder.createMessageText(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE);
        }
    }
}
