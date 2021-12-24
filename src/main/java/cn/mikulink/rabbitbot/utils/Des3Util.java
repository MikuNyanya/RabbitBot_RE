package cn.mikulink.rabbitbot.utils;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * created by MikuNyanya on 2021/12/10 16:28
 * For the Reisen
 * 3Des加密
 */
@Slf4j
public class Des3Util {
    private static final String KET_ALGORITHM = "DESede";
    private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";


    public static String encrypt(String key, String data) {
        return encrypt(key, null, data);
    }

    /**
     * 3DES加密
     *
     * @param key  密钥 24位
     * @param iv   偏移
     * @param data 需要加密的数据
     * @return 加密后的字符串
     */
    public static String encrypt(String key, String iv, String data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KET_ALGORITHM);
            Key deskey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //偏移
            if (null != iv && iv.trim().length() > 0) {
                IvParameterSpec ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
                cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            } else {
                cipher.init(Cipher.ENCRYPT_MODE, deskey);
            }

            byte[] bOut = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(bOut);
        } catch (Exception ex) {
            log.error("3des加密异常", ex);
            return null;
        }
    }

    public static String decrypt(String key, String data) {
        return decrypt(key, null, data);
    }

    /**
     * 3DES解密
     *
     * @param key  密钥 24位
     * @param iv   偏移
     * @param data 需要解密的密文
     * @return 数据
     */
    public static String decrypt(String key, String iv, String data) {
        try {
            DESedeKeySpec spec = new DESedeKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KET_ALGORITHM);
            Key deskey = keyFactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            //偏移
            if (null != iv && iv.trim().length() > 0) {
                IvParameterSpec ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
                cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            } else {
                cipher.init(Cipher.DECRYPT_MODE, deskey);
            }

            byte[] bOut = cipher.doFinal(Base64.getDecoder().decode(data));
            return new String(bOut, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            log.error("3des解密异常", ex);
            return null;
        }
    }
}
