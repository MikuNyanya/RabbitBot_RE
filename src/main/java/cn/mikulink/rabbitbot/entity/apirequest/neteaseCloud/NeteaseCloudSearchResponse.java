package cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author MikuLink
 * @date 2022/9/26 15:51
 * for the Reisen
 */
@NoArgsConstructor
@Data
public class NeteaseCloudSearchResponse {
    private ResultBean result;
    private Integer code;

    @NoArgsConstructor
    @Data
    public static class ResultBean {
        private List<SongsBean> songs;
        private Integer songCount;

        @NoArgsConstructor
        @Data
        public static class SongsBean {
            private String name;
            private Long id;
            private List<ArBean> artists;
            private AlBean album;

            @NoArgsConstructor
            @Data
            public static class AlBean {
                private Long id;
                private String name;
                private String picUrl;
                private List<?> tns;
                private String pic_str;
                private Long pic;
            }

            @NoArgsConstructor
            @Data
            public static class ArBean {
                private Long id;
                private String name;
                private List<String> tns;
                private List<?> alias;
            }
        }
    }
}
