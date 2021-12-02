package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantCapsuleToy;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

/**
 * created by MikuNyanya on 2021/12/1 15:16
 * For the Reisen
 * 扭蛋相关服务
 */
@Slf4j
@Service
public class CapsuleToyService {
    @Value("${file.path.data:}")
    private String dataPath;

    /**
     * 获取资源文件路径
     */
    public String getFilePath() {
        return dataPath + File.separator + "files" + File.separator + "capsule_toy.txt";
    }

    /**
     * 选择一个扭蛋
     *
     * @return 随机扭蛋
     */
    public String capsuleToySelect() {
        //元素少于1/6的时候，重新加载
        if (CollectionUtil.isEmpty(ConstantCapsuleToy.MSG_CAPSULE_TOY)
                || ConstantCapsuleToy.MSG_CAPSULE_TOY.size() < ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAX_SIZE / 6) {
            try {
                this.loadFile();
            } catch (Exception ex) {
                log.error("扭蛋文件加载失败", ex);
            }
        }

        //扭个蛋
        return RandomUtil.rollAndDelStrFromList(ConstantCapsuleToy.MSG_CAPSULE_TOY);
    }


    /**
     * 加载扭蛋池
     */
    public void loadFile() throws IOException {
        String filePath = this.getFilePath();
        FileUtil.fileCheck(this.getFilePath());
        //初始化集合
        ConstantCapsuleToy.MSG_CAPSULE_TOY = new ArrayList<>();

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        //逐行读取文件
        String capsuleToy = null;
        while ((capsuleToy = reader.readLine()) != null) {
            //过滤掉空行
            if (capsuleToy.length() <= 0) continue;
            ConstantCapsuleToy.MSG_CAPSULE_TOY.add(capsuleToy);
        }
        //刷新最大元素数目
        ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAX_SIZE = ConstantCapsuleToy.MSG_CAPSULE_TOY.size();
        //关闭读取器
        reader.close();
    }

    /**
     * 添加一个扭蛋
     *
     * @param capsuleToy 新扭蛋
     * @throws IOException
     */
    public void addCapsuleToy(String capsuleToy) throws IOException {
        //入参检验
        if (StringUtil.isEmpty(capsuleToy)) {
            return;
        }

        String filePath = this.getFilePath();
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, true)));

        //写入内容 写一行换一行
        out.write("\r\n" + capsuleToy);
        //同时把新加的内容同步到系统
        ConstantCapsuleToy.MSG_CAPSULE_TOY.add(capsuleToy);
        //刷新最大数目
        ConstantCapsuleToy.CAPSULE_TOY_SPLIT_MAX_SIZE++;

        //关闭写入流
        out.close();
    }

}
