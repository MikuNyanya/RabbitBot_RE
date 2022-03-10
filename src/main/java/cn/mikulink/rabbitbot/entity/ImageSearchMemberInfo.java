package cn.mikulink.rabbitbot.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * created by MikuNyanya on 2022/3/10 14:33
 * For the Reisen
 * 存储触发了搜图指令的人员信息
 */
@Getter
@Setter
public class ImageSearchMemberInfo implements Serializable {
    /**
     * 账号
     */
    private Long id;
    /**
     * 名称
     */
    private String nick;
    /**
     * 所属群id
     */
    private Long groupId;
    /**
     * 搜图指令过期时间
     */
    private Date expireIn;

}
