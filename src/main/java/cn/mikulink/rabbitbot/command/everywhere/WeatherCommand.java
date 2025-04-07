package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.constant.ConstantAmap;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.WeatherService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 天气情况
 */
@Slf4j
@Command
public class WeatherCommand extends EverywhereCommand {
    @Autowired
    private WeatherService weatherService;

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
        List<String> args = getArgs(messageInfo.getRawMessage());
        if (null == args || args.size() == 0) {
            return null;
        }
        String inputCity = args.get(0);
        String result = "";
        try {
            result = weatherService.getWeatherByCityName(inputCity);
        } catch (Exception ex) {
            log.error("获取天气信息异常", ex);
            result = ConstantAmap.WEATHER_API_FAIL;
        }
        return RabbitBotMessageBuilder.createMessageText(result);
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("天气", "天气");
    }
}
