package cn.mikulink.rabbitbot.bot;


import cn.mikulink.rabbitbot.command.CommandConfig;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.sys.AnnotateScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description: \兔子万岁/
 * @author: MikuLink
 * @date: 2020/12/14 13:46
 * <p>
 * 启动类
 **/
@Component
public class RabbitBot implements ApplicationRunner {
    //指令相关
    @Autowired
    private CommandConfig commandConfig;
    @Autowired
    private ConfigService configService;
    @Autowired
    private AnnotateScanner annotateScanner;

    @Override
    public void run(ApplicationArguments args) {
        //加载资源文件
        configService.dataFileInit();

        //注册指令
        commandConfig.registerCommandHeads();
        commandConfig.registerCommands(annotateScanner.getCommandList());
    }
}
