package cn.mikulink.rabbitbot.quartzs;

import cn.mikulink.rabbitbot.command.everywhere.LoliconAppCommand;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * <p>
 * 一天执行一次的定时器
 */
@Component
public class JobDayRabbit {
    private static final Logger logger = LoggerFactory.getLogger(JobDayRabbit.class);

    public void execute() {
        reset();
    }

    private void reset(){
        //目前只重置推送次数
        LoliconAppCommand.PUSH_INDEX = 300;
    }

    private void historyToday() throws IOException {
        try{
            byte[] responseBytes = HttpsUtil.doGet("https://www.ipip5.com/today/api.php?type=json");
            //String body = new String(responseBytes);
            JSONObject.toJSONString(new String(responseBytes));
        }catch (IOException e){

        }

       // HttpsUtil.doGet("https://www.ipip5.com/today/api.php?type=json");
    }
}
