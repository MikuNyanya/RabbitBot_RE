package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.entity.newpneumonia.ComponentInfo;
import cn.mikulink.rabbitbot.entity.newpneumonia.SummaryDataInInfo;
import cn.mikulink.rabbitbot.entity.newpneumonia.VirusInfo;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

/**
 * created by MikuNyanya on 2022/3/15 15:46
 * For the Reisen
 * 新冠实时数据
 */
@Service
@Slf4j
public class VirusService {

    /**
     * 从页面爬取最新信息
     */
    public VirusInfo getVirusInfo() {
        try {
            byte[] bodyBytes = HttpsUtil.doGet("https://voice.baidu.com/act/newpneumonia/newpneumonia");
            String responseHtml = new String(bodyBytes);
            //使用jsoup解析html
            Document document = Jsoup.parse(responseHtml);
            //选择目标节点，类似于JS的选择器
            Element element = document.getElementById("captain-config");
            return JSONObject.parseObject(element.data(), VirusInfo.class);
        } catch (Exception ex) {
            log.error("新冠信息获取异常", ex);
            return null;
        }
    }

    //转化为消息对象
    public MessageChain parseMsg(VirusInfo virusInfo) {
        try {
            ComponentInfo componentInfo = virusInfo.getComponent().get(0);
            SummaryDataInInfo summaryDataInInfo = componentInfo.getSummaryDataIn();

            MessageChain result = MessageUtils.newChain();
            result = result.plus(componentInfo.getTitle());
            result = result.plus("\n数据更新时间：").plus(componentInfo.getMapLastUpdatedTime());
            result = result.plus("\n===========================");
            result = result.plus("\n现有确诊：").plus(summaryDataInInfo.getCurConfirm()).plus(" 较昨日+").plus(summaryDataInInfo.getCurConfirmRelative());
            result = result.plus("\n累积确诊：").plus(summaryDataInInfo.getConfirmed()).plus(" 较昨日+").plus(summaryDataInInfo.getConfirmedRelative());
            result = result.plus("\n无症状：").plus(summaryDataInInfo.getAsymptomatic()).plus(" 较昨日+").plus(summaryDataInInfo.getAsymptomaticRelative());
            result = result.plus("\n现有疑似：").plus(summaryDataInInfo.getUnconfirmed()).plus(" 较昨日+").plus(summaryDataInInfo.getUnconfirmedRelative());
            result = result.plus("\n现有重症：").plus(summaryDataInInfo.getIcu()).plus(" 较昨日+").plus(summaryDataInInfo.getIcuRelative());
            result = result.plus("\n境外输入：").plus(summaryDataInInfo.getOverseasInput()).plus(" 较昨日+").plus(summaryDataInInfo.getOverseasInputRelative());
            result = result.plus("\n累积治愈：").plus(summaryDataInInfo.getCured()).plus(" 较昨日+").plus(summaryDataInInfo.getCuredRelative());
            result = result.plus("\n累积死亡：").plus(summaryDataInInfo.getDied()).plus(" 较昨日+").plus(summaryDataInInfo.getDiedRelative());
            result = result.plus("\n===========================");
            result = result.plus("\n数据来源 ").plus("https://voice.baidu.com/act/newpneumonia/newpneumonia");

            return result;
        } catch (Exception ex) {
            log.error("新冠消息转化异常", ex);
            return null;
        }
    }
}
