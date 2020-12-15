package cn.mikulink.service;

import cn.mikulink.apirequest.tracemoe.WhatAnimeApi;
import cn.mikulink.constant.ConstantAnime;
import cn.mikulink.entity.apirequest.tracemoe.WhatAnimeDoc;
import cn.mikulink.entity.apirequest.tracemoe.WhatAnimeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * create by MikuLink on 2020/2/21 14:18
 * for the Reisen
 * 以图搜番服务
 */
@Service
public class WhatAnimeService {
    private static final Logger logger = LoggerFactory.getLogger(WhatAnimeService.class);

    /**
     * 以图搜图，用trace.moe
     *
     * @param imgUrl 网络图片链接
     * @return 返回消息
     */
    public static String searchAnimeFromWhatAnime(String imgUrl) {
        try {
            WhatAnimeApi request = new WhatAnimeApi();
            request.setImgUrl(imgUrl);
            request.doRequest();
            WhatAnimeResult result = request.getEntity();

            List<WhatAnimeDoc> docList = result.getDocs();
            if (null == docList || docList.size() <= 0) {
                return ConstantAnime.TRACE_MOE_SEARCH_FAIL;
            }
            //只取第一个信息
            WhatAnimeDoc doc = docList.get(0);

            StringBuilder resultStr = new StringBuilder();
            resultStr.append("[相似度] " + (doc.getSimilarity() * 100) + "%");
            resultStr.append("\n[番名] " + doc.getAnime());
            resultStr.append("\n[档期] " + doc.getSeason());
            resultStr.append("\n[集数] " + doc.getEpisode());
            resultStr.append("\n[图片位置] " + doc.getAt() + "秒");
            resultStr.append("\n[文件名] " + doc.getFilename());
            resultStr.append("\n[番名(日文)] " + doc.getTitle_native());
            resultStr.append("\n[番名(英文)] " + doc.getTitle_english());
            resultStr.append("\n[番名(中文)] " + doc.getTitle_chinese());

            return resultStr.toString();
        } catch (IOException ioEx) {
            logger.error(ConstantAnime.TRACE_MOE_API_REQUEST_ERROR, ioEx);
            return ConstantAnime.TRACE_MOE_SEARCH_ERROR;
        }
    }
}
