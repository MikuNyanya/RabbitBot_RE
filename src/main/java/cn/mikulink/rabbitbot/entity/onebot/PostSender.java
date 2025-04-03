package cn.mikulink.rabbitbot.entity.onebot;

import lombok.Data;
/**
 * MikuLink created in 2025/3/31 22:58
 * For the Reisen
 */

@Data
public class PostSender {
    //qq号
    private Integer userId;
    //昵称
    private String nickname;
    //群备注
    private String card;
    //角色 owner 或 admin 或 member
    private String role;
    //专属头衔
    private String title;
}
