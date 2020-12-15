package cn.mikulink.service;

import cn.mikulink.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.entity.pixiv.PixivImageInfo;
import cn.mikulink.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.utils.HttpUtil;
import cn.mikulink.utils.NumberUtil;
import cn.mikulink.utils.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author MikuLink
 * @date 2020/02/20 18:33
 * for the Reisen
 * p站相关服务 依赖爬虫
 */
@Service
public class PixivBugService {
    //插画日榜链接，get请求，可以添加日期参数进行扩展
    private static final String PIXIV_RANK_URL = "https://www.pixiv.net/ranking.php?mode=daily&content=illust";
    //根据pid查看图片的页面，get请求，最后需要追加p站图片id
    private static final String PIXIV_IMAGE_ID_URL = "https://www.pixiv.net/artworks/";

    @Autowired
    private PixivService pixivService;

    /**
     * 获取当前P站日榜数据
     *
     * @param pageSize 获取个数，排行榜10个差不多了，一次加载50个吧大概
     * @return 日榜图片信息
     */
    public List<PixivRankImageInfo> getPixivIllustRank(int pageSize) throws IOException {
        List<PixivRankImageInfo> rankImageList = new ArrayList<>();

        //使用爬虫获取当前日榜信息
        //通过请求获取到返回的页面
        Proxy proxy = HttpUtil.getProxy();
        String htmlStr = HttpUtil.get(PIXIV_RANK_URL, proxy);
        //使用jsoup解析html
        Document document = Jsoup.parse(htmlStr);
        //选择目标节点，类似于JS的选择器
        Elements rankElements = document.getElementsByClass("ranking-item");

        //循环节点，提取排名和pid信息，其他不在这里拿，图片太小了
        for (Element element : rankElements) {
            Attributes attrs = element.attributes();
            //排名
            String rank = attrs.get("data-rank");
            //上次排名
            String previousRank = element.getElementsByClass("rank").get(0).childNode(1).childNode(0).attr("text");
            //pixivId
            String pixivId = attrs.get("data-id");

            PixivRankImageInfo tempImgInfo = new PixivRankImageInfo();
            tempImgInfo.setRank(NumberUtil.toInt(rank));
            tempImgInfo.setPreviousRank(previousRank);
            tempImgInfo.setPid(NumberUtil.toLong(pixivId));
            rankImageList.add(tempImgInfo);

            //取到指定数量即可
            if (pageSize <= rankImageList.size()) {
                break;
            }
        }

        //根据pid，去单独爬图片的信息
        for (PixivRankImageInfo rankImageInfo : rankImageList) {
            //根据pid获取图片信息
            PixivImageInfo pixivImageInfo = getImageInfoByPixivId(rankImageInfo.getPid());

            //信息合并
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

            //下载图片
            String imgCQ = pixivService.downloadPixivImg(pixivImageInfo.getUrls().getOriginal(), NumberUtil.toLong(pixivImageInfo.getId()));

        }

        return rankImageList;
    }

    /**
     * 查询p站图片id并返回结果
     *
     * @param pid p站图片id
     * @return 拼装好的结果信息
     */
    public String searchPixivImgById(Long pid) throws IOException {
        //根据pid获取图片列表
        PixivImageInfo imageInfo = getImageInfoByPixivId(pid);
        return parsePixivImgInfoByApiInfo(imageInfo, null);
    }

    /**
     * 根据pid获取p站图片信息
     * 使用的是爬虫
     *
     * @param pixivId p站图片id
     * @return 需要的信息
     */
    private PixivImageInfo getImageInfoByPixivId(Long pixivId) throws IOException {
        //代理
        Proxy proxy = HttpUtil.getProxy();
        //爬虫获取pid图片详情信息
        String pidHtmlStr = HttpUtil.get(PIXIV_IMAGE_ID_URL + pixivId, proxy);
        //使用jsoup解析html
        Document document = Jsoup.parse(pidHtmlStr);

        //这里从页面可以获取到图片详情信息，是一个超长的json字符串
        String pidJsonStr = document.getElementById("meta-preload-data").attr("content");
        //取需要的即可，信息太多，不需要的都不解析
        JSONObject pidInfoJson = JSONObject.parseObject(pidJsonStr);
        //结构特殊，还包含动态字段名称，需要多点步骤处理
        String illustInfoStr = JSONObject.toJSONString(pidInfoJson.get("illust"));
        pidInfoJson = JSONObject.parseObject(illustInfoStr);
        String imageInfoStr = JSONObject.toJSONString(pidInfoJson.get(String.valueOf(pixivId)));

        return JSONObject.parseObject(imageInfoStr, PixivImageInfo.class);
    }

    /**
     * 拼装识图结果_p站
     * 搜索结果只会取一个
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public String parsePixivImgRequest(SaucenaoSearchInfoResult infoResult) throws IOException {
        //根据pid获取信息
        PixivImageInfo imageInfo = getImageInfoByPixivId((long) infoResult.getData().getPixiv_id());

        //Saucenao搜索结果相似度
        String similarity = infoResult.getHeader().getSimilarity();
        //拼装结果
        return parsePixivImgInfoByApiInfo(imageInfo, similarity);
    }

    /**
     * 接口返回的图片信息拼装为群消息
     *
     * @param imageInfo  接口返回对象
     * @param similarity 相似度
     * @return 群消息
     * @throws IOException api异常
     */
    private String parsePixivImgInfoByApiInfo(PixivImageInfo imageInfo, String similarity) throws IOException {
        //图片cq码
        String pixivImgCQ = pixivService.downloadPixivImg(imageInfo.getUrls().getOriginal(), NumberUtil.toLong(imageInfo.getId()));

        StringBuilder resultStr = new StringBuilder();
        resultStr.append(pixivImgCQ);
        if (1 < imageInfo.getPageCount()) {
            resultStr.append("\n该Pid包含多张图片");
        }
        if (StringUtil.isNotEmpty(similarity)) {
            resultStr.append("\n[相似度] " + similarity + "%");
        }
        resultStr.append("\n[P站id] " + imageInfo.getId());
        resultStr.append("\n[标题] " + imageInfo.getTitle());
        resultStr.append("\n[作者] " + imageInfo.getUserName());
        resultStr.append("\n[上传时间] " + imageInfo.getCreateDate());
        return resultStr.toString();
    }
}
