package cn.mikulink.rabbitbot.constant;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by MikuLink on 2020/1/10 15:19
 * for the Reisen
 * 图片相关
 */
public class ConstantImage extends ConstantCommon {
    //默认的文件储存路径
    public static final String DEFAULT_IMAGE_SAVE_PATH = "data/images";
    //处理后的图片存放目录
    public static final String DEFAULT_IMAGE_SCALE_SAVE_PATH = "data/images/scale";
    //微博图片存放目录
    public static final String IMAGE_WEIBO_SAVE_PATH = "data/images/weibo";
    //微信图片存放目录
    public static final String IMAGE_WEIXIN_SAVE_PATH = "data/images/weixin";
    //trace.moe图片存放目录
    public static final String IMAGE_TRACEMOE_SAVE_PATH = "data/images/tracemoe";
    //B站图片存放目录
    public static final String IMAGE_BILIBILI_SAVE_PATH = "data/images/bilibili";

    //经过处理的图片前缀
    public static String IMAGE_SCALE_PREFIX = "rabbit_scale_";
    //需要处理的大小阈值(mb)
    public static double IMAGE_SCALE_MIN_SIZE = 4;
    //需要处理的宽度尺寸阈值(像素)
    public static double IMAGE_SCALE_MIN_HEIGHT = 3500;
    //需要处理的长度尺寸阈值(像素)
    public static double IMAGE_SCALE_MIN_WIDTH = 3500;


    //鸽子图片的map key
    public static final String IMAGE_MAP_KEY_GUGUGU = "鸽了|咕咕|咕了";
    //图片列表，按key分类 list里面暂时存的是图片文件路径，未来计划改为网络图片链接
    public static Map<String, List<String>> map_images = new HashMap<>();
    //图片关键词列表
    public static final List<String> LIST_KEY_IMAGES = Arrays.asList(
            IMAGE_MAP_KEY_GUGUGU
    );


    public static final String NETWORK_IMAGE_URL_IS_EMPRY = "网络图片链接为空";

    //搜图相关
    public static final String IMAGE_SEARCH_NO_IMAGE_INPUT = "你得给我一张图";
    public static final String IMAGE_SEARCH_IMAGE_URL_PARSE_FAIL = "图片解析失败......啊咧？";
    public static final String IMAGE_GET_ERROR = "图片获取异常";
    public static final String IMAGE_GET_TIMEOUT_ERROR = "图片获取超时";

    public static final String SAUCENAO_API_KEY = "saucenaoKey";
    public static final String SAUCENAO_API_KEY_EMPTY = "Saucenao Api Key为空";
    public static final String SAUCENAO_API_TIMEOUT_FAIL = "Saucenao Api请求失败";
    public static final String SAUCENAO_API_SEARCH_FAIL = "Saucenao Api请求失败";
    public static final String SAUCENAO_API_REQUEST_ERROR = "Saucenao Api请求异常";
    public static final String SAUCENAO_SEARCH_FAIL = "Saucenao识图失败，没有任何结果";
    public static final String SAUCENAO_SEARCH_FAIL_PART = "Saucenao识图失败，没有在P站和Danbooru获得结果";
    public static final String SAUCENAO_SEARCH_FAIL_PARAM = "Saucenao识图失败，没有符合条件的结果:[源于P站或Danbooru，相似度50%以上]";

    ///Danbooru
    public static final String DANBOORU_ID_GET_NOT_FOUND = "根据Danbooru图片id没有找到图片";
    public static final String DANBOORU_IMAGE_DOWNLOAD_FAIL = "Danbooru图片下载失败";
    public static final String DANBOORU_ID_GET_FAIL_GROUP_MESSAGE = "根据Danbooru图片id获取信息失败";
    public static final String DANBOORU_ID_GET_ERROR_GROUP_MESSAGE = "根据Danbooru图片id获取信息异常";
}
