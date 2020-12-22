package cn.mikulink.rabbitbot.filemanage;


import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantMorseCode;
import cn.mikulink.rabbitbot.utils.FileUtil;

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

    /**
     * 加载文件内容
     */
    public static void loadFile() throws IOException {
        File morsecodeFile = FileUtil.fileCheck(ConstantFile.APPEND_MORSECODE_FILE_PATH);

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
    }
}
