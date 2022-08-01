package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.MobileNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;
import org.junit.Test;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * created by MikuNyanya in 14:44 2022/7/27
 * for the Reisen
 */
public class MobileNumberUtilTest {

    @Test
    public void test() {
        try {
            String phoneNum = "17718059576";
            String description = MobileNumberUtil.getCNDescription(phoneNum);
            String platform = MobileNumberUtil.getPlatform(phoneNum);


            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Test
    public void testO() {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();

        Phonenumber.PhoneNumber referencePhonenumber = null;

        String phoneNum = "17718059576";

        try {

            referencePhonenumber = phoneUtil.parse(phoneNum, "CN");

        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        /**
         * 中国移动号码正则
         * 139、138、137、136、135、134、147、150、151、152、157、158、159、178、182、183、184、187、188、198、195
         * 虚拟运营商号段: 1703、1705、1706、165
         **/
        String MOBILE_PATTERN = "(^1(3[4-9]|47|5[0-27-9]|65|78|8[2-478]|98)\\d{8}$)|(^170[356]\\d{7}$)";

        /**
         * 中国电信号码正则
         * 133、149、153、173、177、180、181、189、199、191
         * 虚拟运营商号段: 162、1700、1701、1702
         **/
        String TELECOM_PATTERN = "(^1(33|49|53|62|7[37]|8[019]|9[19])\\d{8}$)|(^170[012]\\d{7}$)";

        /**
         * 中国联通号码正则
         * 130、131、132、155、156、185、186、145、175、176、166、140
         * 虚拟运营商号段: 171、1707、1708、1709、167
         **/
        String UNICOM_PATTERN = "(^1(3[0-2]|4[05]|5[56]|6[67]|7[156]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";


        String mobileC = "";

        if (Pattern.matches(MOBILE_PATTERN, phoneNum)) {
            mobileC = "中国移动";
        } else if (Pattern.matches(TELECOM_PATTERN, phoneNum)) {
            mobileC = "中国电信";
        } else if (Pattern.matches(UNICOM_PATTERN, phoneNum)) {
            mobileC = "中国联通";
        } else {
            mobileC = "其他运营商";
        }

        //手机号码归属城市 city
        String city = phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);

        System.out.println("");
    }
}
