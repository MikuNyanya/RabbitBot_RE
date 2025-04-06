package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantBlackList;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.sys.BlackListService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 兔叽配置
 */
@Command
public class BlackListCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(BlackListCommand.class);

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private BlackListService blackListService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("BlackList", "blacklist");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//        //权限限制
//        if (!rabbitBotService.isMaster(sender.getId())) {
//            return new PlainText(RandomUtil.rollStrFromList(ConstantCommon.COMMAND_MASTER_ONLY));
//        }
//
//        if (null == args || args.size() == 0) {
//            return new PlainText("[.blacklist (add,remove)]");
//        }
//
//        if (args.size() < 2 || StringUtil.isEmpty(args.get(1))) {
//            return new PlainText(ConstantBlackList.ADD_OR_REMOVE_BLACK_ID_USERID_EMPTY);
//        }
//
//        //二级指令
//        String arg = args.get(0);
//        String userIds = args.get(1);
//        switch (arg) {
//            case ConstantCommon.ADD:
//                //添加黑名单
//                blackListAdd(userIds, ConstantBlackList.ADD);
//                return new PlainText(ConstantBlackList.ADD_SUCCESS);
//            case ConstantCommon.REMOVE:
//                //移除黑名单
//                blackListAdd(userIds, ConstantBlackList.REMOVE);
//                return new PlainText(ConstantBlackList.REMOVE_SUCCESS);
//        }
        return null;
    }

    private void blackListAdd(String idsStr, String action) {
        //解析出ids
        if (StringUtil.isEmpty(idsStr)) return;
        String[] ids = idsStr.split(",");
        if (ids.length <= 0) return;

        List<Long> idList = new ArrayList<>();
        for (String id : ids) {
            if (StringUtil.isEmpty(id)) continue;
            if (!NumberUtil.isNumberOnly(id)) continue;
            idList.add(NumberUtil.toLong(id));
        }
        try {
            //添加或移除黑名单
            switch (action) {
                case ConstantCommon.ADD:
                    //添加黑名单
                    idList = blackListService.writeFile(idList);
                    //新的黑名单添加到当前列表里
                    ConstantBlackList.BLACK_LIST.addAll(idList);
                    break;
                case ConstantCommon.REMOVE:
                    //移除黑名单
                    idList = blackListService.removeBlack(idList);
                    //覆盖当前黑名单列表
                    ConstantBlackList.BLACK_LIST = idList;
                    break;
            }
        } catch (Exception ex) {
            logger.error("黑名单业务异常,ids:{},action:{}", idsStr, action, ex);
        }
    }

}
