package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.filemanage.FileManagerSetu;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.message.data.MessageChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * create by MikuLink on 2020/12/31 15:18
 * for the Reisen
 * 来点色图
 */
@Service
public class SetuService {
    @Autowired
    private PixivImjadService pixivImjadService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 来张色图
     *
     */
    public MessageChain getSetu() throws IOException {
        //获取一个pid
        Long setu_pid = NumberUtil.toLong(randOneSetuPid());

        //走pixiv图片id获取流程
        MessageChain resultChain = null;
        //是否走爬虫
        String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantImage.PIXIV_CONFIG_USE_API);
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
        String timeCheck = rabbitBotService.commandTimeSplitCheck(ConstantFile.SETU_PID_SPLIT_MAP, userId, userNick,
                ConstantFile.SETU_PID_SPLIT_TIME, RandomUtil.rollStrFromList(ConstantFile.SETU_SPLIT_ERROR_LIST));
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
    public String randOneSetuPid() {
        //集合为空时，重新加载一次色图文件
        if (ConstantFile.List_SETU_PID.size() == 0) {
            FileManagerSetu.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
        }
        //随机色图
        String setuPid = RandomUtil.rollStrFromList(ConstantFile.List_SETU_PID);
        //删除这个沙壤土，实现伪随机
        ConstantFile.List_SETU_PID.remove(setuPid);
        //元素少于1/6的时候，重新加载
        if (ConstantFile.List_SETU_PID.size() < ConstantFile.SETU_PID_SPLIT_TIME / 6) {
            FileManagerSetu.doCommand(ConstantFile.FILE_COMMAND_LOAD, null);
        }
        return setuPid;
    }

}
