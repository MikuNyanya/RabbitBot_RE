package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.utils.*;
import com.alibaba.fastjson2.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/12/31 15:18
 * for the Reisen
 * 来点色图
 */
@Service
public class SetuService {
    private static final Logger logger = LoggerFactory.getLogger(SetuService.class);

    //pixiv色图操作间隔
    @Value("${setu.split.time:60000}")
    public Long SETU_PID_SPLIT_TIME = 1000L * 60;
    @Value("${file.path.data:}")
    private String dataPath;

    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 获取资源文件路径
     */
    public String getFilePath() {
        return dataPath + File.separator + "files" + File.separator + "pixiv_setu_pids.txt";
    }

    /**
     * 来张色图
     */
    public PixivImageInfo getSetu() throws IOException {
        //获取一个pid
        Long setu_pid = NumberUtil.toLong(randOneSetuPid());

        //走pixiv图片id获取流程
        return pixivService.getPixivImgInfoById(setu_pid);
    }


    /**
     * 检查色图操作间隔
     *
     * @param userId   企鹅号
     * @param userNick 昵称
     * @return 是否可以操作，和提示语什么的
     */
    public ReString setuTimeCheck(Long userId, String userNick) {
        ReString reString = new ReString(true);
        //操作间隔判断
        String timeCheck = rabbitBotService.commandTimeSplitCheck(ConstantPixiv.SETU_PID_SPLIT_MAP, userId, userNick,
                SETU_PID_SPLIT_TIME, RandomUtil.rollStrFromList(ConstantPixiv.SETU_SPLIT_ERROR_LIST));
        if (StringUtil.isNotEmpty(timeCheck)) {
            reString = new ReString(false, timeCheck);
        }
        return reString;
    }

    /**
     * 从列表里选取一张色图pid
     *
     * @return 色图pid
     */
    public String randOneSetuPid() throws IOException {
        //元素少于1/6的时候，重新加载
        if (CollectionUtil.isEmpty(ConstantPixiv.List_SETU_PID)
                || ConstantPixiv.List_SETU_PID.size() < ConstantPixiv.SETU_PID_LIST_MAX_SIZE / 6) {
            this.loadFile();
        }
        //随机色图
        String setuPid = RandomUtil.rollStrFromList(ConstantPixiv.List_SETU_PID);
        //删除这个色图，实现伪随机
        ConstantPixiv.List_SETU_PID.remove(setuPid);

        return setuPid;
    }

    /**
     * 添加一些色图
     *
     * @param pidList 色图pid列表
     */
    public void addSetu(List<String> pidList) {
        //写入文件，顺便判重
        try {
            pidList = this.writeFile(pidList);
        } catch (Exception ex) {
            //异常直接舍弃所有，允许这类损失
            logger.error("SetuService addSetu error,pidList:{}", JSONObject.toJSONString(pidList), ex);
        }

        //加入当前内存列表里
        ConstantPixiv.List_SETU_PID.addAll(pidList);
        //刷新最大元素数目
        ConstantPixiv.SETU_PID_LIST_MAX_SIZE += pidList.size();
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public void loadFile() throws IOException {
        String path = this.getFilePath();
        File setuFile = FileUtil.fileCheck(path);

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
        //刷新最大元素数目
        ConstantPixiv.SETU_PID_LIST_MAX_SIZE = ConstantPixiv.List_SETU_PID.size();
        //关闭读取器
        reader.close();
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    public List<String> writeFile(List<String> pids) throws IOException {
        String path = this.getFilePath();

        //覆写原本配置
        //先读取出所有pid，判重后直接覆写文件
        //如果超出2W，真要考虑接DB了
        List<String> tempSetuPid = new ArrayList<>();
        File setuFile = FileUtil.fileCheck(path);
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(setuFile));
        //逐行读取文件
        String setuPid = null;
        while ((setuPid = reader.readLine()) != null) {
            //过滤掉空行
            if (setuPid.length() <= 0) continue;
            tempSetuPid.add(setuPid);
        }
        //关闭读取器
        reader.close();

        //过滤重复
        List<String> tempNewPid = new ArrayList<>();
        for (String pidStr : pids) {
            if (tempSetuPid.contains(pidStr)) {
                continue;
            }
            tempSetuPid.add(pidStr);
            tempNewPid.add(pidStr);
        }
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false)));
        for (String pidStr : tempSetuPid) {
            out.write("\r\n" + pidStr);
        }
        //关闭写入流
        out.close();

        //返回删选后的pid列表
        return tempNewPid;
    }
}
