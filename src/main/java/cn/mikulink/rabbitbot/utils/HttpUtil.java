package cn.mikulink.rabbitbot.utils;

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
    public static final String REQUEST_METHOD_GET = "GET";
    public static final String REQUEST_METHOD_POST = "POST";
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
        return get(connUrl, null, proxy);
    }

    public static String get(String connUrl, Map<String, String> header, Proxy proxy) throws IOException {
        HttpURLConnection httpURLConnection = getHttpURLConnection(connUrl, REQUEST_METHOD_GET, proxy);
        //模拟chrome
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");
        if (null != header) {
            //加入请求头
            for (String key : header.keySet()) {
                //忽略空的参数
                if (StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))) {
                    continue;
                }
                httpURLConnection.setRequestProperty(key, header.get(key));
            }
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

    public static HttpURLConnection getHttpURLConnection(String connUrl, String method, Proxy proxy) throws IOException {
        //使用url对象打开一个链接
        HttpURLConnection httpURLConnection = null;
        if (null != proxy) {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection(proxy);
        } else {
            httpURLConnection = (HttpURLConnection) new URL(connUrl).openConnection();
        }
        httpURLConnection.setRequestMethod(method);
        //设置链接超时时间
        httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
        //设置返回超时时间
        httpURLConnection.setReadTimeout(READ_TIME_OUT);

        return httpURLConnection;
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
            return "";
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
            urlEncode.append(name).append("=").append(EncodingUtil.encodeURIComponent(value));
        }
        if (urlEncode.length() <= 0) {
            return "";
        }
        return "?" + urlEncode.substring(1);
    }
}
