package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.saucenao.SaucenaoImageSearch;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantConfig;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchInfoResult;
import cn.mikulink.rabbitbot.entity.apirequest.saucenao.SaucenaoSearchResult;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.exceptions.RabbitException;
import cn.mikulink.rabbitbot.utils.*;
import net.coobird.thumbnailator.Thumbnails;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/1/15 11:17
 * for the Reisen
 * 一些图片处理的方法
 * 有些图片处理的代码会跟随业务代码
 * 这里主要存放共用且业务划分不明显的处理逻辑
 */
@Service
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Value("${saucenao.key}")
    private String saucenaoKey;

    /**
     * 随机获取一张鸽子图
     * 伪随机
     *
     * @return 生成好的CQ码
     */
    public String getGuguguRandom() throws IOException {
        String guguguPath = ConstantImage.DEFAULT_IMAGE_SAVE_PATH + "/gugugu";
        //如果集合为空，读取文件列表
        //只存放路径，由于使用伪随机，所以list里面的内容会一直减少
        List<String> imageGuguguList = ConstantImage.map_images.get(ConstantImage.IMAGE_MAP_KEY_GUGUGU);
        if (null == imageGuguguList || imageGuguguList.size() <= 0) {
            String[] guguguImages = FileUtil.getList(guguguPath);
            if (null == guguguImages || guguguImages.length <= 0) {
                return "";
            }
            imageGuguguList = new ArrayList<>();
            //过滤掉文件夹，和后缀不是图片的文件
            for (String imagePath : guguguImages) {
                if (!ImageUtil.isImage(imagePath)) {
                    continue;
                }
                //刷新内存中的列表
                imageGuguguList.add(imagePath);
            }
            ConstantImage.map_images.put(ConstantImage.IMAGE_MAP_KEY_GUGUGU, imageGuguguList);
        }

        //列表中有图片，随机一个，使用伪随机
        String guguguImageFullName = RandomUtil.rollAndDelStrFromList(imageGuguguList);
        //生成CQ码并返回
        return scaleForceByLocalImagePath(guguguPath + File.separator + guguguImageFullName);
    }

    /**
     * 根据q号获取头像CQ码，获取不到会返回空
     * 大概100x100
     * 使用的是腾讯自家链接
     * http://q1.qlogo.cn/g?b=qq&s=100&nk=qq号
     * http://users.qzone.qq.com/fcg-bin/cgi_get_portrait.fcg?uins=qq号
     *
     * @param qq q号
     * @return 下载后的本地图片路径
     */
    public String getQLogoCq(Long qq) {
        if (null == qq) {
            return null;
        }
        String url = String.format("http://q1.qlogo.cn/g?b=qq&s=100&nk=%s", qq);
        //链接无后缀，是重定向后直接返回图片的，所以使用qq名称作为图片名
        String qlogoImgName = String.format("qlogo_%s.jpg", qq);

        try {
            //下载头像
            return ImageUtil.downloadImage(url, ConstantImage.DEFAULT_IMAGE_SAVE_PATH + File.separator + "qlogo", qlogoImgName);
        } catch (Exception ex) {
            logger.error("qq[" + qq + "]获取头像异常:" + ex.toString(), ex);
            return null;
        }
    }

    /**
     * 压缩图片
     * 不是总是压缩，视配置和图片参数是否超标决定
     *
     * @param localImagePath 本地文件路径，带文件和后缀的那种
     * @return 本地图片路径
     */
    public String scaleForceByLocalImagePath(String localImagePath) throws IOException {
        if (StringUtil.isEmpty(localImagePath)) {
            return "";
        }
        //本地图片是否存在
        if (!FileUtil.exists(localImagePath)) {
            return "";
        }

        //获取图片名称
        String imageFullName = FileUtil.getFileName(localImagePath);

        //如果图片超出大小，则缩小图片，减少不必要的麻烦

        boolean isScale = false;
        //是否总是压制图片
        String image_scale_force = ConstantCommon.common_config.get(ConstantConfig.CONFIG_IMAGE_SCALE_FORCE);
        if (ConstantConfig.ON.equalsIgnoreCase(image_scale_force)) {
            isScale = true;
        } else {
            //判断图片的大小
            ImageInfo imageInfo = ImageUtil.getImageInfo(localImagePath);
            boolean overSize = ConstantImage.IMAGE_SCALE_MIN_SIZE * 1024 * 1024 < imageInfo.getSize();
            boolean overHeight = ConstantImage.IMAGE_SCALE_MIN_HEIGHT < imageInfo.getHeight();
            boolean overWidth = ConstantImage.IMAGE_SCALE_MIN_WIDTH < imageInfo.getWidth();
            isScale = overSize || overHeight || overWidth;
        }
        if (isScale) {
            //生成修改后的文件名和路径，后缀为jpg
            imageFullName = imageFullName.substring(0, imageFullName.lastIndexOf("."));
            String scaleImgName = ConstantImage.IMAGE_SCALE_PREFIX + imageFullName + ".jpg";
            String scaleImgPath = ConstantImage.DEFAULT_IMAGE_SCALE_SAVE_PATH + File.separator + scaleImgName;
            //如果已经存在就不重复处理了
            if (!FileUtil.exists(scaleImgPath)) {
                //压缩图片尺寸，实际上这个方法的作用是向指定尺寸数值靠拢，比例不会变，取长宽中最接近指定数值的一方为准
//              Thumbnails.of(localImagePath).size(2500, 2500).toFile(scaleImgPath);
                //处理出来jpg的dpi是91，文件挺小的 所以基本上太大的文件转为jpg就行了
                Thumbnails.of(localImagePath).scale(1).toFile(scaleImgPath);
            }
            //使用处理后的本地图片路径和文件名
            return scaleImgPath;
        }

        //返回本地连接
        return localImagePath;
    }

    /**
     * 使用Saucenao搜图
     *
     * @param imgUrl 网络图片链接
     * @return 搜索结果
     */
    public SaucenaoSearchInfoResult searchImgFromSaucenao(String imgUrl) throws RabbitException {
        if (StringUtil.isEmpty(saucenaoKey)) {
            logger.warn(ConstantImage.SAUCENAO_API_KEY_EMPTY);
            throw new RabbitException(ConstantImage.SAUCENAO_API_KEY_EMPTY);
        }
        try {
            //调用API
            SaucenaoImageSearch request = new SaucenaoImageSearch();
            request.setAccessToken(saucenaoKey);
            request.setNumres(6);
            request.setUrl(imgUrl);

            request.doRequest();
            SaucenaoSearchResult saucenaoSearchResult = request.getEntity();
            if (null == saucenaoSearchResult) {
                throw new RabbitException(ConstantImage.SAUCENAO_API_SEARCH_FAIL);
            }

            //解析结果 header基本不用看，看结果就行，取第一个
            List<SaucenaoSearchInfoResult> infoResultList = saucenaoSearchResult.getResults();
            if (null == infoResultList || infoResultList.size() <= 0) {
                throw new RabbitException(ConstantImage.SAUCENAO_SEARCH_FAIL);
            }
            for (SaucenaoSearchInfoResult infoResult : infoResultList) {
                //暂时只识别pixiv和Danbooru的
                int indexId = infoResult.getHeader().getIndex_id();
                boolean isPixiv = 5 == indexId;
                boolean isDanbooru = (9 == indexId || null != infoResult.getData().getDanbooru_id());
                if (!isPixiv && !isDanbooru) {
                    continue;
                }

                //过滤掉相似度50一下的
                String similarity = infoResult.getHeader().getSimilarity();
                if (StringUtil.isEmpty(similarity) || 50.0 > NumberUtil.toDouble(similarity)) {
                    continue;
                }
                return infoResult;
            }
        } catch (SocketTimeoutException timeoutException) {
            logger.error(ConstantImage.SAUCENAO_API_TIMEOUT_FAIL + timeoutException.toString(), timeoutException);
            throw new RabbitException(ConstantImage.SAUCENAO_API_TIMEOUT_FAIL);
        } catch (Exception ex) {
            logger.error(ConstantImage.SAUCENAO_API_REQUEST_ERROR + ex.toString(), ex);
            throw new RabbitException(ConstantImage.SAUCENAO_API_REQUEST_ERROR);
        }
        return null;
    }
}
