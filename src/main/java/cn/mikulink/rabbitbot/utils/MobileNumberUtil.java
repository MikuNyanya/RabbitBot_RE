package cn.mikulink.rabbitbot.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.geocoding.PhoneNumberOfflineGeocoder;

import java.util.Locale;
import java.util.regex.Pattern;

/**
 * created by MikuNyanya in 14:44 2022/7/27
 * for the Reisen
 */
public class MobileNumberUtil {

    /**
     * 获取手机号归属地
     *
     * @param mobile 手机号
     * @return
     * @throws NumberParseException
     */
    public static String getCNDescription(String mobile) throws NumberParseException {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

        PhoneNumberOfflineGeocoder phoneNumberOfflineGeocoder = PhoneNumberOfflineGeocoder.getInstance();

        Phonenumber.PhoneNumber referencePhonenumber = phoneUtil.parse(mobile, "CN");

        //手机号码归属城市
        return phoneNumberOfflineGeocoder.getDescriptionForNumber(referencePhonenumber, Locale.CHINA);
    }

    /**
     * 获取手机运营商
     *
     * @param mobile 手机号
     * @return
     */
    public static String getPlatform(String mobile) {
        //中国移动
        String MOBILE_PATTERN = "(^1(3[4-9]|47|5[0-27-9]|65|78|8[2-478]|98)\\d{8}$)|(^170[356]\\d{7}$)";
        //中国电信
        String TELECOM_PATTERN = "(^1(33|49|53|62|7[37]|8[019]|9[19])\\d{8}$)|(^170[012]\\d{7}$)";
        //中国联通
        String UNICOM_PATTERN = "(^1(3[0-2]|4[05]|5[56]|6[67]|7[156]|8[56])\\d{8}$)|(^170[7-9]\\d{7}$)";


        String mobilePlatform = "";
        if (Pattern.matches(MOBILE_PATTERN, mobile)) {
            mobilePlatform = "中国移动";
        } else if (Pattern.matches(TELECOM_PATTERN, mobile)) {
            mobilePlatform = "中国电信";
        } else if (Pattern.matches(UNICOM_PATTERN, mobile)) {
            mobilePlatform = "中国联通";
        } else {
            mobilePlatform = "其他运营商";
        }

        return mobilePlatform;
    }
}
