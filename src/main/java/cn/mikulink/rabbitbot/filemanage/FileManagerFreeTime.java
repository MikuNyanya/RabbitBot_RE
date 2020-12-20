package cn.mikulink.rabbitbot.filemanage;

import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 日常语句文件专用管理器
 */
public class FileManagerFreeTime {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerFreeTime.class);
    //常规语句 文件
    private static File freeTimeFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != freeTimeFile) {
            return;
        }
        freeTimeFile = FileUtil.fileCheck(ConstantFile.APPEND_FREE_TIME_FILE_PATH);
    }

    /**
     * 根据传入指令执行对应程序
     */
    public static void doCommand(String command, String... text) {
        try {
            //检查文件状态
            fileInit();

            //执行对应指令
            switch (command) {
                case ConstantFile.FILE_COMMAND_LOAD:
                    loadFile();
                    break;
                case ConstantFile.FILE_COMMAND_WRITE:
                    writeFile(text);
                    break;
            }
        } catch (IOException ioEx) {
            logger.error("日常语句文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //初始化集合
        ConstantFreeTime.MSG_TYPE_FREE_TIME = new ArrayList<>();

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

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    private static void writeFile(String... text) throws IOException {
        //入参检验
        if (null == text || text.length <= 0) {
            throw new IOException("必须传递文件写入内容");
        }

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.APPEND_FREE_TIME_FILE_PATH, true)));
        for (String s : text) {
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

}
