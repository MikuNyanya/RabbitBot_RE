package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.DanbooruService;
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

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2022/02/18 16:35
 * for the Reisen
 * <p>
 * 根据Danbooru图片id搜索图片
 */
@Command
public class DanbooruIdCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(DanbooruIdCommand.class);

    @Autowired
    private DanbooruService danbooruService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("DanbooruImageId", "did", "DanbooruId");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        if (null == args || args.size() == 0) {
//            return new PlainText(ConstantImage.IMAGE_SEARCH_NO_IMAGE_ID_INPUT);
//        }
//        //基本输入校验
//        String did = args.get(0);
//        if (StringUtil.isEmpty(did)) {
//            return new PlainText(ConstantImage.IMAGE_SEARCH_NO_IMAGE_ID_INPUT);
//        }
//        if (!NumberUtil.isNumberOnly(did)) {
//            return new PlainText(ConstantImage.IMAGE_SEARCH_IMAGE_ID_NOT_NUMBER_ONLY);
//        }
//
//        try {
//            return danbooruService.getImgInfoById(NumberUtil.toLong(did));
//        } catch (RabbitException rabEx) {
//            logger.error("DanbooruIdCommand " + rabEx.getMessage(), rabEx);
//            return new PlainText(rabEx.getMessage());
//        } catch (Exception ex) {
//            logger.error(ConstantImage.IMAGE_GET_ERROR + ex.toString(), ex);
//            return new PlainText(ConstantImage.IMAGE_GET_ERROR);
//        }
        return null;
    }
}
