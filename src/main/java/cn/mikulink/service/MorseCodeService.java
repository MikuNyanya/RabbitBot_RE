package cn.mikulink.service;

import cn.mikulink.constant.ConstantMorseCode;
import cn.mikulink.utils.StringUtil;
import org.springframework.stereotype.Service;

/**
 * create by MikuLink on 2020/2/26 17:38
 * for the Reisen
 * 摩尔斯电码相关
 */
@Service
public class MorseCodeService {

    /**
     * 编码
     *
     * @param str   需要加密的字符串
     * @param split 摩尔斯电码之间的间隔
     * @return 加密好的摩尔斯电码
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
            if (ConstantMorseCode.morse_code_map.containsKey(tempStr)) {
                resultStr.append(ConstantMorseCode.morse_code_map.get(tempStr));
            } else {
                resultStr.append(tempStr);
            }
            resultStr.append(split);
        }
        return resultStr.substring(0, resultStr.length() - 1);
    }

    /**
     * 解码
     *
     * @param morseStr 摩尔斯电码
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

}
