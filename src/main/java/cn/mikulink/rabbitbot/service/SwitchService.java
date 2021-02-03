package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantConfig;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.filemanage.FileManagerSwitch;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * create by MikuLink on 2021/2/3 16:37
 * for the Reisen
 * 功能开关
 * 这里会包括所有与开关有关的服务，无论业务或者系统层
 * 开关保存在文件里，采用json格式
 * 默认使用0代表关闭 1代表开启
 */
@Service
public class SwitchService {
    private Logger logger = LoggerFactory.getLogger(SwitchService.class);

    public ReString setSwitch(String switchName, String switchValue) throws IOException {
        return setSwitch(switchName, switchValue, null);
    }

    /**
     * 设置开关
     *
     * @param switchName  开关业务名称
     * @param switchValue 开关值
     * @param groupId     群id,每个群会有一个单独的开关配置
     * @return 执行结果
     */
    public ReString setSwitch(String switchName, String switchValue, Long groupId) throws IOException {
        FileManagerSwitch.setSwitch(switchName, switchValue, groupId);
        return new ReString(true);
    }

    /**
     * 获取开关值
     *
     * @param switchName 开关名称
     * @param groupId    群id
     * @return 开关值
     */
    public String getSwitchValue(String switchName, Long groupId) throws IOException {
        //如果被最高权限强制接管中，则获取默认开关配置
        if (switchFarceCheck()) {
            groupId = null;
        }
        return FileManagerSwitch.getSwitch(switchName, groupId);
    }

    /**
     * 开关配置是否被最高权限强制接管中
     * 这个属于配置，不在开关里
     *
     * @return boolean
     */
    public boolean switchFarceCheck() {
        String configSwitchFarce = ConstantConfig.common_config.get(ConstantConfig.CONFIG_SWITCH_FORCE);
        return StringUtil.isNotEmpty(configSwitchFarce) && ConstantCommon.ON.equalsIgnoreCase(configSwitchFarce);
    }
}
