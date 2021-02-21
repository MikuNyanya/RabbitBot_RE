package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.apirequest.lolicon.LoliconImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.LoliconAppImageService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.SwitchService;
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
import java.util.Map;

@Command
public class LoliconAppCommand implements EverywhereCommand {

    private static final Logger logger = LoggerFactory.getLogger(LoliconAppCommand.class);

    //操作间隔 账号，操作时间戳
    public static Map<Long, Long> LOLICON_SPLIT_MAP = new HashMap<>();
    //推送次数,每天0点重置7
    public static int PUSH_INDEX = 300;

    //操作间隔
    public static final Long PIXIV_TAG_SPLIT_TIME = 1000L * 60;
    public static final String PIXIV_TAG_SPLIT_ERROR = "[%s]%s秒后可以使用推送";

    public static final String PUSH_INDEX_ERROR = "已经超过每天调用上限";


    @Autowired
    private LoliconAppImageService loliconAppImageService;

    @Autowired
    private RabbitBotService rabbitBotService;

    @Autowired
    private SwitchService switchService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("LoliconPush", "来张图");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        //检查功能开关
        ReString reStringSwitch = switchService.switchCheck(sender, subject, "loliconApp");
        if (!reStringSwitch.isSuccess()) {
            return new PlainText(reStringSwitch.getMessage());
        }
        //操作间隔判断
        String timeCheck = rabbitBotService.commandTimeSplitCheck(LOLICON_SPLIT_MAP, 1L, sender.getNick(), PIXIV_TAG_SPLIT_TIME, PIXIV_TAG_SPLIT_ERROR);
        if (StringUtil.isNotEmpty(timeCheck)) {
            return new PlainText(timeCheck);
        }

        String pushIndexCheck = rabbitBotService.commandPushIndexCheck(PUSH_INDEX, PUSH_INDEX_ERROR);
        if (StringUtil.isNotEmpty(pushIndexCheck)) {
            return new PlainText(pushIndexCheck);
        }

        //基本输入校验
        StringBuilder tags = new StringBuilder();
        boolean r18 = false;
        for (String param : args) {
            if("r18".equals(param.toLowerCase())){
                r18 = true;
            }else{
                tags.append(" ");
                tags.append(param);
            }

        }
        String tag = StringUtil.trim(tags.toString());
        //刷新操作间隔
        LOLICON_SPLIT_MAP.put(1L, System.currentTimeMillis());
        //推送次数 减 1
        PUSH_INDEX--;
        try {
            //根据tag获取接口返回信息
            LoliconImageInfo pixivImageInfo = this.loliconAppImageService.getPixivIllust(tag,r18);
            if (pixivImageInfo == null || pixivImageInfo.getCode() != 0) {
                return new PlainText(pixivImageInfo.getMsg());
            }
            pixivImageInfo.setSender(sender);
            pixivImageInfo.setSubject(subject);
            return loliconAppImageService.parsePixivImgInfoByApiInfo(pixivImageInfo);
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantPixiv.LOLICON_PIXIV_ID_API_ERROR + ex.toString(), ex);
            return new PlainText(ConstantPixiv.LOLICON_PIXIV_ID_API_ERROR);
        }


    }


}
