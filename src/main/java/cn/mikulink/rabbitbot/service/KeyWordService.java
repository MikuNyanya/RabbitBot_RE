package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.constant.ConstantFreeTime;
import cn.mikulink.rabbitbot.constant.ConstantImage;
import cn.mikulink.rabbitbot.constant.ConstantKeyWord;
import cn.mikulink.rabbitbot.constant.ConstantRepeater;
import cn.mikulink.rabbitbot.service.greetings.*;
import cn.mikulink.rabbitbot.utils.FileUtil;
import cn.mikulink.rabbitbot.utils.RandomUtil;
import cn.mikulink.rabbitbot.utils.RegexUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.internal.message.image.OnlineGroupImageImpl;
import net.mamoe.mirai.message.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * create by MikuLink on 2020/8/31 15:40
 * for the Reisen
 * 专门处理关键词触发的相关业务
 */
@Slf4j
@Service
public class KeyWordService {
    //保存群最后一条消息，用于复读
    private static Map<Long, String[]> LAST_MSG_MAP = new HashMap<>();

    @Value("${file.path.data:}")
    private String dataPath;

    @Autowired
    private ImageService imageService;
    @Autowired
    private RabbitBotService rabbitBotService;

    /**
     * 群消息关键词匹配
     *
     * @param event 群消息监听事件
     */
    public void keyWordMatchGroup(GroupMessageEvent event) throws IOException {
        //每次只会触发一个回复
        //特殊关键词，优先匹配
        boolean groupRep = groupKeyWordGreetings(event);
        if (groupRep) {
            return;
        }

        //ABABA 句式检索
        groupRep = groupABABA(event);
        if (groupRep) {
            return;
        }

        //图片响应
        groupRep = groupKeyWordImage(event);
        if (groupRep) {
            return;
        }

        //关键词全匹配
        groupRep = groupKeyWord(event);
        if (groupRep) {
            return;
        }

        //关键词匹配(模糊)
        groupRep = groupKeyWordLike(event);
        if (groupRep) {
            return;
        }

        //群复读
        groupRep = groupRepeater(event);
        if (groupRep) {
            return;
        }
    }

    /**
     * 群复读
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群复读
     */
    private boolean groupRepeater(GroupMessageEvent event) {
        //接收并解析到的群消息
        boolean isFirst = true;
        StringBuilder sbMsg = new StringBuilder();
        for (SingleMessage singleMessage : event.getMessage()) {
            //第一个元素为信息id，忽略
            if (isFirst) {
                isFirst = false;
                continue;
            }

            //目前已知三种消息类型
            //net.mamoe.mirai.message.data.Face
            //net.mamoe.mirai.message.data.PlainText
            //net.mamoe.mirai.internal.message.OnlineGroupImageImpl
            if (singleMessage.getClass() == net.mamoe.mirai.message.data.PlainText.class) {
                //普通文本 直接拼接
                sbMsg.append(((PlainText) singleMessage).getContent());

            } else if (singleMessage.getClass() == net.mamoe.mirai.internal.message.image.OnlineGroupImageImpl.class) {
                //图片
                sbMsg.append(((OnlineGroupImageImpl) singleMessage).getImageId());
            } else if (singleMessage.getClass() == net.mamoe.mirai.message.data.Face.class) {
                //qq表情
                sbMsg.append(((Face) singleMessage).getId());
            }
        }

        String groupMsg = sbMsg.toString();
        //如果消息为空 则忽略本次复读
        if (StringUtil.isEmpty(groupMsg)) {
            return false;
        }

        Long groupId = event.getGroup().getId();

        //第一次消息初始化
        if (!LAST_MSG_MAP.containsKey(groupId)) {
            LAST_MSG_MAP.put(groupId, new String[2]);
        }

        String[] msgs = LAST_MSG_MAP.get(groupId);
        //群复读，三个相同的消息，复读一次，并重置计数
        if ((StringUtil.isEmpty(msgs[0]) || StringUtil.isEmpty(msgs[1]))
                || !(msgs[0].equals(msgs[1]) && msgs[0].equals(groupMsg))) {
            //刷新消息列表
            msgs[1] = msgs[0];
            msgs[0] = groupMsg;
            LAST_MSG_MAP.put(groupId, msgs);
            return false;
        }

        MessageChain resultMsg = event.getMessage();
        //概率打断复读，100%对复读打断复读的语句做出反应
        if (ConstantRepeater.REPEATER_KILLER_LIST.contains(groupMsg) || ConstantRepeater.REPEATER_STOP_LIST.contains(groupMsg)) {
            resultMsg = MessageUtils.newChain();
            //打断复读的复读
            resultMsg = resultMsg.plus(RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_STOP_LIST));
        } else if (RandomUtil.rollBoolean(-80)) {
            resultMsg = MessageUtils.newChain();
            //打断复读
            resultMsg = resultMsg.plus(RandomUtil.rollStrFromList(ConstantRepeater.REPEATER_KILLER_LIST));
        }

        //判定复读概率
        if (!RandomUtil.rollBoolean(20)) {
            return false;
        }

        //进行复读
        event.getSubject().sendMessage(resultMsg);
        //复读一次后，重置复读计数
        LAST_MSG_MAP.put(groupId, new String[2]);
        return true;
    }

    /**
     * 跟随回复ABABA句式
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupABABA(GroupMessageEvent event) {
        //接收到的群消息
        String groupMsg = event.getMessage().contentToString();

        //检查句式
        if (!StringUtil.isABABA(groupMsg)) {
            return false;
        }
        String msg = RandomUtil.rollStrFromList(ConstantFreeTime.MSG_TYPE_ABABA);
        //回复群消息
        event.getSubject().sendMessage(msg);
        return true;
    }

    /**
     * 群消息关键词检测
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWord(GroupMessageEvent event) {
        String groupMsg = event.getMessage().contentToString();

        if (ConstantKeyWord.key_wrod_normal.size() <= 0) {
            try {
                this.loadFileKeyWordNormal();
            } catch (Exception ex) {
                log.error("关键词回复文件读取异常", ex);
            }
        }

        //全匹配关键词
        String mapKey = null;
        //循环mapkey，找到包含关键词的key，然后拆分key确认是否全匹配，如果不是继续循环到下一个key
        lab_mapKey:
        for (String keyRegex : ConstantKeyWord.key_wrod_normal.keySet()) {
            //正则匹配
            for (String oneKey : keyRegex.split("\\|")) {
                //找到关键词，返回map的key
                if (RegexUtil.regex(groupMsg, "^" + oneKey + "$")) {
                    mapKey = keyRegex;
                    break lab_mapKey;
                }
            }
        }

        if (StringUtil.isEmpty(mapKey)) {
            return false;
        }

        //随机选择回复
        String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_normal.get(mapKey));
        //回复群消息
        event.getSubject().sendMessage(msg);
        return true;

    }

    /**
     * 群消息关键词检测(模糊)
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWordLike(GroupMessageEvent event) {
        String groupMsg = event.getMessage().contentToString();

        if (ConstantKeyWord.key_wrod_like_list.size() <= 0) {
            try {
                this.loadFileKeyWordLike();
            } catch (Exception ex) {
                log.error("关键词(模糊)回复文件读取异常", ex);
            }
        }

        //检测模糊关键词
        String mapKey = keyWordLikeRegex(ConstantKeyWord.key_wrod_like_list, groupMsg);
        if (StringUtil.isEmpty(mapKey)) {
            return false;
        }

        //随机选择回复
        String msg = RandomUtil.rollStrFromList(ConstantKeyWord.key_wrod_like.get(mapKey));
        //回复群消息
        event.getSubject().sendMessage(msg);
        return true;
    }

    /**
     * 图片关键词触发
     *
     * @param event 群消息监控
     * @return bol值 表示有没有进行群消息回复
     */
    private boolean groupKeyWordImage(GroupMessageEvent event) throws IOException {
        String groupMsg = event.getMessage().contentToString();

        //检测模糊关键词
        String mapKey = keyWordLikeRegex(ConstantImage.LIST_KEY_IMAGES, groupMsg);
        if (StringUtil.isEmpty(mapKey)) {
            return false;
        }

        String imgLocalPath = "";
        //根据key选择业务
        switch (mapKey) {
            case ConstantImage.IMAGE_MAP_KEY_GUGUGU:
                imgLocalPath = imageService.getGuguguRandom();
                break;
        }
        if (StringUtil.isEmpty(imgLocalPath)) {
            return false;
        }
        //上传图片
        Image miraiImage = rabbitBotService.uploadMiraiImage(imgLocalPath);

        //发送图片
        event.getSubject().sendMessage(miraiImage);
        return true;
    }

    /**
     * 时间问候关键词匹配
     *
     * @param event 群消息监听时间
     * @return 是否回复了消息
     */
    private boolean groupKeyWordGreetings(GroupMessageEvent event) {
        //5秒触发一次，没必要太严谨
        if ((System.currentTimeMillis() - ConstantKeyWord.KEY_WORD_GREETINGS_LAST_SEND) < ConstantKeyWord.KEY_WORD_GREETINGS_SPLIT) {
            return false;
        }

        String groupMsg = event.getMessage().contentToString();

        //检测模糊关键词
        String keyWordEx = keyWordLikeRegex(ConstantKeyWord.LIST_KEY_WORD_GREETINGS, groupMsg);
        if (StringUtil.isEmpty(keyWordEx)) {
            return false;
        }

        GreetingsBase greetingService = null;
        //根据关键词识别问候类型
        switch (keyWordEx) {
            case ConstantKeyWord.KEY_WORD_GREETINGS_MORNING:
                greetingService = new GreetingsMorning();
                break;
            case ConstantKeyWord.KEY_WORD_GREETINGS_NOON:
                greetingService = new GreetingsNoon();
                break;
            case ConstantKeyWord.KEY_WORD_GREETINGS_AFTERNOON:
                greetingService = new GreetingsAfternoon();
                break;
            case ConstantKeyWord.KEY_WORD_GREETINGS_NIGHT:
                greetingService = new GreetingsNight();
                break;
            case ConstantKeyWord.KEY_WORD_GREETINGS_GOOD_NIGHT:
                greetingService = new GreetingsGoodNight();
                break;
        }
        if (null == greetingService) {
            return false;
        }

        String resultMsg = greetingService.getGreetingsByTime();
        //返回信息为空接着往下走业务
        if (StringUtil.isEmpty(resultMsg)) {
            return false;
        }
        //发送回复
        event.getSubject().sendMessage(resultMsg);
        //刷新最后响应时间
        ConstantKeyWord.KEY_WORD_GREETINGS_LAST_SEND = System.currentTimeMillis();
        return true;
    }

    /**
     * 模糊匹配关键词
     *
     * @param inputKey 输入的关键词
     * @return 匹配到的key，可以用来获取回复列表
     */
    public static String keyWordLikeRegex(List<String> keyWrodList, String inputKey) {
        //去keylist寻找关键词
        for (String keyRegex : keyWrodList) {
            //正则匹配
            for (String keyWords : keyRegex.split("\\|")) {
                //拼接正则
                String regex = getKeyWordLikeRegex(keyWords);

                //进行正则匹配
                if (RegexUtil.regex(inputKey, regex)) {
                    //匹配到了返回map的完整key
                    return keyRegex;
                }
            }
        }
        return null;
    }

    /**
     * 解析模糊关键词，组成正则
     *
     * @param keyWords 模糊匹配关键词原始字符串
     * @return 模糊匹配正则表达式
     */
    private static String getKeyWordLikeRegex(String keyWords) {
        StringBuilder regex = new StringBuilder();
        boolean isFirst = true;
        for (String key : keyWords.split("&")) {
            if (isFirst) {
                regex.append(key);
                isFirst = false;
                continue;
            }
            //中间可以间隔任意字符
            regex.append("\\S*");
            regex.append(key);
        }
        return regex.toString();
    }

    /**
     * 获取资源文件路径
     */
    public String getFilePathKeyWordNormal() {
        return dataPath + File.separator + "files" + File.separator + "key_word_normal.txt";
    }

    /**
     * 加载常规关键词回复文件内容
     *
     * @throws IOException 读写异常
     */
    public void loadFileKeyWordNormal() throws IOException {
        String path = this.getFilePathKeyWordNormal();
        FileUtil.fileCheck(path);
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String tempKey = null;
        List<String> tempList = null;

        //逐行读取文件
        String freeTimeStr = null;
        while ((freeTimeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (freeTimeStr.length() <= 0) continue;
            //第一行是关键词
            tempKey = freeTimeStr;
            //再往下读一行，是回复
            freeTimeStr = reader.readLine();
            tempList = Arrays.asList(freeTimeStr.split("\\|"));

            //内容同步到系统
            ConstantKeyWord.key_wrod_normal.put(tempKey, tempList);
        }
        //关闭读取器
        reader.close();
    }

    /**
     * 对文件写入内容
     *
     * @param text 第一段为关键词，多个关键词用 \ 隔开，后面为回复列表
     * @throws IOException 读写异常
     */
    public void writeFileKeyWordNormal(String... text) throws IOException {
        //入参检验
        if (null == text || text.length <= 0) {
            return;
        }
        String path = this.getFilePathKeyWordNormal();

        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
        //index
        int i = 0;
        //热更新
        String keyWord = "";
        List<String> repList = new ArrayList<>();

        StringBuilder fileSb = new StringBuilder();
        for (String s : text) {
            //过滤空行
            if (StringUtil.isEmpty(s)) continue;
            //index+1
            i++;

            //第一个属于关键词，直接丢进去
            if (i == 1) {
                fileSb.append(s);
                keyWord = s;
                continue;
            }
            //第二属于第一个回复，需要换行，其他的开头加分隔符
            if (i == 2) {
                fileSb.append("\r\n");
            } else {
                fileSb.append("|");
            }
            //追加回复
            fileSb.append(s);
            repList.add(s);
        }
        //写入内容
        out.write("\r\n" + fileSb.toString());

        //把新加的内容同步到系统
        ConstantKeyWord.key_wrod_normal.put(keyWord, repList);

        //关闭写入流
        out.close();
    }


    public String getFilePathKeyWordLike() {
        return dataPath + File.separator + "files" + File.separator + "key_word_like.txt";
    }

    /**
     * 加载文件内容
     *
     * @throws IOException 读写异常
     */
    public void loadFileKeyWordLike() throws IOException {
        String path = this.getFilePathKeyWordLike();
        FileUtil.fileCheck(path);
        //创建读取器
        BufferedReader reader = new BufferedReader(new FileReader(path));

        String tempKey = null;
        List<String> tempList = null;

        //逐行读取文件
        String freeTimeStr = null;
        while ((freeTimeStr = reader.readLine()) != null) {
            //过滤掉空行
            if (freeTimeStr.length() <= 0) continue;
            //第一行是关键词
            tempKey = freeTimeStr;
            //再往下读一行，是回复
            freeTimeStr = reader.readLine();
            tempList = Arrays.asList(freeTimeStr.split("\\|"));

            //内容同步到系统
            ConstantKeyWord.key_wrod_like_list.add(tempKey);
            ConstantKeyWord.key_wrod_like.put(tempKey, tempList);
        }
        //关闭读取器
        reader.close();
    }


    /**
     * 对文件写入内容
     *
     * @param text 第一段为关键词，多个关键词用 \ 隔开，后面为回复列表
     * @throws IOException 读写异常
     */
    public void writeFileKeyWordLike(String... text) throws IOException {
        //入参检验
        if (null == text || text.length <= 0) {
            return;
        }

        String path = this.getFilePathKeyWordLike();
        //创建写入流
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)));
        //index
        int i = 0;
        //热更新
        String keyWord = "";
        List<String> repList = new ArrayList<>();

        StringBuilder fileSb = new StringBuilder();
        for (String s : text) {
            //过滤空行
            if (StringUtil.isEmpty(s)) continue;
            //index+1
            i++;

            //第一个属于关键词，直接丢进去
            if (i == 1) {
                fileSb.append(s);
                keyWord = s;
                continue;
            }
            //第二属于第一个回复，需要换行，其他的开头加分隔符
            if (i == 2) {
                fileSb.append("\r\n");
            } else {
                fileSb.append("|");
            }
            //追加回复
            fileSb.append(s);
            repList.add(s);
        }
        //写入内容
        out.write("\r\n" + fileSb.toString());

        //把新加的内容同步到系统
        ConstantKeyWord.key_wrod_like_list.add(keyWord);
        ConstantKeyWord.key_wrod_like.put(keyWord, repList);

        //关闭写入流
        out.close();
    }
}
