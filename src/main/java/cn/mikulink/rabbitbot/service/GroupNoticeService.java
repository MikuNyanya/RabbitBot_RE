package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.entity.ReString;
import net.mamoe.mirai.contact.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by MikuNyanya on 2021/9/14 13:50
 * For the Reisen
 * 群公告相关
 */
@Service
public class GroupNoticeService {
    @Autowired
    private ConfigService configService;

    /**
     * 设置群公告
     * 理应只有群主可以设置
     *
     * @param groupId        群id
     * @param groupNoticeStr 群公告
     * @return 执行结果
     */
    public ReString createGroupNotice(Long groupId, String groupNoticeStr) {
        return configService.setGroupNotice(groupId, groupNoticeStr);
    }

    /**
     * 获取群公告
     *
     * @param groupId 群id
     * @return 群公告字符串  占位符原样返回
     */
    public String getGroupNotice(Long groupId) {
        return configService.getGroupNotice(groupId);
    }

    /**
     * 转化群公告
     * 处理占位符之类的
     *
     * @param groupNoticeStr 群公告
     * @param sender         入群人信息
     * @return 处理后的群公告
     */
    public String parseGroupNotice(String groupNoticeStr, User sender) {
        return configService.getGroupNotice(groupId);
    }
}
