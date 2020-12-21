package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.service.PixivImjadService;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 根据pixiv图片id搜索图片
 */
@Command
public class PidCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PidCommand.class);

    @Autowired
    private PixivImjadService pixivImjadService;
    @Autowired
    private PixivService pixivService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivImageId", "pid");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
        }
        //基本输入校验
        String pid = args.get(0);
        if (StringUtil.isEmpty(pid)) {
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
        }
        if (!NumberUtil.isNumberOnly(pid)) {
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_ID_IS_NUMBER_ONLY);
        }

        try {
            MessageChain resultChain = null;
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                PixivImageInfo pixivImageInfo = pixivService.getPixivImgInfoById(NumberUtil.toLong(pid));
                resultChain = pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
            } else {
                resultChain = pixivImjadService.searchPixivImgById(NumberUtil.toLong(pid));
            }
            return resultChain;
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.warn(ConstantPixiv.PIXIV_IMAGE_DELETE + fileNotFoundEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_DELETE);
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            return new PlainText(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE);
        }
    }
}
