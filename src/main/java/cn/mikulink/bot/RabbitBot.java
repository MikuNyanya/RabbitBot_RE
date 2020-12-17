package cn.mikulink.bot;


import cn.mikulink.command.CommandConfig;
import cn.mikulink.event.MessageEvents;
import cn.mikulink.filemanage.*;
import cn.mikulink.sys.AnnotateScanner;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactoryJvm;
import net.mamoe.mirai.event.Events;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: \兔子万岁/
 * @author: MikuLink
 * @date: 2020/12/14 13:46
 **/
@Component
public class RabbitBot {
    private static final Logger logger = LoggerFactory.getLogger(RabbitBot.class);

    //一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    //指令相关
    @Autowired
    private CommandConfig commandConfig;
    //监听事件
    @Autowired
    private MessageEvents messageEvents;
    @Autowired
    private AnnotateScanner annotateScanner;
    //账号
    @Value("${bot.account}")
    private Long botAccount;
    //密码
    @Value("${bot.pwd}")
    private String botPwd;
    //设备认证信息文件
    private static final String deviceInfo = "deviceInfo.json";

    /**
     * 启动BOT
     */
    public void startBot() {
        if (null == botAccount || null == botPwd) {
            System.err.println("*****未配置兔叽的账号或密码*****");
            logger.warn("*****未配置兔叽的账号或密码*****");
        }

        Bot bot = BotFactoryJvm.newBot(botAccount, botPwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
            }
        });
        bot.login();

        //注册指令
        commandConfig.registerCommandHeads();
        commandConfig.registerCommands(annotateScanner.getCommandList());

        //注册事件
//        List<ListenerHost> events = Arrays.asList(
//                new MessageEvents()
//        );
//        for (ListenerHost event : events) {
//            Events.registerEvents(bot, event);
//        }

        Events.registerEvents(bot, messageEvents);

        //设置https协议，已解决SSL peer shut down incorrectly的异常
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        //加载资源文件
        FileManagerConfig.dataFileInit();

        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> {
            bot.join();
        }).start();
    }

}
