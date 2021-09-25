package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantGroupNotice;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.User;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
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
    @Autowired
    private ImageService imageService;
    @Autowired
    private RabbitBotService rabbitBotService;

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
        String groupNotic = configService.getGroupNotice(groupId);
        if (StringUtil.isNotEmpty(groupNotic)) {
            //处理换行符问题
            groupNotic = groupNotic.replaceAll("\\\\n", "\n");
        }
        return groupNotic;
    }

    /**
     * 转化群公告
     * 处理占位符之类的
     *
     * @param groupNoticeStr 群公告
     * @param sender         入群人信息
     * @return 处理后的群公告
     */
    public MessageChain parseGroupNotice(String groupNoticeStr, User sender) {
        MessageChain result = MessageUtils.newChain();

//        Long qq = sender.getId();
        String name = sender.getNick();

        //群员名称
        groupNoticeStr = groupNoticeStr.replaceAll(ConstantGroupNotice.REPLACE_USERNAME, name);

        //群员头像
        if (groupNoticeStr.contains(ConstantGroupNotice.REPLACE_USERLOGE)) {
            String qlogoLocalPath = imageService.getQLogoCq(sender.getId());
            Image imgLogo = rabbitBotService.uploadMiraiImage(qlogoLocalPath);

            String[] msgSplit = groupNoticeStr.split(ConstantGroupNotice.REPLACE_USERLOGE);
            for (int i = 0; i < msgSplit.length; i++) {
                if (i != 0) {
                    result = result.plus(imgLogo);
                }
                result = result.plus(msgSplit[i]);
            }
        }
        return result;
    }
}
