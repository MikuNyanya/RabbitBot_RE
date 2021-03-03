package cn.mikulink.rabbitbot.entity.apirequest.weixin;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MikuLink
 * @date 2021/3/3 9:40
 * for the Reisen
 * 微信公众号文章对象
 * 只取必要字段
 */
@Getter
@Setter
public class WeiXinAppMsgInfo {
    /**
     * aid : 2247516645_1
     * album_id : 0
     * appmsg_album_infos : []
     * appmsgid : 2247516645
     * checking : 0
     * copyright_type : 0
     * cover : https://mmbiz.qlogo.cn/mmbiz_jpg/0KDDF9gwBOwyEQz5L3Bc8e8rfPMgBC9icXBT6gS4FsLqUQvKRsaxZehZ65HaZJSmSibUNTibQkCBJqlRjW6vUEsibg/0?wx_fmt=jpeg
     * create_time : 1614729635
     * digest : 今日简报（3月3日）
     * has_red_packet_cover : 0
     * is_pay_subscribe : 0
     * item_show_type : 8
     * itemidx : 1
     * link : http://mp.weixin.qq.com/s?__biz=MzI4NzcwNjY4NQ==&mid=2247516645&idx=1&sn=105e223a25f8df681cb4a0574ba86308&chksm=ebcb5ab7dcbcd3a19316b8275d80f81cec737da41e2621d7428f5c2dae6161c8ab7dd5c02c3b#rd
     * media_duration : 0:00
     * mediaapi_publish_status : 0
     * pay_album_info : {"appmsg_album_infos":[]}
     * tagid : []
     * title : 今日简报（3月3日）
     * update_time : 1614729635
     */

    /**
     * 公众号标题
     */
    private String title;
    /**
     * 文章链接
     */
    private String link;
    /**
     * 更新时间
     */
    private Integer update_time;
}
