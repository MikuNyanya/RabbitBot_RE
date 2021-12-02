package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

/**
 * @author MikuLink
 * @date 2021/11/04 14:58
 * for the Reisen
 * 日常语句相关服务
 */
@Service
public class FreeTimeService {
    private static final Logger logger = LoggerFactory.getLogger(FreeTimeService.class);

    @Value("${file.path.data:}")
    private String dataPath;

    /**
     * 获取日常语句资源文件路径
     */
    public String getFreeTimeFilePath() {
        return dataPath + File.separator + "files" + File.separator + "free_time.txt";
    }

    /**
     * 随机抽取一条日常语句
     *
     * @return 日常语句
     */
    public String randomMsg() {
        //删到六分之一时重新加载集合
        if (CollectionUtil.isEmpty(ConstantFreeTime.MSG_TYPE_FREE_TIME)
                || ConstantFreeTime.MSG_TYPE_FREE_TIME.size() < ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE / 6) {
            try {
                this.fildLoad();
            } catch (Exception ex) {
                logger.error("日常语句文件读取异常", ex);
            }
        }

        //选出一条信息
        //从列表中删除获取的消息，实现伪随机，不然重复率太高了，体验比较差
        return RandomUtil.rollAndDelStrFromList(ConstantFreeTime.MSG_TYPE_FREE_TIME);
    }

    /**
     * 添加一个日常语句
     *
     * @param freeTime 新的日常语句
     */
    public void addFreeTime(String... freeTime) throws IOException {
        //入参检验
        if (null == freeTime || freeTime.length <= 0) {
            return;
        }

        String filePath = this.getFreeTimeFilePath();
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));
        for (String s : freeTime) {
            //过滤空行
            if (StringUtil.isEmpty(s)) continue;

            //写入内容 写一行换一行
            out.write("\r\n" + s);
            //同时把新加的内容同步到系统
            ConstantFreeTime.MSG_TYPE_FREE_TIME.add(s);
            //刷新最大条目数,做伪随机用
            ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE++;
        }

        //关闭写入流
        out.close();
    }

    /**
     * 读取文件内容
     */
    public void fildLoad() throws IOException {
        String filePath = this.getFreeTimeFilePath();
        //初始化集合
        ConstantFreeTime.MSG_TYPE_FREE_TIME = new ArrayList<>();

        File freeTimeFile = FileUtil.fileCheck(filePath);

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(freeTimeFile));
        //逐行读取文件
        String freeTimeStr = null;
        while ((freeTimeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (freeTimeStr.length() <= 0) continue;
            ConstantFreeTime.MSG_TYPE_FREE_TIME.add(freeTimeStr);
        }
        //刷新最大条目数,做伪随机用
        ConstantFreeTime.MSG_TYPE_FREE_TIME_MAX_SIZE = ConstantFreeTime.MSG_TYPE_FREE_TIME.size();
        //关闭读取器
        reader.close();
    }
}
