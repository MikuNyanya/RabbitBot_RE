package cn.mikulink.rabbitbot.test.service;

import cn.mikulink.rabbitbot.utils.HttpsUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

/**
 * created by MikuNyanya on 2022/2/21 13:41
 * For the Reisen
 */
public class MeiPinServiceTest {

    @Test
    public void test() {
        //解析 article
        //article  header
        //使用jsoup解析html
        Document document = Jsoup.parse(htmlStr);
        //选择目标节点，类似于JS的选择器
//        Element element = document.getElementById("image-resize-link");
        Elements elements = document.getElementsByTag("article");

        //文本
        String text = elements.get(0).getElementsByTag("a").get(0).text();
        //链接
        String url = elements.get(0).getElementsByTag("a").get(0).attr("href");

        System.out.println("");
//        String imageUrl = null;
//        if (null != element) {
//            //获取原图
//            imageUrl = element.attributes().get("href");
//        } else {
//            //本身图片比较小就没有显示原图的
//            imageUrl = document.getElementById("image").attributes().get("src");
//        }
    }


    private String htmlStr = "<!DOCTYPE html>\n" +
            "<html lang=\"zh-CN\" class=\"no-js\">\n" +
            "<head>\n" +
            "<meta charset=\"UTF-8\">\n" +
            "<meta name=\"viewport\" content=\"width=device-width\">\n" +
            "<link rel=\"profile\" href=\"https://gmpg.org/xfn/11\">\n" +
            "<link rel=\"pingback\" href=\"http://meipin.im/xmlrpc.php\">\n" +
            "<!--[if lt IE 9]>\n" +
            "\t<script src=\"http://meipin.im/wp-content/themes/twentyfifteen/js/html5.js\"></script>\n" +
            "\t<![endif]-->\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\">(function(html){html.className = html.className.replace(/\\bno-js\\b/,'js')})(document.documentElement);</script>\n" +
            "<title>没品</title>\n" +
            "<link rel='dns-prefetch' href='//s.w.org' />\n" +
            "<link rel=\"alternate\" type=\"application/rss+xml\" title=\"没品 &raquo; Feed\" href=\"http://meipin.im/feed\" />\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\">\n" +
            "\t\t\twindow._wpemojiSettings = {\"baseUrl\":\"https:\\/\\/s.w.org\\/images\\/core\\/emoji\\/12.0.0-1\\/72x72\\/\",\"ext\":\".png\",\"svgUrl\":\"https:\\/\\/s.w.org\\/images\\/core\\/emoji\\/12.0.0-1\\/svg\\/\",\"svgExt\":\".svg\",\"source\":{\"concatemoji\":\"http:\\/\\/meipin.im\\/wp-includes\\/js\\/wp-emoji-release.min.js?ver=5.2.14\"}};\n" +
            "\t\t\t!function(e,a,t){var n,r,o,i=a.createElement(\"canvas\"),p=i.getContext&&i.getContext(\"2d\");function s(e,t){var a=String.fromCharCode;p.clearRect(0,0,i.width,i.height),p.fillText(a.apply(this,e),0,0);e=i.toDataURL();return p.clearRect(0,0,i.width,i.height),p.fillText(a.apply(this,t),0,0),e===i.toDataURL()}function c(e){var t=a.createElement(\"script\");t.src=e,t.defer=t.type=\"text/javascript\",a.getElementsByTagName(\"head\")[0].appendChild(t)}for(o=Array(\"flag\",\"emoji\"),t.supports={everything:!0,everythingExceptFlag:!0},r=0;r<o.length;r++)t.supports[o[r]]=function(e){if(!p||!p.fillText)return!1;switch(p.textBaseline=\"top\",p.font=\"600 32px Arial\",e){case\"flag\":return s([55356,56826,55356,56819],[55356,56826,8203,55356,56819])?!1:!s([55356,57332,56128,56423,56128,56418,56128,56421,56128,56430,56128,56423,56128,56447],[55356,57332,8203,56128,56423,8203,56128,56418,8203,56128,56421,8203,56128,56430,8203,56128,56423,8203,56128,56447]);case\"emoji\":return!s([55357,56424,55356,57342,8205,55358,56605,8205,55357,56424,55356,57340],[55357,56424,55356,57342,8203,55358,56605,8203,55357,56424,55356,57340])}return!1}(o[r]),t.supports.everything=t.supports.everything&&t.supports[o[r]],\"flag\"!==o[r]&&(t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&t.supports[o[r]]);t.supports.everythingExceptFlag=t.supports.everythingExceptFlag&&!t.supports.flag,t.DOMReady=!1,t.readyCallback=function(){t.DOMReady=!0},t.supports.everything||(n=function(){t.readyCallback()},a.addEventListener?(a.addEventListener(\"DOMContentLoaded\",n,!1),e.addEventListener(\"load\",n,!1)):(e.attachEvent(\"onload\",n),a.attachEvent(\"onreadystatechange\",function(){\"complete\"===a.readyState&&t.readyCallback()})),(n=t.source||{}).concatemoji?c(n.concatemoji):n.wpemoji&&n.twemoji&&(c(n.twemoji),c(n.wpemoji)))}(window,document,window._wpemojiSettings);\n" +
            "\t\t</script>\n" +
            "<style type=\"text/css\">\n" +
            "img.wp-smiley,\n" +
            "img.emoji {\n" +
            "\tdisplay: inline !important;\n" +
            "\tborder: none !important;\n" +
            "\tbox-shadow: none !important;\n" +
            "\theight: 1em !important;\n" +
            "\twidth: 1em !important;\n" +
            "\tmargin: 0 .07em !important;\n" +
            "\tvertical-align: -0.1em !important;\n" +
            "\tbackground: none !important;\n" +
            "\tpadding: 0 !important;\n" +
            "}\n" +
            "</style>\n" +
            "<link rel='stylesheet' id='genericons-css' href='http://meipin.im/wp-content/themes/twentyfifteen/genericons/genericons.css?ver=3.2' type='text/css' media='all' />\n" +
            "<link rel='stylesheet' id='twentyfifteen-style-css' href='http://meipin.im/wp-content/themes/twentyfifteen/style.css?ver=5.2.14' type='text/css' media='all' />\n" +
            "<!--[if lt IE 9]>\n" +
            "<link rel='stylesheet' id='twentyfifteen-ie-css'  href='http://meipin.im/wp-content/themes/twentyfifteen/css/ie.css?ver=20141010' type='text/css' media='all' />\n" +
            "<![endif]-->\n" +
            "<!--[if lt IE 8]>\n" +
            "<link rel='stylesheet' id='twentyfifteen-ie7-css'  href='http://meipin.im/wp-content/themes/twentyfifteen/css/ie7.css?ver=20141010' type='text/css' media='all' />\n" +
            "<![endif]-->\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\" src='http://meipin.im/wp-includes/js/jquery/jquery.js?ver=1.12.4-wp'></script>\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\" src='http://meipin.im/wp-includes/js/jquery/jquery-migrate.min.js?ver=1.4.1'></script>\n" +
            "<link rel='https://api.w.org/' href='http://meipin.im/wp-json/' />\n" +
            "<link rel=\"EditURI\" type=\"application/rsd+xml\" title=\"RSD\" href=\"http://meipin.im/xmlrpc.php?rsd\" />\n" +
            "<link rel=\"wlwmanifest\" type=\"application/wlwmanifest+xml\" href=\"http://meipin.im/wp-includes/wlwmanifest.xml\" />\n" +
            "<meta name=\"generator\" content=\"WordPress 5.2.14\" />\n" +
            "<meta name=\"referrer\" content=\"never\">\n" +
            "<style type=\"text/css\" id=\"wp-custom-css\">\n" +
            "\t\t\thtml, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p, blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn, em, font, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var, dl, dt, dd, ol, ul, li, fieldset, form, label, legend, table, caption, tbody, tfoot, thead, tr, th, td{font-size: 99%;font-family: \"Microsoft YaHei\", Tahoma, Arial;line-height: 1.5;}\n" +
            "p {margin:0 !important;}\n" +
            "body, button, input, select, textarea{line-height: 1.5;}\n" +
            ".entry-title, .widecolumn h2{font-size: 2rem;}\n" +
            ".entry-content h4, .entry-summary h4, .page-content h4, .comment-content h4,.comment-reply-title,.page-title, .comments-title, .comment-reply-title, .post-navigation .post-title{font-size: 1.5rem;margin:0 !important;}\n" +
            ".comments-title{font-size: 1.2em;}\n" +
            ".entry-footer{font-size: 1em;}\n" +
            ".site-title{font-size: 2em;}\n" +
            ".widget-title{font-size: 1rem;}\n" +
            ".textwidget,.comment-metadata{font-size: 0.8rem;}\n" +
            ".comment-list .reply a{margin-top:10px !important;}\n" +
            "comment-content{margin:0 !important;}\n" +
            ".site-info{display:none;}\t\t</style>\n" +
            "</head>\n" +
            "<body class=\"home blog\">\n" +
            "<div id=\"page\" class=\"hfeed site\">\n" +
            "<a class=\"skip-link screen-reader-text\" href=\"#content\">跳至正文</a>\n" +
            "<div id=\"sidebar\" class=\"sidebar\">\n" +
            "<header id=\"masthead\" class=\"site-header\" role=\"banner\">\n" +
            "<div class=\"site-branding\">\n" +
            "<h1 class=\"site-title\"><a href=\"http://meipin.im/\" rel=\"home\">没品</a></h1>\n" +
            "<button class=\"secondary-toggle\">菜单和挂件</button>\n" +
            "</div>\n" +
            "</header>\n" +
            "<div id=\"secondary\" class=\"secondary\">\n" +
            "<div id=\"widget-area\" class=\"widget-area\" role=\"complementary\">\n" +
            "<aside id=\"text-3\" class=\"widget widget_text\"><h2 class=\"widget-title\">关于本站</h2> <div class=\"textwidget\"><p>由Fall_ark翻译的没品+4chan笑话集，<br />\n" +
            "每周日更新。</p>\n" +
            "</div>\n" +
            "</aside><aside id=\"text-6\" class=\"widget widget_text\"><h2 class=\"widget-title\">瞎买网</h2> <div class=\"textwidget\"><p><a href=\"https://xiamai.net/\" target=\"_blank\" rel=\"noopener noreferrer\"><img src=\"https://ww1.sinaimg.cn/mw690/00745YaMgy1gu95zq2d1qj60go09g0vc02.jpg\" referrerpolicy=\"no-referrer\" height=\"170\" width=\"300\" /></a></p>\n" +
            "</div>\n" +
            "</aside> </div>\n" +
            "</div>\n" +
            "</div>\n" +
            "<div id=\"content\" class=\"site-content\">\n" +
            "<div id=\"primary\" class=\"content-area\">\n" +
            "<main id=\"main\" class=\"site-main\" role=\"main\">\n" +
            "<article id=\"post-101494\" class=\"post-101494 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101494\" rel=\"bookmark\">没品+4chan笑话集：你们在电视上看体育运动的全都是绿帽奴！</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101494\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-02-20T19:56:49+08:00\">2022/02/20</time><time class=\"updated\" datetime=\"2022-02-20T19:57:25+08:00\">2022/02/20</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101491\" class=\"post-101491 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101491\" rel=\"bookmark\">没品+4chan笑话集：国际象棋这游戏也太政治正确了！</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101491\" rel=\"bookmark\"><time class=\"entry-date published updated\" datetime=\"2022-02-13T19:49:36+08:00\">2022/02/13</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101488\" class=\"post-101488 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101488\" rel=\"bookmark\">没品+4chan笑话集：不管是男是女，只要衣服穿的少就是最好的</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101488\" rel=\"bookmark\"><time class=\"entry-date published updated\" datetime=\"2022-02-06T20:54:26+08:00\">2022/02/06</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101484\" class=\"post-101484 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101484\" rel=\"bookmark\">没品+4chan笑话集：暴雪要出生存类游戏了，你们有什么想法吗？</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101484\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-01-30T19:35:59+08:00\">2022/01/30</time><time class=\"updated\" datetime=\"2022-02-02T00:00:46+08:00\">2022/02/02</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101481\" class=\"post-101481 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101481\" rel=\"bookmark\">没品+4chan笑话集：这种黄油正常人会去玩吗？</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101481\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-01-23T18:58:12+08:00\">2022/01/23</time><time class=\"updated\" datetime=\"2022-01-23T23:25:58+08:00\">2022/01/23</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101475\" class=\"post-101475 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101475\" rel=\"bookmark\">没品+4chan笑话集：没有女友事小，增不了肌事大</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101475\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-01-16T20:07:53+08:00\">2022/01/16</time><time class=\"updated\" datetime=\"2022-01-16T20:11:14+08:00\">2022/01/16</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101472\" class=\"post-101472 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101472\" rel=\"bookmark\">没品+4chan笑话集：在乱伦片里寻求真实感是否搞错了什么？</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101472\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-01-09T20:09:36+08:00\">2022/01/09</time><time class=\"updated\" datetime=\"2022-01-09T20:09:52+08:00\">2022/01/09</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101468\" class=\"post-101468 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101468\" rel=\"bookmark\">没品+4chan笑话集：在世界的中心呼唤nai</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101468\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2022-01-02T19:58:55+08:00\">2022/01/02</time><time class=\"updated\" datetime=\"2022-01-03T00:22:41+08:00\">2022/01/03</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101463\" class=\"post-101463 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101463\" rel=\"bookmark\">没品+4chan笑话集：声优最重要的，当然是胸大</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101463\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2021-12-26T19:50:00+08:00\">2021/12/26</time><time class=\"updated\" datetime=\"2021-12-27T10:33:18+08:00\">2021/12/27</time></a></span> </footer>\n" +
            "</article>\n" +
            "<article id=\"post-101457\" class=\"post-101457 post type-post status-publish format-standard hentry category-mp\">\n" +
            "<header class=\"entry-header\">\n" +
            "<h2 class=\"entry-title\"><a href=\"http://meipin.im/p/101457\" rel=\"bookmark\">没品+4chan笑话集：爱她就让她gang你啊</a></h2> </header>\n" +
            "<footer class=\"entry-footer\">\n" +
            "<span class=\"posted-on\"><span class=\"screen-reader-text\">发布于 </span><a href=\"http://meipin.im/p/101457\" rel=\"bookmark\"><time class=\"entry-date published\" datetime=\"2021-12-19T19:41:52+08:00\">2021/12/19</time><time class=\"updated\" datetime=\"2021-12-20T00:03:53+08:00\">2021/12/20</time></a></span> </footer>\n" +
            "</article>\n" +
            "<nav class=\"navigation pagination\" role=\"navigation\">\n" +
            "<h2 class=\"screen-reader-text\">文章导航</h2>\n" +
            "<div class=\"nav-links\"><span aria-current='page' class='page-numbers current'><span class=\"meta-nav screen-reader-text\">页 </span>1</span>\n" +
            "<a class='page-numbers' href='http://meipin.im/page/2'><span class=\"meta-nav screen-reader-text\">页 </span>2</a>\n" +
            "<span class=\"page-numbers dots\">&hellip;</span>\n" +
            "<a class='page-numbers' href='http://meipin.im/page/57'><span class=\"meta-nav screen-reader-text\">页 </span>57</a>\n" +
            "<a class=\"next page-numbers\" href=\"http://meipin.im/page/2\">下一页</a></div>\n" +
            "</nav>\n" +
            "</main>\n" +
            "</div>\n" +
            "</div>\n" +
            "<footer id=\"colophon\" class=\"site-footer\" role=\"contentinfo\">\n" +
            "<div class=\"site-info\">\n" +
            "<a href=\"https://cn.wordpress.org/\" class=\"imprint\">\n" +
            "自豪地采用WordPress </a>\n" +
            "</div>\n" +
            "</footer>\n" +
            "</div>\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\" src='http://meipin.im/wp-content/themes/twentyfifteen/js/skip-link-focus-fix.js?ver=20141010'></script>\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\">\n" +
            "/* <![CDATA[ */\n" +
            "var screenReaderText = {\"expand\":\"<span class=\\\"screen-reader-text\\\">\\u5c55\\u5f00\\u5b50\\u83dc\\u5355<\\/span>\",\"collapse\":\"<span class=\\\"screen-reader-text\\\">\\u6298\\u53e0\\u5b50\\u83dc\\u5355<\\/span>\"};\n" +
            "/* ]]> */\n" +
            "</script>\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\" src='http://meipin.im/wp-content/themes/twentyfifteen/js/functions.js?ver=20150330'></script>\n" +
            "<script type=\"50b0be3989e2a9cbf2e3bb07-text/javascript\" src='http://meipin.im/wp-includes/js/wp-embed.min.js?ver=5.2.14'></script>\n" +
            "<script src=\"/cdn-cgi/scripts/7d0fa10a/cloudflare-static/rocket-loader.min.js\" data-cf-settings=\"50b0be3989e2a9cbf2e3bb07-|49\" defer=\"\"></script></body>\n" +
            "</html>\n";
}

