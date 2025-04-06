package cn.mikulink.rabbitbot.entity.rabbitbotmessage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * MikuLink created in 2025/4/2 11:55
 * For the Reisen
 * 消息链内容
 */
@Getter
@Setter
public class MessageChainData {
    public MessageChainData(){}
    public MessageChainData(String text){this.text = text;}

    public static MessageChainData createImageMessageData(String urlOrPath){
        MessageChainData info = new MessageChainData();
        info.setFile(urlOrPath);
        return info;
    }

    //文本内容
    private String text;

    //当MessageChain的type为image时，下列字段才会有用
    private String file;
    private String url;
    @JsonProperty("file_size")
    private String fileSize;
    @JsonProperty("sub_type")
    private Integer subType;

    //当MessageChain的type为music时，下列字段才会有用
    private String type;
    private Long id;
    private String audio;
    private String title;
    private String image;

    //当MessageChain的type为at时，下列字段才会有用
    private String qq;
}
