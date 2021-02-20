package cn.mikulink.rabbitbot.quartzs;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.command.group.RPCommand;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.service.PixivService;
import cn.mikulink.rabbitbot.service.SetuService;
import cn.mikulink.rabbitbot.service.SwitchService;
import cn.mikulink.rabbitbot.service.WeatherService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
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
@Component
public class JobTimeRabbit {
    private static final Logger logger = LoggerFactory.getLogger(JobTimeRabbit.class);

    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    //当前时间，方便其他地方使用
    private int hour_now = 0;

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private SetuService setuService;
    @Autowired
    private SwitchService switchService;

    public void execute() {
        //刷新当前时间
        hour_now = DateUtil.getHour();

        //报时兔子
        timeRabbit();
        //天气
//        weatherRabbit();

        //0点清理
        //RP缓存
        clearRPMap();

        //每日色图
        setuOnDay();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        pixivRankDay();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();
        //大晚上的就不发了
        int hour = DateUtil.getHour();

        if (hour < 8) {
            return;
        }

        //群报时，时间间隔交给定时器，这里返回值取当前时间即可
        String msg = String.format("这里是%s报时：%s%s", rabbit_bot_name, DateUtil.toString(new Date()), msgEx);
        try {
            //给每个群发送报时
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "sj");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                groupInfo.sendMessage(msg);
            }
        } catch (Exception ex) {
            logger.error("报时兔子 消息发送异常" + ex.toString(), ex);
        }
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx() {
        switch (hour_now) {
            //半夜0点
            case 0:
                return ConstantCommon.NEXT_LINE + "新的一天开始啦ヽ(#`Д´)ノ";
            //凌晨4点
            case 4:
                return ConstantCommon.NEXT_LINE + "还有人活着嘛~";
            //早上7点
            case 8:
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
        if (hour_now != 0) {
            return;
        }

        RPCommand.MAP_RP.clear();
        logger.info("每日人品缓存已清除");
    }

    //天气兔子
    private void weatherRabbit() {
        //每天9点，13点，19点进行自动播报
        if (hour_now != 9 && hour_now != 13 && hour_now != 19) {
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

    //每日色图
    private void setuOnDay() {
        if (hour_now != 20) {
            return;
        }
        try {
            //目前默认展示r18
            PixivImageInfo pixivImageInfo = setuService.getSetu();
            MessageChain messageChain = pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
            //给每个群发送消息
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "setuday");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                groupInfo.sendMessage(RandomUtil.rollStrFromList(ConstantPixiv.SETU_DAY_EX_MSG_List));
                groupInfo.sendMessage(messageChain);
            }
        } catch (Exception ex) {
            logger.error(ConstantPixiv.SETU_DAY_ERROR + ex.toString(), ex);
        }
    }

    //P站日榜兔子
    private void pixivRankDay() {
        //每天晚上18点推送日榜信息
        if (hour_now != 18) {
            return;
        }

        try {
            //获取日榜
            List<PixivRankImageInfo> imageList = pixivService.getPixivIllustRank(ConstantPixiv.PIXIV_IMAGE_PAGESIZE);
            for (PixivRankImageInfo imageInfo : imageList) {
                //上传图片
                MessageChain resultChain = pixivService.parsePixivImgInfoByApiInfo(imageInfo);

                //给每个群发送消息
                ContactList<Group> groupList = RabbitBot.getBot().getGroups();
                for (Group groupInfo : groupList) {
                    //检查功能开关
                    ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "pixivRank");
                    if (!reStringSwitch.isSuccess()) {
                        continue;
                    }
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
