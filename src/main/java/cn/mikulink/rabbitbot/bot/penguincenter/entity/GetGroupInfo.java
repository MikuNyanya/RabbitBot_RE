package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MikuLink created in 2025/4/7 13:13
 * For the Reisen
 * 群信息
 */
@NoArgsConstructor
@Data
public class GetGroupInfo {
    //群备注
    @JSONField(name = "group_remark")
    private String groupRemark;
    //群号
    @JSONField(name = "group_id")
    private Long groupId;
    //群名
    @JSONField(name = "group_name")
    private String groupName;
    //群人数
    @JSONField(name = "member_count")
    private Integer memberCount;
    //群最大人数
    @JSONField(name = "max_member_count")
    private Integer maxMemberCount;
}
