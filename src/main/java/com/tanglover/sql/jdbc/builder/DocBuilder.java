package com.tanglover.sql.jdbc.builder;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;

public class DocBuilder {
    public String build(ResultSet rs, Map<String, String> map)
            throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        StringBuffer sb = new StringBuffer();
        sb.append("#########返回字段说明");
        sb.append("\r\n");
        sb.append("|名称|描述|类型|");
        sb.append("\r\n");
        sb.append("|----|----|---|");
        sb.append("\r\n");
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            String javaType = JavaType.getType(rsmd, i);
            sb.append("|");
            sb.append(columnName);
            sb.append("|");
            sb.append((String) map.get(columnName));
            sb.append("|");
            sb.append(javaType);
            sb.append("|");
            sb.append("\r\n");
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public static String getFieldArrayString(ResultSetMetaData rsmd, String key) throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsmd.getColumnCount();
        fields.append("{");
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
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

    private String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        switch (dbType) {
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
                return "1";
            case "NUMBER":
            case "DECIMAL":
                return "4";
            case "INT":
            case "SMALLINT":
            case "INTEGER":
                return "2";
            case "BIGINT":
                return "6";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
                return "7";
        }
        return "1";
    }
}
