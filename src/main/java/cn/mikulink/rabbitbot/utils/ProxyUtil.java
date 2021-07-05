package cn.mikulink.rabbitbot.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;

/**
 * created by MikuNyanya on 2021/7/5 9:51
 * For the Reisen
 * 代理工具类
 */
public class ProxyUtil {
    /**
     * 获取代理信息
     */
    public static Proxy getProxy(String address, Integer prot) {
        // 创建代理
        Proxy proxy = null;
        if (checkProt(address, prot)) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(address, prot));
        }
        return proxy;
    }

    /**
     * 检查端口是否可用
     * 经过测试，好像只要端口被占用了就判定链接成功
     * ssr不开局域网端口，或者开了以后没开代理，端口也是通的
     *
     * @param address 地址
     * @param prot    端口
     * @return 是否可用
     */
    public static boolean checkProt(String address, int prot) {
        if (StringUtil.isEmpty(address)) {
            return false;
        }
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(address, prot));
            socket.close();
            return true;
        } catch (IOException ioEx) {
            //没那么重要，直接打在控制台里
            ioEx.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException ioEx) {
            //没那么重要，直接打在控制台里
            ioEx.printStackTrace();
        }

        return false;
    }
}
