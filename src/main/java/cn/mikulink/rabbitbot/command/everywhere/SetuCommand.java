package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.MirlKoiService;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.service.SetuService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author MikuLink
 * @date 2020/12/13 15:50
 * for the Reisen
 * <p>
 * 来点色图
 */
@Command
public class SetuCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(SetuCommand.class);

    @Autowired
    private SetuService setuService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private MirlKoiService mirlKoiService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("laidiansetu", "setu", "色图", "涩图", "来点色图", "来涩色图", "来份色图", "来份涩图", "来张色图", "来张涩图");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //检查功能开关
//        ReString reStringSwitch = switchService.switchCheck(sender, subject, "setu");
//        if (!reStringSwitch.isSuccess()) {
//            return new PlainText(reStringSwitch.getMessage());
//        }
//
//        Long userId = sender.getId();
//        String userNick = sender.getNick();
//
//        if (!rabbitBotService.isMaster(userId)) {
//            return new PlainText(RandomUtil.rollStrFromList(ConstantConfig.COMMAND_MASTER_ONLY));
//        }
//
//        //获取指令参数
//        Integer setuCount = 1;
//        if (CollectionUtil.isNotEmpty(args)) {
//            //第一个指令作为色图数量，最少一个，最多10个 参数不合法的时候,使用默认值
//            String setuCountStr = args.get(0);
//            if (NumberUtil.isNumberOnly(setuCountStr)) {
//                setuCount = NumberUtil.toInt(setuCountStr);
//            }
//            if (setuCount <= 0) {
//                setuCount = 1;
//            }
//            if (setuCount > 10) {
//                setuCount = 10;
//            }
//        }
//
//        //检查操作间隔
//        ReString reString = setuService.setuTimeCheck(userId, userNick);
//        if (!reString.isSuccess()) {
//            return new PlainText(reString.getMessage());
//        }
//
//        //刷新操作间隔
//        ConstantPixiv.SETU_PID_SPLIT_MAP.put(sender.getId(), System.currentTimeMillis());
//
//        try {
//            mirlKoiService.sendRandomSetu(subject, setuCount);
//            return null;
//        } catch (Exception ex) {
//            logger.error(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
//            return new PlainText(ConstantPixiv.PIXIV_ID_GET_ERROR_GROUP_MESSAGE);
//        }
        return null;
    }
}
