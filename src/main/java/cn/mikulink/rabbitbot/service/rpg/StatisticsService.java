package cn.mikulink.rabbitbot.service.rpg;

import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.rpg.PlayerStatistics;
import cn.mikulink.rabbitbot.service.sys.ConfigService;
import cn.mikulink.rabbitbot.utils.BarUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * created by MikuNyanya on 2021/12/17 17:45
 * For the Reisen
 * 人物属性服务
 */
@Service
@Slf4j
public class StatisticsService {
    @Autowired
    private ConfigService configService;

    /**
     * 根据名字获取一个值
     * 所有字符的ASCII累加，取最后值不是0的两位
     * 我也不知道该拿这个值干啥
     * <p>
     * 淦，我怕不是把自己绕进去了
     * 得关注着这块算法，也许会有更好的方式
     *
     * @param name 人物名
     * @return rab值
     */
    public int getPlayerRab(String name) {
        return subASCIIStatis(StringUtil.sumASCII(name));
    }

    /**
     * 获取人物力量值
     *
     * @param name 人物名
     * @return STR
     */
    public int getPlayerSTR(String name) {
        int rab = getPlayerRab(name);
        return subASCIIStatis(rab + StringUtil.sumASCII(ConstantRPG.STR));
    }

    /**
     * 获取人物敏捷
     *
     * @param name 人物名
     * @return DEX
     */
    public int getPlayerDEX(String name) {
        int rab = getPlayerRab(name);
        return subASCIIStatis(rab + StringUtil.sumASCII(ConstantRPG.DEX));
    }

    /**
     * 获取人物智力
     *
     * @param name 人物名
     * @return INTE
     */
    public int getPlayerINTE(String name) {
        int rab = getPlayerRab(name);
        return subASCIIStatis(rab + StringUtil.sumASCII(ConstantRPG.INTE));
    }

    /**
     * 获取人物运气
     * 每天运气不同
     *
     * @param name 人物名
     * @return LUCK
     */
    public int getPlayerLUCK(String name) {
        int rab = getPlayerRab(name);
        //获取每日的全局随机数
        int rabbitRandomNum = configService.getRabbitRandomNum();
        return subASCIIStatis((rab + StringUtil.sumASCII(ConstantRPG.LUCK)) * rabbitRandomNum);
    }

    /**
     * 根据传入的名称，计算人物属性
     *
     * @param name 人物名
     * @return 人物属性
     */
    public PlayerStatistics parseStatisticsList(String name) {
        PlayerStatistics result = new PlayerStatistics();
        result.setName(name);
        result.setStr(this.getPlayerSTR(name));
        result.setDex(this.getPlayerDEX(name));
        result.setInte(this.getPlayerINTE(name));
        result.setLuck(this.getPlayerLUCK(name));
        return result;
    }

    /**
     * 转化为文本
     *
     * @param playerStatistics 人物属性对象
     * @return 属性文本
     */
    public String parseStatMsg(PlayerStatistics playerStatistics) {
        StringBuilder result = new StringBuilder();
        result.append("======").append(playerStatistics.getName()).append("=======").append("\n")
                .append("[力量] ").append(getStatisticsBar(playerStatistics.getStr())).append(" ").append(playerStatistics.getStr()).append("\n")
                .append("[敏捷] ").append(getStatisticsBar(playerStatistics.getDex())).append(" ").append(playerStatistics.getDex()).append("\n")
                .append("[智力] ").append(getStatisticsBar(playerStatistics.getInte())).append(" ").append(playerStatistics.getInte()).append("\n")
                .append("[运气] ").append(getStatisticsBar(playerStatistics.getLuck())).append(" ").append(playerStatistics.getLuck()).append("\n");
        return result.toString();
    }

    /**
     * 生成属性条
     *
     * @param statistics 属性值
     * @return 属性条
     */
    private String getStatisticsBar(int statistics) {
        //无关哪个属性，用传进来的值生成一个属性条即可
        Double full = NumberUtil.keepDecimalPoint((statistics / (ConstantRPG.Statistics_MAX * 1.0)) * 10, 1);
        return BarUtil.createStatusBar(full.intValue());
    }

    //从后向前获取一个两位数，如果结果为0，则再向前移动一位
    public int subASCIIStatis(int num) {
        if (num < 100) {
            return num;
        }
        String numStr = String.valueOf(num);

        int tempNum = 0;
        for (int i = numStr.length() - 2; i > 0; i--) {
            String tempStr = numStr.substring(i, i + 2);
            tempNum = NumberUtil.toInt(tempStr);
            if (tempNum != 0) {
                break;
            }
        }

        return tempNum;
    }
}
