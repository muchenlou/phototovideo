package com.muchenlou.phototovideo.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 10:56
 * @Version 1.0
 */
public class MD5Util {
    public static void main(String[] args) {
        String password = "password123"; // 要加密的原始字符串
        String salt = generateSalt(); // 生成一个随机盐值
        String encryptedPassword = encryptMD5(password, salt); // 加密后的字符串
        System.out.println("加密前的字符串：" + password);
        System.out.println("盐值：" + salt);
        System.out.println("加密后的字符串：" + encryptedPassword);
    }

    public static String encryptMD5(String str, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt.getBytes()); // 将盐值添加到消息摘要中
            byte[] bytes = md.digest(str.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length() == 1) {
                    sb.append("0");
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 生成一个16字节的随机数作为盐值
        random.nextBytes(salt);
        return bytesToHexString(salt);
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
