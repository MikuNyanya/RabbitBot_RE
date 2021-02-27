package cn.mikulink.rabbitbot.entity.apirequest.historyToday;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class HistoryTodayEntity {
    private String today;

    private List<HistoryTodayInfoEntity> result ;
}
