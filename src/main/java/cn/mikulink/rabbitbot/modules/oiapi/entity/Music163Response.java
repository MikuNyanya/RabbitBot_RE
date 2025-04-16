package cn.mikulink.rabbitbot.modules.oiapi.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MikuLink created in 2025/4/7 1:44
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class Music163Response {
    private String name;
    private String picurl;
    private Long id;
    private List<Music163Singer> singers;
    private String url;
    private String jumpurl;
    private Boolean pay;
}
