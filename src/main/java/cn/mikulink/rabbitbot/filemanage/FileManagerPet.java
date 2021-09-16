package cn.mikulink.rabbitbot.filemanage;

import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantPet;
import cn.mikulink.rabbitbot.entity.PetInfo;
import cn.mikulink.rabbitbot.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.*;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * 配置文件文件专用管理器
 */
public class FileManagerPet {
    //文件
    private static File configFile = null;

    /**
     * 文件初始化
     * 以及加载文件到系统
     *
     * @throws IOException 读写异常
     */
    private static void fileInit() throws IOException {
        //先载入文件
        if (null != configFile) {
            return;
        }
        configFile = FileUtil.fileCheck(ConstantFile.PET_FILE_PATH);
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public static void loadPetInfo() throws IOException {
        fileInit();
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() <= 0) continue;
            ConstantPet.petInfo = JSONObject.parseObject(configJson, PetInfo.class);
        }
        //关闭读取器
        reader.close();

        //初始化
        if(null == ConstantPet.petInfo){
            ConstantPet.petInfo = new PetInfo();
        }
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    public static void writeFile() throws IOException {
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ConstantFile.PET_FILE_PATH, false)));
        //覆写原本配置
        out.write(JSONObject.toJSONString(ConstantPet.petInfo));
        //关闭写入流
        out.close();
    }
}
