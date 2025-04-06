package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.ImageSearchMemberInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.ImageService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import cn.mikulink.rabbitbot.utils.DateUtil;
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
import java.util.Calendar;
import java.util.Date;

/**
 * @author MikuLink
 * @date 2020/02/19 16:10
 * for the Reisen
 * <p>
 * 搜图指令
 */
@Command
public class ImageSearchCommand extends EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(ImageSearchCommand.class);

    @Autowired
    private ImageService imageService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("ImageSearch", "搜图");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        //todo 重新接入搜图
//        if (null == args || args.size() == 0) {
//            //搜图功能针对手机操作优化
//            //首先触发搜图指令，然后同样的账号单独发一张图片，也可以触发搜图
//            this.soutuTrigger(sender);
//            return new PlainText(ConstantImage.IMAGE_SEARCH_WITE_IMAGE_INPUT);
//        }
//        //获取传入图片，解析mirai消息中的网络图片链接
//        //[mirai:image:{FD4A1FC8-45F7-A3A9-FB8A-C75AFE71C47E}.mirai]
////         String temp_msg_part = messageChain.get(messageChain.size()-1).toString();
////        if(MiraiUtil.isMiraiImg(temp_msg_part)){
////        }
//        //args中，图片被转为了字符串"[图片]"，这里直接判断args第一个参数是否为"[图片]",然后从messageChain中取索引为2的元素，忽略后面的图片
//        if (!args.get(0).equals("[图片]")) {
//            return new PlainText(ConstantImage.IMAGE_SEARCH_NO_IMAGE_INPUT);
//        }
//
//        //获取图片网络链接，gchat.qpic.cn是腾讯自家的
//        //mirai中原图链接在messageChain中的图片元素下，但是需要强转
//        //http://gchat.qpic.cn/gchatpic_new/455806936/3987173185-2655981981-FD4A1FC845F7A3A9FB8AC75AFE71C47E/0?term=2
//        String imgUrl = ((OnlineGroupImageImpl) messageChain.get(2)).getOriginUrl();
//
//
//        if (StringUtil.isEmpty(imgUrl)) {
//            return new PlainText(ConstantImage.IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL);
//        }
//        return imageService.searchImgByImgUrl(imgUrl, sender, subject);
        return null;
    }


    //触发搜图指令
    private void soutuTrigger(User sender) {
        Long id = sender.getId();
        String nick = sender.getNick();
        for (int i = 0; i < ConstantImage.IMAGE_SEARCH_WITE_LIST.size(); i++) {
            if (ConstantImage.IMAGE_SEARCH_WITE_LIST.get(i).getId().equals(id)) {
                ConstantImage.IMAGE_SEARCH_WITE_LIST.remove(i);
                break;
            }
        }
        ImageSearchMemberInfo searchMemberInfo = new ImageSearchMemberInfo();
        searchMemberInfo.setId(id);
        searchMemberInfo.setNick(nick);
        //标记1分钟后到期
        searchMemberInfo.setExpireIn(DateUtil.dateChange(new Date(), Calendar.MINUTE, 1));
        ConstantImage.IMAGE_SEARCH_WITE_LIST.add(searchMemberInfo);
    }
}
