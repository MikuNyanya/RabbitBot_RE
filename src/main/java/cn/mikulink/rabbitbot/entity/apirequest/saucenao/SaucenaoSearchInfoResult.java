package cn.mikulink.rabbitbot.entity.apirequest.saucenao;

import lombok.Getter;
import lombok.Setter;

/**
 * create by MikuLink on 2020/2/19 13:18
 * for the Reisen
 */
@Setter
@Getter
public class SaucenaoSearchInfoResult {
    private SaucenaoSearchInfoResultHeader header;
    private SaucenaoSearchInfoResultData data;
}
