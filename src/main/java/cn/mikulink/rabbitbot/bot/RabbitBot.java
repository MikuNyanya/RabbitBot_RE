package cn.mikulink.rabbitbot.bot;


import cn.mikulink.rabbitbot.command.CommandConfig;
import cn.mikulink.rabbitbot.event.GroupEvents;
import cn.mikulink.rabbitbot.event.MessageEvents;
import cn.mikulink.rabbitbot.event.NudgeEvents;
import cn.mikulink.rabbitbot.filemanage.FileManagerConfig;
import cn.mikulink.rabbitbot.sys.AnnotateScanner;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.ListenerHost;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: \兔子万岁/
 * @author: MikuLink
 * @date: 2020/12/14 13:46
 **/
@Component
public class RabbitBot implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(RabbitBot.class);

    //一个实例只给一个bot，暂时不考虑一个实例允许部署多个bot
    private static Bot bot;

    public static Bot getBot() {
        return bot;
    }

    //指令相关
    @Autowired
    private CommandConfig commandConfig;
    //监听事件 再多一点就要跟指令一样写个自定义注解了
    @Autowired
    private MessageEvents messageEvents;
    @Autowired
    private GroupEvents groupEvents;
    @Autowired
    private NudgeEvents nudgeEvents;

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

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.startBot();
    }

    /**
     * 启动BOT
     */
    private void startBot() {
        if (null == botAccount || null == botPwd) {
            System.err.println("*****未配置兔叽的账号或密码*****");
            logger.warn("*****未配置兔叽的账号或密码*****");
        }

        //加载资源文件
        FileManagerConfig.dataFileInit();

        bot = BotFactory.INSTANCE.newBot(botAccount, botPwd, new BotConfiguration() {
            {
                //保存设备信息到文件deviceInfo.json文件里相当于是个设备认证信息
                fileBasedDeviceInfo(deviceInfo);
                setProtocol(MiraiProtocol.ANDROID_PHONE); // 切换协议
            }
        });
        bot.login();

        //注册指令
        commandConfig.registerCommandHeads();
        commandConfig.registerCommands(annotateScanner.getCommandList());

        //注册事件
        List<ListenerHost> events = Arrays.asList(
                messageEvents,
                groupEvents,
                nudgeEvents
        );
        for (ListenerHost event : events) {
            GlobalEventChannel.INSTANCE.registerListenerHost(event);
        }

        //设置https协议，已解决SSL peer shut down incorrectly的异常
        System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2,SSLv3");

        // 这个和picbotx 还是不太一样 那个不会占用主线程
        // 这里必须要启新线程去跑bot 不然会占用主线程
        new Thread(() -> {
            bot.join();
        }).start();
    }
}
