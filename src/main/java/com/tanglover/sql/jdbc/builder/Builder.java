package com.tanglover.sql.jdbc.builder;

import com.tanglover.pinyin.PinYin;
import com.tanglover.sql.jdbc.util.DbUtil;
import com.tanglover.sql.jdbc.util.SqlExecutor;
import com.tanglover.sql.jdbc.util.StringExecutor;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Map;

/**
 * @author TangXu
 * @create 2019-03-31 15:53
 * @description:
 */
public class Builder {

    public static void main(String[] args) throws Exception {

        boolean is_maven = true;
        boolean src = true;
        String moduleName = "";
        String pkg = "com.tanglover.";

        String[] tableNames = {
                "sys_user"
                ,
                "sys_role",
                "sys_user_role"
        };
        String ip = "127.0.0.1";
        int port = 3306;
        String user = "root";
        String password = "system";
        String databaseName = "test";
        autoCoder(is_maven, src, moduleName, pkg, tableNames, ip, port, user, password, databaseName);
    }


    public static void autoCoder(boolean is_maven, boolean src, String moduleName, String pkg, String[] sqlTableNames, String ip, int port, String username, String password, String databaseName) throws Exception {

        Connection conn = null;
        String[] var11 = sqlTableNames;
        int var12 = sqlTableNames.length;

        for (int var13 = 0; var13 < var12; ++var13) {
            String sqlTableName = var11[var13];
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
            Map<String, String> map = DbUtil.returnRemarkInfo(ip, port, databaseName, username, password, true, "UTF-8", sqlTableName);
            beanBuilder(moduleName, is_maven, conn, sqlTableName, pkg, src, map);
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
            daoBuilder(moduleName, is_maven, conn, sqlTableName, pkg, src, map);
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
            map = DbUtil.returnRemarkInfoDOC(ip, port, databaseName, username, password, true, "UTF-8", sqlTableName);
            docBuild(moduleName, is_maven, conn, sqlTableName, pkg, src, map);
        }
        daoFactoryBuild(moduleName, is_maven, sqlTableNames, pkg, src);
    }

    public static void beanBuilder(String moduleName, boolean is_maven, Connection conn, String tableName, String pkg, boolean src, Map<String, String> map) throws Exception {
        String sql = String.format("SELECT * FROM `%s` LIMIT 1", tableName);
        ResultSet rs = SqlExecutor.executeQuery(conn, sql);
        BeanBuilder builder = new BeanBuilder();
        String xml = builder.build(rs, pkg + "bean", src, map);
        System.out.println(xml);
        String filename = file(is_maven, pkg, src, "bean", tableName, "java");
        if (moduleName != null && !"".equalsIgnoreCase(moduleName)) {
            filename = moduleName + File.separator + filename;
        }
        writeFile(filename, xml);
        conn.close();
    }

    public static void daoBuilder(String moduleName, boolean is_maven, Connection conn, String tableName, String pkg, boolean src, Map<String, String> map_comment) throws Exception {
        String sql = String.format("SELECT * FROM `%s` LIMIT 1", new Object[]{tableName});
        ResultSet rs = SqlExecutor.executeQuery(conn, sql);
        DaoBuilder builder = new DaoBuilder();
        String xml = builder.build(conn, rs, pkg + "dao", pkg + "bean", map_comment);
        System.out.println(xml);
        String filename = file(is_maven, pkg, src, "dao", tableName + "Dao", "java");
        if ((moduleName != null) && (!"".equalsIgnoreCase(moduleName))) {
            filename = moduleName + File.separator + filename;
        }
        writeFile(filename, xml);
        conn.close();
    }

    public static void docBuild(String moduleName, boolean is_maven, Connection conn, String sqlTableName, String pkg, boolean src, Map<String, String> map) throws Exception {
        String sql = String.format("SELECT * FROM `%s` LIMIT 1", new Object[]{sqlTableName});
        ResultSet rs = SqlExecutor.executeQuery(conn, sql);
        DocBuilder builder = new DocBuilder();
        String xml = builder.build(rs, map);
        System.out.println(xml);
        String filename = file(is_maven, pkg, src, "doc", sqlTableName, "TXT");
        if ((moduleName != null) && (!"".equalsIgnoreCase(moduleName))) {
            filename = moduleName + File.separator + filename;
        }
        writeFile(filename, xml);
        conn.close();
    }

    public static void daoFactoryBuild(String moduleName, boolean is_maven, String[] sqlTableNames, String pkg, boolean src) throws Exception {
        DaoFactoryBuilder builder = new DaoFactoryBuilder();
        String xml = builder.build(sqlTableNames, pkg + "dao");
        System.out.println(xml);
        String filename = file(is_maven, pkg, src, "dao", "DaoFactory", "java");
        if ((moduleName != null) && (!"".equalsIgnoreCase(moduleName))) {
            filename = moduleName + File.separator + filename;
        }
        writeFile(filename, xml);
    }

    public static String file(boolean is_maven, String pkg, boolean src, String type, String sqlTableName, String ext) {
        sqlTableName = StringExecutor.removeUnderline(sqlTableName);
        String path = StringExecutor.package2Path(pkg);
        if (is_maven) {
            if (src) {
                path = "src/main/java/" + path;
            }
            path = path + type + "/" + StringExecutor.upperFirstChar(PinYin.getShortPinYin(sqlTableName)) + "." + ext;
        } else {
            if (src) {
                path = "src/" + path;
            }
            path = path + type + "/" + StringExecutor.upperFirstChar(PinYin.getShortPinYin(sqlTableName)) + "." + ext;
        }
        return path;
    }

    public static void writeFile(String f, String s) throws Exception {
        File file = new File(f);
        if (file != null && !file.exists()) {
            file.mkdirs();
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(s.getBytes());
        fos.close();
    }
}