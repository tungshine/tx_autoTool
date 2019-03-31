package com.mysql.jdbc.builder;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Date;

/**
 * @author TangXu
 * @create 2019-03-31 17:15
 * @description:
 */
public class JavaType {

    public static String getType(ResultSetMetaData rsmd, String columnName) throws SQLException {
        int count = rsmd.getColumnCount();
        for (int i = 0; i < count; i++) {
            String key = rsmd.getColumnName(i);
            if (key.equals(columnName)) {
                return getType(rsmd, i);
            }
        }
        return "";
    }

    public static String getType(ResultSetMetaData rsmd, String columnName, boolean flag) throws SQLException {
        int count = rsmd.getColumnCount();
        for (int i = 0; i < count; i++) {
            String key = rsmd.getColumnName(i);
            if (key.equals(columnName)) {
                if (flag) {
                    return getTypeOld(rsmd, i);
                }
                return getType(rsmd, i);
            }
        }
        return "";
    }

    private static String getTypeOld(ResultSetMetaData rsmd, int i) throws SQLException {
        int count = rsmd.getColumnCount();
        if (i > count) {
            return "";
        } else {
            int columnType = rsmd.getColumnType(i);
            switch (columnType) {
                case -16:
                    return String.class.getSimpleName();
                case -15:
                    return String.class.getName();
                case -9:
                    return String.class.getSimpleName();
                case -8:
                    return RowId.class.getName();
                case -7:
                    return Boolean.class.getSimpleName();
                case -6:
                    return Byte.class.getSimpleName();
                case -5:
                    return Long.class.getSimpleName();
                case -4:
                    return "byte[]";
                case -3:
                    return "byte[]";
                case -2:
                    return "byte[]";
                case -1:
                    return String.class.getSimpleName();
                case 0:
                case 2001:
                case 2002:
                case 2006:
                default:
                    return "";
                case 1:
                    return String.class.getSimpleName();
                case 2:
                    return BigDecimal.class.getName();
                case 3:
                    return BigDecimal.class.getName();
                case 4:
                    return Integer.class.getSimpleName();
                case 5:
                    return Short.class.getSimpleName();
                case 6:
                    return Float.class.getSimpleName();
                case 7:
                    return Double.class.getSimpleName();
                case 8:
                    return Double.class.getSimpleName();
                case 12:
                    return String.class.getSimpleName();
                case 16:
                    return Boolean.class.getSimpleName();
                case 91:
                    return Date.class.getName();
                case 92:
                    return Time.class.getName();
                case 93:
                    return Date.class.getName();
                case 1111:
                    return Object.class.getSimpleName();
                case 2000:
                    return Object.class.getSimpleName();
                case 2003:
                    return Array.class.getSimpleName();
                case 2004:
                    return Blob.class.getName();
                case 2005:
                    return Clob.class.getName();
                case 2009:
                    return SQLXML.class.getName();
                case 2011:
                    return NClob.class.getName();
            }
        }
    }

    public static String getType(ResultSetMetaData rsmd, int i) throws SQLException {
        int count = rsmd.getColumnCount();
        if (i > count) {
            return "";
        } else {
            int columnType = rsmd.getColumnType(i);
            switch (columnType) {
                case -16:
                    return getBasicType(String.class.getSimpleName());
                case -15:
                    return getBasicType(String.class.getName());
                case -9:
                    return getBasicType(String.class.getSimpleName());
                case -8:
                    return getBasicType(RowId.class.getName());
                case -7:
                    return getBasicType(Boolean.class.getSimpleName());
                case -6:
                    return getBasicType(Byte.class.getSimpleName());
                case -5:
                    return getBasicType(Long.class.getSimpleName());
                case -4:
                    return getBasicType("byte[]");
                case -3:
                    return getBasicType("byte[]");
                case -2:
                    return getBasicType("byte[]");
                case -1:
                    return getBasicType(String.class.getSimpleName());
                case 0:
                case 2001:
                case 2002:
                case 2006:
                default:
                    return "";
                case 1:
                    return getBasicType(String.class.getSimpleName());
                case 2:
                    return getBasicType(BigDecimal.class.getName());
                case 3:
                    return getBasicType(BigDecimal.class.getName());
                case 4:
                    return getBasicType(Integer.class.getSimpleName());
                case 5:
                    return getBasicType(Short.class.getSimpleName());
                case 6:
                    return getBasicType(Float.class.getSimpleName());
                case 7:
                    return getBasicType(Double.class.getSimpleName());
                case 8:
                    return getBasicType(Double.class.getSimpleName());
                case 12:
                    return getBasicType(String.class.getSimpleName());
                case 16:
                    return getBasicType(Boolean.class.getSimpleName());
                case 91:
                    return getBasicType(Date.class.getName());
                case 92:
                    return getBasicType(Time.class.getName());
                case 93:
                    return getBasicType(Date.class.getName());
                case 1111:
                    return getBasicType(Object.class.getSimpleName());
                case 2000:
                    return getBasicType(Object.class.getSimpleName());
                case 2003:
                    return getBasicType(Array.class.getSimpleName());
                case 2004:
                    return getBasicType(Blob.class.getName());
                case 2005:
                    return getBasicType(Clob.class.getName());
                case 2009:
                    return getBasicType(SQLXML.class.getName());
                case 2011:
                    return getBasicType(NClob.class.getName());
            }
        }
    }

    public static String getBasicType(String type) {
        if (type.equals("Boolean")) {
            type = "boolean";
        }
        if (type.equals("Byte")) {
            type = "byte";
        } else if (type.equals("Short")) {
            type = "short";
        } else if (type.equals("Integer")) {
            type = "int";
        } else if (type.equals("Long")) {
            type = "long";
        } else if (type.equals("Float")) {
            type = "float";
        } else if (type.equals("Double")) {
            type = "double";
        }
        return type;
    }
}