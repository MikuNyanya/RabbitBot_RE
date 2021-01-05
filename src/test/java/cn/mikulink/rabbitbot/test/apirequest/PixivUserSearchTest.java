package cn.mikulink.rabbitbot.test.apirequest;

import cn.mikulink.rabbitbot.apirequest.pixiv.PixivUserSearch;
import org.junit.Test;

public class PixivUserSearchTest {

    @Test
    public void test() {
        try {
            PixivUserSearch request = new PixivUserSearch();
            request.getHeader().put("cookie", "__cfduid=da55ebedf1ee37cee69b813626fc82e441609871958; first_visit_datetime_pc=2021-01-06+03%3A39%3A18; p_ab_id=2; p_ab_id_2=9; p_ab_d_id=922231483; yuid_b=GJk2gnM; __utma=235335808.579827654.1609871963.1609871963.1609871963.1; __utmc=235335808; __utmz=235335808.1609871963.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __cf_bm=06208d4daead5f72f7daf142b94494f6a3a5dc54-1609871960-1800-AX5ACfpFSw76uLIXyic1avsa71ZagyfRmVVPtaCmPi9m84AV1JpjEBqP4r+6BgSBIkkHzWnRHIkzVY0MY9bjnM6+Bzv/p1dYL+rnJxRH1kGjlJ2e6AFjwTcCidZTHaWOmvOcwL2Kq+Y6dhV9htgUDr90Xfn+MixccCHrjUvW8Eoi; _ga=GA1.2.579827654.1609871963; _gid=GA1.2.727578025.1609871966; PHPSESSID=63552215_WD1eHB7eItAOQBJ2i8esls56F7bRx4V7; device_token=dd0270d5639ac86cff08c5758187b4ef; c_type=25; privacy_policy_agreement=2; a_type=0; b_type=1; ki_t=1609871985579%3B1609871985579%3B1609871985579%3B1%3B1; ki_r=; ki_s=; adr_id=hdkHCbhVAR5Jtky0EjY1OklEDSYQxfVoSpHW1Za0caKFtHB7; tag_view_ranking=0xsDLqCEW6~_EOd7bsGyl~QniSV1XRdS~_3oeEue7S7~zUV1dBrslN~ujS7cIBGO-~KOnmT1ndWG~aMSPvw-ONW~BSlt10mdnm; limited_ads=%7B%22responsive%22%3A%22%22%7D; login_ever=yes; __utmv=235335808.|2=login%20ever=yes=1^3=plan=normal=1^5=gender=male=1^6=user_id=63552215=1^9=p_ab_id=2=1^10=p_ab_id_2=9=1^11=lang=zh=1; __utmb=235335808.10.10.1609871963");
            request.setPixivUserNick("薯子");
            request.doRequest();

            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
