package com.tanglover.wechat.util.baidu;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author TangXu
 * @create 2019-02-12 10:33
 * @description:
 */
public class SnCal {

    private static final String AK = "DE0e0d0e9796e54756dff1fecd7ca766";

    public static String sn(LinkedHashMap<String, String> params) {
        params.put("output", "json");
        params.put("ak", AK);
        String paramsStr = null;
        try {
            paramsStr = buildQueryString(params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 对paramsStr前面拼接上/geocoder/v2/?，后面直接拼接yoursk得到/geocoder/v2/?address=%E7%99%BE%E5%BA%A6%E5%A4%A7%E5%8E%A6&output=json&ak=yourakyoursk
        String wholeStr = new String("/geocoder/v2/?" + paramsStr + "88d3e8275db75660ef31bc543e9e0a8c");

        // 对上面wholeStr再作utf8编码
        String tempStr = null;
        try {
            tempStr = URLEncoder.encode(wholeStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 调用下面的MD5方法得到最后的sn签名7de5a22212ffaa9e326444c75a58f9a0
        String sn = MD5(tempStr);
//        System.out.println(sn);
        return sn;
    }

    /**
     * @author: TangXu
     * @date: 2019/2/12 10:35
     * @description: 对Map内所有value作utf8编码，拼接返回结果
     * @param: [data]
     */
    public static String buildQueryString(Map<?, ?> data)
            throws UnsupportedEncodingException {
        StringBuffer queryString = new StringBuffer();
        for (Entry<?, ?> pair : data.entrySet()) {
            queryString.append(pair.getKey() + "=");
            queryString.append(URLEncoder.encode((String) pair.getValue(),
                    "UTF-8") + "&");
        }
        if (queryString.length() > 0) {
            queryString.deleteCharAt(queryString.length() - 1);
        }
        return queryString.toString();
    }

    /**
     * @author: TangXu
     * @date: 2019/2/12 10:34
     * @description: 来自stackoverflow的MD5计算方法，调用了MessageDigest库函数，并把byte数组结果转换成16进制
     * @param: [md5]
     */
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}