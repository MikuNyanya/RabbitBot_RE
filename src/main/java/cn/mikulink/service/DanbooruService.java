package cn.mikulink.service;

import cn.mikulink.constant.ConstantImage;
import cn.mikulink.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.exceptions.RabbitException;
import cn.mikulink.utils.FileUtil;
import cn.mikulink.utils.HttpUtil;
import cn.mikulink.utils.ImageUtil;
import cn.mikulink.utils.StringUtil;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Proxy;

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
    private ImageService imageService;

    /**
     * 拼装识图结果_Danbooru
     * 根据id去爬网页
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public MessageChain parseDanbooruImgRequest(SaucenaoSearchInfoResult infoResult, Contact subject) throws RabbitException {
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
            result = getImgIdByDanbooruId(String.valueOf(danbooruId), subject);

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
     * 根据danbooruId下载图片然后转为mirai图片id
     *
     * @param danbooruId danbooru图片id
     * @return mirai图片id
     */
    private MessageChain getImgIdByDanbooruId(String danbooruId, Contact subject) throws RabbitException {
        try {
            //目标页面
            String danbooru = "https://danbooru.donmai.us/posts/" + danbooruId;
            //通过请求获取到返回的页面
            Proxy proxy = HttpUtil.getProxy();
            String htmlStr = HttpUtil.get(danbooru, proxy);
            //使用jsoup解析html
            Document document = Jsoup.parse(htmlStr);
            //选择目标节点，类似于JS的选择器
            Element element = document.getElementById("image-resize-link");
            String imageUrl = null;
            if (null != element) {
                //获取原图
                imageUrl = element.attributes().get("href");
            } else {
                //本身图片比较小就没有显示原图的
                imageUrl = document.getElementById("image").attributes().get("src");
            }

            //如果已经下载过了，直接返回CQ
            //先检测是否已下载，如果已下载直接返回CQ，以p站图片名称为key
            String imgFileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            String localDanbooruFilePath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru" + File.separator + imgFileName;
            if (FileUtil.exists(localDanbooruFilePath)) {
                return imageService.uploadMiraiImage(localDanbooruFilePath, subject);
            }

            //下载图片
            String localUrl = ImageUtil.downloadImage(null, imageUrl, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru", null, proxy);
            if (StringUtil.isNotEmpty(localUrl)) {
                return imageService.uploadMiraiImage(localDanbooruFilePath, subject);
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
