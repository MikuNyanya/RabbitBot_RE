package cn.mikulink.rabbitbot.service.rpg;

import cn.mikulink.rabbitbot.constant.ConstantRPG;
import cn.mikulink.rabbitbot.entity.rpg.KickingASSInfo;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * created by MikuNyanya on 2021/12/24 9:37
 * For the Reisen
 * 群友互殴模块
 * <p>
 * 与人斗其乐无穷哈？
 * 可以写成两人参与，但是受限于qq群的界面，体验可能很差
 * 所以写成1人参与打AI，当然本质没区别，核心是属性对抗，与掷骰子
 * 同一个群同一时间只能开启一场互殴，为了防止刷屏体验过差啥的
 */
@Service
@Slf4j
public class KickASSService {
    //以群区分，保存当前互殴信息
    //同一时间同一个群只能开启异常互殴
    public static Map<Long, KickingASSInfo> ROUND_NOW = new HashMap<>();

    @Autowired
    private CharacterStatsService characterStatsService;


    /**
     * 在群里发起一场互殴
     */
    public MessageChain kickASSStart(KickingASSInfo info) {
        MessageChain result = MessageUtils.newChain();

        //如果该群正在互殴，不允许开启另一场互殴
        if (ROUND_NOW.containsKey(info.getGroupId())) {
            result = result.plus(ConstantRPG.KICKASS_STARTED);
            return result;
        }

        info.setStartTime(System.currentTimeMillis());
        info.setOperationLastTime(info.getStartTime());
        //初始化hp
        info.setPlayHealthOne(200);
        info.setPlayHealthTwo(200);

        ROUND_NOW.put(info.getGroupId(), info);

        //返回互殴信息 todo 记得优化
        //有的群友要干自己
        result = result.plus("群友互殴初始化完毕，描述待定\n\n可以使用的攻击：" +
                "\n1.敲脑阔 2.给他一拳 3.速攻猫猫拳 4.飞踢 5.尝试切他中路 6.聊天流" +
                "\n每个攻击有对应属性，比如猫猫拳属于敏捷对抗" +
                "\n使用指令：.vs 3  或者 .vs 速攻猫猫拳");
        return result;
    }

    /**
     * 通用行动，力量
     *
     * @param groupId 群号
     * @return 行动结果
     */
    public MessageChain actionSTR(Long groupId) {
        MessageChain result = MessageUtils.newChain();
        if (!ROUND_NOW.containsKey(groupId) || null == ROUND_NOW.get(groupId)) {
            result = result.plus(ConstantRPG.KICKASS_404);
            return result;
        }

        KickingASSInfo info = ROUND_NOW.get(groupId);

        //计算行动结果
        //使用双方的力量+运气进行掷骰
        int strP1 = characterStatsService.getPlayerSTR(info.getPlayNameOne());
        int luckP1 = characterStatsService.getPlayerLUCK(info.getPlayNameOne());

        int strP2 = characterStatsService.getPlayerSTR(info.getPlayNameTwo());
        int luckP2 = characterStatsService.getPlayerLUCK(info.getPlayNameTwo());

        int rollP1 = characterStatsService.rollD(strP1, luckP1);
        int rollP2 = characterStatsService.rollD(strP2, luckP2);

        //回合结束时hp 根据掷点造成伤害
        int hpUpdateP1 = info.getPlayHealthOne() - rollP2;
        int hpUpdateP2 = info.getPlayHealthTwo() - rollP1;

        result = result.plus("===").plus(info.getPlayNameOne()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthOne())).plus("\n")
                .plus("掷点:").plus("力量(").plus(String.valueOf(strP1)).plus(") 运气(").plus(String.valueOf(luckP1)).plus(")=").plus(String.valueOf(rollP1)).plus("\n")
                .plus("===").plus(info.getPlayNameTwo()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthTwo())).plus("\n")
                .plus("掷点:").plus("力量(").plus(String.valueOf(strP2)).plus(") 运气(").plus(String.valueOf(luckP2)).plus(")=").plus(String.valueOf(rollP2)).plus("\n")
                .plus("=======================").plus("\n")
                .plus("这里随便整点啥描述然后展示剩余生命").plus("\n")
                .plus(info.getPlayNameOne()).plus(":").plus(String.valueOf(hpUpdateP1)).plus("\n")
                .plus(info.getPlayNameTwo()).plus(":").plus(String.valueOf(hpUpdateP2));

        //刷新回合结果
        info.setPlayHealthOne(hpUpdateP1);
        info.setPlayHealthTwo(hpUpdateP2);
        ROUND_NOW.put(groupId, info);

        //根据hp进行胜负判定
        MessageChain winCheck = winCheck(groupId, info);
        if (null != winCheck) {
            result = result.plus("\n").plus(winCheck);
        }

        return result;
    }

    /**
     * 通用行动，敏捷
     *
     * @param groupId 群号
     * @return 行动结果
     */
    public MessageChain actionDEX(Long groupId) {
        MessageChain result = MessageUtils.newChain();
        if (!ROUND_NOW.containsKey(groupId) || null == ROUND_NOW.get(groupId)) {
            result = result.plus(ConstantRPG.KICKASS_404);
            return result;
        }

        KickingASSInfo info = ROUND_NOW.get(groupId);

        //计算行动结果
        //使用双方的力量+运气进行掷骰
        int dexP1 = characterStatsService.getPlayerDEX(info.getPlayNameOne());
        int luckP1 = characterStatsService.getPlayerLUCK(info.getPlayNameOne());

        int dexP2 = characterStatsService.getPlayerDEX(info.getPlayNameTwo());
        int luckP2 = characterStatsService.getPlayerLUCK(info.getPlayNameTwo());

        int rollP1 = characterStatsService.rollD(dexP1, luckP1);
        int rollP2 = characterStatsService.rollD(dexP2, luckP2);

        //回合结束时hp 根据掷点造成伤害
        int hpUpdateP1 = info.getPlayHealthOne() - rollP2;
        int hpUpdateP2 = info.getPlayHealthTwo() - rollP1;

        result = result.plus("===").plus(info.getPlayNameOne()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthOne())).plus("\n")
                .plus("掷点:").plus("敏捷(").plus(String.valueOf(dexP1)).plus(") 运气(").plus(String.valueOf(luckP1)).plus(")=").plus(String.valueOf(rollP1)).plus("\n")
                .plus("===").plus(info.getPlayNameTwo()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthTwo())).plus("\n")
                .plus("掷点:").plus("敏捷(").plus(String.valueOf(dexP2)).plus(") 运气(").plus(String.valueOf(luckP2)).plus(")=").plus(String.valueOf(rollP2)).plus("\n")
                .plus("=======================").plus("\n")
                .plus("这里随便整点啥描述然后展示剩余生命").plus("\n")
                .plus(info.getPlayNameOne()).plus(":").plus(String.valueOf(hpUpdateP1)).plus("\n")
                .plus(info.getPlayNameTwo()).plus(":").plus(String.valueOf(hpUpdateP2));

        //刷新回合结果
        info.setPlayHealthOne(hpUpdateP1);
        info.setPlayHealthTwo(hpUpdateP2);
        ROUND_NOW.put(groupId, info);

        //根据hp进行胜负判定
        MessageChain winCheck = winCheck(groupId, info);
        if (null != winCheck) {
            result = result.plus("\n").plus(winCheck);
        }

        return result;
    }

    /**
     * 通用行动，智力
     *
     * @param groupId 群号
     * @return 行动结果
     */
    public MessageChain actionINTE(Long groupId) {
        MessageChain result = MessageUtils.newChain();
        if (!ROUND_NOW.containsKey(groupId) || null == ROUND_NOW.get(groupId)) {
            result = result.plus(ConstantRPG.KICKASS_404);
            return result;
        }

        KickingASSInfo info = ROUND_NOW.get(groupId);

        //计算行动结果
        //使用双方的力量+运气进行掷骰
        int inteP1 = characterStatsService.getPlayerINTE(info.getPlayNameOne());
        int luckP1 = characterStatsService.getPlayerLUCK(info.getPlayNameOne());

        int inteP2 = characterStatsService.getPlayerINTE(info.getPlayNameTwo());
        int luckP2 = characterStatsService.getPlayerLUCK(info.getPlayNameTwo());

        int rollP1 = characterStatsService.rollD(inteP1, luckP1);
        int rollP2 = characterStatsService.rollD(inteP2, luckP2);

        //回合结束时hp 根据掷点造成伤害
        int hpUpdateP1 = info.getPlayHealthOne() - rollP2;
        int hpUpdateP2 = info.getPlayHealthTwo() - rollP1;

        result = result.plus("===").plus(info.getPlayNameOne()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthOne())).plus("\n")
                .plus("掷点:").plus("智力(").plus(String.valueOf(inteP1)).plus(") 运气(").plus(String.valueOf(luckP1)).plus(")=").plus(String.valueOf(rollP1)).plus("\n")
                .plus("===").plus(info.getPlayNameTwo()).plus("===").plus("\n")
                .plus("HP:").plus(String.valueOf(info.getPlayHealthTwo())).plus("\n")
                .plus("掷点:").plus("智力(").plus(String.valueOf(inteP2)).plus(") 运气(").plus(String.valueOf(luckP2)).plus(")=").plus(String.valueOf(rollP2)).plus("\n")
                .plus("=======================").plus("\n")
                .plus("这里随便整点啥描述然后展示剩余生命").plus("\n")
                .plus(info.getPlayNameOne()).plus(":").plus(String.valueOf(hpUpdateP1)).plus("\n")
                .plus(info.getPlayNameTwo()).plus(":").plus(String.valueOf(hpUpdateP2));

        //刷新回合结果
        info.setPlayHealthOne(hpUpdateP1);
        info.setPlayHealthTwo(hpUpdateP2);
        ROUND_NOW.put(groupId, info);

        //根据hp进行胜负判定
        MessageChain winCheck = winCheck(groupId, info);
        if (null != winCheck) {
            result = result.plus("\n").plus(winCheck);
        }

        return result;
    }

    /**
     * 胜负检测
     *
     * @param groupId 群id
     * @param info    群殴信息
     * @return 拼装好的结果消息链
     */
    public MessageChain winCheck(Long groupId, KickingASSInfo info) {
        MessageChain result = MessageUtils.newChain();
        int hpP1 = info.getPlayHealthOne();
        int hpP2 = info.getPlayHealthTwo();
        boolean clearRound = true;

        if (hpP1 <= 0 && hpP2 <= 0) {
            result = result.plus("平局");
        } else if (hpP1 <= 0) {
            result = result.plus(info.getPlayNameTwo()).plus(" 获胜");
        } else if (hpP2 <= 0) {
            result = result.plus(info.getPlayNameOne()).plus(" 获胜");
        } else {
            //胜负未分
            result = result.plus("互殴还在继续");
            clearRound = false;
        }

        if (clearRound) {
            ROUND_NOW.remove(groupId);
        }
        return result;
    }

}
