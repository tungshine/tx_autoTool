package com.tanglover.sql.jdbc.builder;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class MyIndex {
    public int id;
    public String mz;
    public String field;
    public boolean wy;
    public String PrimaryKey;

    public String getType(ResultSetMetaData rsmd)
            throws SQLException {
        return JavaType.getType(rsmd, this.field);
    }

    public String toString() {
        return "\nMyIndex [field=" + this.field + ", id=" + this.id + ", mz=" + this.mz + ", wy=" + this.wy + "]";
    }

    public static final List<MyIndex> indexes(Connection conn, String db, String table) throws SQLException {
        List ret = new Vector();
        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getIndexInfo(db, null, table, false, true);
        ResultSet rs2 = md.getPrimaryKeys(db, null, table);
        rs2.next();
        String primaryKey = rs2.getString("COLUMN_NAME");
        rs2.close();
        while (rs.next()) {
            MyIndex e = new MyIndex();
            e.id = rs.getInt(8);
            e.mz = rs.getString(6);
            e.field = rs.getString(9);
            e.wy = (!rs.getBoolean(4));
            e.PrimaryKey = primaryKey;
            if (e.mz.equals("PRIMARY"))
                continue;
            ret.add(e);
        }
        rs.close();
        return ret;
    }
}