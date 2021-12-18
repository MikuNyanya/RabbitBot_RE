package cn.mikulink.rabbitbot.utils;

/**
 * created by MikuNyanya on 2021/12/18 11:01
 * For the Reisen
 * 进度条工具
 */
public class BarUtil {
    //状态条_表示充满 ▆▆▆▆▆▁▁▁▁▁ 50%
    private static final String STATUS_FULL = "▆";
    //状态条_表示空的
    private static final String STATUS_EMPTY = "▁";
    //状态条最大长度
    private static final Integer STATUS_BAR_MIN = 0;
    //状态条最大长度
    private static final Integer STATUS_BAR_MAX = 10;

    /**
     * 生成一个进度条
     *
     * @param full 已完成的部分 值为 0 - 10
     * @return 进度条
     */
    public static String createStatusBar(int full) {
        if (full < STATUS_BAR_MIN) {
            full = STATUS_BAR_MIN;
        }
        if (full > STATUS_BAR_MAX) {
            full = STATUS_BAR_MAX;
        }

        StringBuilder result = new StringBuilder();
        //填充充满的条
        for (int i = 0; i < full; i++) {
            result.append(STATUS_FULL);
        }

        //除了充满的条，剩余的为空条
        int empty = STATUS_BAR_MAX - full;
        for (int i = 0; i < empty; i++) {
            result.append(STATUS_EMPTY);
        }
        return result.toString();
    }

}
