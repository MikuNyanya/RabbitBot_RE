package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivUserInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.PixivService;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
public class PUserIllustCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(PUserIllustCommand.class);
    //操作间隔 账号，操作时间戳
    private static Map<Long, Long> PIXIV_USER_SPLIT_MAP = new HashMap<>();
    //操作间隔
    private static final Long PIXIV_USER_SPLIT_TIME = 1000L * 60;
    private static final String PIXIV_USER_SPLIT_ERROR = "[%s]%s秒后可以使用用户作品搜索";

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("PixivUserIllust", "puser");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //操作间隔判断
//        String timeCheck = rabbitBotService.commandTimeSplitCheck(PIXIV_USER_SPLIT_MAP, sender.getId(), sender.getNick(), PIXIV_USER_SPLIT_TIME, PIXIV_USER_SPLIT_ERROR);
//        if (StringUtil.isNotEmpty(timeCheck)) {
//            return new PlainText(timeCheck);
//        }
//        //刷新操作间隔
//        PIXIV_USER_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());
//
//        if (null == args || args.size() == 0) {
//            return new PlainText(ConstantPixiv.PIXIV_MEMBER_IS_EMPTY);
//        }
//        //基本输入校验
//        String memberName = "";
//        for (String argStr : args) {
//            memberName = memberName + argStr + " ";
//        }
//        memberName = StringUtil.trim(memberName);
//        if (StringUtil.isEmpty(memberName)) {
//            return new PlainText(ConstantPixiv.PIXIV_MEMBER_IS_EMPTY);
//        }
//
//        try {
//            //1.定位用户
//            List<PixivUserInfo> pixivUserInfos = pixivService.pixivUserSearch(memberName);
//            //如果没有结果，或者多个结果，均需要返回对应信息
//            if (CollectionUtil.isEmpty(pixivUserInfos)) {
//                return new PlainText(String.format("没有找到叫这个的用户[%s]", memberName));
//            }
//            if (pixivUserInfos.size() > 1) {
//                return parseResultMsg(pixivUserInfos);
//            }
//
//            List<PixivImageInfo> pixivImageInfoList = pixivService.getPixivIllustByUserId(pixivUserInfos.get(0).getId());
//            if (CollectionUtil.isEmpty(pixivImageInfoList)) {
//                return new PlainText(ConstantPixiv.PIXIV_MEMBER_NO_ILLUST);
//            }
//            //拼装一条发送一条
//            for (PixivImageInfo pixivImageInfo : pixivImageInfoList) {
//                pixivImageInfo.setSender(sender);
//                pixivImageInfo.setSubject(subject);
//                MessageChain tempMsg = pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
//                subject.sendMessage(tempMsg);
//            }
//            return null;
//        } catch (RabbitApiException rabbitEx) {
//            logger.info("PUserIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + rabbitEx.toString());
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + ":" + rabbitEx.toString());
//        } catch (SocketTimeoutException stockTimeoutEx) {
//            logger.error("PUserIllustCommand " + ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
//            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
//        } catch (Exception ex) {
//            logger.error("PUserIllustCommand " + ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
//            //异常后清除间隔允许再次操作
//            PIXIV_USER_SPLIT_MAP.remove(sender.getId());
//            return new PlainText(ConstantPixiv.PIXIV_MEMBER_GET_ERROR_GROUP_MESSAGE);
//        }
        return null;
    }

    //多个结果提示信息
    private MessageChain parseResultMsg(List<PixivUserInfo> userInfos) {
        //展示前10条用户的名称和id
        MessageChain messages = MessageUtils.newChain();
        messages = messages.plus("未找到名称完全相同的用户\n建议使用puid指令");
        messages = messages.plus("\n=========相似用户(前20个)==========");
        for (PixivUserInfo userInfo : userInfos) {
            messages = messages.plus("\nid:[").plus(userInfo.getId()).plus("] ").plus("名称:[").plus(userInfo.getNick()).plus("]");
        }
        return messages;
    }
}
