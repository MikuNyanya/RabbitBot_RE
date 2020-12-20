package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustGet;
import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustPagesGet;
import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustRankGet;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantConfig;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageUrlInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.utils.*;
import com.alibaba.fastjson.JSONObject;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MikuLink
 * @date 2020/02/20 18:33
 * for the Reisen
 * p站相关服务 依赖爬虫
 */
@Service
public class PixivService {
    private static final Logger logger = LoggerFactory.getLogger(PixivService.class);

    @Value("${pixiv.account}")
    private String pixivAccount;
    @Value("${pixiv.pwd}")
    private String pixivPwd;


    @Autowired
    private ImageService imageService;
    @Autowired
    private RabbitBotService rabbitBotService;


    /**
     * * 查询p站图片id并返回结果
     *
     * @param pid p站图片id
     * @return 拼装好的结果信息
     * @throws IOException 异常继续上抛，调用端处理
     */
    public PixivImageInfo getPixivImgInfoById(Long pid) throws IOException {
        if (null == pid) {
            return null;
        }
        //根据pid获取图片列表
        PixivIllustGet request = new PixivIllustGet(pid);
        request.doRequest();
        return request.getPixivImageInfo();
    }

    /**
     * 获取当前P站日榜数据
     *
     * @param pageSize 获取个数，传个5个10个的差不多了，一次大概最多50个
     * @return 日榜图片信息
     */
    public List<PixivRankImageInfo> getPixivIllustRank(int pageSize) throws IOException {
        //获取排行榜信息
        PixivIllustRankGet request = new PixivIllustRankGet();
        request.setMode("daily");
        request.setContent("illust");
        request.setPageSize(pageSize);
        request.doRequest();
        List<PixivRankImageInfo> rankImageList = request.getResponseList();

        logger.info("PixivService getPixivIllustRank list:{}", JSONObject.toJSONString(rankImageList));

        //根据pid，去单独爬图片的信息
        for (PixivRankImageInfo rankImageInfo : rankImageList) {
            //根据pid获取图片信息
            PixivImageInfo pixivImageInfo = getPixivImgInfoById(rankImageInfo.getPid());

            //下载图片到本地 todo 改为下载一个pid发送一份结果，不然间隔太长了
            try {
                parseImages(pixivImageInfo);
            } catch (SocketTimeoutException stockTimeoutEx) {
                logger.warn("PixivService getPixivIllustRank {}", ConstantImage.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString(),stockTimeoutEx);
            }

            //信息合并
            //图片
            rankImageInfo.setLocalImagesPath(pixivImageInfo.getLocalImgPathList());
            //标题
            rankImageInfo.setTitle(pixivImageInfo.getTitle());
            //简介
            rankImageInfo.setCaption(pixivImageInfo.getDescription());
            //创建时间
            rankImageInfo.setCreatedTime(pixivImageInfo.getCreateDate());
            //总P数
            rankImageInfo.setPageCount(pixivImageInfo.getPageCount());
            //作者id
            rankImageInfo.setUserId(pixivImageInfo.getUserId());
            //作者名称
            rankImageInfo.setUserName(pixivImageInfo.getUserName());
        }

        return rankImageList;
    }

    /**
     * 接口返回的图片信息拼装为群消息
     *
     * @param imageInfo 接口返回对象
     * @return 群消息
     * @throws IOException api异常
     */
    public MessageChain parsePixivImgInfoByApiInfo(PixivImageInfo imageInfo) throws IOException {
        return parsePixivImgInfoByApiInfo(imageInfo, null);
    }

    /**
     * 接口返回的图片信息拼装为群消息
     * 重载 为识图结果提供相似度参数
     *
     * @param imageInfo  接口返回对象
     * @param similarity 相似度
     * @return 群消息
     * @throws IOException api异常
     */
    public MessageChain parsePixivImgInfoByApiInfo(PixivImageInfo imageInfo, String similarity) throws IOException {
        MessageChain result = MessageUtils.newChain();

        //r18过滤
        boolean showImage = true;
        Integer xRestrict = imageInfo.getXRestrict();
        if (null != xRestrict && 1 == xRestrict) {
            String configR18 = ConstantConfig.common_config.get(ConstantConfig.CONFIG_R18);
            if (StringUtil.isEmpty(configR18) || ConstantCommon.OFF.equalsIgnoreCase(configR18)) {
                result = result.plus(ConstantImage.PIXIV_IMAGE_R18);
                showImage = false;
            }
        }

        //展示图片
        if (showImage) {
            parseImages(imageInfo);
            List<Image> miraiImageList = rabbitBotService.uploadMiraiImage(imageInfo.getLocalImgPathList());
            result = rabbitBotService.parseMsgChainByImgs(miraiImageList);
        }

        StringBuilder resultStr = new StringBuilder();
        if (1 < imageInfo.getPageCount()) {
            resultStr.append("\n该Pid包含多张图片");
        }
        if (StringUtil.isNotEmpty(similarity)) {
            resultStr.append("\n[相似度] ").append(similarity).append("%");
        }
        resultStr.append("\n[P站id] ").append(imageInfo.getId());
        resultStr.append("\n[标题] ").append(imageInfo.getTitle());
        resultStr.append("\n[作者] ").append(imageInfo.getUserName());
        resultStr.append("\n[上传时间] ").append(imageInfo.getCreateDate());
        result = result.plus(resultStr.toString());
        return result;
    }

    /**
     * 排行榜图片信息拼装为群消息
     *
     * @param imageInfo 排行榜图片信息
     * @return 群消息
     */
    public MessageChain parsePixivImgInfoByApiInfo(PixivRankImageInfo imageInfo) {
        MessageChain result = MessageUtils.newChain();

        //r18过滤
        boolean showImage = true;
        Integer xRestrict = imageInfo.getXRestrict();
        if (null != xRestrict && 1 == xRestrict) {
            String configR18 = ConstantConfig.common_config.get(ConstantConfig.CONFIG_R18);
            if (StringUtil.isEmpty(configR18) || ConstantCommon.OFF.equalsIgnoreCase(configR18)) {
                result = result.plus(ConstantImage.PIXIV_IMAGE_R18);
                showImage = false;
            }
        }

        //展示图片
        if (showImage) {
            List<Image> miraiImageList = rabbitBotService.uploadMiraiImage(imageInfo.getLocalImagesPath());
            result = rabbitBotService.parseMsgChainByImgs(miraiImageList);
        }

        StringBuilder resultStr = new StringBuilder();
        if (1 < imageInfo.getPageCount()) {
            resultStr.append("\n该Pid包含多张图片");
        }
        resultStr.append("\n[排名] ").append(imageInfo.getRank());
        resultStr.append("\n[昨日排名] ").append(imageInfo.getPreviousRank());
        resultStr.append("\n[P站id] ").append(imageInfo.getPid());
        resultStr.append("\n[标题] ").append(imageInfo.getTitle());
        resultStr.append("\n[作者] ").append(imageInfo.getUserName());
        resultStr.append("\n[创建时间] ").append(imageInfo.getCreatedTime());
        result = result.plus(resultStr.toString());
        return result;
    }


    //下载图片到本地
    public void parseImages(PixivImageInfo imageInfo) throws IOException {
        Long pixivId = NumberUtil.toLong(imageInfo.getId());
        List<String> localImagesPathList = new ArrayList<>();

        if (1 < imageInfo.getPageCount()) {
            //多图 如果因为没登录而获取不到，则只取封面
            try {
                localImagesPathList.addAll(downloadPixivImgs(pixivId));
            } catch (FileNotFoundException fileNotFoundEx) {
                logger.warn("pixiv多图获取失败，可能登录过期,imageInfo:{}", JSONObject.toJSONString(imageInfo), fileNotFoundEx);
                //限制级会要求必须登录，如果不登录会抛出异常
                localImagesPathList.add(downloadPixivImg(imageInfo.getUrls().getOriginal(), pixivId));
            }
        } else {
            //单图
            localImagesPathList.add(downloadPixivImg(imageInfo.getUrls().getOriginal(), pixivId));
        }
        imageInfo.setLocalImgPathList(localImagesPathList);
    }

    /**
     * 下载P站图片
     *
     * @param url p站图片链接
     * @return 压缩后的本地图片地址
     */
    public String downloadPixivImg(String url, Long pixivId) throws IOException {
        //先检测是否已下载，如果已下载直接送去压图
        String pixivImgFileName = url.substring(url.lastIndexOf("/") + 1);
        String localPixivFilePath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv" + File.separator + pixivImgFileName;
        if (FileUtil.exists(localPixivFilePath)) {
            return imageService.scaleForceByLocalImagePath(localPixivFilePath);
        }

        //是否不加载p站图片，由于从p站本体拉数据，还没代理，很慢
        String pixiv_image_ignore = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_IMAGE_IGNORE);
        if ("1".equalsIgnoreCase(pixiv_image_ignore)) {
            return null;
        }

        String scaleForceLocalUrl = null;
        try {
            String localUrl = downloadPixivImgByPixivImgUrl(url, pixivId);

            if (StringUtil.isNotEmpty(localUrl)) {
                scaleForceLocalUrl = imageService.scaleForceByLocalImagePath(localUrl);
            }
            if (StringUtil.isEmpty(scaleForceLocalUrl)) {
                //图片下载或压缩失败
                logger.warn(String.format("PixivImjadService downloadPixivImg %s url:%s", ConstantImage.PIXIV_IMAGE_DOWNLOAD_FAIL, url));
            }
        } catch (FileNotFoundException fileNotFoundEx) {
            //图片被删了
            logger.warn(String.format("PixivImjadService downloadPixivImg %s pixivId:%s", ConstantImage.PIXIV_IMAGE_DELETE, pixivId));
        }
        return scaleForceLocalUrl;
    }

    /**
     * 下载并压缩P站图片(多图)
     *
     * @return 处理后的本地图片地址列表
     */
    public List<String> downloadPixivImgs(Long pixivId) throws IOException {
        List<String> localImagesPath = new ArrayList<>();

        //查看多图展示数量配置，默认为3
        String pixiv_config_images_show_count = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_IMAGES_SHOW_COUNT);
        if (!NumberUtil.isNumberOnly(pixiv_config_images_show_count)) {
            pixiv_config_images_show_count = ConstantImage.PIXIV_CONFIG_IMAGES_SHOW_COUNT_DEFAULT;
        }
        Integer showCount = NumberUtil.toInt(pixiv_config_images_show_count);

        //获取多图
        PixivIllustPagesGet request = new PixivIllustPagesGet(pixivId);
        try {
            //如果是登录可见图片，必须附带cookie，不然会抛出404异常
            Map<String, String> header = new HashMap<>();
            //todo 研究pixiv登录，并刷新cookie
            header.put("cookie", "__cfduid=d0ff4b4cc2b7f1867df7f963b0b369f371606650332; first_visit_datetime_pc=2020-11-29+20%3A45%3A32; yuid_b=JiZFMGQ; p_ab_id=8; p_ab_id_2=8; p_ab_d_id=2116786429; __utmz=235335808.1606650336.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _fbp=fb.1.1606650344862.1495176799; _ga=GA1.2.1499363042.1606650336; PHPSESSID=3151313_l23wvfqAygH2aAZip9krEfxARA5cmaA2; device_token=d706869bc7f0145f46f23e5d5bc9f30d; c_type=25; privacy_policy_agreement=2; a_type=0; b_type=1; adr_id=mEDklBY4J9f9IjkJdru78T81ooMGKVPY0NncuJFRogE2kRba; ki_r=; ki_s=212334%3A0.0.0.0.2; login_ever=yes; __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=3151313=1^9=p_ab_id=8=1^10=p_ab_id_2=8=1^11=lang=zh=1; ki_t=1607946070172%3B1607946070172%3B1607952493085%3B1%3B3; __utmc=235335808; categorized_tags=kP7msdIeEU~m3EJRa33xU; __cf_bm=b907d56848f61b19aa2d9971a9f0e48446e7ee5b-1608236179-1800-AR8iS6al4gcPt7JMH3pXSE9TwL92qdx/y5UkQBc62U4TXmE1HwpLQFLsZy+uuORzvezpYDntPrh9xeehu284/bEwg4PWTHhKMgTHTkXPjowT6oP128v4HVJq1mCKz5BQaTW6Of5ZMX8Yvr3PjcGXQ/Dt2g6OM5piYuCgLOXYIOAd; __utma=235335808.1499363042.1606650336.1608211250.1608236186.6; tag_view_ranking=zIv0cf5VVk~0xsDLqCEW6~_7r4EMWAAx~xgA3yCXKWS~m3EJRa33xU~n4OVgsIt_C~Kzwb_D669F~kP7msdIeEU~OT4SuGenFI~gpglyfLkWs~WjRN9ve4kb~Aa5GphyXq3~HlDdLQY3rl~2tBmt-ssFk~WzEZN2jz2H~_hSAdpN9rx~Q93StGtvUH~Ce-EdaHA-3~Bd2L9ZBE8q~RTJMXD26Ak~zyKU3Q5L4C~NE-E8kJhx6~3cT9FM3R6t~KN7uxuR89w; __utmt=1; __utmb=235335808.1.10.1608236186; tags_sended=1");
            request.setHeader(header);
            request.doRequest();
        } catch (RabbitApiException rabApiEx) {
            logger.warn("Pixiv downloadPixivImgs apierr msg:{}", rabApiEx.getMessage(), rabApiEx);
            return new ArrayList<>();
        }
        List<PixivImageUrlInfo> urlInfoList = request.getResponseList();

        int i = 0;

        for (PixivImageUrlInfo urlInfo : urlInfoList) {
            //下载并压缩图片
            String scaleForceLocalUrl = downloadPixivImg(urlInfo.getOriginal(), pixivId);
            localImagesPath.add(scaleForceLocalUrl);
            //达到指定数量，结束追加图片
            i++;
            if (i >= showCount) {
                break;
            }
        }
        return localImagesPath;
    }

    /**
     * 根据p站图片链接下载图片
     * 带图片后缀的那种，比如
     * https://i.pximg.net/img-original/img/2018/03/31/01/10/08/67994735_p0.png
     *
     * @param url     p站图片链接
     * @param pixivId p站图片id，用于防爬链，必须跟url中的id一致
     * @return 下载后的本地连接
     */
    public String downloadPixivImgByPixivImgUrl(String url, Long pixivId) throws IOException {
        logger.info("Pixiv image download:" + url);
        //加入p站防爬链
        //目前一共遇到的
        //1.似乎是新连接，最近UI改了 https://i.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        //Referer: https://www.pixiv.net/artworks/79561788
        //2.没研究出来的链接，还是403，但是把域名替换成正常链接的域名，可以正常获取到数据 https://i-cf.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        HashMap<String, String> header = new HashMap<>();
        if (url.contains("i-cf.pximg.net")) {
            url = url.replace("i-cf.pximg.net", "i.pximg.net");
        }
        header.put("referer", "https://www.pixiv.net/artworks/" + pixivId);
        // 创建代理
        Proxy proxy = HttpUtil.getProxy();
        //下载图片
        return ImageUtil.downloadImage(header, url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv", null, proxy);
    }
}
