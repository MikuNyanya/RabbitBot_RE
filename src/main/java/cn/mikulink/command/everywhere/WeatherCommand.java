package cn.mikulink.command.everywhere;

import cn.mikulink.command.EverywhereCommand;
import cn.mikulink.constant.ConstantAmap;
import cn.mikulink.entity.CommandProperties;
import cn.mikulink.service.WeatherService;
import cn.mikulink.sys.annotate.Command;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 天气情况
 */
@Command
public class WeatherCommand implements EverywhereCommand {
    private static final Logger logger = LoggerFactory.getLogger(WeatherCommand.class);
    @Autowired
    private WeatherService weatherService;

    @Override
    public Message execute(User sender, ArrayList<String> args, MessageChain messageChain, Contact subject) {
        if (null == args || args.size() == 0) {
            return null;
        }
        String inputCity = args.get(0);
        String result = "";
        try {
            result = weatherService.getWeatherByCityName(inputCity);
        } catch (Exception ex) {
            logger.error("获取天气信息异常:" + ex.toString(), ex);
            result = ConstantAmap.WEATHER_API_FAIL;
        }
        return new PlainText(result);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("天气", "天气");
    }
}
