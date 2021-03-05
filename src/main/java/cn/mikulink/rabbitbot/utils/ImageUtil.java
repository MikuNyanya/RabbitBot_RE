package cn.mikulink.rabbitbot.utils;

import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.ImageInfo;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * create by MikuLink on 2019/12/12 17:38
 * for the Reisen
 * 图片
 */
public class ImageUtil {
    private static final List<String> IMAGE_SUFFIXS = Arrays.asList(".jpg", ".jpeg", ".gif", ".png");

    /**
     * 下载图片到本地
     *
     * @param imageUrl 网络图片url
     * @return 图片名称
     */
    public static String downloadImage(String imageUrl) throws IOException {
        return downloadImage(imageUrl, ConstantImage.DEFAULT_IMAGE_SAVE_PATH, null);
    }

    /**
     * 下载图片到本地
     *
     * @param imageUrl 网络图片url
     * @param localUrl 本地储存地址
     * @param fileName 文件名称，带后缀的那种，如果为空则取链接最后一段作为文件名
     * @return 图片路径
     */
    public static String downloadImage(String imageUrl, String localUrl, String fileName) throws IOException {
        return downloadImage(null, imageUrl, localUrl, fileName);
    }

    /**
     * 下载图片到本地
     *
     * @param header   http请求header
     * @param imageUrl 网络图片url
     * @param localUrl 本地储存地址
     * @param fileName 文件名称，带后缀的那种，如果为空则取链接最后一段作为文件名
     * @return 本地图片相对路径
     * @throws IOException 异常
     */
    public static String downloadImage(HashMap<String, String> header, String imageUrl, String localUrl, String fileName) throws IOException {
        return downloadImage(header, imageUrl, localUrl, fileName, null);
    }

    /**
     * 下载图片到本地
     *
     * @param header   http请求header
     * @param imageUrl 网络图片url
     * @param localUrl 本地储存地址
     * @param fileName 文件名称，带后缀的那种，如果为空则取链接最后一段作为文件名
     * @param proxy    代理
     * @return 本地图片相对路径
     * @throws IOException 异常
     */
    public static String downloadImage(HashMap<String, String> header, String imageUrl, String localUrl, String fileName, Proxy proxy) throws IOException {
        if (StringUtil.isEmpty(imageUrl)) {
            throw new IOException("网络图片链接为空");
        }

        //类型根据创建连接
        HttpURLConnection conn = null;
        if (imageUrl.startsWith("https")) {
            conn = HttpsUtil.getHttpsURLConnection(imageUrl, HttpUtil.REQUEST_METHOD_GET, header, proxy);
        } else {
            conn = HttpUtil.getHttpURLConnection(imageUrl, HttpUtil.REQUEST_METHOD_GET, proxy);
        }
        //请求header
        if (null != header) {
            for (String key : header.keySet()) {
                conn.setRequestProperty(key, header.get(key));
            }
        }

        //获取输出流
        InputStream inStream = conn.getInputStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        //把图片信息存下来，写入内存
        byte[] data = outStream.toByteArray();
        //创建本地文件
        File result = new File(localUrl);
        if (!result.exists()) {
            result.mkdirs();
        }

        //如果为空，使用网络图片的名称和后缀
        if (StringUtil.isEmpty(fileName)) {
            fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        }

        //写入图片数据
        String fileFullNmae = result + File.separator + fileName;
        FileOutputStream fileOutputStream = new FileOutputStream(fileFullNmae);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
        outStream.close();

        //返回文件路径
        return fileFullNmae;
    }

    /**
     * 判断文件是否存在
     *
     * @param imageUrl 文件路径
     * @return 是否存在
     */
    public static boolean isExists(String imageUrl) {
        if (StringUtil.isEmpty(imageUrl)) {
            return false;
        }
        //创建本地文件
        File result = new File(imageUrl);
        return result.exists();
    }

    /**
     * 是否为图片文件
     *
     * @param fileName 文件名称
     * @return 是否为图片
     */
    public static boolean isImage(String fileName) {
        String suffix = FileUtil.getFileSuffix(fileName);
        if (StringUtil.isEmpty(suffix)) {
            return false;
        }

        return IMAGE_SUFFIXS.contains(suffix);
    }

    /**
     * 获取本地图片信息
     *
     * @param imagePath 图片文件路径
     * @return 图片信息
     */
    public static ImageInfo getImageInfo(String imagePath) throws IOException {
        //这个方法太慢了
//        BufferedImage sourceImg = ImageIO.read(new FileInputStream(new File(imagePath)));
//        sourceImg.getWidth();
//        sourceImg.getHeight();


        //没研究透，复制来的代码，但比上面的快多了
        //获取图片后缀,不要"."
        String imgType = FileUtil.getFileSuffix(imagePath).replace(".", "");
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName(imgType.toLowerCase());
        ImageReader reader = readers.next();
        ImageInputStream iis = ImageIO.createImageInputStream(new File(imagePath));
        reader.setInput(iis, true);

        int width = reader.getWidth(0);
        int height = reader.getHeight(0);

        ImageInfo imageInfo = new ImageInfo();
        imageInfo.setLocalPath(imagePath);
        imageInfo.setWidth(width);
        imageInfo.setHeight(height);
        imageInfo.setType(imgType);
        imageInfo.setSize(new File(imagePath).length());

        return imageInfo;
    }
}
