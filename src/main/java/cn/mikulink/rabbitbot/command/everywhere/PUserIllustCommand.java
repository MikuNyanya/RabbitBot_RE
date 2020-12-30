package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.apirequest.imjad.ImjadPixivResponse;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.PixivImjadService;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.StringUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 根据pixiv用户随机展示图片
 */
@Command
@Deprecated
public class PUserIllustCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PUserIllustCommand.class);
    //操作间隔 账号，操作时间戳
    private static Map<Long, Long> PIXIV_USER_SPLIT_MAP = new HashMap<>();
    //操作间隔
    private static final Long PIXIV_USER_SPLIT_TIME = 1000L * 60;
    private static final String PIXIV_USER_SPLIT_ERROR = "[%s]%s秒后可以使用用户作品搜索";

    @Autowired
    private PixivImjadService pixivImjadService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivUserIllust", "puser");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //操作间隔判断
        String timeCheck = rabbitBotService.commandTimeSplitCheck(PIXIV_USER_SPLIT_MAP, sender.getId(), sender.getNick(), PIXIV_USER_SPLIT_TIME, PIXIV_USER_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return new PlainText(timeCheck);
        }
        //刷新操作间隔
        PIXIV_USER_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());

        if (null == args || args.size() == 0) {
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_IS_EMPTY);
        }
        //基本输入校验
        String memberName = "";
        for (String argStr : args) {
            memberName = memberName + argStr + " ";
        }
        memberName = StringUtil.trim(memberName);
        if (StringUtil.isEmpty(memberName)) {
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_IS_EMPTY);
        }

        try {
            List<ImjadPixivResponse> randIllustList = pixivImjadService.getPixivIllustByMember(memberName);
            if (null == randIllustList) {
                return new PlainText(ConstantPixiv.PIXIV_MEMBER_NO_ILLUST);
            }

            //拼装作品信息
            for (ImjadPixivResponse imjadPixivResponse : randIllustList) {
                MessageChain msgChain = pixivImjadService.parsePixivImgInfoByApiInfo(imjadPixivResponse, null);
                subject.sendMessage(msgChain);
            }
            return null;
        } catch (RabbitException rabbitEx) {
            logger.info("PUserIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + rabbitEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + ":" + rabbitEx.toString());
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.error("PUserIllustCommand " + ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error("PUserIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            //异常后清除间隔允许再次操作
            PIXIV_USER_SPLIT_MAP.remove(sender.getId());
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE);
        }
    }

}
