package com.tanglover.sql.jdbc.builder;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BatchOP {
    public static String setOP(ResultSetMetaData rsmd, String columnName)
            throws SQLException {
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String key = rsmd.getColumnName(i);
            if (key.equals(columnName)) {
                return setOP(rsmd, i);
            }
        }
        return "";
    }

    public static String setOP(ResultSetMetaData rsmd, int i) throws SQLException {
        int count = rsmd.getColumnCount();
        if (i > count)
            return "";
        int columnType = rsmd.getColumnType(i);
        switch (columnType) {
            case 2003:
                return "setArray";
            case -5:
                return "setLong";
            case -2:
                return "setBytes";
            case -7:
                return "setBoolean";
            case 2004:
                return "setBlob";
            case 16:
                return "setBoolean";
            case 1:
                return "setString";
            case 2005:
                return "setClob";
            case 91:
                return "setDate";
            case 3:
                return "setBigDecimal";
            case 2001:
                break;
            case 8:
                return "setDouble";
            case 6:
                return "setFloat";
            case 4:
                return "setInt";
            case 2000:
                return "setObject";
            case -1:
                return "setString";
            case -16:
                return "setString";
            case -4:
                return "setBytes";
            case -15:
                return "setString";
            case 2011:
                return "setNClob";
            case 0:
                break;
            case 2:
                return "setBigDecimal";
            case -9:
                return "setString";
            case 1111:
                return "setObject";
            case 7:
                return "setDouble";
            case 2006:
                break;
            case -8:
                return "setRowId";
            case 5:
                return "setShort";
            case 2009:
                return "setSQLXML";
            case 2002:
                break;
            case 92:
                return "setTime";
            case 93:
                return "setTimestamp";
            case -6:
                return "setByte";
            case -3:
                return "setBytes";
            case 12:
                return "setString";
        }
        return "";
    }

    public static String getOP(ResultSetMetaData rsmd, String columnName) throws SQLException {
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String key = rsmd.getColumnName(i);
            if (key.equals(columnName)) {
                return getOP(rsmd, i);
            }
        }
        return "";
    }

    public static String getOP(ResultSetMetaData rsmd, int i) throws SQLException {
        int count = rsmd.getColumnCount();
        if (i > count)
            return "";
        int columnType = rsmd.getColumnType(i);
        switch (columnType) {
            case 2003:
                return "getArray";
            case -5:
                return "getLong";
            case -2:
                return "getBytes";
            case -7:
                return "getBoolean";
            case 2004:
                return "getBlob";
            case 16:
                return "getBoolean";
            case 1:
                return "getString";
            case 2005:
                return "getClob";
            case 91:
                return "getDate";
            case 3:
                return "getBigDecimal";
            case 2001:
                break;
            case 8:
                return "getDouble";
            case 6:
                return "getFloat";
            case 4:
                return "getInt";
            case 2000:
                return "getObject";
            case -1:
                return "getString";
            case -16:
                return "getString";
            case -4:
                return "getBytes";
            case -15:
                return "getString";
            case 2011:
                return "getNClob";
            case 0:
                break;
            case 2:
                return "getBigDecimal";
            case -9:
                return "getString";
            case 1111:
                return "getObject";
            case 7:
                return "getDouble";
            case 2006:
                break;
            case -8:
                return "getRowId";
            case 5:
                return "getShort";
            case 2009:
                return "getSQLXML";
            case 2002:
                break;
            case 92:
                return "getTime";
            case 93:
                return "getTimestamp";
            case -6:
                return "getByte";
            case -3:
                return "getBytes";
            case 12:
                return "getString";
        }
        return "";
    }
}