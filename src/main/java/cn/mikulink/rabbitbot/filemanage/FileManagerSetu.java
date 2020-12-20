package cn.mikulink.rabbitbot.filemanage;



import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;

/**
 * create by MikuLink on 2020/12/13 15:44
 * for the Reisen
 * <p>
 * p站色图的文件专用管理器
 */
public class FileManagerSetu {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerSetu.class);
    //扭蛋 文件
    private static File setuFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != setuFile) {
            return;
        }
        setuFile = FileUtil.fileCheck(ConstantFile.PIXIV_SETU_FILE_PATH);
    }

    /**
     * 根据传入指令执行对应程序
     */
    public static void doCommand(String command, String text) {
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
            logger.error("色图pid文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //初始化集合
        ConstantFile.List_SETU_PID = new ArrayList<>();

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(setuFile));
        //逐行读取文件
        String setuPid = null;
        while ((setuPid = reader.readLine()) != null) {
            //过滤掉空行
            if (setuPid.length() <= 0) continue;
            ConstantFile.List_SETU_PID.add(setuPid);
        }
        //关闭读取器
        reader.close();
        //刷新最大元素数目
        ConstantFile.SETU_PID_LIST_MAX_SIZE = ConstantFile.List_SETU_PID.size();
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    private static void writeFile(String text) throws IOException {
        //入参检验
        if (StringUtil.isEmpty(text)) {
            throw new IOException("必须传递文件写入内容");
        }

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PIXIV_SETU_FILE_PATH, true)));

        //写入内容 写一行换一行
        out.write("\r\n" + text);
        //同时把新加的内容同步到系统
        ConstantFile.List_SETU_PID.add(text);
        //刷新最大数目
        ConstantFile.SETU_PID_LIST_MAX_SIZE++;

        //关闭写入流
        out.close();
    }
}
