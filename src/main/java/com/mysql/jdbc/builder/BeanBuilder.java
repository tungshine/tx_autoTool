package com.mysql.jdbc.builder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author TangXu
 * @create 2019-03-31 15:47
 * @description: 自动生产Bean
 */
public class BeanBuilder {

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public BeanBuilder() {
    }

    public static void main(String[] args) {
        System.out.println(sdf.format(new Date()));
    }

    public String build(ResultSet rs, String pkg, boolean gs, Map<String, String> map) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        String tableName = rsmd.getTableName(1);
        Map<String, String> mExist = new Hashtable<>();
        StringBuffer sb = new StringBuffer();
        if (pkg != null && pkg.length() > 0) {
            sb.append("package " + pkg + ";");
            sb.append("\r\n");
            sb.append("\r\n");
        }

        sb.append("import java.io.*;");
        sb.append("\r\n");
        sb.append("import java.util.*;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("/**");
        sb.append(" * @author TangXu");
        sb.append(" * @create " + sdf.format(new Date()));
        sb.append(" * @description: " + tableName);
        sb.append(" */");
        sb.append("@SuppressWarnings({\"serial\"})");
        sb.append("\r\n");
        sb.append("public class ");
        sb.append(StringExecutor.removeUnderline(tableName));
        sb.append(" implements Cloneable , Serializable {\r\n");
        sb.append("\r\n");
        String columns = getFieldArrayString(rsmd, "id");
        sb.append("    //public static String[] _arrays =" + columns + ";\r\n");
        sb.append("\r\n");
        int count = rsmd.getColumnCount();

        int i;
        String columnName;
        String javaType;
        for (i = 1; i <= count; ++i) {
            columnName = rsmd.getColumnName(i);
            javaType = JavaType.getType(rsmd, i);
            sb.append("    public ");
            sb.append(javaType);
            sb.append(" ");
            sb.append(columnName);
            if (javaType.contains("String")) {
                sb.append("=\"\"");
            }

            if (javaType.contains("Date")) {
                sb.append("=new java.util.Date()");
            }

            sb.append(";");
            sb.append((String) map.get(columnName));
            sb.append("\r\n");
        }
        sb.append("\r\n");
        sb.append("\r\n");
        String columnName;

        return sb.toString();
    }

    public String getFieldArrayString(ResultSetMetaData rsMetaData, String id) throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsMetaData.getColumnCount();
        fields.append("{");
        for (int i = 1; i <= count; ++i) {
            String columnName = rsMetaData.getColumnName(i);
            fields.append("\"");
            fields.append(columnName);
            fields.append("\"");
            if (i < count) {
                fields.append(",");
            }
        }
        fields.append("}");
        return fields.toString();
    }
}