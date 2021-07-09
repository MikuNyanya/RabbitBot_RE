package cn.mikulink.rabbitbot.filemanage;


import cn.mikulink.rabbitbot.constant.ConstantBlackList;
import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2021/07/09 15:44
 * for the Reisen
 * <p>
 * 黑名单列表
 */
public class FileManagerBlackList {

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public static void loadFile() throws IOException {
        //初始化集合
        ConstantBlackList.BLACK_LIST = loadList();
    }

    private static List<Long> loadList() throws IOException {
        File blackListFile = FileUtil.fileCheck(ConstantFile.BLACK_LIST_FILE_PATH);

        //初始化集合
        List<Long> tempList = new ArrayList<>();

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(blackListFile));
        //逐行读取文件
        String blackQId = null;
        while ((blackQId = reader.readLine()) != null) {
            //过滤掉空行
            if (blackQId.length() <= 0) continue;
            //过滤掉非数字的异常数据
            if (!NumberUtil.isNumberOnly(blackQId)) continue;
            tempList.add(NumberUtil.toLong(blackQId));
        }
        //关闭读取器
        reader.close();

        return tempList;
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    public static List<Long> writeFile(List<Long> pids) throws IOException {

        //覆写原本配置
        //先读取出所有id，判重后直接覆写文件
        List<Long> tempBlackList = loadList();

        //过滤重复
        List<Long> tempNewPid = new ArrayList<>();
        for (Long pidStr : pids) {
            if (tempBlackList.contains(pidStr)) {
                continue;
            }
            tempBlackList.add(pidStr);
            tempNewPid.add(pidStr);
        }
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PIXIV_SETU_FILE_PATH, false)));
        for (Long qid : tempBlackList) {
            out.write("\r\n" + qid);
        }
        //关闭写入流
        out.close();

        //返回删选后的pid列表
        return tempNewPid;
    }

    /**
     * 移除文件内内容
     *
     * @throws IOException 读写异常
     */
    public static List<Long> removeBlack(List<Long> pids) throws IOException {

        //覆写原本配置
        //先读取出所有id，判重后直接覆写文件
        List<Long> tempBlackList = loadList();

        //过滤重复
        for (Long qid : pids) {
            if (!tempBlackList.contains(qid)) {
                continue;
            }
            tempBlackList.remove(qid);
        }
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PIXIV_SETU_FILE_PATH, false)));
        for (Long qid : tempBlackList) {
            out.write("\r\n" + qid);
        }
        //关闭写入流
        out.close();

        return tempBlackList;
    }
}
