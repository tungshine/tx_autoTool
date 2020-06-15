package com.tanglover.sql.jdbc.builder;

import com.tanglover.pinyin.PinYin;
import com.tanglover.sql.jdbc.util.StringExecutor;

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

    public static void main(String[] args) {
        System.out.println(sdf.format(new Date()));
    }

    public String build(ResultSet rs, String pkg, boolean gs, Map<String, String> map) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        String entityName = StringExecutor.removeUnderline(rsMetaData.getTableName(1));
        Map<String, String> mExist = new Hashtable<>();
        StringBuffer sb = new StringBuffer();

        // build entityClass
        String buildEntityClass = buildEntityClass(rsMetaData, entityName, pkg);
        sb.append(buildEntityClass);

        // build properties
        String buildProperties = buildProperties(rsMetaData, map);
        sb.append(buildProperties);

        // build propertyMethods
        String propertyMethods = buildPropertyMethods(rsMetaData, mExist);
        sb.append(propertyMethods);

        // buildConstructor
        String buildConstructor = buildConstructor(entityName, rsMetaData);
        sb.append(buildConstructor);

        sb.append("\r\n");
        sb.append("}");
        return sb.toString();
    }

    public String buildEntityClass(ResultSetMetaData rsMetaData, String entityName, String pkg) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if (pkg != null && pkg.length() > 0) {
            sb.append("package " + pkg + ";");
            sb.append("\r\n");
            sb.append("\r\n");
        }

        sb.append("import java.io.*;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("/**");
        sb.append("\r\n");
        sb.append(" * @author TangXu");
        sb.append("\r\n");
        sb.append(" * @create " + sdf.format(new Date()));
        sb.append("\r\n");
        sb.append(" * @description: " + entityName);
        sb.append("\r\n");
        sb.append(" */");
        sb.append("\r\n");
        sb.append("public class " + entityName + " implements Cloneable, Serializable {\n");
        sb.append("\r\n");
        String columns = getSqlColumnString(rsMetaData, "id");
        sb.append("    public static String[] columns = " + columns + ";\r\n");
        return sb.toString();
    }

    public String buildProperties(ResultSetMetaData rsMetaData, Map<String, String> map) throws SQLException {
        StringBuilder sb = new StringBuilder();
        String columnName;
        String sqlColumnName;
        String javaType;
        for (int i = 1; i <= rsMetaData.getColumnCount(); ++i) {
            sqlColumnName = rsMetaData.getColumnName(i);
            columnName = StringExecutor.underline2Hump(sqlColumnName);
            javaType = JavaType.getType(rsMetaData, i);
            sb.append("    public ");
            sb.append(javaType);
            sb.append(" ");
            sb.append(columnName);
            if (javaType.contains("String")) {
                sb.append(" = \"\"");
            }

            if (javaType.contains("Date")) {
                sb.append(" = new java.util.Date()");
            }
            sb.append(";");
            sb.append(map.get(sqlColumnName));
            sb.append("\r\n");
        }
        return sb.toString();
    }

    public String buildPropertyMethods(ResultSetMetaData rsMetaData, Map<String, String> mExist) throws SQLException {
        String columnName;
        String sqlColumnName;
        String javaType;
        if (null != rsMetaData && 0 < rsMetaData.getColumnCount()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= rsMetaData.getColumnCount(); ++i) {
                sqlColumnName = rsMetaData.getColumnName(i);
                javaType = JavaType.getType(rsMetaData, i);
                columnName = StringExecutor.removeUnderline(sqlColumnName);
                if (!mExist.containsKey(sqlColumnName)) {
                    mExist.put(columnName, columnName);
                    sb.append("\r\n");
                    sb.append("    ");
                    sb.append("public " + javaType + " get" + columnName + "() {");
                    sb.append("\r\n");
                    sb.append("    ");
                    sb.append("    ");
                    sb.append("return " + StringExecutor.lowerFirstChar(columnName) + ";");
                    sb.append("\r\n");
                    sb.append("    }\r\n");
                    sb.append("\r\n");
                    sb.append("    ");
                    sb.append("public void set" + columnName + "(" + javaType + " " + StringExecutor.lowerFirstChar(columnName) + ") {");
                    sb.append("\r\n");
                    if (javaType.contains("String")) {
                        sb.append("    \tif (" + StringExecutor.lowerFirstChar(columnName) + " == null) {");
                        sb.append("\r\n");
                        sb.append("            " + StringExecutor.lowerFirstChar(columnName) + " = \"\";");
                        sb.append("\r\n");
                        sb.append("        }\r\n");
                    }

                    if (javaType.contains("Date")) {
                        sb.append("    \tif (" + StringExecutor.lowerFirstChar(columnName) + " == null) {");
                        sb.append("\r\n");
                        sb.append("            " + StringExecutor.lowerFirstChar(columnName) + " = new java.util.Date();");
                        sb.append("\r\n");
                        sb.append("        }\r\n");
                    }
                    sb.append("        ");
                    sb.append("this.");
                    sb.append(StringExecutor.lowerFirstChar(columnName));
                    sb.append(" = " + StringExecutor.lowerFirstChar(columnName) + ";");
                    sb.append("\r\n");
                    sb.append("    }\r\n");
                }
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 生成构造方法
     *
     * @param entityName
     * @param rsMetaData
     * @description
     * @author TangXu
     * @date 2020/6/12 15:35
     */
    public String buildConstructor(String entityName, ResultSetMetaData rsMetaData) throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n");
        sb.append("    ");
        sb.append("public static " + entityName + " new" + entityName + "(");
        String javaType;
        String columnName;
        String sqlColumnName;
        int count = rsMetaData.getColumnCount();
        for (int i = 1; i <= count; ++i) {
            sqlColumnName = rsMetaData.getColumnName(i);
            columnName = StringExecutor.removeUnderline(sqlColumnName);
            String propertyName = StringExecutor.lowerFirstChar(columnName);
            javaType = JavaType.getType(rsMetaData, i);
            sb.append(javaType + " ").append(propertyName);
            if (i + 1 <= count) {
                sb.append(", ");
            }
        }

        sb.append(") {");
        sb.append("\r\n");
        sb.append("        ");
        sb.append(entityName);
        sb.append(" ret = new " + entityName + "();\r\n");
        sb.append("    ");

        for (int i = 1; i <= count; ++i) {
            sqlColumnName = rsMetaData.getColumnName(i);
            String propertyName = StringExecutor.underline2Hump(sqlColumnName);
            sb.append("    ret.set" + StringExecutor.upperFirstChar(propertyName));
            sb.append("(" + propertyName + ");");
            sb.append("\r\n");
            sb.append("    ");
        }
        sb.append("    return ret;");
        sb.append("    \r\n");
        sb.append("    }");
        return sb.toString();
    }

    /**
     * 数据库字段去下划线转换为bean 属性
     *
     * @param rsMetaData
     * @param id
     * @return
     * @throws SQLException
     */
    public String getFieldArrayString(ResultSetMetaData rsMetaData, String id) throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsMetaData.getColumnCount();
        fields.append("{");
        for (int i = 1; i <= count; ++i) {
            String columnName = rsMetaData.getColumnName(i);
            fields.append("\"");
            fields.append(StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(columnName)));
            fields.append("\"");
            if (i < count) {
                fields.append(", ");
            }
        }
        fields.append("}");
        return fields.toString();
    }

    /**
     * 获取数据库字段集合
     *
     * @param rsMetaData
     * @param id
     * @return
     * @throws SQLException
     */
    public String getSqlColumnString(ResultSetMetaData rsMetaData, String id) throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsMetaData.getColumnCount();
        fields.append("{");
        for (int i = 1; i <= count; ++i) {
            String sqlColumnName = rsMetaData.getColumnName(i);
            fields.append("\"");
            fields.append(sqlColumnName);
            fields.append("\"");
            if (i < count) {
                fields.append(", ");
            }
        }
        fields.append("}");
        return fields.toString();
    }
}