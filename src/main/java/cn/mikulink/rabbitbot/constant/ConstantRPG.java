package cn.mikulink.rabbitbot.constant;

/**
 * created by MikuNyanya on 2021/12/18 9:49
 * For the Reisen
 * rpg相关静态
 */
public class ConstantRPG extends ConstantCommon {
    public static final String EXPLAIN = "[.roll]指令是根据人物对应属性roll点"
            + "人物属性可使用[.人物属性]指令查看\n"
            + "指令后面添加roll的基础属性，不区分大小写，属性列表如下:\n"
            + "STR(力量)，DEX（敏捷），INTE（智力）\n"
            + "如果需要加入运气进行修正则使用下列属性:\n"
            + "LSTR(力量)，LDEX（敏捷），LINTE（智力）\n"
            + "举个栗子，以力量为基础掷点:\n"
            + ".roll str\n"
            + "以敏捷为基础附带运气修正掷点:\n"
            + ".roll ldex\n";

    //属性英文缩写，也会用于各种数值运算
    public static final String STR = "STR";     //力量
    public static final String DEX = "DEX";     //敏捷
    public static final String INTE = "INTE";   //智力
    public static final String LUCK = "LUCK";   //运气

    //roll点时附带运气修正的指令标识
    public static final String LSTR = "LSTR";     //力量
    public static final String LDEX = "LDEX";     //敏捷
    public static final String LINTE = "LINTE";   //智力

    //属性最大值
    public static final int Statistics_MAX = 99;

    public static final String NO_NAME = "你的名字是空的，得起个名字才行";
    public static final String COMMON_PARAM_ERROR = "输入的属性参数不正确";
}
