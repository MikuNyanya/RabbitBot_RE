package cn.mikulink.rabbitbot.filemanage;


import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * create by MikuLink on 2020/12/13 15:44
 * for the Reisen
 * <p>
 * p站色图的文件专用管理器
 */
public class FileManagerSetu {

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public static void loadFile() throws IOException {
        File setuFile = FileUtil.fileCheck(ConstantFile.PIXIV_SETU_FILE_PATH);

        //初始化集合
        ConstantPixiv.List_SETU_PID = new ArrayList<>();

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(setuFile));
        //逐行读取文件
        String setuPid = null;
        while ((setuPid = reader.readLine()) != null) {
            //过滤掉空行
            if (setuPid.length() <= 0) continue;
            ConstantPixiv.List_SETU_PID.add(setuPid);
        }
        //关闭读取器
        reader.close();
        //刷新最大元素数目
        ConstantPixiv.SETU_PID_LIST_MAX_SIZE = ConstantPixiv.List_SETU_PID.size();
    }
}
