package cn.mikulink.rabbitbot.entity.apirequest.soyiji;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * created by MikuNyanya in 12:26 2022/8/1
 * for the Reisen
 */
@NoArgsConstructor
@Data
public class SoyijiResponseInfo {

    @JSONField(name = "success")
    private Boolean success;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "result")
    private ResultDTO result;
    @JSONField(name = "timestamp")
    private Long timestamp;

    @NoArgsConstructor
    @Data
    public static class ResultDTO {
        @JSONField(name = "date")
        private String date;
        @JSONField(name = "data")
        private List<String> data;
    }
}
