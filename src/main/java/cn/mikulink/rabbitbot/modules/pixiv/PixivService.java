package cn.mikulink.rabbitbot.modules.pixiv;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitApiException;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.modules.pixiv.api.*;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivImageUrlInfo;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivRankImageInfo;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivUserInfo;
import cn.mikulink.rabbitbot.service.ImageService;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    //pid下多图时，展示的最多图片数量
    private int imageShowCountMax = 10;

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
    public PixivImageInfo getPixivImgInfoById(Long pid) throws RabbitApiException, IOException {
        if (null == pid) {
            return null;
        }
        //根据pid获取图片列表
        PixivIllustDetailGet request = new PixivIllustDetailGet(pid);
        Map<String, String> header = new HashMap<>();
        header.put("cookie", pixivCookie);
        request.setHeader(header);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        return request.getPixivImageInfo();
    }

    /**
     * 获取pid下所有图片
     */
    public List<PixivImageUrlInfo> getPixivImgUrlListById(Long pid) throws RabbitApiException, IOException {
        PixivIllustPagesGet request = new PixivIllustPagesGet(pid);
        Map<String, String> header = new HashMap<>();
        header.put("cookie", pixivCookie);
        request.setHeader(header);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        return request.getResponseList();
    }

    /**
     * 根据标签，搜索出一张图片
     * 会从所有结果中随机出一张
     * 根据图片分数会有不同的随机权重
     *
     * @param tag 标签 参数在上一层过滤好再进来
     * @return 结果对象
     */
    public PixivImageInfo getPixivIllustByTag(String tag) throws RabbitException, RabbitApiException, IOException {
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
    public List<PixivRankImageInfo> getPixivIllustRank(int pageSize) throws RabbitApiException, IOException {
        //获取排行榜信息
        PixivIllustRankGet request = new PixivIllustRankGet();
        request.setMode("daily");
        request.setContent("illust");
        request.setPageSize(pageSize);
        request.setProxy(proxyService.getProxy());
        request.doRequest();
        List<PixivRankImageInfo> rankImageList = request.getResponseList();

        //根据pid，去单独获取图片的信息
        for (PixivRankImageInfo rankImageInfo : rankImageList) {
            //根据pid获取图片信息
            PixivImageInfo pixivImageInfo = getPixivImgInfoById(rankImageInfo.getPid());

            //图片转化代理
            List<String> imgUrlList = parseImages(pixivImageInfo);

            //信息合并
            //图片
            rankImageInfo.setImagesProxyUrlList(imgUrlList);
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
    public MessageInfo parsePixivImgInfoByApiInfo(PixivImageInfo imageInfo) throws RabbitApiException, IOException {
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
    public MessageInfo parsePixivImgInfoByApiInfo(PixivImageInfo imageInfo, String similarity) throws RabbitApiException, IOException {
        List<MessageChain> messageChainList = new ArrayList<>();

        //图片 使用代理
        List<String> imgUrlList = parseImages(imageInfo);
        for (String imgUrl : imgUrlList) {
            messageChainList.add(RabbitBotMessageBuilder.parseMessageChainImage(imgUrl));
            messageChainList.add(RabbitBotMessageBuilder.parseMessageChainText("\n"));
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
        messageChainList.add(RabbitBotMessageBuilder.parseMessageChainText(resultStr.toString()));
        return new MessageInfo(messageChainList);
    }

    /**
     * 排行榜图片信息拼装为群消息
     *
     * @param imageInfo 排行榜图片信息
     * @return 群消息
     */
    public MessageInfo parsePixivImgInfoByApiInfo(PixivRankImageInfo imageInfo) {

        List<MessageChain> messageChainList = new ArrayList<>();

        //展示图片
        if (CollectionUtil.isNotEmpty(imageInfo.getImagesProxyUrlList())) {
            for (String imgUrl : imageInfo.getImagesProxyUrlList()) {
                messageChainList.add(RabbitBotMessageBuilder.parseMessageChainImage(imgUrl));
                messageChainList.add(RabbitBotMessageBuilder.parseMessageChainText("\n"));
            }
        } else {
            messageChainList.add(RabbitBotMessageBuilder.parseMessageChainText("[未获取到相关图片]"));
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
        messageChainList.add(RabbitBotMessageBuilder.parseMessageChainText(resultStr.toString()));

        return new MessageInfo(messageChainList);
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

    public List<String> parseImages(PixivImageInfo imageInfo) throws RabbitApiException, IOException {
        //https://pixiv.cat/
        //https://i.pximg.net/img-original/img/2025/03/24/21/03/46/128565958_p0.jpg
        //https://i.pixiv.re/img-original/img/2025/03/24/21/03/46/128565958_p0.jpg
        if (1 >= imageInfo.getPageCount()) {
            if (StringUtil.isEmpty(imageInfo.getUrls().getOriginal())) {
                return null;
            }
            String preUrl = imageInfo.getUrls().getOriginal().replace("i.pximg.net", "i.pixiv.re");
            return List.of(preUrl);
        }

        //多图
        List<String> resultList = new ArrayList<>();
        Long pixivId = NumberUtil.toLong(imageInfo.getId());

        List<PixivImageUrlInfo> urlList = getPixivImgUrlListById(pixivId);
        int count = 0;
        for (PixivImageUrlInfo pixivImageUrlInfo : urlList) {
            String tempUrl = pixivImageUrlInfo.getOriginal().replace("i.pximg.net", "i.pixiv.re");
            resultList.add(tempUrl);
            //图片展示数量上限
            count++;
            if (count >= imageShowCountMax) {
                break;
            }
        }
        return resultList;
    }
}
