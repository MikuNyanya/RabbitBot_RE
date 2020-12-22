package cn.mikulink.rabbitbot.filemanage;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.utils.FileUtil;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 配置文件文件专用管理器
 */
public class FileManagerConfig {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerConfig.class);
    //配置文件
    private static File configFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != configFile) {
            return;
        }
        configFile = FileUtil.fileCheck(ConstantFile.CONFIG_FILE_PATH);
    }

    /**
     * 根据传入指令执行对应程序
     */
    public static void doCommand(String command) {
        try {
            //检查文件状态
            fileInit();

            //执行对应指令
            switch (command) {
                case ConstantFile.FILE_COMMAND_LOAD:
                    loadFile();
                    break;
                case ConstantFile.FILE_COMMAND_WRITE:
                    writeFile();
                    break;
            }
        } catch (IOException ioEx) {
            logger.error("配置文件读写异常:" + ioEx.toString(), ioEx);
        } catch (JSONException jsonEx) {
            logger.error("配置文件json转化异常:" + jsonEx.toString(), jsonEx);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() <= 0) continue;
            ConstantCommon.common_config = JSONObject.parseObject(configJson, HashMap.class);
            return;
        }
        //关闭读取器
        reader.close();
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    private static void writeFile() throws IOException {
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.CONFIG_FILE_PATH, false)));
        //覆写原本配置
        out.write(JSONObject.toJSONString(ConstantCommon.common_config));
        //关闭写入流
        out.close();
    }

    /**
     * 资源文件初始化
     */
    public static void dataFileInit() {
        try {
            //配置
            doCommand(ConstantFile.FILE_COMMAND_LOAD);
            //日常语句
            FileManagerFreeTime.doCommand(ConstantFile.FILE_COMMAND_LOAD);
            //关键词匹配-全匹配
            FileManagerKeyWordNormal.doCommand(ConstantFile.FILE_COMMAND_LOAD);
            //关键词匹配-模糊匹配
            FileManagerKeyWordLike.doCommand(ConstantFile.FILE_COMMAND_LOAD);
            //高德地图接口区域代码信息
            FileManagerAmapAdcode.loadFile();
            //摩尔斯电码
            FileManagerMorseCode.loadFile();
            //pixiv图片所有tag
            FileManagerPixivTags.loadFile();
            //pixiv图片已整理的tag
            FileManagerPixivTags.loadRabbitFile();
            //pixiv用户信息
            FileManagerPixivMember.loadFile();
            //塔罗牌
            FileManagerTarot.loadFile();

            //压缩图片文件夹检测
            FileUtil.fileDirsCheck(ConstantImage.DEFAULT_IMAGE_SCALE_SAVE_PATH);
        } catch (Exception ex) {
            logger.error("资源文件读取异常:{}", ex.getMessage(), ex);
        }
    }
}
