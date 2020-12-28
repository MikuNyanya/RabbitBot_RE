package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.filemanage.FileManagerSetu;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * create by MikuLink on 2020/12/31 15:18
 * for the Reisen
 * 来点色图
 */
@Service
public class SetuService {
    //pixiv色图操作间隔
    @Value("${setu.split.time:60000}")
    public Long SETU_PID_SPLIT_TIME = 1000L * 60;

    @Autowired
    private PixivImjadService pixivImjadService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 来张色图
     */
    public MessageChain getSetu() throws IOException {
        //获取一个pid
        Long setu_pid = NumberUtil.toLong(randOneSetuPid());

        //走pixiv图片id获取流程
        MessageChain resultChain = null;
        //是否走爬虫
        String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_USE_API);
        if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
            PixivImageInfo imageInfo = pixivService.getPixivImgInfoById(setu_pid);
            resultChain = pixivService.parsePixivImgInfoByApiInfo(imageInfo);
        } else {
            resultChain = pixivImjadService.searchPixivImgById(setu_pid);
        }
        return resultChain;
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
        //集合为空时，重新加载一次色图文件
        if (ConstantPixiv.List_SETU_PID.size() == 0) {
            FileManagerSetu.loadFile();
        }
        //随机色图
        String setuPid = RandomUtil.rollStrFromList(ConstantPixiv.List_SETU_PID);
        //删除这个沙壤土，实现伪随机
        ConstantPixiv.List_SETU_PID.remove(setuPid);
        //元素少于1/6的时候，重新加载
        if (ConstantPixiv.List_SETU_PID.size() < ConstantPixiv.SETU_PID_LIST_MAX_SIZE / 6) {
            FileManagerSetu.loadFile();
        }
        return setuPid;
    }

}
