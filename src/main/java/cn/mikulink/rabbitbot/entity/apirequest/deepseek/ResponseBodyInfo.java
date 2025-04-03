package cn.mikulink.rabbitbot.entity.apirequest.deepseek;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * MikuLink created in 2025/3/31 22:38
 * For the Reisen
 */
@NoArgsConstructor
@Data
public class ResponseBodyInfo {

    private String id;
    private String object;
    private Long created;
    private String model;
    private List<ChoicesDTO> choices;
    private UsageDTO usage;
    private String systemFingerprint;

    @NoArgsConstructor
    @Data
    public static class UsageDTO {
        private Integer promptTokens;
        private Integer completionTokens;
        private Integer totalTokens;
        private PromptTokensDetailsDTO promptTokensDetails;
        private Integer promptCacheHitTokens;
        private Integer promptCacheMissTokens;

        @NoArgsConstructor
        @Data
        public static class PromptTokensDetailsDTO {
            private Integer cachedTokens;
        }
    }

    @NoArgsConstructor
    @Data
    public static class ChoicesDTO {
        private Integer index;
        private MessageInfo message;
        private String finishReason;
    }
}
