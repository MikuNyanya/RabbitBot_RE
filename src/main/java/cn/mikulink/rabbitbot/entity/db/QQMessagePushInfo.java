package cn.mikulink.rabbitbot.entity.db;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * MikuLink created in 2025/4/1 18:30
 * For the Reisen
 */
@Getter
@Setter
public class QQMessagePushInfo implements Serializable {
    public QQMessagePushInfo(){}
    public QQMessagePushInfo(String originalBody){this.originalBody = originalBody;}
    private Long id;
    private Date createTime;
    private String originalBody;
}
