package cn.mikulink.rabbitbot.entity.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * MikuLink created in 2025/4/1 18:30
 * For the Reisen
 */
@Data
@NoArgsConstructor
public class QQMessagePushInfo {
    public QQMessagePushInfo(String originalBody) {
        this.originalBody = originalBody;
    }

    private Long id;
    private Date createTime;
    private String originalBody;
}
