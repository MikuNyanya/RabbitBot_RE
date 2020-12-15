package cn.mikulink.filemanage;


import cn.mikulink.constant.ConstantFile;
import cn.mikulink.constant.ConstantMorseCode;
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
 * 摩尔斯代码资源文件
 */
public class FileManagerMorseCode {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerMorseCode.class);
    //摩尔斯电码 文件
    private static File morsecodeFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != morsecodeFile) {
            return;
        }
        morsecodeFile = FileUtil.fileCheck(ConstantFile.APPEND_MORSECODE_FILE_PATH);
    }

    /**
     * 加载文件内容
     */
    public static void loadFile() {
        try {
            //检查文件状态
            fileInit();

            //创建读取器
            BufferedReader reader = new BufferedReader(new FileReader(morsecodeFile));

            //逐行读取文件
            String textStr = null;
            while ((textStr = reader.readLine()) != null) {
                //过滤掉空行
                if (textStr.length() <= 0) continue;
                //第一行是明文，再往下读一行，是摩尔斯电码
                String tempValue = reader.readLine();

                //内容同步到系统
                ConstantMorseCode.morse_code_map.put(textStr, tempValue);
            }
            //关闭读取器
            reader.close();
        } catch (IOException ioEx) {
            logger.error("摩尔斯电码文件读写异常:" + ioEx.toString(), ioEx);
        }
    }
}
