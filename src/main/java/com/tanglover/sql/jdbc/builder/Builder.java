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

    public static void autoCoder(boolean is_maven, boolean src, String moduleName, String pkg, String[] tablenames, String ip, int port, String username, String password, String databaseName) throws Exception {

        Connection conn = null;
        String[] var11 = tablenames;
        int var12 = tablenames.length;

        for (int var13 = 0; var13 < var12; ++var13) {
            String tablename = var11[var13];
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
            Map<String, String> map = DbUtil.returnRemarkInfo(ip, port, databaseName, username, password, true, "UTF-8", tablename);
            beanBuilder(moduleName, is_maven, conn, tablename, pkg, src, map);
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
//            DaoBuild(moduleName, is_maven, conn, tablename, pkg, src, map);
            conn = SqlExecutor.newMysqlConnection(ip, port, databaseName, username, password);
            map = DbUtil.returnRemarkInfoDOC(ip, port, databaseName, username, password, true, "UTF-8", tablename);
//            DocBuild(moduleName, is_maven, conn, tablename, pkg, src, map);
        }

//        DaoFactoryBuild(moduleName, is_maven, tablenames, pkg, src);
    }


    public static void main(String[] args) throws Exception {

        boolean is_maven = true;
        boolean src = true;
        String moduleName = "";
        String pkg = "com.tanglover.";

        String[] tablenames = {
                "sys_user"
//                ,
//                "sys_role",
//                "sys_user_role"
        };
        String ip = "127.0.0.1";
        int port = 3306;
        String user = "root";
        String password = "system";
        String databaseName = "test";
        autoCoder(is_maven, src, moduleName, pkg, tablenames, ip, port, user, password, databaseName);
    }

    public static String file(boolean is_maven, String pkg, boolean src, String type, String tableName, String ext) {
        tableName = StringExecutor.removeUnderline(tableName);
        String path = StringExecutor.package2Path(pkg);
        if (is_maven) {
            if (src) {
                path = "src/main/java/" + path;
            }

            path = path + type + "/" + StringExecutor.upperFirstChar(PinYin.getShortPinYin(tableName)) + "." + ext;
        } else {
            if (src) {
                path = "src/" + path;
            }

            path = path + type + "/" + StringExecutor.upperFirstChar(PinYin.getShortPinYin(tableName)) + "." + ext;
        }

        return path;
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