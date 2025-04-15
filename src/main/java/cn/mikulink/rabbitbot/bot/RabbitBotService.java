package cn.mikulink.rabbitbot.bot;

import cn.mikulink.rabbitbot.bot.penguincenter.NapCatApi;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/1/9 17:00
 * for the Reisen
 * 机器人服务
 */
@Service
public class RabbitBotService {
    @Value("${bot.master:}")
    private String account_master;

    @Autowired
    private NapCatApi napCatApi;

    private List<Long> accountMasterList = new ArrayList<>();

    //群列表，缓存并单例下来供给其他业务使用
    private static List<GroupInfo> groupList = null;

    public List<GroupInfo> getGroupList() {
        //null表示没有初始化，长度为0则说明没加入任何群聊
        if (null != groupList) {
            return groupList;
        }

        //获取群信息
        groupList = napCatApi.getGroupList();
        return groupList;
    }

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
        if (CollectionUtils.isEmpty(accountMasterList)) {
            accountStrCheck(account_master, accountMasterList);
        }
        return accountMasterList.contains(userId);
    }

    private void accountStrCheck(String account_strs, List<Long> accountList) {
        if (StringUtil.isEmpty(account_strs)) {
            return;
        }
        String[] accounts = account_strs.split(",");
        for (String account : accounts) {
            if (StringUtil.isEmpty(account) || !NumberUtil.isNumberOnly(account)) {
                continue;
            }
            accountList.add(NumberUtil.toLong(account));
        }
    }

    /**
     * 给最高权限发送消息
     */
    public void sendMasterMessage(MessageInfo messageInfo) {
        if (CollectionUtils.isEmpty(accountMasterList)) {
            accountStrCheck(account_master, accountMasterList);
        }
        for (Long accountMaster : accountMasterList) {
            napCatApi.sendPrivateMessage(accountMaster, messageInfo.getMessage());
        }
    }

}
