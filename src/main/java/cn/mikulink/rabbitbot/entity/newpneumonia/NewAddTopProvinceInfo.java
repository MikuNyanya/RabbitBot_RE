package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * created by MikuNyanya on 2022/3/15 15:43
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class NewAddTopProvinceInfo {
    @JSONField(name = "name")
    private String name;
    @JSONField(name = "local")
    private Integer local;
    @JSONField(name = "overseasInput")
    private Integer overseasInput;
}
