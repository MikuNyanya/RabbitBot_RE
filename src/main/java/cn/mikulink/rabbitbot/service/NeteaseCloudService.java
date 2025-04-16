package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.neteaseCloudMusic.NeteaseCloudSearch;
import cn.mikulink.rabbitbot.modules.oiapi.OiapiMusic163;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import cn.mikulink.rabbitbot.modules.oiapi.entity.Music163Response;
import cn.mikulink.rabbitbot.modules.oiapi.entity.Music163Singer;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChain;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageChainData;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MikuLink
 * @date 2022/9/26 16:00
 * for the Reisen
 * 网易云相关服务
 */
@Service
@Slf4j
public class NeteaseCloudService {
    @Value("${neteasecloud.apiurl:}")
    private String apiUrl;


    public NeteaseCloudSearchResponse.ResultBean.SongsBean searchByKeywords(String keyWords) throws IOException {
        NeteaseCloudSearch request = new NeteaseCloudSearch();
        request.setUrl(apiUrl);
        request.setKeywords(keyWords);

        request.doRequest();
        NeteaseCloudSearchResponse rsp = request.parseResponseInfo();
        List<NeteaseCloudSearchResponse.ResultBean.SongsBean> songs = rsp.getResult().getSongs();
        return CollectionUtil.isEmpty(songs) ? null : songs.get(0);
    }

    public MessageChain parseMessage(NeteaseCloudSearchResponse.ResultBean.SongsBean songInfo) {
        if (null == songInfo) {
            return null;
        }
        //网易云歌曲外链 http://music.163.com/song/media/outer/url?id=1952191507.mp3";
        //网易云歌曲页面：https://music.163.com/song?id=1952191507

        //歌曲点击后跳转到的页面
        String pageUrl = "https://music.163.com/song?id=" + songInfo.getId();
        //歌曲封面
        String musicImg = "https://p1.music.126.net/6y-UleORITEDbvrOLV0Q8A==/5639395138885805.jpg";
        //歌曲音频url
        String musicUrl = "http://music.163.com/song/media/outer/url?id=" + songInfo.getId() + ".mp3";
        //歌曲名称
        String title = songInfo.getName();
        //作者/演唱者
        String author = songInfo.getArtists().get(0).getName();

        MessageChainData data = new MessageChainData();
        data.setType("custom");
        data.setUrl(pageUrl);
        data.setAudio(musicUrl);
        data.setImage(musicImg);
        data.setTitle(title);
        data.setContent(author);


        MessageChain result = new MessageChain();
        result.setType("music");
        result.setData(data);

        return result;
    }


    public MessageChain searchKeywordsByOiapi(String keyWords) throws IOException {
        //获取网易云歌曲信息
        OiapiMusic163 request = new OiapiMusic163();
        request.setName(keyWords);
        request.doRequest();
        Music163Response rsp = request.parseResponseInfo();

        //网易云歌曲外链 http://music.163.com/song/media/outer/url?id=1952191507.mp3";
        //网易云歌曲页面：https://music.163.com/song?id=1952191507

        //转化成兔叽消息
        //歌曲点击后跳转到的页面
        String pageUrl = "https://music.163.com/song?id=" + rsp.getId();
        //歌曲封面
        String musicImg = rsp.getPicurl();
        //歌曲音频url
        String musicUrl = rsp.getUrl();
        //歌曲名称
        String title = rsp.getName();
        //作者/演唱者
        String author = rsp.getSingers().stream().map(Music163Singer::getName).collect(Collectors.joining("/"));

        MessageChainData data = new MessageChainData();
        data.setType("custom");
        data.setUrl(pageUrl);
        data.setAudio(musicUrl);
        data.setImage(musicImg);
        data.setTitle(title);
        data.setContent(author);

        MessageChain result = new MessageChain();
        result.setType("music");
        result.setData(data);

        return result;
    }
}
