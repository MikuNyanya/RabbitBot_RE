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
        int index = ConstantTarot.TARTO_LIST.size();
        
        //随机出一个元素
        int rollNum = RandomUtil.roll(ConstantTarot.TARTO_LIST.size() - 1);
        TarotInfo tarotInfo = ConstantTarot.TARTO_LIST.get(rollNum);
        //随机正位逆位
        tarotInfo.setStatus(RandomUtil.rollBoolean(0));
        return tarotInfo;
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
