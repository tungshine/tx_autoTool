package com.tanglover.sql.jdbc.builder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IndexBuilder {
    public static Map<String, List<String>> getIndex(Connection conn, ResultSetMetaData rsmd)
            throws SQLException {
        Map m = new HashMap();
        DatabaseMetaData dmd = conn.getMetaData();
        String tableName = rsmd.getTableName(1);
        ResultSet rs = dmd.getIndexInfo(null, null, tableName, false, true);
        while (rs.next()) {
            String indexName = rs.getString("INDEX_NAME");
            String columnName = rs.getString("COLUMN_NAME");
            List l = (List) m.get(indexName);
            if (l == null) {
                l = new ArrayList();
                l.add(columnName);
                m.put(indexName, l);
            } else {
                l.add(columnName);
            }
        }
        return m;
    }

    public String gainPrimaryKey(Connection connection, String tableName) {
        Object primaryKeyName = null;
        try {
            DatabaseMetaData dbMeta = connection.getMetaData();
            ResultSet pkRSet = dbMeta.getPrimaryKeys(null, null, tableName);
            while (pkRSet.next())
                primaryKeyName = pkRSet.getObject(4);
        } catch (Exception localException) {
        }
        return primaryKeyName == null ? null : primaryKeyName.toString();
    }
}