package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.entity.ConfigGroupInfo;
import cn.mikulink.rabbitbot.filemanage.FileManagerConfig;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
            FileManagerConfig.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
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
            FileManagerConfig.writerConfigGroup(groupId, JSONObject.toJSONString(configGroupInfo));
        } catch (Exception ex) {
            logger.error("ConfigService unpullWeibo error,groupId:{},idsStr:{}", groupId, idsStr, ex);
        }
    }

    private ConfigGroupInfo getConfigByGroupId(Long groupId) {
        ConfigGroupInfo configGroupInfo = configGroupsMap.get(groupId);
        try {
            if (null == configGroupInfo) {
                String configJsonStr = FileManagerConfig.loadConfigGroup(groupId);
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
}
