package cn.mikulink.apirequest.pixiv;

import cn.mikulink.apirequest.BaseRequest;
import cn.mikulink.entity.pixiv.PixivRankImageInfo;
import cn.mikulink.utils.HttpUtil;
import cn.mikulink.utils.NumberUtil;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2020/12/18 2:10
 * for the Reisen
 * 获取pixiv榜单信息 尚未找到官方API，或者稳定的第三方，所以使用爬虫
 * 由于是爬排行榜网页，所以能获取信息有限，图片的详情信息还需要进一步获取
 * 然后一页大概50还是100个来着，再向后的还没看怎么获取的，暂时不支持
 * https://www.pixiv.net/ranking.php?mode=daily&content=illust&date=20201215
 */
@Setter
@Getter
public class PixivIllustRankGet extends BaseRequest {
    //插画日榜链接，get请求，可以添加日期参数进行扩展
    private static final String URL = "https://www.pixiv.net/ranking.php";

    /**
     * 排行榜日期类型 必填
     * daily 每日
     * weekly 每周
     * monthly 每月
     * rookie 新人榜
     */
    private String mode;
    /**
     * 排行榜类型 非必填 默认为 综合
     * illust 插画
     * ugoira 动图
     * manga 漫画
     */
    private String content;
    /**
     * 日期 非必填 默认为 当天
     * 20201215
     */
    private String date;
    /**
     * 取前多少条数据 非请求所需参数
     */
    private int pageSize = 5;

    //排行榜解析结果
    private List<PixivRankImageInfo> responseList = new ArrayList<>();

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        //拼装参数
        addParam();
        //代理
        Proxy proxy = HttpUtil.getProxy();
        //爬虫获取排行榜信息
        String pidHtmlStr = HttpUtil.get(URL + HttpUtil.parseUrlEncode(param), proxy);
        //使用jsoup解析html
        Document document = Jsoup.parse(pidHtmlStr);

        //选择目标节点，类似于JS的选择器
        Elements rankElements = document.getElementsByClass("ranking-item");

        //循环节点，提取排名和pid信息，其他不在这里拿，图片太小了
        for (Element element : rankElements) {
            Attributes attrs = element.attributes();
            //排名
            String rank = attrs.get("data-rank");
            //上次排名
            String previousRank = element.getElementsByClass("rank").get(0).childNode(1).childNode(0).attr("text");
            //pixivId
            String pixivId = attrs.get("data-id");

            PixivRankImageInfo tempImgInfo = new PixivRankImageInfo();
            tempImgInfo.setRank(NumberUtil.toInt(rank));
            tempImgInfo.setPreviousRank(previousRank);
            tempImgInfo.setPid(NumberUtil.toLong(pixivId));
            responseList.add(tempImgInfo);

            //取到指定数量即可
            if (pageSize <= responseList.size()) {
                return;
            }
        }
    }

    //拼装参数
    private void addParam() {
        param.put("mode", mode);
        param.put("content", content);
        param.put("date", date);
    }
}
