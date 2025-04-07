package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MikuLink created in 2025/4/7 17:46
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class GroupMemberInfo {
    //群号
    @JSONField(name = "group_id")
    private Integer groupId;
    //群员id
    @JSONField(name = "user_id")
    private Integer userId;
    //群员名称
    @JSONField(name = "nickname")
    private String nickname;
    //群名片
    @JSONField(name = "card")
    private String card;
    //性别 male男 female女 unknown未知
    @JSONField(name = "sex")
    private String sex;
    //年龄
    @JSONField(name = "age")
    private Integer age;
    @JSONField(name = "area")
    private String area;
    //群等级
    @JSONField(name = "level")
    private String level;
    //号等级
    @JSONField(name = "qq_level")
    private Integer qqLevel;
    //入群时间
    @JSONField(name = "join_time")
    private Integer joinTime;
    //最后发言时间
    @JSONField(name = "last_sent_time")
    private Integer lastSentTime;
    @JSONField(name = "title_expire_time")
    private Integer titleExpireTime;
    //是否不良记录成员
    @JSONField(name = "unfriendly")
    private Boolean unfriendly;
    //是否允许修改群名片
    @JSONField(name = "card_changeable")
    private Boolean cardChangeable;
    //是否是官方机器人
    @JSONField(name = "is_robot")
    private Boolean isRobot;
    //禁言到期时间
    @JSONField(name = "shut_up_timestamp")
    private Integer shutUpTimestamp;
    //群权限 owner创始人 admin管理员 member普通群员
    @JSONField(name = "role")
    private String role;
    //专属头衔
    @JSONField(name = "title")
    private String title;
}
