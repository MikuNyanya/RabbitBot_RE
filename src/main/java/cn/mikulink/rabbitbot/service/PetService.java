package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantPet;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import org.springframework.stereotype.Service;

/**
 * created by MikuNyanya on 2021/9/15 18:04
 * For the Reisen
 * 养成相关服务
 */
@Service
public class PetService {
    /**
     * 增加经验
     */
    public void addExp(int addExp) {
        //增加经验
        ConstantPet.petInfo.setExp(ConstantPet.petInfo.getExp() + addExp);

        //如果增加后经验足够升级，则执行升级
        if (ConstantPet.petInfo.getExp() >= ConstantPet.petInfo.getNextLevelExp()) {
            levelUp();
        }
    }

    /**
     * 清空经验
     */
    public void clearExp() {
        ConstantPet.petInfo.setExp(0);
    }

    /**
     * 升级操作
     */
    public void levelUp() {
        //经验不足则不执行升级
        if (ConstantPet.petInfo.getExp() < ConstantPet.petInfo.getNextLevelExp()) {
            return;
        }
        //减少经验
        ConstantPet.petInfo.setExp(ConstantPet.petInfo.getExp() - ConstantPet.petInfo.getNextLevelExp());

        //升1级
        ConstantPet.petInfo.setLevel(ConstantPet.petInfo.getLevel() + 1);

        //递归升级，直到经验不足
        levelUp();
    }

    /**
     * 生成当前经验条
     *
     * @return 经验条
     */
    public String getExpBar() {
        //计算出当前经验和下一级的比例,向下取整
        Double full = NumberUtil.keepDecimalPoint((ConstantPet.petInfo.getExp() / (ConstantPet.petInfo.getNextLevelExp() * 1.0)) * 10, 1);
        return this.createStatusBar(full.intValue());
    }

    /**
     * 生成当前心情条
     *
     * @return 心情条
     */
    public String getHeartBar() {
        //计算出当前心情和最大心情的比例,向下取整
        Double full = NumberUtil.keepDecimalPoint((ConstantPet.petInfo.getHeart() / (ConstantPet.HEART_MAX * 1.0)) * 10, 1);
        return this.createStatusBar(full.intValue());
    }

    /**
     * 生成一个进度条
     *
     * @param full 已完成的部分 值为 0 - 10
     * @return 进度条
     */
    public String createStatusBar(int full) {
        if (full < ConstantPet.STATUS_BAR_MIN) {
            full = ConstantPet.STATUS_BAR_MIN;
        }
        if (full > ConstantPet.STATUS_BAR_MAX) {
            full = ConstantPet.STATUS_BAR_MAX;
        }

        StringBuilder result = new StringBuilder();
        //填充充满的条
        for (int i = 0; i < full; i++) {
            result.append(ConstantPet.STATUS_FULL);
        }

        //除了充满的条，剩余的为空条
        int empty = ConstantPet.STATUS_BAR_MAX - full;
        for (int i = 0; i < empty; i++) {
            result.append(ConstantPet.STATUS_EMPTY);
        }
        return result.toString();
    }
}
