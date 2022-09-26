package cn.mikulink.rabbitbot.service;

import cn.mikulink.rabbitbot.apirequest.neteaseCloudMusic.NeteaseCloudSearch;
import cn.mikulink.rabbitbot.entity.apirequest.neteaseCloud.NeteaseCloudSearchResponse;
import cn.mikulink.rabbitbot.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.MusicKind;
import net.mamoe.mirai.message.data.MusicShare;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

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

    public MusicShare parseMessage(NeteaseCloudSearchResponse.ResultBean.SongsBean songInfo) {
        if (null == songInfo) {
            return null;
        }

        //网易云歌曲外链 http://music.163.com/song/media/outer/url?id=1952191507.mp3";
        //网易云歌曲页面：https://music.163.com/song?id=1952191507

        return new MusicShare(
                MusicKind.NeteaseCloudMusic,
                songInfo.getName(),
                songInfo.getAr().get(0).getName(),
                "https://music.163.com/song?id=" + songInfo.getId(),
                songInfo.getAl().getPicUrl(),
                "http://music.163.com/song/media/outer/url?id=" + songInfo.getId() + ".mp3",
                "[震惊]兔子要吃小铃子");
    }

}
