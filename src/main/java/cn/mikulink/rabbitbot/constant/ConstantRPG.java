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
    public static final int STATS_MAX = 99;

    public static final String NO_NAME = "你的名字是空的，得起个名字才行";
    public static final String COMMON_PARAM_ERROR = "输入的属性参数不正确";


    public static final String KICKASS_EXPLAIN = "使用[.vs @群友]来发起群友互殴,比如："
            + ".vs @兔叽"
            + "一场互殴结束后才能发起下一场互殴";

    //互殴操作超时时间 秒
    public static final int KICKASS_TIME_OUT = 1;
    //操作 todo 写为配置，每个动作为一个对象，包含id，名称，类型信息，从文件读取，缓存
    //力量系
    public static final String KICKASS_ACTION_TUNKHEAD_NO = "1";
    public static final String KICKASS_ACTION_TUNKHEAD = "敲脑阔";
    public static final String KICKASS_ACTION_PUNCH_NO = "2";
    public static final String KICKASS_ACTION_PUNCH_EN = "PUNCH";
    public static final String KICKASS_ACTION_PUNCH = "给他一拳";
    //敏捷系
    public static final String KICKASS_ACTION_NYAPUNCH_NO = "3";
    public static final String KICKASS_ACTION_NYAPUNCH = "速攻猫猫拳";
    public static final String KICKASS_ACTION_JUMPKICK_NO = "4";
    public static final String KICKASS_ACTION_JUMPKICK = "飞踢";
    //智力系
    public static final String KICKASS_ACTION_PLANB_NO = "5";
    public static final String KICKASS_ACTION_PLANB = "尝试切他中路";
    public static final String KICKASS_ACTION_TALK_NO = "6";
    public static final String KICKASS_ACTION_TALK = "聊天流";

    //当然可以有混乱系，要单独写计算公式


    public static final String KICKASS_STARTED = "本群已经开始一场互殴，结束后才能开始下一场";
    public static final String KICKASS_TARGET_404 = "目标群友不存在";
    public static final String KICKASS_TARGET_ERROR = "获取目标群友信息失败";
    public static final String KICKASS_404 = "本群没有正在进行的互殴";
    public static final String KICKASS_IS_NOT_STARTER = "";


}
