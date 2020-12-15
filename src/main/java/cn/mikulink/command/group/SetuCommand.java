package cn.mikulink.command.group;

import cn.mikulink.command.GroupCommand;
import cn.mikulink.constant.ConstantImage;
import cn.mikulink.entity.ReString;
import cn.mikulink.service.SetuService;
import cn.mikulink.entity.CommandProperties;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/12/13 15:50
 * for the Reisen
 * <p>
 * 来点色图
 */
@Component
public class SetuCommand implements GroupCommand {
    private static final Logger logger = LoggerFactory.getLogger(SetuCommand.class);

    @Autowired
    private SetuService setuService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("laidiansetu", "来点色图", "色图", "来份色图");
    }

    @Override
    public Message execute(Member sender, ArrayList<String> args, MessageChain messageChain, Group subject) {
        Long userId = sender.getId();
        String userNick = sender.getNameCard();

        //检查操作间隔
        ReString reString = setuService.setuTimeCheck(userId, userNick);
        if (!reString.isSuccess()) {
            return new PlainText(reString.getMessage());
        }

        try {
            //来张色图
            return setuService.getSetu(subject);
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.warn(ConstantImage.PIXIV_IMAGE_DELETE + fileNotFoundEx.toString());
            return new PlainText(ConstantImage.PIXIV_IMAGE_DELETE);
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantImage.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantImage.PIXIV_IMAGE_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantImage.PIXIV_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            return new PlainText(ConstantImage.PIXIV_ID_GET_ERROR_GROUP_MESSAGE);
        }
    }

}
