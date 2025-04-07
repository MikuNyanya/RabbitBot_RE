package cn.mikulink.rabbitbot.service.sys;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantConfig;
import cn.mikulink.rabbitbot.constant.ConstantSwitch;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import com.alibaba.fastjson2.JSONObject;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.contact.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;

/**
 * create by MikuLink on 2021/2/3 16:37
 * for the Reisen
 * 功能开关
 * 这里会包括所有与开关有关的服务，无论业务或者系统层
 * 开关保存在文件里，采用json格式
 * 默认使用0代表关闭 1代表开启
 * 也许这个功能应该叫功能状态
 */
@Service
public class SwitchService {
    private Logger logger = LoggerFactory.getLogger(SwitchService.class);

    @Value("${file.path.config:}")
    private String configPath;

    @Autowired
    private RabbitBotService rabbitBotService;


    /**
     * 获取开关文件路径
     */
    public String getFilePath() {
        return configPath + File.separator + "switch";
    }

    /**
     * 获取群开关文件路径
     * config/group/1111111/switch
     */
    public String getGroupFilePath(Long groupId) {
        if (null == groupId) {
            return this.getFilePath();
        }
        return configPath + File.separator + "groups" + File.separator + groupId + File.separator + "switch";
    }

    public String getSwitch(String switchName) throws IOException {
        return getSwitch(switchName, null);
    }

    /**
     * 读取开关配置
     *
     * @param switchName 开关名称
     * @param groupId    群id,如果没有群id，读取默认配置
     * @throws IOException 文件操作异常
     */
    public String getSwitch(String switchName, Long groupId) throws IOException {
        String filePath = this.getGroupFilePath(groupId);
        //读取文件
        File switchFile = FileUtil.fileCheck(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(switchFile));
        //只读取第一行
        String switchJson = reader.readLine();
        HashMap<String, String> switchMapTamp = null;
        if (StringUtil.isEmpty(switchJson)) {
            switchMapTamp = new HashMap<>();
        } else {
            switchMapTamp = JSONObject.parseObject(switchJson, HashMap.class);
        }

        //关闭读取器
        reader.close();

        return switchMapTamp.get(switchName);
    }

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
        String filePath = this.getGroupFilePath(groupId);
        //读取文件
        File switchFile = FileUtil.fileCheck(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(switchFile));
        //只读取第一行
        String switchJson = reader.readLine();
        HashMap<String, String> switchMapTamp = null;
        if (null == switchJson || switchJson.length() <= 0) {
            switchMapTamp = new HashMap<>();
        } else {
            switchMapTamp = JSONObject.parseObject(switchJson, HashMap.class);
        }

        //添加或覆盖开关配置
        switchMapTamp.put(switchName, switchValue);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        out.write(JSONObject.toJSONString(switchMapTamp));

        //关闭写入流
        out.close();
        //关闭读取器
        reader.close();
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
        return this.getSwitch(switchName, groupId);
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

    /**
     * 开关状态检查
     *
     * @param sender     消息发送人 可以为空 用于特殊判断
     * @param subject    消息体    可以为空 会读取默认配置
     * @param switchName 开关名称  可以为空 默认为关闭
     * @return 开关状态以及反馈信息
     */
    public ReString switchCheck(User sender, Contact subject, String switchName) {
        //传入判断，参数有问题，一律按照关闭返回
        if (StringUtil.isEmpty(switchName)) {
            return new ReString(false, ConstantSwitch.SWITCH_GET_ERROR);
        }

        //最高权限不受限制
        if (null != sender && rabbitBotService.isMaster(sender.getId())) {
            return new ReString(true);
        }

        String switchValue = null;
        Long groupId = null;
        if (subject instanceof Group) {
            //来源于群
            groupId = subject.getId();
        }
        try {
            //todo 开关丢缓存里，不用每次读取文件
            switchValue = getSwitchValue(switchName, groupId);
        } catch (IOException ioEx) {
            //异常视为关闭
            logger.warn(ConstantSwitch.SWITCH_GET_ERROR, ioEx);
            switchValue = ConstantCommon.OFF;
        }

        //优先判断其他类型
        //工作模式，规定时间内，禁止执行
        if (ConstantSwitch.SWITCH_WORK.equalsIgnoreCase(switchValue)) {
            if (DateUtil.isTimeOfWork()) {
                return new ReString(false, ConstantSwitch.SWITCH_WORK_MSG);
            }
            return new ReString(true);
        }

        //最后判断是否开启
        if (ConstantSwitch.ON.equalsIgnoreCase(switchValue)) {
            return new ReString(true);
        }

        //其他情况禁用功能
        return new ReString(false, ConstantSwitch.SWITCH_OFF_MSG);
    }
}
