package cn.mikulink.rabbitbot.filemanage;



import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * create by MikuLink on 2020/03/18 11:44
 * for the Reisen
 * <p>
 * pxiv tag列表文件
 */
public class FileManagerPixivTags {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerPixivTags.class);
    //tag文件，包含所有收集到的tag
    private static File pixivTagFile = null;
    //整理后的tag
    private static File pixivTagRabbitFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != pixivTagFile) {
            return;
        }
        pixivTagFile = FileUtil.fileCheck(ConstantFile.PIXIV_TAG_FILE_PATH);
    }

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileRabbitInit() throws IOException {
        //先载入文件
        if (null != pixivTagRabbitFile) {
            return;
        }
        pixivTagRabbitFile = FileUtil.fileCheck(ConstantFile.PIXIV_TAG_RABBIT_FILE_PATH);
    }

    /**
     * 加载已整理的tag文件内容
     */
    public static void loadRabbitFile() {
        try {
            fileRabbitInit();

            //创建读取器
            BufferedReader reader = new BufferedReader(new FileReader(pixivTagRabbitFile));
            //逐行读取
            String tag = null;
            while ((tag = reader.readLine()) != null) {
                //过滤掉空行
                if (tag.length() <= 0) continue;
                ConstantImage.PIXIV_TAG_RABBIT_LIST.add(tag);
            }
            //关闭读取器
            reader.close();
        } catch (IOException ioEx) {
            logger.error("pixiv_tag_rabbit文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 加载文件内容
     */
    public static void loadFile() {
        try {
            fileInit();

            //创建读取器
            BufferedReader reader = new BufferedReader(new FileReader(pixivTagFile));
            //逐行读取
            String tag = null;
            while ((tag = reader.readLine()) != null) {
                //过滤掉空行
                if (tag.length() <= 0) continue;
                ConstantImage.PIXIV_TAG_LIST.add(tag);
            }
            //关闭读取器
            reader.close();
        } catch (IOException ioEx) {
            logger.error("pixiv_tag文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 写入tag
     *
     * @throws IOException 读写异常
     */
    public static void addTags(List<String> tags) throws IOException {
        if (null == tags || tags.size() == 0) {
            return;
        }

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PIXIV_TAG_FILE_PATH, true)));
        //写入内容，每个tag占一行
        for (String tag : tags) {
            out.write("\r\n" + tag);
        }
        //关闭写入流
        out.close();

        //刷新内存中的列表
        ConstantImage.PIXIV_TAG_LIST.addAll(tags);
    }

}
