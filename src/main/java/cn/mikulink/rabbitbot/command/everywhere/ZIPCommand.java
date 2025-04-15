package cn.mikulink.rabbitbot.command.everywhere;

import cn.mikulink.rabbitbot.bot.RabbitBotMessageBuilder;
import cn.mikulink.rabbitbot.command.EverywhereCommand;
import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.db.RabbitbotPrivateMessageInfo;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.service.DeepSeekService;
import cn.mikulink.rabbitbot.service.db.DeepseekChatRecordService;
import cn.mikulink.rabbitbot.service.db.RabbitbotPrivateMessageService;
import cn.mikulink.rabbitbot.sys.annotate.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;


/**
 * @author MikuLink
 * @date 2020/8/31 10:50
 * for the Reisen
 * <p>
 * 兔叽配置
 */
@Command
public class ZIPCommand extends EverywhereCommand {
    @Autowired
    private DeepSeekService deepSeekService;
    @Autowired
    private RabbitbotPrivateMessageService rabbitbotPrivateMessageService;
    @Autowired
    private DeepseekChatRecordService deepseekChatRecordService;

    @Override
    public CommandProperties properties() {
        return new CommandProperties("ZIP", "zip");
    }

    @Override
    public MessageInfo execute(MessageInfo messageInfo) {
//
//        if(messageInfo instanceof GroupMessageInfo){
//            return RabbitBotMessageBuilder.createMessageText("是群消息");
//        }

        ArrayList<String> arrayList = getArgs(messageInfo.getRawMessage());
        //进行总结
//        String id = arrayList.get(0);
//        String resp = deepSeekService.privateMessageZIP(Long.valueOf(id), 1000);
//        System.out.println(resp);

        //保存压缩
//        Long targetId = 455806936L;
//        Long botId = 1020992834L;
//        zipSave(targetId, botId);

//          List<RabbitbotPrivateMessageInfo> privateMessageInfoList = rabbitbotPrivateMessageService.getHistoryByTargetId(455806936L);
//        List<cn.mikulink.rabbitbot.entity.apirequest.deepseek.MessageInfo> dsMessages = deepSeekService.getPrivateMessageRecordList(455806936L,500);
//
//        for (cn.mikulink.rabbitbot.entity.apirequest.deepseek.MessageInfo dsMessage : dsMessages) {
//            DeepseekChatRecordInfo chatRecordInfo = new DeepseekChatRecordInfo();
//            chatRecordInfo.setTag("凛雪飘渺");
//            chatRecordInfo.setRoleType(dsMessage.getRole());
//            chatRecordInfo.setMessage(dsMessage.getContent());
//
//            deepseekChatRecordService.create(chatRecordInfo);
//        }


        return RabbitBotMessageBuilder.createMessageText("success");
    }

    private void zipSave(Long targetId, Long botId) {
        RabbitbotPrivateMessageInfo historyByTargetId = rabbitbotPrivateMessageService.getLastBotHistoryByTargetId(targetId, botId);
        historyByTargetId.setId(null);
        historyByTargetId.setMessageId(0L);
        historyByTargetId.setRawMessage(str);

        String json = String.format("[{\"data\":{\"text\":\"%s\"},\"type\":\"text\"}]", str);

        historyByTargetId.setMessageJson(json);

        rabbitbotPrivateMessageService.create(historyByTargetId);
        System.out.println("已保存压缩信息");
    }


    private String str = """
              压缩信息
              """;
}
