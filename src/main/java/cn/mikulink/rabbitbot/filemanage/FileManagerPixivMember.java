package cn.mikulink.rabbitbot.filemanage;


import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * create by MikuLink on 2020/04/29 15:44
 * for the Reisen
 * <p>
 * pxiv 用户信息
 */
public class FileManagerPixivMember {
    private static final Logger logger = LoggerFactory.getLogger(FileManagerPixivMember.class);
    //tag文件，包含所有收集到的tag
    private static File pixivMemberFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != pixivMemberFile) {
            return;
        }
        pixivMemberFile = FileUtil.fileCheck(ConstantFile.PIXIV_TMEMBER_FILE_PATH);
    }

    /**
     * 加载文件内容
     */
    public static void loadFile() {
        try {
            fileInit();

            //创建读取器
            BufferedReader reader = new BufferedReader(new FileReader(pixivMemberFile));
            //逐行读取
            String tag = null;
            while ((tag = reader.readLine()) != null) {
                //过滤掉空行
                if (tag.length() <= 0) continue;
                ConstantPixiv.PIXIV_MEMBER_LIST.add(tag);
            }
            //关闭读取器
            reader.close();
        } catch (IOException ioEx) {
            logger.error("pixiv_tag文件读写异常:" + ioEx.toString(), ioEx);
        }
    }

    /**
     * 写入用户信息
     * 业务层组装好字符串，这一层不做处理
     * 格式：memberId,memberName,member
     *
     * @throws IOException 读写异常
     */
    public static void addMemberInfo(String memberStr) throws IOException {
        if (StringUtil.isEmpty(memberStr)) {
            return;
        }

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PIXIV_TMEMBER_FILE_PATH, true)));
        //写入内容，每个用户信息占一行
        out.write("\r\n" + memberStr);
        //关闭写入流
        out.close();

        //刷新内存中的列表
        ConstantPixiv.PIXIV_MEMBER_LIST.add(memberStr);
    }

}
