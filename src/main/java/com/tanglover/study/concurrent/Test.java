package com.tanglover.study.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

/**
 * @author TangXu
 * @create 2019-05-22 14:08
 * @description:
 */
public class Test {

    private static final Logger logger = LoggerFactory.getLogger(TestConcurrent.class);
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @org.junit.Test
    public void test() {
        long start = System.currentTimeMillis();
        logger.info("主线程开始时间: {}", sdf.format(start));
        for (int i = 0; i < 10000; i++) {
            TestConcurrent concurrent = new TestConcurrent();
            concurrent.concurrent();
        }
        long end = System.currentTimeMillis();
        logger.info("主线程结束时间: {}", sdf.format(end));
        logger.info("主线程花费时间: {} ms", end - start);
    }

    @org.junit.Test
    public void getConnect() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/trh_bill?useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "system");
        System.out.println(conn);
        String sql = "select * from investor limit 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong(1));
            System.out.println(rs.getString(2));
        }
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        logger.info("主线程开始时间: {}", sdf.format(start));
        for (int i = 0; i < 10000; i++) {
            TestConcurrent concurrent = new TestConcurrent();
            concurrent.concurrent();
        }
        long end = System.currentTimeMillis();
        logger.info("主线程结束时间: {}", sdf.format(end));
        logger.info("主线程花费时间: {} ms", end - start);

    }

}