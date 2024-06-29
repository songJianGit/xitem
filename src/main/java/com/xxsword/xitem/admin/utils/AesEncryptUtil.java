package com.xxsword.xitem.admin.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES 工具类
 * 可能遇到的问题：
 * 使用AES加密时，当密钥大于128时，代码会抛出java.security.InvalidKeyException: Illegal key size or default parameters
 * Illegal key size or default parameters是指密钥长度是受限制的，java运行时环境读到的是受限的policy文件。文件位于${java_home}/jre/lib/security
 * 这种限制是因为美国对软件出口的控制。
 * 解决办法：1.缩短key的长度 2.百度解决方案 3.使用不受限制的jdk
 */
public class AesEncryptUtil {

    private static final String K = "e21a7ef46557cb9de812a1f8t267cb29";

    private static final String AES_ECB_PKCS5PADDING = "AES/ECB/PKCS5Padding";// 相同的内容，加密出来的密文相同；速度稍微快一点
    private static final String AES_CBC_PKCS5PADDING = "AES/CBC/PKCS5Padding";// 相同的内容，加密出来的密文不同；安全性高一点；建议用CBC
    private static final int AES_TYPE = 1;// 默认加密模式 1-AES_TYPE_ECB 2-AES_TYPE_CBC

//    public static void main(String[] args) {
//        long startTime = System.nanoTime();
//        for (int i = 0; i < 100000; i++) {
//            String xx = encrypt("{\"userPhoneNum\":\"15523456789\",\"userClient\":\"client1\"}");
//            decrypt(xx);
//        }
//        long endTime = System.nanoTime();
//        long duration = endTime - startTime;
//        System.out.printf("程序执行时间: %.3f 毫秒%n", (double) duration / 1_000_000);
//        String xx = encrypt("{\"userPhoneNum\":\"15523456789\",\"userClient\":\"client1\"}");
//        System.out.println(xx);
//        System.out.println(decrypt(xx));
//    }

    public static String encrypt(String data) {
        return encrypt(data, K);
    }

    public static String decrypt(String data) {
        return decrypt(data, K);
    }

    public static String encrypt(String data, String key) {
        return encrypt(data, key, AES_TYPE);
    }

    public static String decrypt(String data, String key) {
        return decrypt(data, key, AES_TYPE);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param aesType 指定模式 1-AES_TYPE_ECB 2-AES_TYPE_CBC
     * @return
     */
    public static String encrypt(String data, String key, int aesType) {
        if (aesType == 1) {
            return encryptECB(data, key);
        }
        if (aesType == 2) {
            return encryptCBC(data, key);
        }
        return null;
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param aesType 指定模式 1-AES_TYPE_ECB 2-AES_TYPE_CBC
     * @return
     */
    public static String decrypt(String data, String key, int aesType) {
        if (aesType == 1) {
            return decryptECB(data, key);
        }
        if (aesType == 2) {
            return decryptCBC(data, key);
        }
        return null;
    }

    private static String encryptECB(String plainText, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryptECB(String encryptedText, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance(AES_ECB_PKCS5PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String encryptCBC(String plainText, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");

            // 初始化向量，对于CBC模式是必需的，通常也是16字节
            SecureRandom random = new SecureRandom();
            byte[] ivBytes = new byte[16];
            random.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString((Base64.getEncoder().encodeToString(encryptedBytes) + ":" + Base64.getEncoder().encodeToString(ivBytes)).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String decryptCBC(String encryptedText, String key) {
        encryptedText = new String(Base64.getDecoder().decode(encryptedText), StandardCharsets.UTF_8);
        String[] parts = encryptedText.split(":");
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(parts[0]);
            byte[] ivBytes = Base64.getDecoder().decode(parts[1]);

            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
