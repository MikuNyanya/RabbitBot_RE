package cn.mikulink.command.everywhere;

import cc.moecraft.icq.command.CommandProperties;
import cc.moecraft.icq.command.interfaces.EverywhereCommand;
import cc.moecraft.icq.event.events.message.EventMessage;
import cc.moecraft.icq.user.User;
import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantAmap;
import gugugu.service.WeatherService;

import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2019/11/30 23:33
 * for the Reisen
 * <p>
 * 天气情况
 */
public class CommandWeather implements EverywhereCommand {
    /**
     * 执行指令
     *
     * @param event   事件
     * @param sender  发送者的用户
     * @param command 指令名 ( 不包含指令参数 )
     * @param args    指令参数 ( 不包含指令名 )
     * @return 发送回去的消息 ( 当然也可以手动发送然后返回空 )
     */
    @Override
    public String run(EventMessage event, User sender, String command, ArrayList<String> args) {
        if (null == args || args.size() == 0) {
            return "";
        }
        String inputCity = args.get(0);
        String result = "";
        try {
            result = WeatherService.getWeatherByCityName(inputCity);
        } catch (Exception ex) {
            LoggerRabbit.logger().error("获取天气信息异常:" + ex.toString(), ex);
            result = ConstantAmap.WEATHER_API_FAIL;
        }
        return result;
    }

    @Override
    public CommandProperties properties() {
        return new CommandProperties("天气", "天气");
    }
}
