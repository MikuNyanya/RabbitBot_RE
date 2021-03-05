package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.historyToday.HistoryTodayGet;
import cn.mikulink.rabbitbot.apirequest.news.NewsTodayGet;
import cn.mikulink.rabbitbot.entity.apirequest.news.NewsTodayEntity;
import cn.mikulink.rabbitbot.utils.DateUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.ProgressUtils;
import net.mamoe.mirai.message.data.Message;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.PlainText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 生成每日新闻的服务
 */
@Service
public class NewsTodayService {

    private static final Logger logger = LoggerFactory.getLogger(NewsTodayService.class);

    public NewsTodayEntity getNewsToday() throws IOException {
        NewsTodayGet request = new NewsTodayGet();
        request.doRequest();
        return request.getNewsTodayEntity();
    }

    public Message parseNewsToday(NewsTodayEntity newsTodayEntity) {

        StringBuilder resultStr = new StringBuilder();
        resultStr.append("【每日简报】\n").append(newsTodayEntity.getData().getCalendar().getCMonth()).
                append("月").append(newsTodayEntity.getData().getCalendar().getCDay()).
                append("日").append(newsTodayEntity.getData().getCalendar().getNcWeek())
                .append(",农历").append(newsTodayEntity.getData().getCalendar().getMonthCn())
                .append(newsTodayEntity.getData().getCalendar().getDayCn())
                .append("\n ☀\n");
        int index = 1;
        for (NewsTodayEntity.DataDTO.NewsListDTO dto : newsTodayEntity.getData().getNewsList()) {
            resultStr.append(index);
            resultStr.append(". ");
            resultStr.append(dto.getTitle());
            resultStr.append("\n");
            index++;
        }
        resultStr.append("【名言】\n");
        resultStr.append(newsTodayEntity.getData().getSentence().getSentence());
        resultStr.append("--------");
        resultStr.append(newsTodayEntity.getData().getSentence().getAuthor());
        resultStr.append("\n【今年进度条】\n");
        resultStr.append(ProgressUtils.produce());
        resultStr.append("\n你已使用");
        resultStr.append(NumberUtil.calculateUtil(
                BigDecimal.valueOf((365 - DateUtil.daysToNewYearDay())),BigDecimal.valueOf(365)));


        return new PlainText(resultStr.toString());
    }

}
