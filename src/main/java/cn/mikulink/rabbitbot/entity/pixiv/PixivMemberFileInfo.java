package cn.mikulink.rabbitbot.entity.pixiv;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/4/29 15:21
 * for the Reisen
 * 存在本地文件中的p站用户信息
 */
@Getter
@Setter
public class PixivMemberFileInfo {
    //用户id
    private Long mamberId;
    //用户名称
    private String memberName;
    //用户账号
    private String memberAccount;
    //用户头像 计划存放图片文件名称
    private String memberLogo;
}
