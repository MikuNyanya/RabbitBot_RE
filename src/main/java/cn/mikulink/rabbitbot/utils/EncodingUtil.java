package cn.mikulink.rabbitbot.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * created by MikuNyanya on 2021/3/17 11:36
 * For the Reisen
 * http链接编码什么的
 * http://www.blogjava.net/liuyz2006/articles/385742.html
 */
public class EncodingUtil {
    //region 相关资料
    /*
     * js对文字进行编码涉及3个函数：escape,encodeURI,encodeURIComponent，相应3个解码函数：unescape,decodeURI,decodeURIComponent
     *
     * 1、传递参数时需要使用encodeURIComponent，这样组合的url才不会被#等特殊字符截断。
     * 例如：<script language="javascript">document.write('<a href="http://passport.baidu.com/?logout&aid=7&u='+encodeURIComponent("http://cang.baidu.com/bruce42")+'">退出</a>');</script>
     *
     * 2、进行url跳转时可以整体使用encodeURI
     * 例如：Location.href=encodeURI("http://cang.baidu.com/do/s?word=百度&ct=21");
     *
     * 3、js使用数据时可以使用escape
     * 例如：搜藏中history纪录。
     *
     * 4、escape对0-255以外的unicode值进行编码时输出%u****格式，其它情况下escape，encodeURI，encodeURIComponent编码结果相同。
     *
     * 最多使用的应为encodeURIComponent，它是将中文、韩文等特殊字符转换成utf-8格式的url编码，所以如果给后台传递参数需要使用encodeURIComponent时需要后台解码对utf-8支持（form中的编码方式和当前页面编码方式相同）
     *
     * escape不编码字符有69个：*，+，-，.，/，@，_，0-9，a-z，A-Z
     * encodeURI不编码字符有82个：!，#，$，&，'，(，)，*，+，,，-，.，/，:，;，=，?，@，_，~，0-9，a-z，A-Z
     * encodeURIComponent不编码字符有71个：!， '，(，)，*，-，.，_，~，0-9，a-z，A-Z
     *
     * 在传递非英文字符串的处理：
     * URL： addressgrp.action?oper=addgrp&groupname="+encodeURIComponent(groupsname)
     * 在action获取 groupname（ getGroupname() ）时并没有进过编码转化，获取的信息并不正确，经过尝试发现需要进行一次编码转换：String grpname= new String(getGroupname().getBytes("ISO-8859-1"), "UTF-8");
     * 在网上搜索相关的信息，网上提供了另一种方法：在页面上进行两次编码操作，然后在后台再进行一次解码，这是由于java后台在获取数据时已经进行了一次解码，可问题是进行一次解码后的数据并不正确，而如果在页面中编码两次然后在后台进行一次解码就可以获取真确的数据。页面URL： addressgrp.action?oper=addgrp&groupname="+ encodeURIComponent（ encodeURIComponent(groupsname)）
     * 后台获取数据： String grpname =java.net.URLDecoder.decode( getGroupname() ,"UTF-8")
     */
    //endregion

    /**
     * Decodes the passed UTF-8 String using an algorithm that's compatible with
     * JavaScript's <code>decodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The UTF-8 encoded String to be decoded
     * @return the decoded String
     */
    public static String decodeURIComponent(String s) {
        if (s == null) {
            return null;
        }

        String result = null;

        try {
            result = URLDecoder.decode(s, "UTF-8");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    /**
     * Encodes the passed String as UTF-8 using an algorithm that's compatible
     * with JavaScript's <code>encodeURIComponent</code> function. Returns
     * <code>null</code> if the String is <code>null</code>.
     *
     * @param s The String to be encoded
     * @return the encoded String
     */
    public static String encodeURIComponent(String s) {
        String result = null;

        try {
            result = URLEncoder.encode(s, "UTF-8")
                    .replaceAll("\\+", "%20")
                    .replaceAll("\\%21", "!")
                    .replaceAll("\\%27", "'")
                    .replaceAll("\\%28", "(")
                    .replaceAll("\\%29", ")")
                    .replaceAll("\\%7E", "~");
        }

        // This exception should never occur.
        catch (UnsupportedEncodingException e) {
            result = s;
        }

        return result;
    }

    /**
     * Private constructor to prevent this class from being instantiated.
     */
    private EncodingUtil() {
        super();
    }
}
