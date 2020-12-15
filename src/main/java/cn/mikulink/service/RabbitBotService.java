package cn.mikulink.service;

import cn.mikulink.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * create by MikuLink on 2020/1/9 17:00
 * for the Reisen
 * 机器人服务
 */
@Service
public class RabbitBotService {
    @Value("${bot.master}")
    private String account_master;
    @Value("${bot.admin}")
    private String account_admin;

    /**
     * 检测指令操作间隔
     *
     * @param splitMap        指令对应的计时map
     * @param userId          qq号
     * @param userName        qq名称
     * @param commadSplitTime 间隔多久（毫秒）
     * @param splitMsg        检测未通过时的提示信息
     */
    public String commandTimeSplitCheck(Map<Long, Long> splitMap, Long userId, String userName, Long commadSplitTime, String splitMsg) {
        if (isMaster(userId)) {
            return null;
        }
        if (!splitMap.containsKey(userId)) {
            return null;
        }
        Long lastTime = splitMap.get(userId);
        if (null == lastTime) {
            return null;
        }
        Long nowTime = System.currentTimeMillis();
        Long splitTime = nowTime - lastTime;
        //判断是否允许操作
        if (splitTime <= commadSplitTime) {
            return String.format(splitMsg, userName, (commadSplitTime - splitTime) / 1000);
        }
        //其他情况允许操作
        return null;
    }

    /**
     * 是否最高权限
     *
     * @param userId qq号
     * @return 是否为最高权限
     */
    public boolean isMaster(Long userId) {
        if (null == userId || StringUtil.isEmpty(account_master)) {
            return false;
        }

        return accountStrCheck(account_master, userId);
    }

    /**
     * 是否为管理员权限
     *
     * @param userId qq号
     * @return 是否为管理员权限
     */
    public boolean isRabbitAdmin(Long userId) {
        if (null == userId) {
            return false;
        }
        if (isMaster((userId))) {
            return true;
        }
        if (StringUtil.isEmpty(account_admin)) {
            return false;
        }

        return accountStrCheck(account_admin, userId);
    }

    private boolean accountStrCheck(String account_strs, Long userId) {
        if (StringUtil.isEmpty(account_strs) || null == userId) {
            return false;
        }

        String userIdStr = String.valueOf(userId);
        String[] accounts = account_strs.split(",");
        for (String account : accounts) {
            if (account.equals(userIdStr)) return true;
        }

        return false;
    }
}
