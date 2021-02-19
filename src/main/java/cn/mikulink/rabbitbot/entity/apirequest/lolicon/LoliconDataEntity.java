package cn.mikulink.rabbitbot.entity.apirequest.lolicon;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoliconDataEntity {
    private int pid;
    private int p;
    private int uid;
    private String title;
    private String author;
    private String url;
    private boolean r18;
    private int width;
    private int height;
    private List<String> tags;

}
