package cn.mikulink.entity.apirequest.imjad;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 15:00
 * for the Reisen
 * 收藏数，分为收藏为公共，收藏为私有
 */
@Setter
@Getter
public class ImjadPixivFavoritedCount {
    /**
     * public : 18562
     * private : 1279
     */

    @SerializedName("public")
    private int publicCount;
    @SerializedName("private")
    private int privateCount;
}
