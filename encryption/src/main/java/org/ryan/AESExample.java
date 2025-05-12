package org.ryan;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @Author Ryan Yuan
 * @Description Java 使用 AES 对称加密算法案例
 * @Create 2025-05-12 11:35
 */
public class AESExample {

    static class AESUtil {
        private static final String AES_ALGORITHM = "AES";

        private static final String AES_SECRET_KEY = "4128D9CDAC7E2F82951CBAF7FDFE675B";

        private static final String AES_TRANSFORMATION = "AES/GCM/NoPadding";

        private static Cipher encryptionCipher;

        private static Cipher decryptionCipher;

        public static void init() throws Exception {
            // 将AES密钥转换为SecretKeySpec对象
            SecretKeySpec secretKeySpec = new SecretKeySpec(AES_SECRET_KEY.getBytes(), AES_ALGORITHM);
            // 使用指定的AES加密模式和填充方式获取对应的加密器并初始化
            encryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
            encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            // 使用指定的AES加密模式和填充方式获取对应的解密器并初始化
            decryptionCipher = Cipher.getInstance(AES_TRANSFORMATION);
            decryptionCipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new GCMParameterSpec(128, encryptionCipher.getIV()));
        }

        public static String encrypt(String data) throws Exception {
            byte[] dataInBytes = data.getBytes();
            // 加密数据
            byte[] encryptedBytes = encryptionCipher.doFinal(dataInBytes);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        }

        public static String decrypt(String encryptedData) throws Exception {
            byte[] encryptedDataInBytes = Base64.getDecoder().decode(encryptedData);
            // 解密数据
            byte[] decryptedBytes = decryptionCipher.doFinal(encryptedDataInBytes);
            return new String(decryptedBytes);
        }

    }

    public static void main(String[] args) throws Exception {
        AESUtil.init();
        String data = "Hello, World!";
        String encryptedData = AESUtil.encrypt(data);
        System.out.println(encryptedData);
        String decryptedData = AESUtil.decrypt(encryptedData);
        System.out.println(decryptedData);
    }

}
