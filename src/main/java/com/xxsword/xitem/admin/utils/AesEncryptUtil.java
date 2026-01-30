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

    private static final String K = "e812a1f8t267cb29";
    private static final int IV_LENGTH = 16; // AES block size

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
            throw new RuntimeException("AES-ECB encryption failed", e);
        }
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
            throw new RuntimeException("AES-ECB decryption failed", e);
        }
    }

    /**
     * 加密（CBC 模式）
     * @param plainText 明文
     * @param key       必须是 16/24/32 字节的原始密钥（作为字符串传入）
     * @return Base64 编码的 (IV + ciphertext)
     */
    public static String encryptCBC(String plainText, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            validateKeyLength(keyBytes);

            // 生成随机 IV
            byte[] iv = new byte[IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            // 执行加密
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(iv));
            byte[] ciphertext = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // 拼接 IV + ciphertext 并 Base64
            byte[] combined = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("AES-CBC encryption failed", e);
        }
    }

    /**
     * 解密（CBC 模式）
     * @param encryptedText Base64 编码的 (IV + ciphertext)
     * @param key           必须与加密时相同的密钥
     * @return 明文
     */
    public static String decryptCBC(String encryptedText, String key) {
        try {
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            validateKeyLength(keyBytes);

            byte[] combined = Base64.getDecoder().decode(encryptedText);
            if (combined.length < IV_LENGTH) {
                throw new IllegalArgumentException("Invalid encrypted data: too short");
            }

            // 分离 IV 和 ciphertext
            byte[] iv = new byte[IV_LENGTH];
            byte[] ciphertext = new byte[combined.length - IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, IV_LENGTH, ciphertext, 0, ciphertext.length);

            // 执行解密
            Cipher cipher = Cipher.getInstance(AES_CBC_PKCS5PADDING);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"), new IvParameterSpec(iv));
            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES-CBC decryption failed", e);
        }
    }

    private static void validateKeyLength(byte[] keyBytes) {
        if (keyBytes.length != 16 && keyBytes.length != 24 && keyBytes.length != 32) {
            throw new IllegalArgumentException(
                    "Invalid AES key length: " + keyBytes.length + " bytes. Must be 16, 24, or 32 bytes.");
        }
    }

}
