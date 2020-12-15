package cn.mikulink.service.greetings;

import cn.mikulink.utils.DateUtil;
import cn.mikulink.utils.RandomUtil;

import java.util.List;

/**
 * create by MikuLink on 2020/1/17 14:40
 * for the Reisen
 * 时间问候，父类
 * 把一天切分成一个时间段，根据不同时间段进行不同的回复
 * 而子类则需要实现这些回复
 */
public abstract class GreetingsBase {

    /**
     * 根据当前系统关键词获取回复
     *
     * @return 回复
     */
    public String getGreetingsByTime() {
        List<String> resultList = null;

        int hour = DateUtil.getHour();
        switch (hour) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                resultList = beforeDawn();
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                resultList = morning();
                break;
            case 11:
            case 12:
            case 13:
                resultList = noon();
                break;
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
                resultList = afternoon();
                break;
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 0:
            case 1:
                resultList = night();
                break;
        }

        if (null == resultList) {
            return "";
        }
        //随机获取一个回复
        return RandomUtil.rollStrFromList(resultList);
    }

    //凌晨2点-6点
    protected abstract List<String> beforeDawn();

    //早上7点-10点
    protected abstract List<String> morning();

    //中午11-13
    protected abstract List<String> noon();

    //下午14-18
    protected abstract List<String> afternoon();

    //晚上19-次日01
    protected abstract List<String> night();
}
