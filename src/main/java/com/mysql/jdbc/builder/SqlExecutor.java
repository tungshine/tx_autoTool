package com.mysql.jdbc.builder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.Executor;

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
}