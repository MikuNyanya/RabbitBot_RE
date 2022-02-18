package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.DanbooruImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * @author MikuLink
 * @date 2020/02/21 10:33
 * for the Reisen
 * Danbooru相关服务
 */
@Service
public class DanbooruService {
    private static final Logger logger = LoggerFactory.getLogger(DanbooruService.class);

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private ProxyService proxyService;

    /**
     * 拼装识图结果_Danbooru
     * 根据id去爬网页
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public MessageChain parseDanbooruImgRequest(SaucenaoSearchInfoResult infoResult) throws RabbitException {

        //根据id获取图片列表
        try {
            //Danbooru图片id
            Long danbooruId = infoResult.getData().getDanbooru_id();
            //图片id
            DanbooruImageInfo danbooruImageInfo = downloadImgByDanbooruId(danbooruId);
            return parseMessageChan(danbooruImageInfo);
        } catch (Exception ex) {
            logger.error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_ERROR_GROUP_MESSAGE);
        }
    }

    /**
     * 根据danbooruId下载图片
     *
     * @param danbooruId danbooru图片id
     * @return 本地图片路径
     */
    public DanbooruImageInfo downloadImgByDanbooruId(Long danbooruId) throws RabbitException {
        try {
            //目标页面
            DanbooruImageGet request = new DanbooruImageGet();
            request.setDanbooruId(String.valueOf(danbooruId));
            request.doRequest();
            request.setProxy(proxyService.getProxy());
            DanbooruImageInfo danbooruImageInfo = request.parseDanbooruImageInfo();

            if (null == danbooruImageInfo) {
                throw new RabbitException(ConstantImage.DANBOORU_IMAGE_DOWNLOAD_FAIL);
            }
            return danbooruImageInfo;
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.warn("DanbooruService " + ConstantImage.DANBOORU_ID_GET_NOT_FOUND + "(" + danbooruId + ")");
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_NOT_FOUND);
        } catch (SocketTimeoutException timeoutEx) {
            logger.error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_TIMEOUT_GROUP_MESSAGE + "(" + danbooruId + ")", timeoutEx);
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_TIMEOUT_GROUP_MESSAGE + "(" + danbooruId + ")");
        } catch (IOException ioEx) {
            logger.error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE + "(" + danbooruId + ")", ioEx);
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE);
        }
    }

    public MessageChain getImgInfoById(Long danbooruId) throws IOException, RabbitException {
        DanbooruImageInfo danbooruImageInfo = downloadImgByDanbooruId(danbooruId);
        return parseMessageChan(danbooruImageInfo);
    }

    //转化结果对象
    private MessageChain parseMessageChan(DanbooruImageInfo danbooruImageInfo) throws IOException {
        String imageUrl = danbooruImageInfo.getLargeFileUrl();

        //如果已经下载过了，直接返回
        //先检测是否已下载，如果已下载直接返回，以p站图片名称为key
        String imgFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        String localDanbooruFilePath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru" + File.separator + imgFileName;
        String localUrl = null;
        if (FileUtil.exists(localDanbooruFilePath)) {
            localUrl = localDanbooruFilePath;
        } else {
            //下载图片
            localUrl = ImageUtil.downloadImage(null,
                    imageUrl,
                    ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru",
                    null,
                    proxyService.getProxy());
        }
        MessageChain result = rabbitBotService.parseMsgChainByLocalImgs(localUrl);
        SaucenaoSearchInfoResult saucenaoSearchInfoResult = danbooruImageInfo.getSaucenaoSearchInfoResult();

        StringBuilder resultStr = new StringBuilder();
        if (null != saucenaoSearchInfoResult) {
            //Saucenao搜索结果相似度
            String similarity = saucenaoSearchInfoResult.getHeader().getSimilarity();
            resultStr.append("\n[相似度] ").append(similarity).append("%");
        }

        Long danbooruId = danbooruImageInfo.getId();
        Integer width = danbooruImageInfo.getImageWidth();
        Integer height = danbooruImageInfo.getImageHeight();
        String source = danbooruImageInfo.getSource();
        String createdAt = danbooruImageInfo.getCreatedAt();

        resultStr.append("\n[DanbooruId] ").append(danbooruId);
        resultStr.append("\n[图片尺寸] ").append(width).append("x").append(height);
        resultStr.append("\n[来源] ").append(source);
        resultStr.append("\n[主要TAG] ").append(danbooruImageInfo.getTagStringCharacter()).append(" ").append(danbooruImageInfo.getTagStringCopyright());
        resultStr.append("\n[上传时间] ").append(createdAt);
        result = result.plus(resultStr.toString());
        return result;
    }
}
