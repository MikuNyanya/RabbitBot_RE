package cn.mikulink.rabbitbot.service.sys;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustRankGet;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.Proxy;
import java.net.SocketException;

/**
 * created by MikuNyanya on 2021/7/5 9:49
 * For the Reisen
 * 代理相关服务
 */
@Slf4j
@Service
public class ProxyService {

    @Value("${proxy.address:127.0.0.1}")
    private String proxyAddress;
    @Value("${proxy.prot:31051}")
    private Integer proxyProt;

    public Proxy getProxy() {
        return getProxy(proxyAddress, proxyProt);
    }

    /**
     * 根据地址和端口获取代理
     *
     * @param address 代理地址
     * @param prot    代理端口
     * @return 代理类
     */
    public Proxy getProxy(String address, Integer prot) {
        return ProxyUtil.getProxy(address, prot);
    }

    public void proxyCheck() {
        proxyCheck(proxyAddress, proxyProt);
    }

    /**
     * 检测代理是否可用
     */
    public void proxyCheck(String address, Integer prot) {
        //检查链接和端口是否存在
        if (!ProxyUtil.checkProt(address, prot)) {
            //通知最高权限
            return;
        }

        //发送一个需要代理的请求，以检测是否可以连接成功
        try {
            //获取排行榜信息
            PixivIllustRankGet request = new PixivIllustRankGet();
            request.setMode("daily");
            request.setContent("illust");
            request.setPageSize(10);
            request.setProxy(getProxy());
            request.doRequest();
        } catch (SocketException socketEx) {
            //链接超时，大概率是代理挂了
            log.info("ProxyService proxyCheck 通过代理请求超时", socketEx);
            //通知最高权限

        } catch (Exception ex) {
            //其他异常
            log.error("ProxyService proxyCheck 代理检测异常", ex);
        }
    }
}
