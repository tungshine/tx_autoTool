/**
 * speedframework-encryption
 *
 * @(#) DES3.java 2013 2013年10月24日 上午9:24:12
 * <p>
 * Copyright 2013 g51.org, Inc. All rights reserved.
 */
package encryption;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:35
 * @description: DES3加密算法实现
 */
public class DES3 {
    private static SecretKey secretKey = null;// key对象
    private static Cipher cipher = null; // 私鈅加密对象Cipher
    private static String keyString = "";// 密钥
    private static final Logger logger = LoggerFactory.getLogger(DES3.class);

    /**
     * 加密
     *
     * @param message
     * @return
     */
    public static String desEncrypt(String message, String key) {
        String result = ""; // DES加密字符串
        String newResult = "";// 去掉换行符后的加密字符串

        if (key.length() < 24) {
            for (int i = key.length(); i < 24; i++) {
                key = key + "0";
            }
        }

        keyString = key;

        try {
            secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");// 获得密钥
            /* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
            cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey); // 设置工作模式为加密模式，给出密钥
            byte[] resultBytes = cipher.doFinal(message.getBytes("UTF-8")); // 正式执行加密操作
            BASE64Encoder enc = new BASE64Encoder();
            result = enc.encode(resultBytes);// 进行BASE64编码
            newResult = filter(result); // 去掉加密串中的换行符
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return newResult;
    }

    /**
     * 解密
     *
     * @param message
     * @return
     * @throws Exception
     */
    public static String desDecrypt(String message, String key) throws Exception {
        if (key.length() < 24) {
            for (int i = key.length(); i < 24; i++) {
                key = key + "0";
            }
        }

        keyString = key;

        try {
            secretKey = new SecretKeySpec(keyString.getBytes(), "DESede");// 获得密钥
            /* 获得一个私鈅加密类Cipher，DESede是算法，ECB是加密模式，PKCS5Padding是填充方式 */
            cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        String result = "";
        try {
            BASE64Decoder dec = new BASE64Decoder();
            byte[] messageBytes = dec.decodeBuffer(message); // 进行BASE64编码
            cipher.init(Cipher.DECRYPT_MODE, secretKey); // 设置工作模式为解密模式，给出密钥
            byte[] resultBytes = cipher.doFinal(messageBytes);// 正式执行解密操作
            result = new String(resultBytes, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 去掉加密字符串换行符
     *
     * @param str
     * @return
     */
    public static String filter(String str) {
        String output = "";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            int asc = str.charAt(i);
            if (asc != 10 && asc != 13) {
                sb.append(str.subSequence(i, i + 1));
            }
        }
        output = new String(sb);
        return output;
    }
}
