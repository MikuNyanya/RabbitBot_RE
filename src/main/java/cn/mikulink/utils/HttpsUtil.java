package cn.mikulink.utils;


import javax.net.ssl.*;
import java.io.*;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
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

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method) throws IOException {
        return getHttpsURLConnection(uri, method, null);
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method, Proxy proxy) throws IOException {
        return getHttpsURLConnection(uri, method, null, proxy);
    }

    private static HttpsURLConnection getHttpsURLConnection(String uri, String method, Map<String, String> header, Proxy proxy) throws IOException {
        SSLContext ctx = null;
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new DefaultTrustManager()}, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SSLSocketFactory ssf = ctx.getSocketFactory();

        URL url = new URL(uri);

        HttpsURLConnection httpsConn = null;
        if (null != proxy) {
            httpsConn = (HttpsURLConnection) url.openConnection(proxy);
        } else {
            httpsConn = (HttpsURLConnection) url.openConnection();
        }
        httpsConn.setSSLSocketFactory(ssf);
        httpsConn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//        httpsConn.setRequestProperty("Authorization", "username");
        httpsConn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36");

        if (null != header) {
            //加入请求头
            for (String key : header.keySet()) {
                //忽略空的参数
                if (StringUtil.isEmpty(key) || StringUtil.isEmpty(header.get(key))) {
                    continue;
                }
                httpsConn.setRequestProperty(key, header.get(key));
            }
        }
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

    public static byte[] doPost(String uri, String data) throws IOException {
        HttpsURLConnection httpsConn = getHttpsURLConnection(uri, "POST");
        setBytesToStream(httpsConn.getOutputStream(), data.getBytes());
        return getBytesFromStream(httpsConn.getInputStream());
    }
}
