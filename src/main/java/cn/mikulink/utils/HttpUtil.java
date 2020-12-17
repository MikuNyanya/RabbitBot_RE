package cn.mikulink.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

/**
 * create by MikuLink on 2019/7/26 14:54
 * for the Reisen
 */
public class HttpUtil {
    //请求方式
    private static final String REQUEST_METHOD_GET = "GET";
    private static final String REQUEST_METHOD_POST = "POST";
    //链接超时时间
    private static final int CONNECT_TIME_OUT = 10000;
    //读取超时时间
    private static final int READ_TIME_OUT = 10000;
    //编码格式，UTF-8
    private static final String CHARSET_UTF8 = "UTF-8";

    /**
     * get请求
     *
     * @param connUrl 完整的请求链接
     * @return 接口返回报文
     * @throws IOException 请求异常
     */
    public static String get(String connUrl) throws IOException {
        return get(connUrl, null);
    }

    public static String get(String connUrl, Proxy proxy) throws IOException {
        //使用url对象打开一个链接
        HttpURLConnection httpURLConnection = null;
        if (null != proxy) {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection();
        }
        httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
        //设置链接超时时间
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        //设置返回超时时间
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        //模拟chrome
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        httpURLConnection.setRequestProperty("cookie","__cfduid=d0ff4b4cc2b7f1867df7f963b0b369f371606650332; first_visit_datetime_pc=2020-11-29+20%3A45%3A32; yuid_b=JiZFMGQ; p_ab_id=8; p_ab_id_2=8; p_ab_d_id=2116786429; __utmz=235335808.1606650336.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); _fbp=fb.1.1606650344862.1495176799; _ga=GA1.2.1499363042.1606650336; PHPSESSID=3151313_l23wvfqAygH2aAZip9krEfxARA5cmaA2; device_token=d706869bc7f0145f46f23e5d5bc9f30d; c_type=25; privacy_policy_agreement=2; a_type=0; b_type=1; adr_id=mEDklBY4J9f9IjkJdru78T81ooMGKVPY0NncuJFRogE2kRba; ki_r=; ki_s=212334%3A0.0.0.0.2; login_ever=yes; __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=3151313=1^9=p_ab_id=8=1^10=p_ab_id_2=8=1^11=lang=zh=1; ki_t=1607946070172%3B1607946070172%3B1607952493085%3B1%3B3; __utmc=235335808; categorized_tags=kP7msdIeEU~m3EJRa33xU; __cf_bm=b907d56848f61b19aa2d9971a9f0e48446e7ee5b-1608236179-1800-AR8iS6al4gcPt7JMH3pXSE9TwL92qdx/y5UkQBc62U4TXmE1HwpLQFLsZy+uuORzvezpYDntPrh9xeehu284/bEwg4PWTHhKMgTHTkXPjowT6oP128v4HVJq1mCKz5BQaTW6Of5ZMX8Yvr3PjcGXQ/Dt2g6OM5piYuCgLOXYIOAd; __utma=235335808.1499363042.1606650336.1608211250.1608236186.6; tag_view_ranking=zIv0cf5VVk~0xsDLqCEW6~_7r4EMWAAx~xgA3yCXKWS~m3EJRa33xU~n4OVgsIt_C~Kzwb_D669F~kP7msdIeEU~OT4SuGenFI~gpglyfLkWs~WjRN9ve4kb~Aa5GphyXq3~HlDdLQY3rl~2tBmt-ssFk~WzEZN2jz2H~_hSAdpN9rx~Q93StGtvUH~Ce-EdaHA-3~Bd2L9ZBE8q~RTJMXD26Ak~zyKU3Q5L4C~NE-E8kJhx6~3cT9FM3R6t~KN7uxuR89w; __utmt=1; __utmb=235335808.1.10.1608236186; tags_sended=1");
        //开始链接
        httpURLConnection.connect();

        //获取错误流
//        httpURLConnection.getErrorStream();
        //获取响应流
        InputStream rspInputStream = httpURLConnection.getInputStream();
        String rspStr = parseInputStreamStr(rspInputStream);

        //关闭流
        rspInputStream.close();
        //断开连接
        httpURLConnection.disconnect();
        return rspStr;
    }

    public static String get(String connUrl,Map<String,String> header, Proxy proxy) throws IOException {
        //使用url对象打开一个链接
        HttpURLConnection httpURLConnection = null;
        if (null != proxy) {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection();
        }
        httpURLConnection.setRequestMethod(REQUEST_METHOD_GET);
        //设置链接超时时间
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        //设置返回超时时间
        httpURLConnection.setReadTimeout(READ_TIME_OUT);
        //模拟chrome
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        //加入请求头
        for(String key : header.keySet()) {
            //忽略空的参数
            if(StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))){
                continue;
            }
            httpURLConnection.setRequestProperty(key, header.get(key));
        }
        //开始链接
        httpURLConnection.connect();

        //获取错误流
//        httpURLConnection.getErrorStream();
        //获取响应流
        InputStream rspInputStream = httpURLConnection.getInputStream();
        String rspStr = parseInputStreamStr(rspInputStream);

        //关闭流
        rspInputStream.close();
        //断开连接
        httpURLConnection.disconnect();
        return rspStr;
    }

    //转化流
    private static String parseInputStreamStr(InputStream inputStream) throws IOException {
        //编码这里暂时固定utf-8，以后根据返回的编码来
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        String tempStr = null;
        //逐行读取
        while ((tempStr = reader.readLine()) != null) {
            stringBuilder.append(tempStr);
            stringBuilder.append("\r\n");
        }
        return stringBuilder.toString();
    }


    /**
     * 转化为urlencode
     * 采用UTF-8格式
     *
     * @param params 转化的参数
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params) throws IOException {
        return parseUrlEncode(params, CHARSET_UTF8);
    }

    /**
     * 转化为urlencode
     *
     * @param params  转化的参数
     * @param charset 编码格式
     * @return 转化后的urlencode
     * @throws IOException 转化异常
     */
    public static String parseUrlEncode(Map<String, Object> params, String charset) throws IOException {
        //非空判断
        if (params == null || params.isEmpty()) {
            return null;
        }

        StringBuilder urlEncode = new StringBuilder();
        Set<Map.Entry<String, Object>> entries = params.entrySet();

        for (Map.Entry<String, Object> entry : entries) {
            String name = entry.getKey();
            String value = null;
            if (null != entry.getValue()) {
                value = entry.getValue().toString();
            }
            // 忽略参数名或参数值为空的参数
            if (StringUtil.isEmpty(name) || StringUtil.isEmpty(value)) {
                continue;
            }

            //多个参数之间使用&连接
            urlEncode.append("&");
            //转化格式，并拼接参数和值
            urlEncode.append(name).append("=").append(URLEncoder.encode(value, charset));
        }
        if (urlEncode.length() <= 0) {
            return "";
        }
        return "?" + urlEncode.substring(1);
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

    /**
     * 获取代理信息
     */
    public static Proxy getProxy() {
        // 创建代理 todo 地址和端口写为配置
        Proxy proxy = null;
        if (HttpUtil.checkProt("127.0.0.1", 31051)) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 31051));
        }
        return proxy;
    }
}
