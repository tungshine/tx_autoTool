package com.mysql.jdbc.builder;

import java.net.ConnectException;
import java.sql.Connection;
import java.util.Map;

/**
 * @author TangXu
 * @create 2019-03-31 15:53
 * @description:
 */
public class Builder {

    public static void main(String[] args) {

        boolean src = true;
        String pkg = "com.tanglover.";
        String[] var10000 = new String[]{"sys_user"};
        String ip = "127.0.0.1";
        String username = "root";
        String password = "system";
        String databaseName = "test";
    }

    public static void BeanBuilder(String moduleName, boolean is_maven, Connection conn, String tableName, String pkg, boolean src, Map<String, String> map) throws Exception {
        String sql = String.format("SELECT * FROM `%s` LIMIT 1", tableName);

    }
}