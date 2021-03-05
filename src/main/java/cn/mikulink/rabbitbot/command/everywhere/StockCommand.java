package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.CommandProperties;

import cn.mikulink.rabbitbot.service.StockService;
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

import java.net.SocketTimeoutException;
import java.util.ArrayList;

@Command
public class StockCommand extends BaseEveryWhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(StockCommand.class);

    @Override
    public CommandProperties properties() {
        return new CommandProperties("Stock", "指数");
    }

    @Autowired
    private StockService stockService;

    @Override
    public Message execute(User sender, ArrayList<String> args,
                           MessageChain messageChain, Contact subject) {
        try {
            if (args.isEmpty() || args.get(0).length() < 2) {
                return new PlainText(ConstantPixiv.API_IS_EMPTY);
            }
            String code = args.get(0);
            String codeHead = code.substring(0, 2).toLowerCase();
            if (!(codeHead.equals("sz") || codeHead.equals("sh"))) {
                return new PlainText(ConstantPixiv.API_PARAM_ERROR);
            }
            String codeBody = code.substring(2, code.length()).toLowerCase();
            if (!NumberUtil.isNumberOnly(codeBody)) {
                return new PlainText(ConstantPixiv.API_PARAM_ERROR);
            }

            return stockService.parseStockImgInfo(codeHead + codeBody);
        } catch (SocketTimeoutException stockTimeoutEx) {
            logger.warn(ConstantPixiv.API_TIMEOUT + stockTimeoutEx.toString());
            return new PlainText(ConstantPixiv.API_TIMEOUT);
        } catch (Exception ex) {
            logger.error(ConstantPixiv.API_ERROR + ex.toString(), ex);
            return new PlainText(ConstantPixiv.API_ERROR);
        }
    }


}
