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
    private String file;
    private String url;
    @JsonProperty("file_size")
    private String fileSize;
    @JsonProperty("sub_type")
    private Integer subType;

    //当MessageChain的type为music时，下列字段才会有用
    //类型，填写163或者qq需要额外一道签证服务，填写custom为自定义音乐分享
    private String type;
    //如果是网易云，qq等，这里填写曲目id
    private Long id;
    //用来播放的音乐URL
    private String audio;
    //标题
    private String title;
    //封面
    private String image;
    //内容描述 一般放入歌手信息之类的
    private String content;

    //当MessageChain的type为at时，该字段表示at的目标q号
    private String qq;
}
