package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.filemanage.FileManagerFreeTime;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author MikuLink
 * @date 2021/11/04 14:58
 * for the Reisen
 * 日常语句相关服务
 */
@Service
public class FreeTimeService {
    private static final Logger logger = LoggerFactory.getLogger(FreeTimeService.class);

    /**
     * 随机抽取一条日常语句
     * @return 日常语句
     */
    public String randomMsg(){
        //选出一条信息
        //从列表中删除获取的消息，实现伪随机，不然重复率太高了，体验比较差
        String msg = RandomUtil.rollAndDelStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);

        //删到六分之一时重新加载集合
        if (ConstantFreeTime.MSG_TYPE_FREE_TIME.size() < ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE / 6) {
            FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_LOAD);
        }

        return msg;
    }

}
