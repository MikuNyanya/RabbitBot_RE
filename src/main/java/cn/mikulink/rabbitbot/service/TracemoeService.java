package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.tracemoe.TracemoeSearch;
import cn.mikulink.rabbitbot.constant.ConstantAnime;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.apirequest.tracemoe.TracemoeSearchDoc;
import cn.mikulink.rabbitbot.entity.apirequest.tracemoe.TracemoeSearchResult;
import cn.mikulink.rabbitbot.utils.EncodingUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import com.alibaba.fastjson2.JSONObject;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * create by MikuLink on 2020/2/21 14:18
 * for the Reisen
 * 以图搜番服务
 */
@Service
public class TracemoeService {
    private static final Logger logger = LoggerFactory.getLogger(TracemoeService.class);

    //预览图请求链接https://media.trace.moe/image/${anilist_id}/${encodeURIComponent(filename)}?t=${at}&token=${tokenthumb}
    private static final String URL = "https://media.trace.moe/image/%s/%s?t=%s&token=%s&size=l";

    /**
     * 以图搜番，用trace.moe
     *
     * @param imgUrl 网络图片链接
     * @return 返回消息
     */
    public TracemoeSearchDoc searchAnimeFromTracemoe(String imgUrl) {
        try {
            TracemoeSearch request = new TracemoeSearch();
            request.setImgUrl(imgUrl);
            request.doRequest();
            TracemoeSearchResult result = request.getEntity();

            List<TracemoeSearchDoc> docList = result.getDocs();
            if (null == docList || docList.size() <= 0) {
                return null;
            }
            //只取第一个信息
            return docList.get(0);
        } catch (IOException ioEx) {
            logger.error(ConstantAnime.TRACE_MOE_API_REQUEST_ERROR, ioEx);
            return null;
        }
    }

    /**
     * 下载预览图
     * 需要搜番结果里的很多参数
     *
     * @param doc 搜番结果
     * @return 下载后的本地图片资源路径
     */
    public String imagePreviewDownload(TracemoeSearchDoc doc) {
        if (null == doc) {
            return null;
        }
        try {
            //url加入参数
            //编码fileName
            String fileName_encodeURIComponent = EncodingUtil.encodeURIComponent(doc.getFilename());
            String requestUrl = String.format(URL, doc.getAnilist_id(), fileName_encodeURIComponent, doc.getAt(), doc.getTokenthumb());
            //下载预览图
            //本地文件名称，由视频数据id和时间轴坐标组成，格式固定jpg吧
            String localImageName = String.format("%s%s.jpg", doc.getAnilist_id(), doc.getAt());
            return ImageUtil.downloadImage(requestUrl, ConstantImage.IMAGE_TRACEMOE_SAVE_PATH, localImageName);

        } catch (Exception ex) {
            //允许业务忽略异常继续执行业务
            logger.error("Api Request TracemoeService imagePreviewDownload,doc:{}", JSONObject.toJSONString(doc), ex);
            return null;
        }
    }

    public MessageChain parseResultMsg(TracemoeSearchDoc doc) {
        MessageChain result = MessageUtils.newChain();

        StringBuilder resultStr = new StringBuilder();
        resultStr.append("[相似度] ").append((doc.getSimilarity() * 100)).append("%")
                .append("\n[番名] ").append(doc.getAnime())
                .append("\n[档期] ").append(doc.getSeason())
                .append("\n[集数] ").append(doc.getEpisode())
                .append("\n[图片位置] ").append(doc.getAt()).append("秒")
                .append("\n[文件名] ").append(doc.getFilename())
                .append("\n[番名(日文)] ").append(doc.getTitle_native())
                .append("\n[番名(英文)] ").append(doc.getTitle_english())
                .append("\n[番名(中文)] ").append(doc.getTitle_chinese());

        result = result.plus(resultStr.toString());

        return result;
    }
}
