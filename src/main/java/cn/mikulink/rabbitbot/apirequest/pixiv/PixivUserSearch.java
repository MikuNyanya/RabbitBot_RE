package cn.mikulink.rabbitbot.apirequest.pixiv;

import cn.mikulink.rabbitbot.apirequest.BaseRequest;
import cn.mikulink.rabbitbot.entity.pixiv.PixivUserInfo;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import cn.mikulink.rabbitbot.utils.HttpUtil;
import cn.mikulink.rabbitbot.utils.HttpsUtil;
import cn.mikulink.rabbitbot.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by MikuLink on 2021/1/6 3:33
 * for the Reisen
 * 搜索pixiv用户 只搜索投稿用户
 * https://www.pixiv.net/search_user.php?nick=初音ミク&s_mode=s_usr
 */
@Setter
@Getter
public class PixivUserSearch extends BaseRequest {
    //模拟页面请求，需要解析html
    private static final String URL = "https://www.pixiv.net/search_user.php?nick=%s&s_mode=s_usr&p=%s";

    /**
     * p站用户名称
     */
    private String pixivUserNick;
    /**
     * 页数
     * 一般没那么多重名的
     */
    private Integer p = 1;

    /**
     * pixiv用户列表
     */
    private List<PixivUserInfo> responseList = new ArrayList<>();

    /**
     * 执行请求
     *
     * @throws IOException 所有异常上抛，由业务处理
     */
    public void doRequest() throws IOException {
        if (StringUtil.isEmpty(pixivUserNick)) return;

        //挂上referer
        header.put("referer", String.format("https://www.pixiv.net/tags/%s/artworks?s_mode=s_tag", pixivUserNick));

        //返回的是一个html
        byte[] resultBytes = HttpsUtil.doGet(String.format(URL, pixivUserNick, p), header, HttpUtil.getProxy());
        body = new String(resultBytes);
        //使用jsoup解析html
        Document document = Jsoup.parse(body);

        //选择目标节点，类似于JS的选择器
        Elements rankElements = document.getElementsByClass("user-recommendation-item");
        //未找到任何用户
        if (CollectionUtil.isEmpty(rankElements)) return;

        //解析用户
        for (Element element : rankElements) {
            //<a href="/users/6098039"class="_user-icon size-128 cover-texture ui-scroll-view"target="_blank"title="初音ミクの猫"data-filter="lazy-image"data-src="https://i.pximg.net/user-profile/img/2018/11/02/15/48/48/14969934_e78e358458e1a790ac4b75cacfba0c11_170.png">
            String userNick = element.childNodes().get(0).attr("title");
            String userId = element.childNodes().get(0).attr("href");
            String logo = element.childNodes().get(0).attr("data-src");
            userId = userId.substring(userId.lastIndexOf("/") + 1);

            PixivUserInfo tempUserInfo = new PixivUserInfo();
            tempUserInfo.setId(userId);
            tempUserInfo.setNick(userNick);
            tempUserInfo.setLogoUrl(logo);
            responseList.add(tempUserInfo);
        }
    }
}
