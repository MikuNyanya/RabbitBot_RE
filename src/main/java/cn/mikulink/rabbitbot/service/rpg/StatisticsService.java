package cn.mikulink.rabbitbot.service.rpg;

import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * created by MikuNyanya on 2021/12/17 17:45
 * For the Reisen
 * 人物属性服务
 */
@Service
@Slf4j
public class StatisticsService {

    /**
     * 获取一个人物
     *
     * @param name
     * @return
     */
    public int getPlayerRab(String name) {
        return StringUtil.sumASCII(name);
    }

}
