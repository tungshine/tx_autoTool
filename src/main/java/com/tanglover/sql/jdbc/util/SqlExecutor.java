package com.tanglover.sql.jdbc.util;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author TangXu
 * @create 2019-03-31 16:01
 * @description:
 */
@SuppressWarnings("unused")
public class SqlExecutor {

    public static Executor _singleExecutor = null;
    public static Executor _4FixedExecutor = null;

    public SqlExecutor() {
    }

    /**
     * get MicrosoftOdbcAccess Connection
     *
     * @param fileName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection newOdbcMicrosoftAccessConnection(String fileName) throws SQLException, ClassNotFoundException {
        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String s = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%s";
        String url = String.format(s, fileName);
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    public static Connection newOdbcMicrosoftAccessConnection(String fileName, String username, String password) throws SQLException, ClassNotFoundException {
        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String s = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=%s";
        String url = String.format(s, fileName);
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * get MicrosoftOdbcExcel Connection
     *
     * @param fileName
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection newOdbcMicrosoftExcelConnection(String fileName) throws SQLException, ClassNotFoundException {
        String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
        String s = "jdbc:odbc:Driver={Microsoft Excel Driver (*.mdb)};DBQ=%s";
        String url = String.format(s, fileName);
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    public static Connection newMysqlConnection(String db) throws ClassNotFoundException, SQLException {
        String host = "127.0.0.1";
        return newMysqlConnection(host, db);
    }

    public static Connection newMysqlConnection(String host, String db) throws ClassNotFoundException, SQLException {
        String user = "root";
        String password = "root";
        return newMysqlConnection(host, db, user, password);
    }

    public static Connection newMysqlConnection(String host, String db, String user, String password) throws ClassNotFoundException, SQLException {
        int port = 3306;
        return newMysqlConnection(host, port, db, user, password);
    }

    public static Connection newMysqlConnection(String host, int port, String db, String user, String password) throws ClassNotFoundException, SQLException {
        boolean reconnect = true;
        String encoding = "utf-8";
        return newMysqlConnection(host, port, db, reconnect, encoding, user, password);
    }

    public static Connection newMysqlConnection(String host, int port, String db, boolean reconnect, String encoding, String username, String password) throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String format = "jdbc:mysql://%s:%d/%s?autoReconnect=%s&characterEncoding=%s";
        String url = String.format(format, host, port, db, String.valueOf(reconnect), encoding);
        Class.forName(driver);
        return DriverManager.getConnection(url);
    }

    public static Connection getConnections(String driver, String url, String username, String password) throws Exception {
        Connection conn = null;
        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", username);
            props.put("password", password);
            Class.forName(driver);
            return DriverManager.getConnection(url, props);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void openCommit(Connection conn) throws SQLException {
        conn.setAutoCommit(true);
    }

    public static void closeCommit(Connection conn) throws SQLException {
        conn.setAutoCommit(false);
    }

    public static void commit(Connection conn) throws SQLException {
        conn.commit();
    }

    public static void rollback(Connection conn) throws SQLException {
        conn.rollback();
    }

    public static List<String> getDatabases(String driver, String url, String user, String password) throws Exception {
        new Vector();
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, user, password);
        List<String> ret = getDatabases(conn);
        conn.close();
        return ret;
    }

    public static List<String> getDatabases(Connection conn) throws Exception {
        List<String> ret = new Vector();
        DatabaseMetaData dme = conn.getMetaData();
        ResultSet rs = dme.getCatalogs();

        while (rs.next()) {
            String d = rs.getString(1);
            ret.add(d);
        }

        return ret;
    }

    public static List<Map> getPrimaryKeys(Connection conn, String table) throws Exception {
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = dmd.getPrimaryKeys((String) null, (String) null, table);
        List<Map> ret = toMaps(rs);
        return ret;
    }

    public static List<Map> getImportedKeys(Connection conn, String table) throws Exception {
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = dmd.getImportedKeys((String) null, (String) null, table);
        List<Map> ret = toMaps(rs);
        return ret;
    }

    public static List<Map> getExportedKeys(Connection conn, String table) throws Exception {
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = dmd.getExportedKeys((String) null, (String) null, table);
        List<Map> ret = toMaps(rs);
        return ret;
    }

    public static Map<String, List<Map<String, Object>>> getIndexs(Connection conn, String table) throws Exception {
        Map ret = new HashMap();
        boolean unique = false;
        List<Map<String, Object>> indexs = getIndexInfo(conn, table, unique);

        Map m;
        Object es;
        for (Iterator var5 = indexs.iterator(); var5.hasNext(); ((List) es).add(m)) {
            m = (Map) var5.next();
            String INDEX_NAME = (String) m.get("INDEX_NAME");
            es = (List) ret.get(INDEX_NAME);
            if (es == null) {
                es = new ArrayList();
                ret.put(INDEX_NAME, es);
            }
        }

        return ret;
    }

    public static List<Map<String, Object>> getIndexInfo(Connection conn, String table, boolean unique) throws Exception {
        boolean approximate = true;
        DatabaseMetaData dmd = conn.getMetaData();
        ResultSet rs = dmd.getIndexInfo((String) null, (String) null, table, unique, approximate);
        Vector ret = new Vector();

        while (rs.next()) {
            Map<String, Object> e = ResultMap.newResultMap(rs);
            ret.add(e);
        }

        return ret;
    }

    public static List<String> getTables(String driver, String url, String user, String password) throws Exception {
        new Vector();
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, user, password);
        List<String> ret = getTables(conn);
        conn.close();
        return ret;
    }

    public static List<String> getTables(DataSource ds) throws Exception {
        return getTables(ds.getConnection());
    }

    public static List<String> getTables(Connection conn) throws Exception {
        List<String> ret = new Vector();
        DatabaseMetaData dme = conn.getMetaData();
        ResultSet rs = dme.getTables((String) null, (String) null, (String) null, (String[]) null);

        while (rs.next()) {
            String t = rs.getString("TABLE_NAME");
            String type = rs.getString(4);
            if (t != null && type != null && type.equals("TABLE")) {
                ret.add(t);
            }
        }

        return ret;
    }

    public static boolean isTableExist(DataSource ds, String table) {
        try {
            return isTableExist(ds.getConnection(), table);
        } catch (SQLException var3) {
            return false;
        }
    }

    public static boolean isTableExist(Connection conn, String table) {
        try {
            DatabaseMetaData dme = conn.getMetaData();
            ResultSet rs = dme.getTables((String) null, (String) null, table, (String[]) null);

            boolean ret;
            for (ret = false; rs.next(); ret = true) {
            }

            return ret;
        } catch (SQLException var5) {
            return false;
        }
    }

    public static List<String> getViews(String driver, String url, String user, String password) throws Exception {
        new Vector();
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url, user, password);
        List<String> ret = getTables(conn);
        conn.close();
        return ret;
    }

    public static List<String> getViews(DataSource ds) throws Exception {
        return getViews(ds.getConnection());
    }

    public static List<String> getViews(Connection conn) throws Exception {
        List<String> ret = new Vector();
        DatabaseMetaData dme = conn.getMetaData();
        ResultSet rs = dme.getTables((String) null, (String) null, (String) null, (String[]) null);

        while (rs.next()) {
            String t = rs.getString("TABLE_NAME");
            String type = rs.getString(4);
            if (t != null && type != null && type.equals("VIEW")) {
                ret.add(t);
            }
        }

        return ret;
    }

    public static List<Map<String, Object>> getColumns(DataSource ds, String sql) throws Exception {
        return getColumns(ds.getConnection(), sql);
    }

    public static List<Map<String, Object>> getColumns(Connection conn, String sql) throws Exception {
        ResultSet rs = executeQuery(conn, sql);
        return getColumns(rs);
    }

    public static List<Map<String, Object>> getColumns(ResultSet rs) throws Exception {
        List<Map<String, Object>> ret = new Vector();
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; ++i) {
            String columnName = rsmd.getColumnName(i);
            int columnType = rsmd.getColumnType(i);
            String columnLabel = rsmd.getColumnLabel(i);
            String columnTypeName = rsmd.getColumnTypeName(i);
            String catalogName = rsmd.getCatalogName(i);
            String columnClassName = rsmd.getColumnClassName(i);
            int precision = rsmd.getPrecision(i);
            int scale = rsmd.getScale(i);
            String schemaName = rsmd.getSchemaName(i);
            String tableName = rsmd.getTableName(i);
            int columnDisplaySize = rsmd.getColumnDisplaySize(i);
            boolean isAutoIncrement = rsmd.isAutoIncrement(i);
            boolean isCaseSensitive = rsmd.isCaseSensitive(i);
            boolean isCurrency = rsmd.isCurrency(i);
            boolean isDefinitelyWritable = rsmd.isDefinitelyWritable(i);
            int isNullable = rsmd.isNullable(i);
            boolean isReadOnly = rsmd.isReadOnly(i);
            boolean isSearchable = rsmd.isSearchable(i);
            boolean isSigned = rsmd.isSigned(i);
            boolean isWritable = rsmd.isWritable(i);
            Map e = new HashMap();
            e.put("i", i);
            e.put("columnName", columnName);
            e.put("columnType", columnType);
            e.put("columnLabel", columnLabel);
            e.put("columnTypeName", columnTypeName);
            e.put("catalogName", catalogName);
            e.put("columnClassName", columnClassName);
            e.put("precision", precision);
            e.put("scale", scale);
            e.put("schemaName", schemaName);
            e.put("tableName", tableName);
            e.put("columnDisplaySize", columnDisplaySize);
            e.put("isAutoIncrement", isAutoIncrement);
            e.put("isCaseSensitive", isCaseSensitive);
            e.put("isCurrency", isCurrency);
            e.put("isDefinitelyWritable", isDefinitelyWritable);
            e.put("isNullable", isNullable);
            e.put("isReadOnly", isReadOnly);
            e.put("isSearchable", isSearchable);
            e.put("isSigned", isSigned);
            e.put("isWritable", isWritable);
            ret.add(e);
        }

        return ret;
    }

    public static Map<String, Object> getColumn(DataSource ds, String sql, String columnName) throws Exception {
        return getColumn(ds.getConnection(), sql, columnName);
    }

    public static Map<String, Object> getColumn(Connection conn, String sql, String columnName) throws Exception {
        ResultSet rs = executeQuery(conn, sql);
        return getColumn(rs, columnName);
    }

    public static Map<String, Object> getColumn(ResultSet rs, String columnName) throws Exception {
        List<Map<String, Object>> columns = getColumns(rs);
        return getColumn(columns, columnName);
    }

    public static Map<String, Object> getColumn(List<Map<String, Object>> columns, String columnName) throws Exception {
        Iterator var2 = columns.iterator();

        Map e;
        String _columnName;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            e = (Map) var2.next();
            _columnName = (String) e.get("columnName");
        } while (!columnName.equals(_columnName));

        return e;
    }

    public static Map<String, Object> getColumn(DataSource ds, String sql, int i) throws Exception {
        return getColumn(ds.getConnection(), sql, i);
    }

    public static Map<String, Object> getColumn(Connection conn, String sql, int i) throws Exception {
        ResultSet rs = executeQuery(conn, sql);
        return getColumn(rs, i);
    }

    public static Map<String, Object> getColumn(ResultSet rs, int i) throws Exception {
        List<Map<String, Object>> columns = getColumns(rs);
        return getColumn(columns, i);
    }

    public static Map<String, Object> getColumn(List<Map<String, Object>> columns, int i) throws Exception {
        Iterator var2 = columns.iterator();

        Map e;
        int _i;
        do {
            if (!var2.hasNext()) {
                return null;
            }

            e = (Map) var2.next();
            _i = (Integer) e.get("i");
        } while (i != _i);

        return e;
    }

    public static PreparedStatement prepareStatement(DataSource ds, String sql) throws SQLException {
        return prepareStatement(ds.getConnection(), sql);
    }

    public static PreparedStatement prepareStatement(Connection conn, String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }

    public static boolean execute(DataSource ds, String sql) throws SQLException {
        return execute(ds.getConnection(), sql);
    }

    public static boolean execute(Connection conn, String sql) throws SQLException {
        PreparedStatement ps = prepareStatement(conn, sql);
        return ps.execute();
    }

    public static ResultSet executeQuery(DataSource ds, String sql) throws SQLException {
        return executeQuery(ds.getConnection(), sql);
    }

    public static ResultSet executeQuery(Connection conn, String sql) throws SQLException {
        PreparedStatement ps = prepareStatement(conn, sql);
        return ps.executeQuery();
    }

    public static int executeUpdate(DataSource ds, String sql) throws SQLException {
        return executeUpdate(ds.getConnection(), sql);
    }

    public static int executeUpdate(Connection conn, String sql) throws SQLException {
        PreparedStatement ps = prepareStatement(conn, sql);
        return ps.executeUpdate();
    }

    public static int pageCount(int count, int pageSize) {
        int page = count / pageSize;
        page = count == page * pageSize ? page : page + 1;
        return page;
    }

    public static List selectByPage(List v, int page, int pageSize) {
        int count = v.size();
        int begin = page * pageSize;
        int end = begin + pageSize;
        if (begin <= count && begin >= 0 && end >= 0) {
            end = count < end ? count : end;
            if (end <= begin) {
                new ArrayList();
            }

            return v.subList(begin, end);
        } else {
            return new ArrayList();
        }
    }

    public static List getPage(List v, int page, int pageSize) {
        return selectByPage(v, page, pageSize);
    }

    public static long pageCount(long count, long pageSize) {
        long page = count / pageSize;
        page = count == page * pageSize ? page : page + 1L;
        return page;
    }

    public static List selectByPage(List v, long page, long pageSize) {
        int count = v.size();
        int begin = (int) (page * pageSize);
        int end = (int) ((long) begin + pageSize);
        if (begin <= count && begin >= 0 && end >= 0) {
            end = count < end ? count : end;
            if (end <= begin) {
                new ArrayList();
            }

            return v.subList(begin, end);
        } else {
            return new ArrayList();
        }
    }

    public static List getPage(List v, long page, long pageSize) {
        return selectByPage(v, page, pageSize);
    }

    public static List<Object[]> toArrays(ResultSet rs) throws SQLException {
        Vector result = new Vector();

        while (rs.next()) {
            Object[] m = toArray(rs);
            result.add(m);
        }

        return result;
    }

    public static Object[] toArray(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();
        Object[] result = new Object[cols];

        for (int i = 0; i < cols; ++i) {
            result[i] = rs.getObject(i + 1);
        }

        return result;
    }

    public static List<Map> toMaps(ResultSet rs) throws SQLException {
        Vector result = new Vector();

        while (rs.next()) {
            Map m = toMap(rs);
            result.add(m);
        }

        return result;
    }

    public static Map toMap(ResultSet rs) throws SQLException {
        Map result = new HashMap();
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();

        for (int i = 1; i <= cols; ++i) {
            result.put(rsmd.getColumnName(i), rs.getObject(i));
        }

        return result;
    }

    public static String createMysqlTable(Connection conn, ResultSet rs, String tableName) throws Exception {
        try {
            List<Map<String, Object>> columns = getColumns(rs);
            StringBuffer sb = new StringBuffer();
            sb.append("CREATE TABLE IF NOT EXISTS `${TABLENAME}` (\n");

            for (Iterator var5 = columns.iterator(); var5.hasNext(); sb.append(",\n")) {
                Map<String, Object> map = (Map) var5.next();
                String columnName = (String) MapExecutor.get(map, "columnName");
                String columnTypeName = (String) MapExecutor.get(map, "columnTypeName");
                int precision = (Integer) MapExecutor.get(map, "precision");
                int scale = (Integer) MapExecutor.get(map, "scale");
                boolean isAutoIncrement = (Boolean) MapExecutor.get(map, "isAutoIncrement");
                int isNullable = (Integer) MapExecutor.get(map, "isNullable");
                sb.append("\t");
                sb.append("`" + columnName + "`");
                sb.append("  ");
                if (columnTypeName.equals("VARCHAR") && precision >= 715827882) {
                    sb.append("LONGTEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 5592405) {
                    sb.append("MEDIUMTEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 21845) {
                    sb.append("TEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 255) {
                    sb.append("TINYTEXT");
                } else if (!columnTypeName.equals("MEDIUMBLOB") && !columnTypeName.equals("LONGBLOB") && !columnTypeName.equals("BLOB") && !columnTypeName.equals("TINYBLOB")) {
                    if (!columnTypeName.equals("DATETIME") && !columnTypeName.equals("DATE") && !columnTypeName.equals("TIME") && !columnTypeName.equals("TIMESTAMP")) {
                        if (columnTypeName.equals("DOUBLE")) {
                            sb.append(columnTypeName);
                        } else if (columnTypeName.equals("DECIMAL")) {
                            sb.append(columnTypeName);
                            sb.append("(");
                            sb.append(precision);
                            sb.append(",");
                            sb.append(scale);
                            sb.append(")");
                        } else {
                            sb.append(columnTypeName);
                            sb.append("(");
                            sb.append(precision);
                            sb.append(")");
                        }
                    } else {
                        sb.append(columnTypeName);
                    }
                } else {
                    sb.append(columnTypeName);
                }

                if (isNullable == 0) {
                    sb.append(" NOT NULL");
                }

                if (isAutoIncrement) {
                    sb.append(" AUTO_INCREMENT");
                }
            }

            List<Map<String, Object>> nouniques = getIndexInfo(conn, tableName, false);
            int nouniquesLength = nouniques.size();
            int i = 0;

            for (Iterator var17 = nouniques.iterator(); var17.hasNext(); sb.append("\n")) {
                Map<String, Object> map = (Map) var17.next();
                String COLUMN_NAME = (String) MapExecutor.get(map, "COLUMN_NAME");
                String INDEX_NAME = (String) MapExecutor.get(map, "INDEX_NAME");
                boolean NON_UNIQUE = Boolean.parseBoolean(MapExecutor.get(map, "NON_UNIQUE").toString());
                sb.append("\t");
                if (INDEX_NAME.equals("PRIMARY")) {
                    sb.append("PRIMARY KEY (`" + COLUMN_NAME + "`)");
                } else if (!NON_UNIQUE) {
                    INDEX_NAME = INDEX_NAME.replace(tableName, "${TABLENAME}");
                    sb.append("UNIQUE KEY `" + INDEX_NAME + "` (`" + COLUMN_NAME + "`)");
                } else {
                    INDEX_NAME = INDEX_NAME.replace(tableName, "${TABLENAME}");
                    sb.append("KEY `" + INDEX_NAME + "` (`" + COLUMN_NAME + "`)");
                }

                ++i;
                if (i < nouniquesLength) {
                    sb.append(",");
                }
            }

            sb.append(") ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;\n");
            return sb.toString();
        } catch (Exception var13) {
            var13.printStackTrace();
            return null;
        }
    }

    public static String createMysqlTable_Comment(Connection conn, ResultSet rs, String tableName, Map<String, String> map_comment, int charset_type, int ENGINE_NAME_TYPE) throws Exception {
        try {
            String ENGINE_NAME = "InnoDB";
            if (ENGINE_NAME_TYPE == 0) {
                ENGINE_NAME = "MyISAM";
            }

            List<Map<String, Object>> columns = getColumns(rs);
            StringBuffer sb = new StringBuffer();
            sb.append("CREATE TABLE IF NOT EXISTS `${TABLENAME}` (\n");

            boolean NON_UNIQUE;
            for (Iterator var9 = columns.iterator(); var9.hasNext(); sb.append(",\n")) {
                Map<String, Object> map = (Map) var9.next();
                String columnName = (String) MapExecutor.get(map, "columnName");
                String comment = (String) map_comment.get(columnName);
                comment = comment.replaceAll("\"", "'");
                String columnTypeName = (String) MapExecutor.get(map, "columnTypeName");
                int precision = (Integer) MapExecutor.get(map, "precision");
                int scale = (Integer) MapExecutor.get(map, "scale");
                NON_UNIQUE = (Boolean) MapExecutor.get(map, "isAutoIncrement");
                int isNullable = (Integer) MapExecutor.get(map, "isNullable");
                sb.append("\t");
                sb.append("`" + columnName + "`");
                sb.append("  ");
                if (columnTypeName.equals("VARCHAR") && precision >= 715827882) {
                    sb.append("LONGTEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 5592405) {
                    sb.append("MEDIUMTEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 21845) {
                    sb.append("TEXT");
                } else if (columnTypeName.equals("VARCHAR") && precision >= 255) {
                    sb.append("TINYTEXT");
                } else if (!columnTypeName.equals("MEDIUMBLOB") && !columnTypeName.equals("LONGBLOB") && !columnTypeName.equals("BLOB") && !columnTypeName.equals("TINYBLOB")) {
                    if (!columnTypeName.equals("DATETIME") && !columnTypeName.equals("DATE") && !columnTypeName.equals("TIME") && !columnTypeName.equals("TIMESTAMP")) {
                        if (columnTypeName.equals("DOUBLE")) {
                            sb.append(columnTypeName);
                        } else if (columnTypeName.equals("DECIMAL")) {
                            sb.append(columnTypeName);
                            sb.append("(");
                            sb.append(precision);
                            sb.append(",");
                            sb.append(scale);
                            sb.append(")");
                        } else {
                            sb.append(columnTypeName);
                            sb.append("(");
                            sb.append(precision);
                            sb.append(")");
                        }
                    } else {
                        sb.append(columnTypeName);
                    }
                } else {
                    sb.append(columnTypeName);
                }

                if (isNullable == 0) {
                    sb.append(" NOT NULL");
                }

                if (NON_UNIQUE) {
                    sb.append(" AUTO_INCREMENT");
                }

                if (comment != null) {
                    sb.append(" COMMENT '" + comment + "'");
                }
            }

            List<Map<String, Object>> nouniques = getIndexInfo(conn, tableName, false);
            int nouniquesLength = nouniques.size();
            int i = 0;

            for (Iterator var22 = nouniques.iterator(); var22.hasNext(); sb.append("\n")) {
                Map<String, Object> map = (Map) var22.next();
                String COLUMN_NAME = (String) MapExecutor.get(map, "COLUMN_NAME");
                String INDEX_NAME = (String) MapExecutor.get(map, "INDEX_NAME");
                NON_UNIQUE = Boolean.parseBoolean(MapExecutor.get(map, "NON_UNIQUE").toString());
                sb.append("\t");
                if (INDEX_NAME.equals("PRIMARY")) {
                    sb.append("PRIMARY KEY (`" + COLUMN_NAME + "`)");
                } else if (!NON_UNIQUE) {
                    INDEX_NAME = INDEX_NAME.replace(tableName, "${TABLENAME}");
                    sb.append("UNIQUE KEY `" + INDEX_NAME + "` (`" + COLUMN_NAME + "`)");
                } else {
                    INDEX_NAME = INDEX_NAME.replace(tableName, "${TABLENAME}");
                    sb.append("KEY `" + INDEX_NAME + "` (`" + COLUMN_NAME + "`)");
                }

                ++i;
                if (i < nouniquesLength) {
                    sb.append(",");
                }
            }

            if (charset_type == 0) {
                sb.append(") ENGINE=" + ENGINE_NAME + "  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;\n");
            } else {
                sb.append(") ENGINE=" + ENGINE_NAME + "  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;\n");
            }

            return sb.toString();
        } catch (Exception var18) {
            var18.printStackTrace();
            return null;
        }
    }

    public static String getType(ResultSetMetaData rsmd, String columnName) throws SQLException {
        int count = rsmd.getColumnCount();

        for (int i = 1; i <= count; ++i) {
            String key = rsmd.getColumnName(i);
            if (key.equals(columnName)) {
                return getType(rsmd, i);
            }
        }

        return "";
    }

    public static String getType(ResultSetMetaData rsmd, int i) throws SQLException {
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
                    return java.util.Date.class.getName();
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

    public static void executeSingle(Runnable r) {
        if (_singleExecutor == null) {
            _singleExecutor = Executors.newSingleThreadExecutor();
        }

        _singleExecutor.execute(r);
    }

    public static void execute4Fixed(Runnable r) {
        if (_4FixedExecutor == null) {
            _4FixedExecutor = Executors.newFixedThreadPool(4);
        }

        _4FixedExecutor.execute(r);
    }
}