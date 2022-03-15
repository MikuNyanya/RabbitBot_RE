package cn.mikulink.rabbitbot.entity.newpneumonia;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * created by MikuNyanya on 2022/3/15 15:41
 * For the Reisen
 * https://voice.baidu.com/act/newpneumonia/newpneumonia
 * 新冠当前数据信息
 */
@NoArgsConstructor
@Data
public class VirusInfo implements Serializable {


    @JSONField(name = "page")
    private PageInfo page;
    @JSONField(name = "component")
    private List<ComponentInfo> component;
    @JSONField(name = "version")
    private Integer version;
}
