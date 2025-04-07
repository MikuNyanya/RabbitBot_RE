package cn.mikulink.rabbitbot.service.sys;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivIllustRankGet;
import cn.mikulink.rabbitbot.bot.RabbitBotService;
import cn.mikulink.rabbitbot.utils.ProxyUtil;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Service
public class ProxyService {
    private static final Logger logger = LoggerFactory.getLogger(ProxyService.class);

    @Value("${proxy.address:127.0.0.1}")
    private String proxyAddress;
    @Value("${proxy.prot:31051}")
    private Integer proxyProt;

    @Autowired
    private RabbitBotService rabbitBotService;


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
            MessageChain messageChain = MessageUtils.newChain();
            messageChain = messageChain.plus("代理检测：无效的地址或端口，请检查代理配置");
            rabbitBotService.sendMasterMessage(messageChain);
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
            logger.info("ProxyService proxyCheck 通过代理请求超时", socketEx);
            //通知最高权限
            MessageChain messageChain = MessageUtils.newChain();
            messageChain = messageChain.plus("代理检测：链接超时，请检查代理状态");
            rabbitBotService.sendMasterMessage(messageChain);
        } catch (Exception ex) {
            //其他异常
            logger.error("ProxyService proxyCheck 代理检测异常", ex);
        }
    }
}
