package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:57
 * For the Reisen
 * 消息发送人
 */
@Getter
@Setter
public class SenderInfo {
    //qq号
    @JSONField(name = "user_id")
    private Long userId;
    //昵称
    private String nickname;
    //群备注
    private String card;
    //角色 owner 或 admin 或 member
    private String role;
    //专属头衔
    private String title;

    //是否为群管理员
    public boolean isGroupAdmin() {
        if (null == role) {
            return false;
        }

        return role.equals("owner") || role.equals("admin");
    }

    //是否为群主
    public boolean isGroupOwner() {
        if (null == role) {
            return false;
        }

        return role.equals("owner");
    }

    //是否为群员
    public boolean isGroupMember() {
        if (null == role) {
            return false;
        }

        return role.equals("member");
    }

    //获取用户名，优先使用群名片
    public String getGroupCardOrUserNick() {
        String userName = this.card;
        if (StringUtil.isEmpty(userName)) {
            userName = this.nickname;
        }
        return userName;
    }
}
