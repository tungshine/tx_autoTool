package com.tanglover.sql.jdbc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author TangXu
 * @create 2019-04-01 11:30
 * @description:
 */
public class DbUtil {
    static Logger logger = LoggerFactory.getLogger(DbUtil.class);


    public static Map<String, String> getTableInfo(String driver, String url, String user, String pwd, String table) {
        Map<String, String> map = new HashMap();
        Connection conn = null;
        DatabaseMetaData dbmd = null;

        try {
            conn = getConnections(driver, url, user, pwd);
            dbmd = conn.getMetaData();
            ResultSet resultSet = dbmd.getTables(null, "%", table, new String[]{"TABLE"});

            while (true) {
                String tableName;
                do {
                    if (!resultSet.next()) {
                        return map;
                    }

                    tableName = resultSet.getString("TABLE_NAME");
                    System.out.println(tableName);
                } while (!tableName.equals(table));

                ResultSet rs = conn.getMetaData().getColumns(null, getSchema(conn), tableName.toUpperCase(), "%");

                while (rs.next()) {
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(rs.getString("REMARKS"));
                    String dest = m.replaceAll("");
                    String str_remark = "//" + rs.getString("TYPE_NAME") + "    " + dest.trim();
                    System.out.println(str_remark);
                    String colName = rs.getString("COLUMN_NAME");
                    map.put(colName, str_remark);
                }
            }
        } catch (SQLException var26) {
            var26.printStackTrace();
        } catch (Exception var27) {
            var27.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException var25) {
                var25.printStackTrace();
            }

        }

        return map;
    }

    public static Map<String, String> getTableInfo2(String driver, String url, String user, String pwd, String table) {
        Map<String, String> map = new HashMap();
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = getConnections(driver, url, user, pwd);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show full columns from " + table);
            System.out.println("【" + table + "】");

            while (rs.next()) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(rs.getString("Comment"));
                String dest = m.replaceAll("");
                String str_remark = "//" + rs.getString("Type") + "    " + dest.trim();
                System.out.println(str_remark);
                String colName = rs.getString("Field");
                map.put(colName, str_remark);
            }
        } catch (SQLException var24) {
            var24.printStackTrace();
        } catch (Exception var25) {
            var25.printStackTrace();
        } finally {
            close(conn, stmt);
        }

        return map;
    }

    public static Map<String, String> getTableInfoDOC(String driver, String url, String user, String pwd, String table) {
        Map<String, String> map = new HashMap();
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnections(driver, url, user, pwd);
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("show full columns from " + table);

            while (rs.next()) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(rs.getString("Comment"));
                String dest = m.replaceAll("");
                String str_remark = dest.trim();
                System.out.println(str_remark);
                String colName = rs.getString("Field");
                map.put(colName, str_remark);
            }
        } catch (SQLException var24) {
            var24.printStackTrace();
        } catch (Exception var25) {
            var25.printStackTrace();
        } finally {
            close(conn, stmt);
        }

        return map;
    }

    private static String changeDbType(String dbType) {
        dbType = dbType.toUpperCase();
        byte var2 = -1;
        switch (dbType.hashCode()) {
            case -2034720975:
                if (dbType.equals("DECIMAL")) {
                    var2 = 4;
                }
                break;
            case -1981034679:
                if (dbType.equals("NUMBER")) {
                    var2 = 3;
                }
                break;
            case -1718637701:
                if (dbType.equals("DATETIME")) {
                    var2 = 9;
                }
                break;
            case -1618932450:
                if (dbType.equals("INTEGER")) {
                    var2 = 7;
                }
                break;
            case -1453246218:
                if (dbType.equals("TIMESTAMP")) {
                    var2 = 10;
                }
                break;
            case -472293131:
                if (dbType.equals("VARCHAR2")) {
                    var2 = 1;
                }
                break;
            case 72655:
                if (dbType.equals("INT")) {
                    var2 = 5;
                }
                break;
            case 2067286:
                if (dbType.equals("CHAR")) {
                    var2 = 2;
                }
                break;
            case 2090926:
                if (dbType.equals("DATE")) {
                    var2 = 11;
                }
                break;
            case 176095624:
                if (dbType.equals("SMALLINT")) {
                    var2 = 6;
                }
                break;
            case 954596061:
                if (dbType.equals("VARCHAR")) {
                    var2 = 0;
                }
                break;
            case 1959128815:
                if (dbType.equals("BIGINT")) {
                    var2 = 8;
                }
        }

        switch (var2) {
            case 0:
            case 1:
            case 2:
                return "1";
            case 3:
            case 4:
                return "4";
            case 5:
            case 6:
            case 7:
                return "2";
            case 8:
                return "6";
            case 9:
            case 10:
            case 11:
                return "7";
            default:
                return "1";
        }
    }

    private static Connection getConnections(String driver, String url, String user, String pwd) throws Exception {
        Connection conn = null;

        try {
            Properties props = new Properties();
            props.put("remarksReporting", "true");
            props.put("user", user);
            props.put("password", pwd);
            Class.forName(driver);
            conn = DriverManager.getConnection(url, props);
            return conn;
        } catch (Exception var6) {
            var6.printStackTrace();
            throw var6;
        }
    }

    private static String getSchema(Connection conn) throws Exception {
        String schema = conn.getMetaData().getUserName();
        if (schema != null && schema.length() != 0) {
            return schema.toUpperCase().toString();
        } else {
            throw new Exception("ORACLE数据库模式不允许为空");
        }
    }

    public static Map<String, String> returnRemarkInfo(String ip, int port, String db, String user, String pwd, boolean reconnect, String encoding, String tablename) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String s = "jdbc:mysql://%s:%d/%s?autoReconnect=%s&characterEncoding=%s&serverTimezone=Asia/Shanghai";
        String url = String.format(s, ip, port, db, String.valueOf(reconnect), encoding);
        System.out.println(url);
        return getTableInfo2(driver, url, user, pwd, tablename);
    }

    public static Map<String, String> returnRemarkInfoDOC(String ip, int port, String db, String user, String pwd, boolean reconnect, String encoding, String tablename) {
        String driver = "com.mysql.cj.jdbc.Driver";
        String s = "jdbc:mysql://%s:%d/%s?autoReconnect=%s&characterEncoding=%s&serverTimezone=Asia/Shanghai";
        String url = String.format(s, ip, port, db, String.valueOf(reconnect), encoding);
        System.out.println(url);
        return getTableInfoDOC(driver, url, user, pwd, tablename);
    }

    public static void close(Connection connection, Statement statement) {
        try {
            if (null != connection) {
                connection.close();
            }
            if (null != statement) {
                statement.close();
            }
        } catch (Exception e) {
            logger.error("close error: {}", e.getMessage());
        }
    }

    public static void main(String[] args) {
        String user = "root";
        String pwd = "system";
        String ip = "127.0.0.1";
        int port = 3306;
        String db = "test";
        boolean reconnect = true;
        String encoding = "UTF-8";
        String tableName = "user_info";
        returnRemarkInfo(ip, port, db, user, pwd, reconnect, encoding, tableName);
    }
}