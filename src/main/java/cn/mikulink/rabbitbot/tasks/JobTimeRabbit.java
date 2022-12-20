package cn.mikulink.rabbitbot.tasks;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.entity.pixiv.PixivImageInfo;
import cn.mikulink.rabbitbot.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.rabbitbot.service.*;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
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
@EnableScheduling
public class JobTimeRabbit {
    private static final Logger logger = LoggerFactory.getLogger(JobTimeRabbit.class);

    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    @Value("${proxy.check:off}")
    private String proxyCheck;
    //当前时间，方便其他地方使用
    private int hour_now = 0;

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private SetuService setuService;
    @Autowired
    private SwitchService switchService;
    @Autowired
    private WeiXinAppMsgService weiXinAppMsgService;
    @Autowired
    private ProxyService proxyService;
    @Autowired
    private PetService petService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private VirusService virusService;
    @Autowired
    private MirlKoiService mirlKoiService;

    @Scheduled(cron = "0 0 * * * ?")
    public void execute() {
        //刷新当前时间
        hour_now = DateUtil.getHour();

        //报时兔子
        timeRabbit();
        //天气
//        weatherRabbit();

        //全局随机数刷新
        rabbitRandomRefresh();

        //每日色图
        setuOnDay();

        //每日新闻简报
        newsToday();

        //微信公众平台cookie刷新
//        refreshWeixinAppCookie();

        //代理检测
//        proxyCheck();

        //养成系统数据刷新
        petRefresh();

        //新冠数据
        virusPush();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        pixivRankDay();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();

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
            String pixivSetu = ConstantCommon.common_config.get(ConstantPixiv.PIXIV_CONFIG_SETU);
            List<String> setu = null;
            PixivImageInfo pixivImageInfo = null;
            if (StringUtil.isNotEmpty(pixivSetu) && "1".equalsIgnoreCase(pixivSetu)) {
                pixivImageInfo = setuService.getSetu();
            } else {
                setu = mirlKoiService.downloadASetu(1);
            }
            //给每个群发送消息
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "setuday");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                MessageChain messageChain = null;
                if (StringUtil.isNotEmpty(pixivSetu) && "1".equalsIgnoreCase(pixivSetu)) {
                    pixivImageInfo.setSubject(groupInfo);
                    messageChain = pixivService.parsePixivImgInfoByApiInfo(pixivImageInfo);
                } else {
                    messageChain = rabbitBotService.parseMsgChainByLocalImgs(setu.get(0));
                }

                groupInfo.sendMessage(RandomUtil.rollStrFromList(ConstantPixiv.SETU_DAY_EX_MSG_List));
                groupInfo.sendMessage(messageChain);
            }
        } catch (Exception ex) {
            logger.error(ConstantPixiv.SETU_DAY_ERROR, ex);
        }
    }

    //每日简报
    private void newsToday() {
        //每天早晨10点播报，接口好像有点爆炸
        if (hour_now != 10) {
            return;
        }
        logger.info("开始获取每日简报");
        try {
//            //请求API获取今日简报
//            WeiXinAppMsgInfo weiXinAppMsgInfo = weiXinAppMsgService.getNewsTodayMsg();
//            //转化为消息链
//            MessageChain messageChain = weiXinAppMsgService.parseNewsToday(weiXinAppMsgInfo);

            MessageChain messageChain = weiXinAppMsgService.getNewsUseSourceConfig();

            //给每个群发送消息
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "newstoday");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }
                groupInfo.sendMessage(messageChain);
            }
        } catch (Exception ex) {
            //挂了就挂了吧
            logger.error("每日简报 消息发送异常" + ex.toString(), ex);
        }
    }

    //刷新微信公众平台cookie
    private void refreshWeixinAppCookie() {
        //微信公众平台那边，长时间没有操作，cookie就会失效，具体期限比较短，建议12小时内至少进行一次动作，保持cookie有效
        //每小时刷新一次
        weiXinAppMsgService.refreshCookie();
    }

    //代理检测是否可用
    private void proxyCheck() {
        if (ConstantCommon.ON.equalsIgnoreCase(proxyCheck)) {
            proxyService.proxyCheck();
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

    //养成数据刷新
    private void petRefresh() {
        try {
            //经验+1
            petService.addExp(1);

            //心情波动
            petService.heartWave();

            //数据保存到文件
            petService.writeFile();
        } catch (Exception ex) {
            logger.error("养成数据刷新执行异常", ex);
        }
    }

    //全局随机数刷新
    private void rabbitRandomRefresh() {
        if (hour_now != 0) {
            return;
        }

        int rabbitRandomNum = configService.getRabbitRandomNum();

        //生成一个新的随机数
        int randomNum = RandomUtil.roll(100);
        ConstantCommon.common_config.put("rabbitRandomNum", String.valueOf(randomNum));
        configService.refreshConfigFile();

        logger.info("全局随机数刷新,{}->{}", rabbitRandomNum, randomNum);
    }

    //新冠数据推送
    private void virusPush() {
        if (hour_now != 23) {
            return;
        }
        try {
            MessageChain msg = virusService.parseMsg(virusService.getVirusInfo());
            //给每个群发送消息
            ContactList<Group> groupList = RabbitBot.getBot().getGroups();
            for (Group groupInfo : groupList) {
                //检查功能开关
                ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "virus");
                if (!reStringSwitch.isSuccess()) {
                    continue;
                }

                groupInfo.sendMessage(msg);
            }
        } catch (Exception ex) {
            logger.error("新冠数据定时推送异常", ex);
        }
    }

}
