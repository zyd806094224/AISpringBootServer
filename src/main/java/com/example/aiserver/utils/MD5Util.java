package com.example.aiserver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

    /**
     * 生成字符串的MD5哈希值
     *
     * @param input 需要计算MD5值的字符串
     * @return 输入字符串的MD5哈希值
     */
    public static String md5(String input) {
        try {
            // 创建MessageDigest实例，指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算输入字符串的字节数组的MD5哈希值
            byte[] messageDigest = md.digest(input.getBytes());
            // 将字节数组转换为十六进制格式的字符串
            return convertByteToHex(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将字节数组转换为十六进制格式的字符串
     *
     * @param bytes 字节数组
     * @return 十六进制格式的字符串
     */
    private static String convertByteToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            // 以两位十六进制形式输出每个字节
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

