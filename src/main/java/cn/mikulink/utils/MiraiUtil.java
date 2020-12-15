package cn.mikulink.utils;

/**
 * create by MikuLink on 2020/12/15 19:07
 * for the Reisen
 * <p>
 * 处理mirai相关的东西
 */
public class MiraiUtil {

    /**
     * 判断是否符合mirai的图片消息格式
     * [mirai:image:{FD4A1FC8-45F7-A3A9-FB8A-C75AFE71C47E}.mirai]
     *
     * @param msgStr 消息段
     * @return 是否为mirai图片码
     */
    public static boolean isMiraiImg(String msgStr) {
        String regex = "^[mirai:image:{FD4A1FC8-45F7-A3A9-FB8A-C75AFE71C47E}.mirai]$";
        return RegexUtil.regex(msgStr,regex);
    }


}
