package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.service.SetuService;
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
import java.util.List;

/**
 * @author MikuLink
 * @date 2021/02/22 0:19
 * for the Reisen
 * <p>
 * 添加色图
 */
@Command
public class SetuAddCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(SetuAddCommand.class);
    @Autowired
    private SetuService setuService;
    @Autowired
    private PixivService pixivService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("SetuAdd", "setuadd", "addsetu");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return new PlainText(ConstantPixiv.PIXIV_IMAGE_ID_IS_EMPTY);
        }
        List<String> pidList = new ArrayList<>();

        //校验pid是否有效
        for (String pidStr : args) {
            //过滤空的和非纯数字的
            if (StringUtil.isEmpty(pidStr) || !NumberUtil.isNumberOnly(pidStr)) {
                continue;
            }
            //调用接口去判断pid是否有效，过滤掉没结果，已被删除，和调用超时的，异常的也丢掉，允许部分损失
            PixivImageInfo pixivImageInfo = null;
            try {
                pixivImageInfo = pixivService.getPixivImgInfoById(NumberUtil.toLong(pidStr));
            } catch (Exception ex) {
                logger.warn("SetuAdd err pid:{}", pidStr, ex);
            }
            if(null ==pixivImageInfo){
                continue;
            }
            pidList.add(pidStr);
        }

        //调用服务
        setuService.addSetu(pidList);

        //最终结果转为小写
        return new PlainText(ConstantCommon.DONE);
    }
}
