package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.danbooru.DanbooruImageGet;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.ImageUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

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
        MessageChain result = null;
        //根据id获取图片列表
        try {
            //Danbooru图片id
            Long danbooruId = infoResult.getData().getDanbooru_id();
            //标签
            String tag = infoResult.getData().getCharacters();
            //来源信息
            String source = infoResult.getData().getSource();
            //Saucenao搜索结果相似度
            String similarity = infoResult.getHeader().getSimilarity();

            //图片id
            String localImgPath = doanloadImgByDanbooruId(String.valueOf(danbooruId));
            result = rabbitBotService.parseMsgChainByLocalImgs(localImgPath);

            StringBuilder resultStr = new StringBuilder();
            resultStr.append("\n[相似度] ").append(similarity).append("%");
            resultStr.append("\n[DanbooruId] ").append(danbooruId);
            resultStr.append("\n[Tag] ").append(tag);
            resultStr.append("\n[来源] ").append(source);
            result.plus(resultStr.toString());
            return result;
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
    private String doanloadImgByDanbooruId(String danbooruId) throws RabbitException {
        try {
            //目标页面
            DanbooruImageGet request = new DanbooruImageGet();
            request.setDanbooruId(danbooruId);
            request.doRequest();
            request.setProxy(proxyService.getProxy());
            String imageUrl = request.getDanbooruImageUrl();

            //如果已经下载过了，直接返回
            //先检测是否已下载，如果已下载直接返回，以p站图片名称为key
            String imgFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            String localDanbooruFilePath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru" + File.separator + imgFileName;
            if (FileUtil.exists(localDanbooruFilePath)) {
                return localDanbooruFilePath;
            }

            //下载图片
            String localUrl = ImageUtil.downloadImage(null,
                    imageUrl,
                    ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru",
                    null,
                    proxyService.getProxy());
            if (StringUtil.isNotEmpty(localUrl)) {
                return localUrl;
            }
            throw new RabbitException(ConstantImage.DANBOORU_IMAGE_DOWNLOAD_FAIL);
        } catch (FileNotFoundException fileNotFoundEx) {
            logger.warn("DanbooruService " + ConstantImage.DANBOORU_ID_GET_NOT_FOUND + "(" + danbooruId + ")");
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_NOT_FOUND);
        } catch (IOException ioEx) {
            logger.error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE + "(" + danbooruId + ")", ioEx);
            throw new RabbitException(ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE);
        }
    }
}
