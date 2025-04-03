package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.bot.RabbitBot;
import cn.mikulink.rabbitbot.constant.ConstantPet;
import cn.mikulink.rabbitbot.entity.PetInfo;
import cn.mikulink.rabbitbot.entity.ReString;
import cn.mikulink.rabbitbot.service.sys.SwitchService;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.NumberUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import com.alibaba.fastjson2.JSONObject;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * created by MikuNyanya on 2021/9/15 18:04
 * For the Reisen
 * 养成相关服务
 */
@Service

public class PetService {
    private static final Logger logger = LoggerFactory.getLogger(PixivService.class);

    @Value("${file.path.data:}")
    private String dataPath;

    @Autowired
    private SwitchService switchService;


    /**
     * 获取资源文件路径
     */
    public String getFilePath() {
        return dataPath + File.separator + "files" + File.separator + "pet";
    }

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

        //全群通知
        this.sendMessageGroupAll(MessageUtils.newChain().plus(RandomUtil.rollStrFromList(ConstantPet.LEVEL_UP_MSG)));

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

    /**
     * 心情波动
     * 当前心情 (+or-) (最大心情 * 波动百分比) 向下取整 不超过最大最小值
     */
    public void heartWave() {
        //随机 增加还是减少
        boolean isAdd = RandomUtil.rollBoolean(0);
        //随机 变更数值
        double waveValue = ConstantPet.HEART_MAX * ConstantPet.HEART_WAVE_PROP;
        int waveInt = (int) waveValue;

        //改变心情
        if (isAdd) {
            ConstantPet.petInfo.setHeart(ConstantPet.petInfo.getHeart() + waveInt);
        } else {
            ConstantPet.petInfo.setHeart(ConstantPet.petInfo.getHeart() - waveInt);
        }

        //边界校验
        if (ConstantPet.petInfo.getHeart() < ConstantPet.HEART_MIN) {
            ConstantPet.petInfo.setHeart(ConstantPet.HEART_MIN);
        } else if (ConstantPet.petInfo.getHeart() > ConstantPet.HEART_MAX) {
            ConstantPet.petInfo.setHeart(ConstantPet.HEART_MAX);
        }
    }

    /**
     * 给开启了养成系统的所有群发送消息
     *
     * @param message 需要发送的消息链
     */
    private void sendMessageGroupAll(MessageChain message) {
        //给每个群发送消息
        ContactList<Group> groupList = RabbitBot.getBot().getGroups();
        for (Group groupInfo : groupList) {
            //检查功能开关
            ReString reStringSwitch = switchService.switchCheck(null, groupInfo, "pet");
            if (!reStringSwitch.isSuccess()) {
                continue;
            }
            try {
                groupInfo.sendMessage(message);
            } catch (Exception ex) {
                logger.error("养成系统群通知异常", ex);
            }
        }
    }


    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public void loadPetInfo() throws IOException {
        FileUtil.fileCheck(this.getFilePath());

        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(this.getFilePath()));
        //读取第一行
        String configJson = null;
        while ((configJson = reader.readLine()) != null) {
            //过滤掉空行
            if (configJson.length() <= 0) continue;
            ConstantPet.petInfo = JSONObject.parseObject(configJson, PetInfo.class);
        }
        //关闭读取器
        reader.close();

        //初始化
        if (null == ConstantPet.petInfo) {
            ConstantPet.petInfo = new PetInfo();
        }
    }

    /**
     * 对文件写入内容
     *
     * @throws IOException 读写异常
     */
    public void writeFile() throws IOException {
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.getFilePath(), false)));
        //覆写原本配置
        out.write(JSONObject.toJSONString(ConstantPet.petInfo));
        //关闭写入流
        out.close();
    }
}
