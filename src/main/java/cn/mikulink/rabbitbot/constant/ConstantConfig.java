package cn.mikulink.rabbitbot.constant;

/**
 * create by MikuLink on 2019/12/3 19:51
 * for the Reisen
 * <p>
 * 高德地图相关
 */
public class ConstantConfig extends ConstantCommon {
    public static final String ARGS_ERROR = ".config [action] [configName] [configValue]" +
            "\nsinceId,amapKey,saucenaoKey,weiboToken,weiboNewStatus,pixivImageIgnore,usePixivApi,r18,imageScaleForce,pixivImagesShowCount,switchForce,weixinAppmsgToken,weixinAppmsgCookie";
    public static final String CONFIG_NAME_EMPTY = "参数名称不能为空";
    public static final String CONFIG_VALUE_EMPTY = "参数值不能为空";
    public static final String CONFIG_SET_SUCCESS = "参数设置完成";
    public static final String CONFIG_NOT_FOUND = "没有该参数信息";

    public static final String CONFIG_KEY_GROUP_WEIBO_PUSH_IDS = "weiboPushIds";

    //最高权限接管开关配置
    public static final String CONFIG_SWITCH_FORCE = "switchForce";
    //强制压图
    public static final String CONFIG_IMAGE_SCALE_FORCE = "imageScaleForce";
}
