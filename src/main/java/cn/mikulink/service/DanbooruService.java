package cn.mikulink.service;

import gugugu.bots.LoggerRabbit;
import gugugu.constant.ConstantImage;
import gugugu.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import utils.FileUtil;
import utils.HttpUtil;
import utils.ImageUtil;
import utils.StringUtil;

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
public class DanbooruService {

    /**
     * 拼装识图结果_Danbooru
     * 根据id去爬网页
     *
     * @param infoResult 识图结果
     * @return 拼装好的群消息
     */
    public static String parseDanbooruImgRequest(SaucenaoSearchInfoResult infoResult) {
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

            //图片cq码
            String danbooruImgCQ = getDanbooruImgCQById(String.valueOf(danbooruId));

            StringBuilder resultStr = new StringBuilder();
            resultStr.append(danbooruImgCQ);
            resultStr.append("\n[相似度] " + similarity + "%");
            resultStr.append("\n[DanbooruId] " + danbooruId);
            resultStr.append("\n[Tag] " + tag);
            resultStr.append("\n[来源] " + source);
            return resultStr.toString();
        } catch (Exception ex) {
            LoggerRabbit.logger().error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_ERROR_GROUP_MESSAGE + ex.toString(), ex);
            return ConstantImage.DANBOORU_ID_GET_ERROR_GROUP_MESSAGE;
        }
    }

    /**
     * 根据danbooruId下载图片然后转为CQ码
     *
     * @param danbooruId danbooru图片id
     * @return cq码，或者错误信息
     */
    private static String getDanbooruImgCQById(String danbooruId) {
        try {
            String imgCQ = null;

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
                return ImageService.parseCQByLocalImagePath(localDanbooruFilePath);
            }

            //下载图片
            String localUrl = ImageUtil.downloadImage(null, imageUrl, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "danbooru", null, proxy);
            if (StringUtil.isNotEmpty(localUrl)) {
                imgCQ = ImageService.parseCQByLocalImagePath(localDanbooruFilePath);
            }
            if (StringUtil.isEmpty(imgCQ)) {
                imgCQ = ConstantImage.DANBOORU_IMAGE_DOWNLOAD_FAIL;
            }
            return imgCQ;
        } catch (FileNotFoundException fileNotFoundEx) {
            LoggerRabbit.logger().warning("DanbooruService " + ConstantImage.DANBOORU_ID_GET_NOT_FOUND + "(" + danbooruId + ")");
            return ConstantImage.DANBOORU_ID_GET_NOT_FOUND;
        } catch (IOException ioEx) {
            LoggerRabbit.logger().error("DanbooruService " + ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE + "(" + danbooruId + ")", ioEx);
            return ConstantImage.DANBOORU_ID_GET_FAIL_GROUP_MESSAGE;
        }
    }
}
