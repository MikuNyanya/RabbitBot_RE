package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 存一些文件相关信息 不过应该写成配置文件的
 */
public class ConstantFile extends ConstantCommon {
    //黑名单列表 文件相对路径
    public static final String BLACK_LIST_FILE_PATH = "src/main/resources/files/black_list";

    public static final String SRC_PATH_EMPTY = "原路径为空";
    public static final String DES_PATH_EMPTY = "目标路径为空";
    public static final String SRC_PATH_NOT_EXISTS = "原路径或文件不存在";
    public static final String DES_PATH_NOT_EXISTS = "目标路径或文件不存在";
}
