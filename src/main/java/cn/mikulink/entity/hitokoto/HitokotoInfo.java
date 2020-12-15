package cn.mikulink.entity.hitokoto;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/3/16 14:40
 * for the Reisen
 */
@Setter
@Getter
public class HitokotoInfo {
    /**
     * id : 5006
     * hitokoto : 那些听不见音乐的人以为跳舞的人疯了。
     * type : k
     * from : 上帝死了
     * from_who : 弗里德里希·威廉·尼采
     * creator : scvoet
     * creator_uid : 1713
     * reviewer : 1044
     * uuid : b45b4ace-c1f7-4feb-8ca2-7b2dc8c728e0
     * created_at : 1581216160
     */

    //数据数字id
    private Long id;
    //一言内容
    private String hitokoto;
    //一言类型(字幕）
    private String type;
    //出自作品
    private String from;
    //出自某人
    private String from_who;
    //词条贡献
    private String creator;
    //词条贡献人uid
    private int creator_uid;
    //词条审核人
    private int reviewer;
    //数据唯一码
    private String uuid;
    //词条创建时间
    private String created_at;
}
