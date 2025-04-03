package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSONException;
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
 * 根据pixiv用户id随机展示图片
 */
@Command
public class PUserIdIllustCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PUserIdIllustCommand.class);
    //操作间隔 账号，操作时间戳
    private static Map<Long, Long> PIXIV_USER_ID_SPLIT_MAP = new HashMap<>();
    //操作间隔
    private static final Long PIXIV_USER_ID_SPLIT_TIME = 1000L * 60;
    private static final String PIXIV_USER_ID_SPLIT_ERROR = "[%s]%s秒后可以使用puid搜索";

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivUserIdIllust", "puid");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //操作间隔判断
        String timeCheck = rabbitBotService.commandTimeSplitCheck(PIXIV_USER_ID_SPLIT_MAP, sender.getId(), sender.getNick(), PIXIV_USER_ID_SPLIT_TIME, PIXIV_USER_ID_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return new PlainText(timeCheck);
        }
        //刷新操作间隔
        PIXIV_USER_ID_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());

        if (CollectionUtil.isEmpty(args)) {
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_ID_IS_EMPTY);
        }
        //基本输入校验
        String memberId = "";
        for (String argStr : args) {
            memberId = memberId + argStr + " ";
        }
        memberId = StringUtil.trim(memberId);
        if (StringUtil.isEmpty(memberId)) {
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_ID_IS_EMPTY);
        }
        if (!NumberUtil.isNumberOnly(memberId)) {
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_ID_IS_NOT_NUMBER_ONLY);
        }

        try {
            List<PixivImageInfo> pixivImageInfoList = pixivService.getPixivIllustByUserId(memberId);
            if (CollectionUtil.isEmpty(pixivImageInfoList)) {
                return new PlainText(ConstantPixiv.PIXIV_MEMBER_NO_ILLUST);
            }
            //拼装一条发送一条
            for (PixivImageInfo pixivImageInfo : pixivImageInfoList) {
                pixivImageInfo.setSender(sender);
                pixivImageInfo.setSubject(subject);
                MessageChain tempMsg = pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
                subject.sendMessage(tempMsg);
            }
            return null;
        } catch (JSONException jsonEx) {
            logger.info("PUserIdIllustCommand " + ConstantPixiv.PIXIV_MEMBER_ID_JSON_ERROR, jsonEx);
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_ID_JSON_ERROR);
        } catch (RabbitApiException rabbitEx) {
            logger.info("PUserIdIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + rabbitEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + ":" + rabbitEx.toString());
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.error("PUserIdIllustCommand " + ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error("PUserIdIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            //异常后清除间隔允许再次操作
            PIXIV_USER_ID_SPLIT_MAP.remove(sender.getId());
            return new PlainText(ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE);
        }
    }
}
