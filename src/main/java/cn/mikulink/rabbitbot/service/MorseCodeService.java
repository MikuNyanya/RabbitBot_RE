package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantMorseCode;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * create by MikuLink on 2020/2/26 17:38
 * for the Reisen
 * 摩斯电码相关
 */
@Service
public class MorseCodeService {

    @Value("${file.path.data:}")
    private String dataPath;

    /**
     * 获取资源文件路径
     */
    public String getFilePath() {
        return dataPath + File.separator + "files" + File.separator + "morsecode.txt";
    }

    /**
     * 编码
     *
     * @param str   需要加密的字符串
     * @param split 摩斯电码之间的间隔
     * @return 加密好的摩斯电码
     */
    public String encode(String str, String split) {
        if (StringUtil.isEmpty(str)) {
            return ConstantMorseCode.INPUT_STR_IS_EMPTY;
        }
        //默认空格作为间隔
        if (StringUtil.isEmpty(split)) {
            split = ConstantMorseCode.DEFAULT_SPLIT;
        }
        //转为全大写
        str = str.toUpperCase();

        StringBuilder resultStr = new StringBuilder();
        for (char c : str.toCharArray()) {
            String tempStr = String.valueOf(c);
            resultStr.append(ConstantMorseCode.morse_code_map.getOrDefault(tempStr, tempStr));
            resultStr.append(split);
        }
        return resultStr.substring(0, resultStr.length() - 1);
    }

    /**
     * 解码
     *
     * @param morseStr 摩斯电码
     * @param split    间隔标识
     * @return 解码后的字符串
     */
    public String decode(String morseStr, String split) {
        if (StringUtil.isEmpty(morseStr)) {
            return ConstantMorseCode.INPUT_MORSE_IS_EMPTY;
        }
        //默认空格作为间隔
        if (StringUtil.isEmpty(split)) {
            split = ConstantMorseCode.DEFAULT_SPLIT;
        }
        //字符统一处理，方便使用
        //转为全大写
        morseStr = morseStr.toUpperCase();
        //统一点和横线
        morseStr = morseStr.replace("━", "-");
        morseStr = morseStr.replace("．", ".");

        String[] morses = morseStr.split(split);

        StringBuilder resultStr = new StringBuilder();
        for (String morse : morses) {
            String tempStr = findMorse(morse);
            if (StringUtil.isEmpty(tempStr)) {
                resultStr.append(split);
                resultStr.append(morse);
                continue;
            }
            resultStr.append(tempStr);
        }
        return resultStr.toString();
    }

    private static String findMorse(String morse) {
        if (StringUtil.isEmpty(morse)) {
            return null;
        }
        for (String key : ConstantMorseCode.morse_code_map.keySet()) {
            if (ConstantMorseCode.morse_code_map.get(key).equalsIgnoreCase(morse)) {
                return key;
            }
        }
        return null;
    }

    /**
     * 加载文件内容
     */
    public void loadFile() throws IOException {
        File morsecodeFile = FileUtil.fileCheck(this.getFilePath());

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
