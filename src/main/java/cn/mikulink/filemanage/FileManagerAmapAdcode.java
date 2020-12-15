package cn.mikulink.filemanage;

import cn.mikulink.constant.ConstantAmap;
import cn.mikulink.constant.ConstantFile;
import cn.mikulink.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 高德地图的城市代码
 */
public class FileManagerAmapAdcode {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerAmapAdcode.class);
    //高德地图城市代码 文件
    private static File amapAdcodeFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != amapAdcodeFile) {
            return;
        }
        amapAdcodeFile = FileUtil.fileCheck(ConstantFile.APPEND_AMAPADCODE_FILE_PATH);
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
            }
        } catch (IOException ioEx) {
            logger.error("高德地图acode文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    private static void loadFile() throws IOException {
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(amapAdcodeFile));

        //逐行读取文件
        String adcodeStr = null;
        while ((adcodeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (adcodeStr.length() <= 0) continue;

            //数据有三列，城市名称.adcode,citycode 三列中间以英文逗号隔开
            String[] adcodes = adcodeStr.split(",");
            //至少要有城市名称和adcode
            if (adcodes.length < 2) {
                continue;
            }

            //内容同步到系统
            ConstantAmap.map_adcode.put(adcodes[0], adcodes[1]);
        }
        //关闭读取器
        reader.close();
    }
}
