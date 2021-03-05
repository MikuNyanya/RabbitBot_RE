package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.apirequest.news.NewsTodayEntity;
import cn.mikulink.rabbitbot.service.NewsTodayService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Optional;

@Command
public class NewsTodayCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(HistoryTodayCommand.class);
    @Autowired
    private NewsTodayService newsTodayService;
    @Override
    public CommandProperties properties() {
        return new CommandProperties("NewsToday", "每日新闻");
    }

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        try {
            //根据tag获取接口返回信息
            NewsTodayEntity newsTodayEntity = this.newsTodayService.getNewsToday();
            if(newsTodayEntity == null){
                return new PlainText(ConstantPixiv.API_ERROR);
            }
            return newsTodayService.parseNewsToday(newsTodayEntity);
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantPixiv.API_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.API_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantPixiv.API_ERROR + ex.toString(), ex);
            return new PlainText(ConstantPixiv.API_ERROR);
        }
    }
}
