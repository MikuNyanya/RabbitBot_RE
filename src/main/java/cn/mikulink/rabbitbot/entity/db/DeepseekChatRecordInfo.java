package cn.mikulink.rabbitbot.entity.db;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * MikuLink created in 2025/4/8 22:00
 * For the Reisen
 */
@Data
@NoArgsConstructor
public class DeepseekChatRecordInfo {
    //主键id
    private Long id;
    //数据创建时间
    private Date createTime;
    private String tag;
    //
    private String roleType;
    private String message;
    private String apiJson;

}
