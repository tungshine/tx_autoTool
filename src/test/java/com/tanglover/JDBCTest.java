package com.tanglover;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author TangXu
 * @create 2019-04-04 10:30
 * @description:
 */
public class JDBCTest {

    @Test
    public void jdbcTest() throws Exception {
        Class<?> aClass = Class.forName("com.mysql.jdbc.Driver");
        Class<?> driverClass = com.mysql.jdbc.Driver.class;
        System.out.println(aClass == driverClass);

    }
}