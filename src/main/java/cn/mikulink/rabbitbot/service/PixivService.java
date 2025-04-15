package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.pixiv.*;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.apirequest.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.pixiv.PixivImageUrlInfo;
import cn.mikulink.rabbitbot.entity.apirequest.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.pixiv.PixivUserInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.*;
import com.alibaba.fastjson2.JSONObject;
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

    //pixiv曲奇 不登录的话，功能有所限制
    @Value("${pixiv.cookie:0}")
    private String pixivCookie;

    @Autowired
    private ImageService imageService;
    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private ProxyService proxyService;

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
        Map<String, String> header = new HashMap<>();
        header.put("cookie", pixivCookie);
        request.setHeader(header);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        return request.getPixivImageInfo();
    }

    /**
     * 根据标签，搜索出一张图片
     * 会从所有结果中随机出一张
     * 根据图片分数会有不同的随机权重
     *
     * @param tag 标签 参数在上一层过滤好再进来
     * @return 结果对象
     */
    public PixivImageInfo getPixivIllustByTag(String tag) throws RabbitException, IOException {
        //1.查询这个tag下的总结果
        PixivIllustTagGet request = new PixivIllustTagGet();
        request.setWord(tag);
        request.setP(1);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        //总结果数量
        int total = request.getTotal();
        if (0 >= total) {
            throw new RabbitException(ConstantPixiv.PIXIV_IMAGE_TAG_NO_RESULT);
        }

        //2.随机获取结果中的一条
        //先按照指定页数算出有多少页，随机其中一页 (模拟页面，每页默认60条数据)
        int totalPage = NumberUtil.toIntUp(total / 60 * 1.0);
        //最多只能获取到第1000页
        if (totalPage > 1000) {
            totalPage = 1000;
        }
        //随机一个页数
        int randomPage = RandomUtil.roll(totalPage);
        if (0 >= randomPage) {
            randomPage = 1;
        }


        //获取该页数的数据
        request = new PixivIllustTagGet();
        request.setWord(tag);
        request.setP(randomPage);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        List<PixivImageInfo> responses = request.parseImageList();

        //todo 页面上没有作品评分，如果真做就需要去获取每个pid的评分，这一页就是60个pid，那就是近乎瞬间60次页面请求，也需要保存60个Obj
        //累积得分
//        Integer scoredCount = 0;
//        Map<Long, Integer> scoredMap = new HashMap<>();
//        Map<Object, Double> additionMap = new HashMap<>();
//        Map<Long, ImjadPixivResponse> imgRspMap = new HashMap<>();
//
//        for (PixivImageInfo response : responses) {
//            //r18过滤
//            if (1 == response.getXRestrict()) {
//                String configR18 = ConstantConfig.common_config.get(ConstantConfig.CONFIG_R18);
//                if (StringUtil.isEmpty(configR18) || ConstantCommon.OFF.equalsIgnoreCase(configR18)) {
//                    continue;
//                }
//            }
//
//            Integer scored = response.getStats().getScored_count();
//            scoredCount += scored;
//            scoredMap.put(response.getId(), scored);
//            imgRspMap.put(response.getId(), response);
//        }
//        if (0 >= scoredMap.size()) {
//            return new ReString(false, ConstantPixiv.PIXIV_IMAGE_TAG_ALL_R18);
//        }
//
//        //计算权重
//        for (Long pixivId : scoredMap.keySet()) {
//            Integer score = scoredMap.get(pixivId);
//            //结果肯定介于0-1之间，然后换算成百分比，截取两位小数
//            Double addition = NumberUtil.keepDecimalPoint((score * 1.0) / scoredCount * 100.0, 2);
//            additionMap.put(pixivId, addition);
//        }
//
//        //根据权重随机出一个元素
//        Object obj = RandomUtil.rollObjByAddition(additionMap);

        PixivImageInfo pixivImageInfo = RandomUtil.rollObjFromList(responses);

        //3.获取该图片信息
        Long pixivId = NumberUtil.toLong(pixivImageInfo.getId());
        return getPixivImgInfoById(pixivId);
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
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        List<PixivRankImageInfo> rankImageList = request.getResponseList();

        logger.info("PixivService getPixivIllustRank list:{}", JSONObject.toJSONString(rankImageList));

        //根据pid，去单独爬图片的信息
        for (PixivRankImageInfo rankImageInfo : rankImageList) {
            //根据pid获取图片信息
            PixivImageInfo pixivImageInfo = getPixivImgInfoById(rankImageInfo.getPid());

            //下载图片到本地
            try {
                parseImages(pixivImageInfo);
            } catch (SocketTimeoutException stockTimeoutEx) {
                logger.warn("PixivService getPixivIllustRank {}", ConstantPixiv.PIXIV_IMAGE_TIMEOUT + stockTimeoutEx.toString(), stockTimeoutEx);
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
            ReString reStringSwitch = switchService.switchCheck(imageInfo.getSender(), imageInfo.getSubject(), "pixivR18");
            if (!reStringSwitch.isSuccess()) {
                result = result.plus(ConstantPixiv.PIXIV_IMAGE_R18);
                showImage = false;
            }
        }

        //展示图片
        if (showImage) {
            parseImages(imageInfo);
//            List<Image> miraiImageList = rabbitBotService.uploadMiraiImage(imageInfo.getLocalImgPathList());
//            result = rabbitBotService.parseMsgChainByImgs(miraiImageList);
        }

        StringBuilder resultStr = new StringBuilder();
        if (1 < imageInfo.getPageCount()) {
            resultStr.append("\n该Pid包含").append(imageInfo.getPageCount()).append("张图片");
        }
        if (StringUtil.isNotEmpty(similarity)) {
            resultStr.append("\n[相似度] ").append(similarity).append("%");
        }
        resultStr.append("\n[P站id] ").append(imageInfo.getId());
        resultStr.append("\n[标题] ").append(imageInfo.getTitle());
        resultStr.append("\n[作者] ").append(imageInfo.getUserName());
        resultStr.append("\n[作者id] ").append(imageInfo.getUserId());
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
        MessageChain result = null;
        //日榜正常榜，不用r18过滤
        //展示图片
        if (CollectionUtil.isNotEmpty(imageInfo.getLocalImagesPath())) {
//            List<Image> miraiImageList = rabbitBotService.uploadMiraiImage(imageInfo.getLocalImagesPath());
//            result = rabbitBotService.parseMsgChainByImgs(miraiImageList);
        }else{
            result = MessageUtils.newChain().plus("[未获取到相关图片]");
        }


        StringBuilder resultStr = new StringBuilder();
        if (1 < imageInfo.getPageCount()) {
            resultStr.append("\n该Pid包含").append(imageInfo.getPageCount()).append("张图片");
        }
        resultStr.append("\n[排名] ").append(imageInfo.getRank());
        resultStr.append("\n[昨日排名] ").append(imageInfo.getPreviousRank());
        resultStr.append("\n[P站id] ").append(imageInfo.getPid());
        resultStr.append("\n[标题] ").append(imageInfo.getTitle());
        resultStr.append("\n[作者] ").append(imageInfo.getUserName());
        resultStr.append("\n[作者id] ").append(imageInfo.getUserId());
        resultStr.append("\n[创建时间] ").append(imageInfo.getCreatedTime());
        result = result.plus(resultStr.toString());
        return result;
    }

    /**
     * 搜索p站用户
     * 可模糊搜索
     *
     * @param userNick 用户昵称
     */
    public List<PixivUserInfo> pixivUserSearch(String userNick) throws IOException {
        //请求pixiv用户搜索
        PixivUserSearch request = new PixivUserSearch();
        request.getHeader().put("cookie", pixivCookie);
        request.setPixivUserNick(userNick);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        return request.getResponseList();
    }

    /**
     * 随机返回用户投稿的插画
     *
     * @param pixivUserId pixiv用户id
     * @return 插画列表
     */
    public List<PixivImageInfo> getPixivIllustByUserId(String pixivUserId) throws RabbitApiException, IOException {
        return getPixivIllustByUserId(pixivUserId, null);
    }

    /**
     * 随机返回用户投稿的插画
     *
     * @param pixivUserId pixiv用户id
     * @param count       返回数量，默认为3
     * @return 插画列表
     */
    public List<PixivImageInfo> getPixivIllustByUserId(String pixivUserId, Integer count) throws RabbitApiException, IOException {
        //默认数量为3
        if (null == count) count = 3;
        List<PixivImageInfo> pixivImageInfos = new ArrayList<>();

        //1.获取该用户下所有插画id
        PixivIllustUserGet request = new PixivIllustUserGet();
        request.setUserId(pixivUserId);
        request.getHeader().put("cookie", pixivCookie);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        List<String> allPid = request.getResponseList();

        //2.随机不重复的指定数量的插画，根据pid获取图片详情
        Map<String, String> tempReMap = new HashMap<>();
        int errCount = 0;
        for (int i = 1; i <= count; ) {
            String tempPid = RandomUtil.rollStrFromList(allPid);
            //判重，用户作品数量过少时，防止陷入死循环
            if (allPid.size() > count && tempReMap.containsKey(tempPid)) {
                continue;
            }
            //获取图片信息
            try {
                pixivImageInfos.add(getPixivImgInfoById(NumberUtil.toLong(tempPid)));
            } catch (Exception ex) {
                //异常跳过，进行下一个，异常次数过多跳出方法，防止死循环
                errCount++;
                logger.error("PixivService getPixivIllustByUserId getPixivImgInfoById error({})", count, ex);
                if (errCount < 5) {
                    continue;
                } else {
                    break;
                }
            }
            tempReMap.put(tempPid, tempPid);
            i++;
        }
        return pixivImageInfos;
    }

    //下载图片到本地
    public void parseImages(PixivImageInfo imageInfo) throws IOException {
        if (StringUtil.isEmpty(imageInfo.getUrls().getOriginal())) {
            imageInfo.setLocalImgPathList(null);
            return;
        }
        Long pixivId = NumberUtil.toLong(imageInfo.getId());
        List<String> localImagesPathList = new ArrayList<>();

        if (1 < imageInfo.getPageCount()) {
            //多图
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
        String pixiv_image_ignore = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_IMAGE_IGNORE);
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
                logger.warn(String.format("PixivImjadService downloadPixivImg %s url:%s", ConstantPixiv.PIXIV_IMAGE_DOWNLOAD_FAIL, url));
            }
        } catch (FileNotFoundException fileNotFoundEx) {
            //图片被删了
            logger.warn(String.format("PixivImjadService downloadPixivImg %s pixivId:%s", ConstantPixiv.PIXIV_IMAGE_DELETE, pixivId));
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
        String pixiv_config_images_show_count = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_IMAGES_SHOW_COUNT);
        if (!NumberUtil.isNumberOnly(pixiv_config_images_show_count)) {
            pixiv_config_images_show_count = ConstantPixiv.PIXIV_CONFIG_IMAGES_SHOW_COUNT_DEFAULT;
        }
        Integer showCount = NumberUtil.toInt(pixiv_config_images_show_count);

        //获取多图
        PixivIllustPagesGet request = new PixivIllustPagesGet(pixivId);
        try {
            //如果是登录可见图片，必须附带cookie，不然会抛出404异常
            Map<String, String> header = new HashMap<>();
            header.put("cookie", pixivCookie);
            request.setHeader(header);
            request.setProxy(proxyService.getProxy());
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
        //目前一共遇到的
        //1.似乎是新连接，最近UI改了 https://i.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        //Referer: https://www.pixiv.net/artworks/79561788
        //2.没研究出来的链接，还是403，但是把域名替换成正常链接的域名，可以正常获取到数据 https://i-cf.pximg.net/img-original/img/2020/02/17/22/07/00/79561788_p0.jpg
        HashMap<String, String> header = new HashMap<>();
        if (url.contains("i-cf.pximg.net")) {
            url = url.replace("i-cf.pximg.net", "i.pximg.net");
        }
        //加入p站防爬链
        header.put("referer", "https://www.pixiv.net/artworks/" + pixivId);
        // 创建代理
        Proxy proxy = proxyService.getProxy();
        //下载图片
        return ImageUtil.downloadImage(header, url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "pixiv", null, proxy);
    }
}
