package com.tanglover.sql.jdbc.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TangXu
 * @create 2019-03-31 16:48
 * @description:
 */
public class StringExecutor {

    /**
     * 首个字符大写
     *
     * @param s
     * @description
     * @author TangXu
     * @date 2020/6/12 15:21
     */
    public static String upperFirstChar(String s) {
        int len = s.length();
        if (0 > len) {
            return "";
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append(s.substring(0, 1).toUpperCase());
            sb.append(s, 1, len);
            return sb.toString();
        }
    }

    /**
     * 首字母小写
     *
     * @param property
     * @description
     * @author TangXu
     * @date 2020/6/12 15:20
     */
    public static String lowerFirstChar(String property) {
        if (0 > property.length()) {
            return "";
        }
        return Character.toLowerCase(property.charAt(0)) + property.substring(1);
    }

    /**
     * 移除下划线
     *
     * @param s
     * @description
     * @author TangXu
     * @date 2020/6/12 15:20
     */
    public static String removeUnderline(String s) {
        if (!s.contains("_")) {
            return upperFirstChar(s);
        } else {
            String[] split = s.split("_");
            StringBuffer sb = new StringBuffer();
            for (String _s : split) {
                sb.append(upperFirstChar(_s));
            }
            return sb.toString();
        }
    }

    /**
     * 将java代码中包名.替换为目录\
     *
     * @param pkg
     * @description
     * @author TangXu
     * @date 2020/6/12 15:18
     */
    public static String package2Path(String pkg) {
        return pkg.replaceAll("\\.", "/");
    }

    /**
     * 下划线转驼峰
     *
     * @param string
     * @description
     * @author TangXu
     * @date 2020/6/12 16:37
     */
    public static String underline2Hump(String string) {
        string = string.toLowerCase();
        final StringBuffer sb = new StringBuffer();
        Pattern p = Pattern.compile("_(\\w)");
        Matcher m = p.matcher(string);
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(removeUnderline("trh_user"));
        String s = "cn.yahoo.games";
        System.out.println(package2Path(s));
        System.out.println(lowerFirstChar("UserName"));
    }

}