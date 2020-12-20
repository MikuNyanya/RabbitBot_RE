package cn.mikulink.rabbitbot.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2020/2/26 17:51
 * for the Reisen
 * 摩尔斯电码相关
 */
public class ConstantMorseCode extends ConstantCommon {
    public static final String MORSE_CODE_TEXT = "摩尔斯电码加密解密，举个栗子：" +
            "\n.摩尔斯 编码 rabbit" +
            "\n.摩尔斯 解码 .-. .- -... -... .. -";

    //摩尔斯电码对照表
    public static Map<String, String> morse_code_map = new HashMap<>();
    //默认以空格拆分
    public static final String DEFAULT_SPLIT = " ";
    //编码
    public static final String ENCODE = "encode";
    //编码_中文
    public static final String CN_ENCODE = "编码";
    //解码
    public static final String DECODE = "decode";
    //解码_中文
    public static final String CN_DECODE = "解码";

    public static final String INPUT_IS_EMPTY = "你得给我要加密的字符串，或者要解密的摩尔斯电码";
    public static final String INPUT_STR_IS_EMPTY = "输入的字符串为空";
    public static final String INPUT_MORSE_IS_EMPTY = "输入的摩尔斯电码为空";
    public static final String ACTION_ERROR = "指令错误，编码还是解码";
}
