package com.example.map.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

public class DESUtil {

    public DESUtil() {
    }

    /**
     * 获取Cipher对象
     */
    private static Cipher generateCipher(String password, int cipherMode) {
        try {
            SecureRandom random = new SecureRandom();
            DESKeySpec desKeySpec = new DESKeySpec(password.getBytes());
            // 创建一个秘钥工厂，然后把DESKeySpec转换成SecretKey
            SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance("DES");
            // 生成一个秘钥
            SecretKey secretKey = desKeyFactory.generateSecret(desKeySpec);
            // Cliper对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用秘钥初始化Cipher对象，ENCRYPT_MODE用于将Cipher初始化为常量
            cipher.init(cipherMode, secretKey, random);
            return cipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     */
    public static byte[] encypt(byte[] dataSource, String password) {
        Cipher cipher = generateCipher(password, Cipher.ENCRYPT_MODE);
        try {
            return cipher.doFinal(dataSource);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     */
    public static byte[] decrypt(byte[] src, String password) {
        Cipher cipher = generateCipher(password, Cipher.DECRYPT_MODE);
        try {
            return cipher.doFinal(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
}
