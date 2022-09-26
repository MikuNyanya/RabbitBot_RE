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
            private Integer id;
            private List<ArBean> ar;
            private AlBean al;

            @NoArgsConstructor
            @Data
            public static class AlBean {
                private Integer id;
                private String name;
                private String picUrl;
                private List<?> tns;
                private String pic_str;
                private Long pic;
            }

            @NoArgsConstructor
            @Data
            public static class ArBean {
                private Integer id;
                private String name;
                private List<String> tns;
                private List<?> alias;
            }
        }
    }
}
