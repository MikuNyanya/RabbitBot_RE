package cn.mikulink.rabbitbot.quartzs.bak.jobs;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.command.group.RPCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.service.PixivImjadService;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.service.RabbitBotService;
import cn.mikulink.rabbitbot.service.WeatherService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * create by MikuLink on 2019/12/3 12:58
 * for the Reisen
 * <p>
 * 1小时执行一次的定时器
 */
@Component("bak_jobTimeRabbit")
public class JobTimeRabbit implements Job {
    private static final Logger logger = LoggerFactory.getLogger(JobTimeRabbit.class);

    @Value("${bot.name.cn:兔叽}")
    private String bot_name_cn;

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PixivImjadService pixivImjadService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private RabbitBotService rabbitBotService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //报时兔子
        timeRabbit();
        //天气
//        weatherRabbit();

        //0点清理
        //RP缓存
        clearRPMap();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        pixivRankDay();
    }

    public void execute() {
        //报时兔子
        timeRabbit();
        //天气
//        weatherRabbit();

        //0点清理
        //RP缓存
        clearRPMap();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        pixivRankDay();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();

        //群报时，时间间隔交给定时器，这里返回值取当前时间即可
        String msg = String.format("这里是%s报时：%s%s", bot_name_cn, DateUtil.toString(new Date()), msgEx);
        try {
            //给每个群发送报时
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                groupInfo.sendMessage(msg);
            }
        } catch (Exception ex) {
            logger.error("报时兔子 消息发送异常" + ex.toString(), ex);
        }
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx() {
        int hour = DateUtil.getHour();

        switch (hour) {
            //半夜0点
            case 0:
                return ConstantCommon.NEXT_LINE + "新的一天开始啦ヽ(#`Д´)ノ";
            //凌晨4点
            case 4:
                return ConstantCommon.NEXT_LINE + "还有人活着嘛~";
            //早上7点
            case 7:
                return ConstantCommon.NEXT_LINE + "早上好,该起床了哦~~";
            //中午11点
            case 11:
                return ConstantCommon.NEXT_LINE + "开始做饭了吗，外卖点了吗";
            //中午12点
            case 12:
                return ConstantCommon.NEXT_LINE + "午安，该是吃午饭的时间了";
            //下午18点
            case 18:
                return ConstantCommon.NEXT_LINE + "到了下班的时间啦!";
            //晚上23点
            case 23:
                return ConstantCommon.NEXT_LINE + "已经很晚了，早点休息哦~~";
        }
        return "";
    }

    //清除RP缓存，不然第二天RP值不会重置
    private void clearRPMap() {
        //0点清除
        if (DateUtil.getHour() != 0) {
            return;
        }

        RPCommand.MAP_RP.clear();
        logger.info("每日人品缓存已清除");
    }

    //天气兔子
    private void weatherRabbit() {
        //每天9点，13点，19点进行自动播报
        int hour = DateUtil.getHour();
        if (hour != 9 && hour != 13 && hour != 19) {
            return;
        }

        try {
            //获取天气情况
            String msg = weatherService.getWeatherByCityName("宿州市");

            //给每个群发送天气
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                groupInfo.sendMessage(msg);
            }
        } catch (Exception ioEx) {
            logger.error("天气兔子发生异常:" + ioEx.toString(), ioEx);
        }
    }

    //P站日榜兔子
    private void pixivRankDay() {
        //每天晚上20点推送日榜信息，不然7点我还没到家，背着兔叽在路上没网络
        int hour = DateUtil.getHour();
        if (hour != 20) {
            return;
        }

        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = null;
            //是否走爬虫
            String pixiv_config_use_api = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_USE_API);
            if (ConstantImage.OFF.equalsIgnoreCase(pixiv_config_use_api)) {
                imageList = pixivService.getPixivIllustRank(ConstantPixiv.PIXIV_IMAGE_PAGESIZE);
            } else {
                imageList = pixivImjadService.getPixivIllustRank(1, ConstantPixiv.PIXIV_IMAGE_PAGESIZE);
            }
            for (PixivRankImageInfo imageInfo : imageList) {
                //上传图片
                MessageChain resultChain = pixivService.parsePixivImgInfoByApiInfo(imageInfo);

                //给每个群发送消息
                ContactList<Group> groupList = RabbitBot.getBot().getGroups();
                for (Group groupInfo : groupList) {
                    groupInfo.sendMessage(resultChain);

                    //每个群之间间隔半秒意思下
                    Thread.sleep(500);
                }

                //每张图片之间间隔5秒
                Thread.sleep(1000L * 2);
            }
        } catch (Exception ex) {
            logger.error(ConstantPixiv.PIXIV_IMAGE_RANK_JOB_ERROR + ex.toString(), ex);
        }
    }
}
