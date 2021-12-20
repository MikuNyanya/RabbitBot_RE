package cn.mikulink.rabbitbot.service.sys;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.ConfigGroupInfo;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.service.MorseCodeService;
import cn.mikulink.rabbitbot.service.PetService;
import cn.mikulink.rabbitbot.service.TarotService;
import cn.mikulink.rabbitbot.service.WeatherService;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2021/04/29 11:30
 * for the Reisen
 */
@Service
public class ConfigService {
    private Logger logger = LoggerFactory.getLogger(ConfigService.class);

    //群配置 缓存
    Map<Long, ConfigGroupInfo> configGroupsMap = new HashMap<>();
    @Value("${file.path.config:}")
    private String configPath;
    @Autowired
    private TarotService tarotService;
    @Autowired
    private MorseCodeService morseCodeService;
    @Autowired
    private PetService petService;
    @Autowired
    private BlackListService blackListService;
    @Autowired
    private WeatherService weatherService;

    /**
     * 获取配置文件路径
     */
    public String getFilePath() {
        return configPath + File.separator + "config";
    }

    /**
     * 获取群配置文件路径
     * config/group/1111111/config
     */
    public String getGroupFilePath(Long groupId) {
        return configPath + File.separator + "groups" + File.separator + groupId + File.separator + "config";
    }

    /**
     * 资源文件初始化
     */
    public void dataFileInit() {
        try {
            //配置
            this.loadFile();
            //摩尔斯电码
            morseCodeService.loadFile();
            //塔罗牌
            tarotService.loadFile();
            //黑名单
            blackListService.loadFile();
            //养成系统信息
            petService.loadPetInfo();
            //加载高的城市信息文件
            weatherService.loadFile();

            //压缩图片文件夹检测
            FileUtil.fileDirsCheck(ConstantImage.DEFAULT_IMAGE_SCALE_SAVE_PATH);
        } catch (Exception ex) {
            logger.error("资源文件读取异常:{}", ex.getMessage(), ex);
        }
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public void loadFile() throws IOException {
        FileUtil.fileCheck(this.getFilePath());
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(this.getFilePath()));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() <= 0) continue;
            ConstantCommon.common_config = JSONObject.parseObject(configJson, HashMap.class);
            return;
        }
        //关闭读取器
        reader.close();
    }

    /**
     * 刷新配置文件
     */
    public void refreshConfigFile() {
        try {
            this.writeFile();
        } catch (Exception ex) {
            logger.error("刷新配置文件失败");
        }
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    public void writeFile() throws IOException {
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getFilePath(), false)));
        //覆写原本配置
        out.write(JSONObject.toJSONString(ConstantCommon.common_config));
        //关闭写入流
        out.close();
    }

    /**
     * 读取群配置
     */
    public String loadConfigGroup(Long groupId) throws IOException {
        String filePath = this.getGroupFilePath(groupId);
        //读取文件
        File groupConfigFile = FileUtil.fileCheck(filePath);
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(groupConfigFile));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() > 0) break;
        }
        //关闭读取器
        reader.close();
        return configJson;
    }

    /**
     * 覆写群配置
     */
    public void writerConfigGroup(Long groupId, String configJsonStr) throws IOException {
        String filePath = this.getGroupFilePath(groupId);
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        //覆写原本配置
        out.write(configJsonStr);
        //关闭写入流
        out.close();
    }

    /**
     * 检查群是否订阅了指定微博账号的推送
     * 默认未订阅
     *
     * @param groupId 群号
     * @return 是否订阅了指定微博账号
     */
    public boolean checkWeiboPushId(Long groupId, Long weiboUserId) {
        if (null == groupId) return true;

        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        if (null == configGroupInfo) {
            return false;
        }
        return configGroupInfo.getWeiboPushIds().contains(weiboUserId);
    }

    /**
     * 群订阅微博消息
     *
     * @param groupId 群id
     * @param idsStr  id列表，多个用逗号隔开
     */
    public void pullWeibo(Long groupId, String idsStr) {
        String[] ids = idsStr.split(",");
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        for (String id : ids) {
            if (!NumberUtil.isNumberOnly(id)) continue;
            Long userId = NumberUtil.toLong(id);
            if (configGroupInfo.getWeiboPushIds().contains(userId)) continue;
            configGroupInfo.getWeiboPushIds().add(userId);
        }
        try {
            this.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService pullWeibo error,groupId:{},idsStr:{}", groupId, idsStr, ex);
        }
    }

    /**
     * 群取消订阅微博消息
     *
     * @param groupId 群id
     * @param idsStr  id列表，多个用逗号隔开
     */
    public void unpullWeibo(Long groupId, String idsStr) {
        String[] ids = idsStr.split(",");
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);

        for (String id : ids) {
            if (!NumberUtil.isNumberOnly(id)) continue;
            Long userId = NumberUtil.toLong(id);
            if (!configGroupInfo.getWeiboPushIds().contains(userId)) continue;
            configGroupInfo.getWeiboPushIds().remove(userId);
        }
        try {
            this.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService unpullWeibo error,groupId:{},idsStr:{}", groupId, idsStr, ex);
        }
    }

    /**
     * 检查群是否订阅了指定bili账号的推送
     * 默认未订阅
     *
     * @param groupId 群号
     * @return 是否订阅了指定b站账号
     */
    public boolean checkBiliPushId(Long groupId, Long biliUserId) {
        if (null == groupId) return true;

        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        if (null == configGroupInfo) {
            return false;
        }
        return configGroupInfo.getBiliPushIds().contains(biliUserId);
    }

    /**
     * 群订阅微博消息
     *
     * @param groupId 群id
     * @param idsStr  id列表，多个用逗号隔开
     */
    public void pullBiliUid(Long groupId, String idsStr) {
        String[] ids = idsStr.split(",");
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        for (String id : ids) {
            if (!NumberUtil.isNumberOnly(id)) continue;
            Long userId = NumberUtil.toLong(id);
            if (configGroupInfo.getBiliPushIds().contains(userId)) continue;
            configGroupInfo.getBiliPushIds().add(userId);
        }
        try {
            this.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService pullBiliUid error,groupId:{},idsStr:{}", groupId, idsStr, ex);
        }
    }

    /**
     * 群取消订阅微博消息
     *
     * @param groupId 群id
     * @param idsStr  id列表，多个用逗号隔开
     */
    public void unpullBiliUid(Long groupId, String idsStr) {
        String[] ids = idsStr.split(",");
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);

        for (String id : ids) {
            if (!NumberUtil.isNumberOnly(id)) continue;
            Long userId = NumberUtil.toLong(id);
            if (!configGroupInfo.getBiliPushIds().contains(userId)) continue;
            configGroupInfo.getBiliPushIds().remove(userId);
        }
        try {
            this.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService unpullBiliUid error,groupId:{},idsStr:{}", groupId, idsStr, ex);
        }
    }

    /**
     * 设置群公告
     *
     * @param groupId        群id
     * @param groupNoticeStr 群公告
     */
    public ReString setGroupNotice(Long groupId, String groupNoticeStr) {
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        if (null == configGroupInfo) {
            configGroupInfo = new ConfigGroupInfo();
        }
        configGroupInfo.setGroupNotice(groupNoticeStr);
        try {
            this.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService setGroupNotice error,groupId:{},groupNoticeStr:{}", groupId, groupNoticeStr, ex);
            return new ReString(false, "群公告设置异常");
        }
        return new ReString(true);
    }

    /**
     * 获取群公告
     *
     * @param groupId 群id
     * @return 群公告
     */
    public String getGroupNotice(Long groupId) {
        ConfigGroupInfo configGroupInfo = this.getConfigByGroupId(groupId);
        if (null == configGroupInfo) {
            return null;
        }
        return configGroupInfo.getGroupNotice();
    }

    private ConfigGroupInfo getConfigByGroupId(Long groupId) {
        ConfigGroupInfo configGroupInfo = configGroupsMap.get(groupId);
        try {
            if (null == configGroupInfo) {
                String configJsonStr = this.loadConfigGroup(groupId);
                configGroupInfo = JSONObject.parseObject(configJsonStr, ConfigGroupInfo.class);
            }
        } catch (Exception ex) {
            logger.error("ConfigService getConfigByGroupId error,groupId:{}", groupId, ex);
        }
        if (null == configGroupInfo) {
            configGroupInfo = new ConfigGroupInfo();
        }
        return configGroupInfo;
    }

    /**
     * 获取今天的全局随机数
     * 该随机数会用于一些神奇的计算
     *
     * @return 本日内不会变动的随机数
     */
    public int getRabbitRandomNum() {
        String rabbitRandomNum = ConstantCommon.common_config.get("rabbitRandomNum");
        if(null == rabbitRandomNum){
            //生成一个新的随机数
            int randomNum = RandomUtil.roll(100);
            ConstantCommon.common_config.put("rabbitRandomNum",String.valueOf(randomNum));
            this.refreshConfigFile();
            return randomNum;
        }
        return NumberUtil.toInt(rabbitRandomNum);
    }
}
