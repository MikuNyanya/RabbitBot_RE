package cn.mikulink.rabbitbot.utils;


import javax.net.ssl.*;
import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * create by MikuLink on 2020/02/19 14:54
 * for the Reisen
 * 复制来的代码，https证书怎么就搞不懂呢
 * https://blog.csdn.net/lufeiRversing/article/details/85683417
 */
public class HttpsUtil {


    private static final int CONNECT_TIME_OUT = 10000;
    private static final int READ_TIME_OUT = 15000;

    private static final class DefaultTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    /**
     * get请求方法列表
     */
    public static byte[] doGet(String uri) throws IOException {
        return doGet(uri, null);
    }

    public static byte[] doGet(String uri, Proxy proxy) throws IOException {
        return doGet(uri, null, proxy);
    }

    public static byte[] doGet(String uri, Map<String, String> header, Proxy proxy) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "GET", header, proxy);
        return getBytesFromStream(httpsConn.getInputStream());
    }


    /**
     * post请求方法列表
     */
    public static byte[] doPost(String uri, String data) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
        setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        return getBytesFromStream(httpsConn.getInputStream());
    }


    /**
     * 获取Https链接
     */
    public static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        return getHttpsURLConnection(uri, method, null);
    }

    public static HttpsURLConnection getHttpsURLConnection(String uri, String method, Proxy proxy) throws IOException {
        return getHttpsURLConnection(uri, method, null, proxy);
    }

    /**
     * @param uri    请求链接
     * @param method 请求方式，比如get post
     * @param header 请求头，有时候需要
     * @param proxy  代理
     * @return http链接对象
     * @throws IOException  链接异常
     */
    public static HttpsURLConnection getHttpsURLConnection(String uri, String method, Map<String, String> header, Proxy proxy) throws IOException {
        URL url = new URL(uri);

        HttpsURLConnection httpsConn = null;
        if (null != proxy) {
            httpsConn = (HttpsURLConnection) url.openConnection(proxy);
        } else {
            httpsConn = (HttpsURLConnection) url.openConnection();
        }
        SSLSocketFactory ssf = getSSLSocketFactory();
        httpsConn.setSSLSocketFactory(ssf);

        //加入请求头
        setHeaderParam(httpsConn,header);

        /*
        在握手期间，如果 URL 的主机名和服务器的标识主机名不匹配，
        则验证机制可以回调此接口的实现程序来确定是否应该允许此连接。
        策略可以是基于证书的或依赖于其他验证方案。
        当验证 URL 主机名使用的默认规则失败时使用这些回调。
         */
        httpsConn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
        httpsConn.setRequestMethod(method);
        httpsConn.setDoInput(true);
        httpsConn.setDoOutput(true);
        //链接超时时间
        httpsConn.setConnectTimeout(CONNECT_TIME_OUT);
        //读取资源超时时间
        httpsConn.setReadTimeout(READ_TIME_OUT);
        return httpsConn;
    }

    /**
     * 获取请求结果
     */
    private static byte[] getBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] kb = new byte[1024];
        int len;
        while ((len = is.read(kb)) != -1) {
            baos.write(kb, 0, len);
        }
        byte[] bytes = baos.toByteArray();
        baos.close();
        is.close();
        return bytes;
    }

    /**
     * 添加post请求参数
     */
    private static void setBytesToStream(OutputStream os, byte[] bytes) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        byte[] kb = new byte[1024];
        int len;
        while ((len = bais.read(kb)) != -1) {
            os.write(kb, 0, len);
        }
        os.flush();
        os.close();
        bais.close();
    }


    //获取SSLSocketFactory
    //SSLSocket通信 应该是种协议吧
    public static SSLSocketFactory getSSLSocketFactory() {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();
        return ssf;
    }

    //添加请求头
    public static void setHeaderParam(HttpsURLConnection httpsConn, Map<String, String> header) {
        if (null == header) {
            header = new HashMap<>();
        }
        //媒体格式
        //region 媒体格式参数范例
        //例如： Content-Type: text/html;charset:utf-8;
        //
        // 常见的媒体格式类型如下：
        //    text/html ： HTML格式
        //    text/plain ：纯文本格式
        //    text/xml ：  XML格式
        //    image/gif ：gif图片格式
        //    image/jpeg ：jpg图片格式
        //    image/png：png图片格式

        //   以application开头的媒体格式类型：
        //   application/xhtml+xml ：XHTML格式
        //   application/xml     ： XML数据格式
        //   application/atom+xml  ：Atom XML聚合格式
        //   application/json    ： JSON数据格式
        //   application/pdf       ：pdf格式
        //   application/msword  ： Word文档格式
        //   application/octet-stream ： 二进制流数据（如常见的文件下载）
        //   application/x-www-form-urlencoded ： <form encType=””>中默认的encType，form表单数据被编码为key/value格式发送到服务器（表单默认的提交数据的格式）
        //   另外一种常见的媒体格式是上传文件之时使用的：
        //    multipart/form-data ： 需要在表单中进行文件上传时，就需要使用该格式
        //endregion
        header.put("Content-Type", "application/json;charset=utf-8");
        //模拟谷歌浏览器
        //region User-Agent简介与构成
        //这段字符串是在chrome的开发者工具里提取的，网页请求里可以见到，所以没必要非得自己一点点拼
        //各部分介绍：
        //https://www.jianshu.com/p/c5cf6a1967d1
        //User-Agent会告诉网站服务器，访问者是通过什么工具来请求的，如果是爬虫请求，一般会拒绝，如果是用户浏览器，就会应答
        //第一部分：Mozilla/5.0 由于历史上的浏览器大战，当时想获得图文并茂的网页，就必须宣称自己是 Mozilla 浏览器。此事导致如今User-Agent里通常都带有Mozilla字样，出于对历史的尊重，大家都会默认填写该部分。
        //第二部分：操作平台
        //第三部分：引擎版本 AppleWebKit/537.36 (KHTML, like Gecko)...Safari/537.36，历史上，苹果依靠了WebKit内核开发出Safari浏览器，WebKit包含了WebCore引擎，而WebCore又从KHTML衍生而来。由于历史原因，KHTML引擎需要声明自己是“类似Gecko”的，因此引擎部分这么写。再后来，Google开发Chrome也是用了WebKit内核，于是也跟着这么写。借用Littern的一句话：“Chrome 希望能得到为Safari编写的网页，于是决定装成Safari，Safari使用了WebKit渲染引擎，而WebKit呢又伪装自己是KHTML，KHTML呢又是伪装成Gecko的。同时所有的浏览器又都宣称自己是Mozilla。”。不过，后来Chrome 28某个版本改用了blink内核，但还是保留了这些字符串。而且，最近的几十个版本中，这部分已经固定，没再变过
        //第四部分：浏览器版本 这里是Chrome
        //endregion
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

        //加入请求头
        for (String key : header.keySet()) {
            //忽略空的参数
            if (StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))) {
                continue;
            }
            httpsConn.setRequestProperty(key, header.get(key));
        }

    }

}
