package org.ryan;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

/**
 * @Author Ryan Yuan
 * @Description
 * @Create 2025-05-12 12:04
 */
public class RSAExample {

    static class RSAUtil {

        private static final String RSA_ALGORITHM = "RSA";

        /**
         * 生成RSA密钥对
         */
        public static KeyPair generateKeyPair() throws Exception {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
            // 密钥大小为2048位
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        }

        /**
         * 使用公钥加密数据
         */
        public static String encrypt(String data, PublicKey publicKey) throws Exception {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        /**
         * 使用私钥解密数据
         */
        public static String decrypt(String encryptedData, PrivateKey privateKey) throws Exception {
            byte[] decodedData = Base64.getDecoder().decode(encryptedData);
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptedData = cipher.doFinal(decodedData);
            return new String(decryptedData, StandardCharsets.UTF_8);
        }

    }

    public static void main(String[] args) throws Exception{
        KeyPair keyPair = RSAUtil.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String originalString = "Hello World!";
        String encryptedData = RSAUtil.encrypt(originalString, publicKey);
        String decryptedData = RSAUtil.decrypt(encryptedData, privateKey);
        System.out.println("Original String: " + originalString);
        System.out.println("RSA Encrypted Data : " + encryptedData);
        System.out.println("RSA Decrypted Data : " + decryptedData);
    }

}
