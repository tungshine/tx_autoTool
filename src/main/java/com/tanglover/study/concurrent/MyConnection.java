package com.tanglover.study.concurrent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author TangXu
 * @create 2019-05-22 13:44
 * @description:
 */
public class MyConnection {

    private static MyConnection singleton;

    private MyConnection() {}

    public static MyConnection getInstance() {
        if (singleton == null) {
            synchronized (MyConnection.class) {
                singleton = new MyConnection();
            }
        }
        return singleton;
    }


    private static Connection conn = null;

    public Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println(1);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trh_bill?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "system");
            System.out.println(conn);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}