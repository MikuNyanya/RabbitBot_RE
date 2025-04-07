package cn.mikulink.rabbitbot.bot.penguincenter.entity;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * MikuLink created in 2025/4/7 13:13
 * For the Reisen
 * 基本返回信息
 */
@NoArgsConstructor
@Data
public class BaseRsp {
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "retcode")
    private Integer retcode;
    @JSONField(name = "data")
    private String data;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "wording")
    private String wording;
    @JSONField(name = "echo")
    private Object echo;
}
