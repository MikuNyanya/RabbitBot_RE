package cn.mikulink.rabbitbot.tasks;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.bot.RabbitBotSender;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantCommon;
import cn.mikulink.rabbitbot.constant.ConstantPixiv;
import cn.mikulink.rabbitbot.modules.pixiv.PixivService;
import cn.mikulink.rabbitbot.modules.pixiv.entity.PixivRankImageInfo;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoStatuses;
import cn.mikulink.rabbitbot.entity.apirequest.weibo.InfoWeiboHomeTimeline;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.GroupInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.*;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.service.sys.ProxyService;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.MessageChain;
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
@Slf4j
@Component
@EnableScheduling
public class JobTimeRabbit {

    //兔叽
    @Value("${bot.name.cn:兔叽}")
    public String rabbit_bot_name;
    @Value("${proxy.check:false}")
    private boolean proxyCheck;
    @Value("${bot.jobOpen:false}")
    private boolean jobOpen;
    //当前时间，方便其他地方使用
    private int hour_now = 0;

    @Autowired
    private RabbitBotService rabbitBotService;
    @Autowired
    private RabbitBotSender rabbitBotSender;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private PixivService pixivService;
    @Autowired
    private NewsDayService newsDayService;
    @Autowired
    private ProxyService proxyService;
    @Autowired
    private PetService petService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private MirlKoiService mirlKoiService;
    @Autowired
    private WeiboNewsService weiboNewsService;

    @Scheduled(cron = "0 1 * * * ?")
    public void execute() {
        if (!jobOpen) {
            return;
        }

        //刷新当前时间
        hour_now = DateUtil.getHour();

        //全局随机数刷新
        rabbitRandomRefresh();

        //报时兔子
//        timeRabbit();
        //天气
//        weatherRabbit();

        //每日色图
        setuOnDay();

        //每日新闻简报
        newsToday();

        //代理检测
//        proxyCheck();

        //养成系统数据刷新
//        petRefresh();

        //pixiv日榜，最好放在最后执行，要下载图片
        //也可以另起一个线程，但我懒
        //pixivRankDay();

        //微博最新消息
        weiboNews();
    }

    //报时兔子
    private void timeRabbit() {
        //附加短语
        String msgEx = getMsgEx();

        //群报时，时间间隔交给定时器，这里返回值取当前时间即可
        String msg = String.format("这里是%s报时：%s%s", rabbit_bot_name, DateUtil.toString(new Date()), msgEx);
        try {
            //给每个群发送报时
            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageImage(msg);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }
        } catch (Exception ex) {
            log.error("报时兔子 消息发送异常" + ex.getMessage(), ex);
        }
    }

    //获取附加短语，可以放一些彩蛋性质的东西，会附带在报时消息尾部
    private String getMsgEx() {
        return switch (hour_now) {
            //半夜0点
            case 0 -> ConstantCommon.NEXT_LINE + "新的一天开始啦ヽ(#`Д´)ノ";
            //凌晨4点
            case 4 -> ConstantCommon.NEXT_LINE + "还有人活着嘛~";
            //早上7点
            case 7 -> ConstantCommon.NEXT_LINE + "早上好,该起床了哦~~";
            //中午11点
            case 11 -> ConstantCommon.NEXT_LINE + "开始做饭了吗，外卖点了吗";
            //中午12点
            case 12 -> ConstantCommon.NEXT_LINE + "午安，该是吃午饭的时间了";
            //下午18点
            case 18 -> ConstantCommon.NEXT_LINE + "到了下班的时间啦!";
            //晚上23点
            case 23 -> ConstantCommon.NEXT_LINE + "已经很晚了，早点休息哦~~";
            default -> "";
        };
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
            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageImage(msg);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }
        } catch (Exception ioEx) {
            log.error("天气兔子发生异常:" + ioEx.getMessage(), ioEx);
        }
    }

    //每日色图
    private void setuOnDay() {
        if (hour_now != 20) {
            return;
        }
        try {
            //获取一张随机色图
            String setuUrl = mirlKoiService.getASetu();
            if (null == setuUrl) {
                return;
            }

            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageImage(setuUrl);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }

        } catch (Exception ex) {
            log.error(ConstantPixiv.SETU_DAY_ERROR, ex);
        }
    }

    //每日简报
    private void newsToday() {
        //每天早晨10点播报
        if (hour_now != 10) {
            return;
        }
        log.info("开始获取每日简报");
        try {
            String newsImgUrl = newsDayService.getZaobNews();

            //给每个群发送消息
            MessageInfo messageInfo = RabbitBotMessageBuilder.createMessageImage(newsImgUrl);
            List<GroupInfo> groupList = rabbitBotService.getGroupList();
            for (GroupInfo groupInfo : groupList) {
                rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
            }
        } catch (Exception ex) {
            //挂了就挂了吧
            log.error("每日简报 消息发送异常" + ex.getMessage(), ex);
        }
    }

    //代理检测是否可用
    private void proxyCheck() {
        if (proxyCheck) {
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
//                MessageChain resultChain = pixivService.parsePixivImgInfoByApiInfo(imageInfo);

                //给每个群发送消息
//                ContactList<Group> groupList = RabbitBot.getBot().getGroups();
//                for (Group groupInfo : groupList) {
//                    //检查功能开关
//                    ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "pixivRank");
//                    if (!reStringSwitch.isSuccess()) {
//                        continue;
//                    }
//                    groupInfo.sendMessage(resultChain);
//
//                    //每个群之间间隔半秒意思下
//                    Thread.sleep(500);
//                }

                //每张图片之间间隔5秒
                Thread.sleep(1000L * 2);
            }
        } catch (Exception ex) {
            log.error(ConstantPixiv.PIXIV_IMAGE_RANK_JOB_ERROR + ex.getMessage(), ex);
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
            log.error("养成数据刷新执行异常", ex);
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

        log.info("全局随机数刷新,{}->{}", rabbitRandomNum, randomNum);
    }


    //微信最新消息
    private void weiboNews() {
        log.info("====开始执行微博推送====");
        try {
            //执行微博消息推送
            //获取接口返回
            InfoWeiboHomeTimeline weiboNews = weiboNewsService.getWeiboNews(10);

            //刷新最后推文标识，但如果一次请求中没有获取到新数据，since_id会为0
            Long sinceId = weiboNews.getSince_id();
            if (0 != sinceId) {
                log.info(String.format("微博sinceId刷新：[%s]->[%s]", ConstantCommon.common_config.get("sinceId"), sinceId));
                //刷新sinceId配置
                ConstantCommon.common_config.put("sinceId", String.valueOf(sinceId));
                //更新配置文件
                configService.refreshConfigFile();
            }

            //获取微博内容
            List<InfoStatuses> statuses = weiboNews.getStatuses();

            if (null == statuses || statuses.size() == 0) {
                return;
            }

            //发送微博
            for (InfoStatuses info : statuses) {
                //解析微博报文
                List<cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain> msgChain = weiboNewsService.parseWeiboBody(info);
                if (null != info.getRetweeted_status()) {
                    //追加被转发的微博消息
                    msgChain.addAll(weiboNewsService.parseWeiboBody(info.getRetweeted_status(), true));
                }

                //每个群发送微博推送
                MessageInfo messageInfo = new MessageInfo(msgChain);
                List<GroupInfo> groupList = rabbitBotService.getGroupList();
                for (GroupInfo groupInfo : groupList) {
                    rabbitBotSender.sendGroupMessage(groupInfo.getGroupId(), messageInfo.getMessage());
                }
            }


        } catch (Exception ex) {
            log.error("微博消息推送执行异常:" + ex.getMessage(), ex);
        }
    }
}
