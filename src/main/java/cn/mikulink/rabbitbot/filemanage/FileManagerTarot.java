package cn.mikulink.rabbitbot.filemanage;


import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.TarotInfo;
import cn.mikulink.rabbitbot.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * create by MikuLink on 2020/12/22 14:44
 * for the Reisen
 * <p>
 * 塔罗牌数据
 * 文件每张牌的存储从上到下
 * [图片文件名称]
 * [卡牌名称]
 * [正位描述]
 * [逆位描述]
 */
public class FileManagerTarot {

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public static void loadFile() throws IOException {
        File tarotLikeFile = FileUtil.fileCheck(ConstantFile.APPEND_TAROT_FILE_PATH);
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(tarotLikeFile));

        //逐行读取文件
        String freeTimeStr = null;
        //跳过第一行 是标识的数据来源
        reader.readLine();
        while ((freeTimeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (freeTimeStr.length() <= 0) continue;

            TarotInfo tempInfo = new TarotInfo();

            //首先是图片名称
            tempInfo.setImgName(freeTimeStr);
            //再往下读一行，是卡牌名称
            freeTimeStr = reader.readLine();
            tempInfo.setName(freeTimeStr);

            //再往下读一行，是正位描述
            freeTimeStr = reader.readLine();
            freeTimeStr = freeTimeStr.substring(freeTimeStr.indexOf("：") + 1);
            tempInfo.setNormalDes(freeTimeStr);

            //再往下读一行，是逆位描述
            freeTimeStr = reader.readLine();
            freeTimeStr = freeTimeStr.substring(freeTimeStr.indexOf("：") + 1);
            tempInfo.setSeDlamron(freeTimeStr);

            ConstantTarot.TARTO_LIST.add(tempInfo);
        }
        //关闭读取器
        reader.close();
    }
}
