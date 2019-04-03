package com.tanglover.sql.jdbc.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author TangXu
 * @create 2019-04-01 11:28
 * @description:
 */
public class ResultMap {
    public ResultMap() {
    }

    public static Map<String, Object> newResultMap(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        return newResultMap(rs, rsmd);
    }

    public static Map<String, Object> newResultMap(ResultSet rs, ResultSetMetaData rsmd) throws SQLException {
        Map<String, Object> rMap = new HashMap();
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; ++i) {
            String key = rsmd.getColumnName(i);
            Object value = null;
            int columnType = rsmd.getColumnType(i);
            switch (columnType) {
                case -16:
                    value = rs.getString(key);
                    break;
                case -15:
                    value = rs.getString(key);
                    break;
                case -9:
                    value = rs.getString(key);
                    break;
                case -8:
                    value = rs.getRowId(key);
                    break;
                case -7:
                    value = rs.getBoolean(key);
                    break;
                case -6:
                    value = rs.getByte(key);
                    break;
                case -5:
                    value = rs.getLong(key);
                    break;
                case -4:
                    value = rs.getBytes(key);
                    break;
                case -3:
                    value = rs.getBytes(key);
                    break;
                case -2:
                    value = rs.getBytes(key);
                    break;
                case -1:
                    value = rs.getString(key);
                    break;
                case 0:
                    value = null;
                    break;
                case 1:
                    value = rs.getString(key);
                    break;
                case 2:
                    value = rs.getBigDecimal(key);
                    break;
                case 3:
                    value = rs.getBigDecimal(key);
                    break;
                case 4:
                    value = rs.getInt(key);
                    break;
                case 5:
                    value = rs.getShort(key);
                    break;
                case 6:
                    value = rs.getFloat(key);
                    break;
                case 7:
                    value = rs.getFloat(key);
                    break;
                case 8:
                    value = rs.getDouble(key);
                    break;
                case 12:
                    value = rs.getString(key);
                    break;
                case 16:
                    value = rs.getBoolean(key);
                    break;
                case 91:
                    value = rs.getDate(key);
                    break;
                case 92:
                    value = rs.getTime(key);
                    break;
                case 93:
                    value = rs.getTimestamp(key);
                    break;
                case 1111:
                    value = rs.getObject(key);
                    break;
                case 2000:
                    value = rs.getObject(key);
                case 2001:
                case 2002:
                case 2006:
                default:
                    break;
                case 2003:
                    value = rs.getArray(key);
                    break;
                case 2004:
                    value = rs.getBlob(key);
                    break;
                case 2005:
                    value = rs.getClob(key);
                    break;
                case 2009:
                    value = rs.getSQLXML(key);
                    break;
                case 2011:
                    value = rs.getNClob(key);
            }

            rMap.put(key, value);
        }

        return rMap;
    }
}