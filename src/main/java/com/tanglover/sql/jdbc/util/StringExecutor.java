package com.tanglover.sql.jdbc.util;

/**
 * @author TangXu
 * @create 2019-03-31 16:48
 * @description:
 */
public class StringExecutor {

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

    public static String lowerFirstChar(String property) {
        if (0 > property.length()) {
            return "";
        }
        return Character.toLowerCase(property.charAt(0)) + property.substring(1);
    }

    public static void main(String[] args) {
        System.out.println(removeUnderline("trh_user"));
        String s = "cn.yahoo.games";
        System.out.println(package2Path(s));
        System.out.println(lowerFirstChar("UserName"));
    }

    public static String removeUnderline(String s) {
        if (!s.contains("_")) {
            return s;
        } else {
            String[] split = s.split("_");
            StringBuffer sb = new StringBuffer();
            for (String _s : split) {
                sb.append(upperFirstChar(_s));
            }
            return sb.toString();
        }
    }

    public static String package2Path(String pkg) {
        return pkg.replaceAll("\\.", "/");
    }

}