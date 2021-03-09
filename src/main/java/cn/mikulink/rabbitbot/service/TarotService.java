package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.constant.ConstantTarot;
import cn.mikulink.rabbitbot.entity.TarotInfo;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

/**
 * create by MikuLink on 2020/12/22 15:18
 * for the Reisen
 * 塔罗牌相关
 */
@Service
public class TarotService {
    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 抽一张塔罗牌
     * 只返回原始数据，包括上传图片在内的所有操作由上层执行
     */
    public TarotInfo getTarot() {
        /**
         * 模拟抽卡
         * 1.先洗牌 , 随机赋值正逆
         * 2.抽取
         */
        TarotInfo[] tarotInfos = this.randomArray(
                ConstantTarot.TARTO_LIST.toArray(
                        new TarotInfo[ConstantTarot.TARTO_LIST.size()]));

        //随机出一个元素
        int rollNum = RandomUtil.roll(ConstantTarot.TARTO_LIST.size() - 1);
        TarotInfo tarotInfo = tarotInfos[rollNum];
        return tarotInfo;
    }

    /**
     * 随机重新组装一个数组
     * @param sourceArray
     * @return
     */
    private  TarotInfo[] randomArray(  TarotInfo[] sourceArray) {
        int len = sourceArray.length;

        TarotInfo[] result = (TarotInfo[]) Array.newInstance(TarotInfo.class, len);

        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(new Random().nextInt() % len--);
            //随机正位逆位
            sourceArray[index].setStatus(RandomUtil.rollBoolean(0));
            //将随机到的数放入结果集
            result[i] = sourceArray[index];

            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            sourceArray[index] = sourceArray[len];
        }
        return result;
    }

    /**
     * 把塔罗牌数据转化为消息链
     *
     * @return 消息链
     */
    public MessageChain parseTarotMessage(TarotInfo tarotInfo) {
        MessageChain result = MessageUtils.newChain();

        if (null == tarotInfo) {
            return result;
        }

        String imagesPath = ConstantTarot.IMAGE_TAROT_SAVE_PATH;
        if (tarotInfo.isCat()) {
            imagesPath = ConstantTarot.IMAGE_CATROT_SAVE_PATH;
        }

        //图片处理
        Image miraiImage = rabbitBotService.uploadMiraiImage(imagesPath + File.separator + tarotInfo.getImgName());
        result = rabbitBotService.parseMsgChainByImg(miraiImage);

        StringBuilder resultStr = new StringBuilder();
        resultStr.append("[").append(tarotInfo.getName()).append(" ").append(tarotInfo.isStatus() ? "正位" : "逆位").append("]");
        resultStr.append("\n").append(tarotInfo.isStatus() ? tarotInfo.getNormalDes() : tarotInfo.getSeDlamron());
        result = result.plus(resultStr.toString());
        return result;
    }
}
