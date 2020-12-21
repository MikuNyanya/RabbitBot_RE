package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.imjad.PixivImjadIllustGet;
import cn.mikulink.rabbitbot.apirequest.imjad.PixivImjadIllustRankGet;
import cn.mikulink.rabbitbot.apirequest.imjad.PixivImjadIllustSearch;
import cn.mikulink.rabbitbot.apirequest.imjad.PixivImjadMemberIllustGet;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantConfig;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.apirequest.imjad.*;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.rabbitbot.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.filemanage.FileManagerPixivMember;
import cn.mikulink.rabbitbot.filemanage.FileManagerPixivTags;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author MikuLink
 * @date 2020/02/20 18:33
 * for the Reisen
 * p站相关服务 依赖第三方接口
 */
@Service
public class PixivImjadService {
    private static final Logger logger = LoggerFactory.getLogger(PixivImjadService.class);

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private PixivService pixivService;

    /**
     * 获取当前P站日榜数据
     *
     * @param page     页数
     * @param pageSize 每页大小
     * @return 日榜图片信息
     */
    public List<PixivRankImageInfo> getPixivIllustRank(Integer page, Integer pageSize) throws IOException {
        List<PixivRankImageInfo> rankImageList = new ArrayList<>();

        //请求接口获取日榜信息
        PixivImjadIllustRankGet request = new PixivImjadIllustRankGet();
        //日榜
        request.setMode("daily");
        //插画
        request.setContent("illust");
        request.setPage(page);
        request.setPageSize(pageSize);
        //时间 12点前获取不到昨日排行，所以这种时候获取前天的
        int beforeDay = -1;
        if (DateUtil.getHour() < 12) {
            beforeDay = -2;
        }
        Date paramDate = DateUtil.dateChange(new Date(), Calendar.DAY_OF_MONTH, beforeDay);
        request.setDate(DateUtil.toString(paramDate, "yyyy-MM-dd"));

        request.doRequest();

        //解析返回报文
        ImjadPixivRankResult result = request.getEntity();
        List<ImjadPixivRankWork> imageList = result.getResponse().get(0).getWorks();
        for (ImjadPixivRankWork image : imageList) {
            ImjadPixivRankWorkWork imageDetail = image.getWork();
            //下载图片
            List<String> localImagesPath = new ArrayList<>();
            if (1 < imageDetail.getPage_count()) {
                localImagesPath.addAll(pixivService.downloadPixivImgs(Long.valueOf(imageDetail.getId())));
            } else {
                localImagesPath.add(pixivService.downloadPixivImg(imageDetail.getImage_urls().getLarge(), Long.valueOf(imageDetail.getId())));
            }

            //拼接成对象并返回
            PixivRankImageInfo rankImageInfo = new PixivRankImageInfo();
            //本地图片路径
            rankImageInfo.setLocalImagesPath(localImagesPath);
            //排行
            rankImageInfo.setRank(image.getRank());
            //过去排行
            rankImageInfo.setPreviousRank(String.valueOf(image.getPrevious_rank()));
            //pid
            rankImageInfo.setPid((long) imageDetail.getId());
            //标题
            rankImageInfo.setTitle(imageDetail.getTitle());
            //简介
            rankImageInfo.setCaption(imageDetail.getCaption());
            //创建时间
            rankImageInfo.setCreatedTime(imageDetail.getCreated_time());
            //总P数
            rankImageInfo.setPageCount(imageDetail.getPage_count());
            //作者id
            rankImageInfo.setUserId((long) imageDetail.getUser().getId());
            //作者名称
            rankImageInfo.setUserName(imageDetail.getUser().getName());

            rankImageList.add(rankImageInfo);

            //保存一份tag
            saveTags(image.getWork().getTags());
            //保存一份作者信息
            saveMemberInfo(imageDetail.getUser());
        }

        return rankImageList;
    }

    /**
     * 根据标签，搜索出一张图片
     * 会从所有结果中随机出一张
     * 根据图片分数会有不同的随机权重
     *
     * @param tag 标签 参数在上一层过滤好再进来
     * @return 结果对象
     */
    public ReString getPixivIllustByTag(String tag) throws IOException {
        if (StringUtil.isEmpty(tag)) {
            return new ReString(false, ConstantPixiv.PIXIV_IMAGE_TAG_IS_EMPTY);
        }

        int pageSize = 30;

        //1.查询这个tag下的总结果
        PixivImjadIllustSearch request = new PixivImjadIllustSearch("tag", tag, 1, 1);
        request.doRequest();
        ImjadPixivResult result = request.getEntity();
        ImjadPixivPagination pagination = result.getPagination();
        //总结果数量
        int total = pagination.getTotal();
        if (0 >= total) {
            return new ReString(false, ConstantPixiv.PIXIV_IMAGE_TAG_NO_RESULT);
        }

        //2.随机获取结果中的一条
        //先按照指定页数算出有多少页，随机其中一页
        int totalPage = NumberUtil.toIntUp(total / pageSize * 1.0);
        //接口限制最多只能获取第1000页，但有时候传递900多也会提示超出1000页，所以限制在800，接口有问题，还是600吧，再多就提示超出1000，迷
        if (totalPage > 600) {
            totalPage = 600;
        }
        //随机一个页数
        int randomPage = RandomUtil.roll(totalPage);
        if (0 >= randomPage) {
            randomPage = 1;
        }

        //获取该页数的数据
        request = new PixivImjadIllustSearch("tag", tag, randomPage, pageSize);
        request.doRequest();
        //这里面至少会有1条有效数据
        result = request.getEntity();
        if (null != result.getHas_error() && result.getHas_error()) {
            return new ReString(false, result.getErrors().getSystem().getMessage());
        }

        //累积得分
        Integer scoredCount = 0;
        Map<Long, Integer> scoredMap = new HashMap<>();
        Map<Object, Double> additionMap = new HashMap<>();
        Map<Long, ImjadPixivResponse> imgRspMap = new HashMap<>();

        List<ImjadPixivResponse> responses = result.getResponse();
        for (ImjadPixivResponse response : responses) {
            //r18过滤
            if ("r18".equalsIgnoreCase(response.getAge_limit())) {
                String configR18 = ConstantConfig.common_config.get(ConstantConfig.CONFIG_R18);
                if (StringUtil.isEmpty(configR18) || ConstantCommon.OFF.equalsIgnoreCase(configR18)) {
                    continue;
                }
            }

            Integer scored = response.getStats().getScored_count();
            scoredCount += scored;
            scoredMap.put(response.getId(), scored);
            imgRspMap.put(response.getId(), response);
        }
        if (0 >= scoredMap.size()) {
            return new ReString(false, ConstantPixiv.PIXIV_IMAGE_TAG_ALL_R18);
        }

        //计算权重
        for (Long pixivId : scoredMap.keySet()) {
            Integer score = scoredMap.get(pixivId);
            //结果肯定介于0-1之间，然后换算成百分比，截取两位小数
            Double addition = NumberUtil.keepDecimalPoint((score * 1.0) / scoredCount * 100.0, 2);
            additionMap.put(pixivId, addition);
        }

        //根据权重随机出一个元素
        Object obj = RandomUtil.rollObjByAddition(additionMap);

        //3.获取该图片信息
        Long pixivId = (Long) obj;
        ImjadPixivResponse respInfo = imgRspMap.get(pixivId);

        return new ReString(true, null, respInfo);
    }

    /**
     * 根据作者名称获取随机三个作品
     * 作者名称进行精准查询
     * 纯随机
     *
     * @param memberName 作者名称， 准确
     * @return 随机出来的作品信息
     */
    public List<ImjadPixivResponse> getPixivIllustByMember(String memberName) throws RabbitException, IOException {
        if (StringUtil.isEmpty(memberName)) {
            return null;
        }

        Long memberId = null;
        //查看当前本地作者缓存
        for (String memberLocalStr : ConstantPixiv.PIXIV_MEMBER_LIST) {
            String[] memberLocalInfos = memberLocalStr.split(",");
            //忽略异常数据
            if (memberLocalInfos.length < 2) {
                continue;
            }
            String memberLocalName = memberLocalInfos[1];
            if (memberName.equals(memberLocalName)) {
                memberId = NumberUtil.toLong(memberLocalInfos[0]);
                break;
            }
        }

        //todo 缓存查不到去页面请求查作者

        //都查不到返回结果
        if (null == memberId) {
            throw new RabbitException(ConstantPixiv.PIXIV_MEMBER_NOT_FOUND + "[" + memberName + "]");
        }

        int pageSize = 10;
        //1.根据作者查询作品总数
        PixivImjadMemberIllustGet request = new PixivImjadMemberIllustGet();
        request.setPage(1);
        request.setPageSize(10);
        request.setMemberId(memberId);
        request.doRequest();

        ImjadPixivResult result = request.getEntity();
        ImjadPixivPagination pagination = result.getPagination();
        //总结果数量
        int total = pagination.getTotal();
        if (0 >= total) {
            throw new RabbitException("[" + memberName + "]" + ConstantPixiv.PIXIV_MEMBER_NO_ILLUST);
        }

        //2.随机获取作者作品中的三个
        List<ImjadPixivResponse> randIllustList = null;
        if (total <= pageSize) {
            //如果作品少于1页，直接使用上面的结果随机即可
            randIllustList = getMemberIllustListLessThanPageSize(result);
        } else {
            //如果多于1页，进行跳页随机
            randIllustList = getMemberIllustListMoreThanPageSize(memberId, pagination.getPages(), pageSize);
        }

        return randIllustList;
    }

    /**
     * 拼装识图结果_p站
     * 搜索结果只会取一个
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public MessageChain parsePixivImgRequest(SaucenaoSearchInfoResult infoResult) throws RabbitException, IOException {
        //根据pid获取信息
        ImjadPixivResponse response = getImgsByPixivId((long) infoResult.getData().getPixiv_id());

        //Saucenao搜索结果相似度
        String similarity = infoResult.getHeader().getSimilarity();
        //拼装结果
        return parsePixivImgInfoByApiInfo(response, similarity);
    }

    /**
     * 查询p站图片id并返回结果
     *
     * @param pid p站图片id
     * @return 拼装好的结果信息
     */
    public MessageChain searchPixivImgById(Long pid) throws IOException {
        //根据pid获取图片列表
        try {
            ImjadPixivResponse pixivResponse = getImgsByPixivId(pid);
            return parsePixivImgInfoByApiInfo(pixivResponse, null);
        } catch (RabbitException rex) {
            return MessageUtils.newChain(new PlainText(rex.getMessage()));
        }
    }

    /**
     * 根据pid获取p站图片地址
     * 因为有多P，所以返回的是集合
     *
     * @param pixivId p站图片id
     * @return 接口返回结果
     */
    private ImjadPixivResponse getImgsByPixivId(Long pixivId) throws RabbitException, IOException {
        PixivImjadIllustGet request = new PixivImjadIllustGet();
        request.setPixivId(pixivId);
        request.doRequest();
        ImjadPixivResult pixivResult = request.getEntity();
        if (null == pixivResult) {
            throw new RabbitException(ConstantPixiv.PIXIV_ID_GET_FAIL_GROUP_MESSAGE + "(" + pixivId + ")");
        }
        if (null != pixivResult.getHas_error() && pixivResult.getHas_error()) {
            //接口错误信息
            throw new RabbitException(pixivResult.getErrors().getSystem().getMessage() + "(" + pixivId + ")");
        }

        List<ImjadPixivResponse> responses = pixivResult.getResponse();
        return responses.get(0);
    }


    /**
     * 接口返回的图片信息拼装为群消息
     *
     * @param response   接口返回对象
     * @param similarity 相似度
     * @return 群消息
     * @throws IOException api异常
     */
    public MessageChain parsePixivImgInfoByApiInfo(ImjadPixivResponse response, String similarity) throws IOException {
        MessageChain result = MessageUtils.newChain();

        //r18过滤
        boolean showImage = true;
        if ("r18".equalsIgnoreCase(response.getAge_limit())) {
            String configR18 = ConstantConfig.common_config.get(ConstantConfig.CONFIG_R18);
            if (StringUtil.isEmpty(configR18) || ConstantCommon.OFF.equalsIgnoreCase(configR18)) {
                result = result.plus(ConstantPixiv.PIXIV_IMAGE_R18);
                showImage = false;
            }
        }

        //展示图片
        if (showImage) {
            List<String> localImagesPath = new ArrayList<>();
            if (1 < response.getPage_count()) {
                //多图
                localImagesPath.addAll(pixivService.downloadPixivImgs(response.getId()));
            } else {
                //单图
                localImagesPath.add(pixivService.downloadPixivImg(response.getImage_urls().getLarge(), response.getId()));
            }
            List<Image> miraiImgList = rabbitBotService.uploadMiraiImage(localImagesPath);
            for (Image image : miraiImgList) {
                result.plus("");
                result.plus(image);
            }
        }

        //图片标题
        String title = response.getTitle();
        //图片简介
        String caption = response.getCaption();
        //作者名称
        String memberName = response.getUser().getName();
        //图片创建时间
        String createDate = response.getCreated_time();

        StringBuilder resultStr = new StringBuilder();
        if (1 < response.getPage_count()) {
            resultStr.append("\n该Pid共包含").append(response.getPage_count()).append("张图片");
        }
        if (StringUtil.isNotEmpty(similarity)) {
            resultStr.append("\n[相似度] ").append(similarity).append("%");
        }
        resultStr.append("\n[P站id] ").append(response.getId());
        resultStr.append("\n[标题] ").append(title);
        resultStr.append("\n[作者] ").append(memberName);
        resultStr.append("\n[上传时间] ").append(createDate);
//            resultStr.append("\n[图片简介] " + caption);

        //保存一份tag
        saveTags(response.getTags());
        //保存一份作者信息
        saveMemberInfo(response.getUser());
        result = result.plus(resultStr.toString());
        return result;
    }


    //从一页里随机出指定数量的结果
    private List<ImjadPixivResponse> getMemberIllustListLessThanPageSize(ImjadPixivResult result) {
        int total = result.getPagination().getTotal();
        List<ImjadPixivResponse> tempList = result.getResponse();
        //如果小于等于指定阈值，直接展示所有
        if (total <= ConstantPixiv.PIXIV_MEMBER_ILLUST_SHOW_COUNT) {
            return tempList;
        }

        //随机出指定数目以内不重复的数字，结果为下标
        List<Integer> randNumList = RandomUtil.roll(tempList.size() - 1, ConstantPixiv.PIXIV_MEMBER_ILLUST_SHOW_COUNT);
        if (null == randNumList) {
            return tempList;
        }

        List<ImjadPixivResponse> rspList = new ArrayList<>();
        for (Integer randNum : randNumList) {
            rspList.add(tempList.get(randNum));
        }
        return rspList;
    }

    //用户作品跳页随机
    private List<ImjadPixivResponse> getMemberIllustListMoreThanPageSize(Long memberId, Integer maxPage, int pageSize) throws IOException {
        List<ImjadPixivResponse> reslutList = new ArrayList<>();

        //防重复 k=page v=index
//        Map<Integer,Integer> reMap = new HashMap<>();

        int i = 1;
        do {
            //随机页数，随机作品下标，可能会有重复
            int page = RandomUtil.roll(maxPage);
            if (page == 0) {
                page = 1;
            }

            PixivImjadMemberIllustGet request = new PixivImjadMemberIllustGet();
            request.setPage(page);
            request.setPageSize(pageSize);
            request.setMemberId(memberId);
            request.doRequest();

            ImjadPixivResult result = request.getEntity();
            List<ImjadPixivResponse> responseList = result.getResponse();
            if (null != responseList && responseList.size() > 0) {
                int index = RandomUtil.roll(responseList.size() - 1);
                reslutList.add(responseList.get(index));
            }
            i++;
        } while (i <= ConstantPixiv.PIXIV_MEMBER_ILLUST_SHOW_COUNT);
        return reslutList;
    }

    //保存tag信息
    public void saveTags(List<String> tags) {
        if (null == tags) {
            return;
        }
        try {
            List<String> tagsTamp = new ArrayList<>();
            //移除已存在的tag，不需要重复保存
            for (String tag : tags) {
                if (StringUtil.isEmpty(tag)) {
                    continue;
                }
                if (ConstantPixiv.PIXIV_TAG_LIST.contains(tag)) {
                    continue;
                }

                tagsTamp.add(tag);
            }
            //保存文件，并刷新内存
            FileManagerPixivTags.addTags(tagsTamp);
        } catch (Exception ex) {
            //异常不上抛，不是主要业务
            logger.error(ConstantPixiv.PIXIV_TAG_SAVE_ERROR + " " + ex.toString(), ex);
        }
    }

    //保存一份作者信息
    public void saveMemberInfo(ImjadPixivUser user) {
        if (null == user) {
            return;
        }
        try {
            //拼接为字符串
            String memberStr = String.format("%s,%s,%s", user.getId(), user.getName(), user.getAccount());

            //判重
            if (ConstantPixiv.PIXIV_MEMBER_LIST.contains(memberStr)) {
                return;
            }

            //保存
            FileManagerPixivMember.addMemberInfo(memberStr);
        } catch (Exception ex) {
            //不影响主业务
            logger.error(ConstantPixiv.PIXIV_MEMBER_SAVE_ERROR + ex.toString(), ex);
        }
    }
}
