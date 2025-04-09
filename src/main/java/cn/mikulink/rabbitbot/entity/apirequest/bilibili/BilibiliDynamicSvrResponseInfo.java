package cn.mikulink.rabbitbot.entity.apirequest.bilibili;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * create by MikuLink on 2021/05/17 19:50
 * for the Reisen
 * b站 视频动态信息对象
 */
@NoArgsConstructor
@Data
public class BilibiliDynamicSvrResponseInfo {
    /**
     * {
     * 	"code": 0,
     * 	"msg": "",
     * 	"message": "",
     * 	"data": {
     * 		"cards": [
     *                        {
     * 				"desc": {
     * 					"uid": 9717562,
     * 					"dynamic_id": 525632309733269018,
     * 					"timestamp": 1621221709,
     * 					"user_profile": {
     * 						"info": {
     * 							"uid": 9717562,
     * 							"uname": "星际老男孩",
     * 							"face": "https://i1.hdslb.com/bfs/face/7c5d421849f3e2596110902109a9b668112fd61d.jpg"
     *                        },
     * 						"sign": "我们就是华语星际圈颜值担当，星际老男孩组合：黄旭东、孙一峰"
     *                    },
     * 					"dynamic_id_str": "525632309733269018",
     * 					"bvid": "BV1XK4y197PU"
     *                },
     * 				"card": {
     * 					"aid": 930688504,
     * 					"cid": 339836340,
     * 					"ctime": 1621221709,
     * 					"desc": "如果看到有类似这样的小剧场既视感可以论坛@或者私信@Magic华亿",
     * 					"pic": "https://i0.hdslb.com/bfs/archive/02dd4529816eecf2223df7d4de6eccbce46c0a78.jpg",
     * 					"short_link": "https://b23.tv/BV1XK4y197PU",
     * 					"title": "【星际老男孩】小剧场之 IG LP恩断义绝",
     * 					"videos": 1
     *                }
     *            }
     * 		],
     * 		"max_dynamic_id": 525632309733269018,
     * 		"history_offset": 524607655094081183
     * 	}
     * }
     */

    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "data")
    private BilibiliDynamicSvrInfo data;
}
