package com.tanglover.sql.jdbc.builder;

import com.tanglover.pinyin.PinYin;
import com.tanglover.sql.jdbc.util.SqlExecutor;
import com.tanglover.sql.jdbc.util.StringExecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class DaoBuilder {
    private static String primaryKey = null;

    public static void main(String[] args) throws Exception {
        String sql = "SELECT * FROM `建筑` LIMIT 1";
        String host = "127.0.0.1";
        String db = "test";
        Connection conn = SqlExecutor.newMysqlConnection(host, db);

        ResultSet rs = SqlExecutor.executeQuery(conn, sql);
        DaoBuilder builder = new DaoBuilder();
        String xml = builder.build(conn, rs, "com.tanglover.dao", "com.tanglover.bean", null);
        System.out.println(xml);
    }

    public String build(Connection conn, ResultSet rs, String pkg, String beanPkg, Map<String, String> map_comment) throws Exception {
        ResultSetMetaData rsmd = rs.getMetaData();
        String sqlTableName = rsmd.getTableName(1);
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        Map indexes = IndexBuilder.getIndex(conn, rsmd);

        if ((indexes != null) && (indexes.size() > 0) &&
                (indexes.get("PRIMARY") != null) && (((List) indexes.get("PRIMARY")).size() > 0)) {
            primaryKey = (String) ((List) indexes.get("PRIMARY")).get(0);
        }

        StringBuffer sb = new StringBuffer();

        if ((pkg != null) && (pkg.length() > 0)) {
            sb.append("package " + pkg + ";");
            sb.append("\r\n");
            sb.append("\r\n");
        }

        sb.append("import org.slf4j.Logger;");
        sb.append("\r\n");
        sb.append("import org.slf4j.LoggerFactory;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("import java.text.SimpleDateFormat;");
        sb.append("\r\n");
        sb.append("import java.util.*;");
        sb.append("\r\n");

        sb.append("\r\n");
        sb.append("import java.sql.*;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("import org.springframework.jdbc.core.*;");
        sb.append("\r\n");
        sb.append("import org.springframework.jdbc.core.namedparam.*;");
        sb.append("\r\n");
        sb.append("import org.springframework.jdbc.support.*;");
        sb.append("\r\n");
        sb.append("import ").append(beanPkg).append(".*;");

        sb.append("\r\n");
        sb.append("import org.springframework.stereotype.Repository;");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("@Repository(\"" + StringExecutor.lowerFirstChar(entityName) + "Dao\")");
        sb.append("\r\n");
        sb.append("public class " + entityName + "Dao extends BaseDao {");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("    Logger log = LoggerFactory.getLogger(" + entityName + "Dao.class)").append(";");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("    public static SimpleDateFormat sdfMm = new SimpleDateFormat(\"yyyy-MM-dd HH:mm\");");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("    public static SimpleDateFormat sdfDd = new SimpleDateFormat(\"yyyy-MM-dd HH:mm:ss\");");
        System.out.println("-----------------------");
        sb.append(generateDef(rsmd, sqlTableName));
        sb.append(generateInsert(rsmd, sqlTableName));
        sb.append(generateBatchInsert(rsmd, sqlTableName));

        if (primaryKey != null) {
            sb.append(generateSelectAll(rsmd, sqlTableName));
            sb.append(generateSelect(rsmd, sqlTableName));
        }

        sb.append(generateCount(rsmd, sqlTableName));
        sb.append(generateSelectByPage(rsmd, sqlTableName));

        if (primaryKey != null) {
            sb.append(generateUpdate(rsmd, sqlTableName));
            sb.append(generateBatchUpdate(rsmd, sqlTableName));
            sb.append(generateDelete(rsmd, sqlTableName));
            sb.append(generateBatchDelete(rsmd, sqlTableName));
        }

        sb.append(generateCreateTable(conn, rs, rsmd, sqlTableName, map_comment));
        sb.append(generateTruncate(rsmd, sqlTableName));
        sb.append(generateRepair(rsmd, sqlTableName));
        sb.append(generateOptimize(rsmd, sqlTableName));
        sb.append(generateExecute(rsmd, sqlTableName));
        sb.append("}\r\n");
        return sb.toString();
    }

    static String generateDef(ResultSetMetaData rsmd, String tableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("\r\n");
        sb.append("    private String TABLE = \"" + tableName + "\";\r\n");
        sb.append("\r\n");
        sb.append("    private String TABLENAME = \"" + tableName + "\";\r\n");
        sb.append("\r\n");
        sb.append("    public String getTABLE() {\r\n");
        sb.append("        return TABLE;\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    public String getTABLENAME() {\r\n");
        sb.append("        return TABLENAME;\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    public String TABLEMM() {\r\n");
        sb.append("        return ").append("TABLE + ").append("sdfMm.format(new java.util.Date());\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    public String TABLEDD() {\r\n");
        sb.append("        return ").append("TABLE + ").append("sdfDd.format(new java.util.Date());\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("\r\n");
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String fields = getFields(rsmd, key, true);
        String fields2 = getFields(rsmd, key, false);
        String fieldArrays = getFieldArrayString(rsmd, key);

        sb.append("    private String[] carrays = " + fieldArrays + ";\r\n");
        sb.append("    private String coulmns = \"" + fields + "\";\r\n");
        sb.append("    private String coulmns2 = \"" + fields2 + "\";\r\n");
        sb.append("\r\n");
        sb.append("    public String[] getCarrays() {\r\n");
        sb.append("        return carrays;\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    public String getCoulmns() {\r\n");
        sb.append("        return coulmns;\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("    public String getCoulmns2() {\r\n");
        sb.append("        return coulmns2;\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateConstruct(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();

        return sb.toString();
    }

    static String generateInsert(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        String fields = getFields(rsmd, key, false);
        String values = getValues(rsmd, key, false);
        sb.append("    //添加数据\r\n");
        sb.append("    public long insert(" + entityName + " bean) throws SQLException {\r\n");
        sb.append("        return insert(bean, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //添加数据\r\n");
        sb.append("    public long insert(" + entityName + " bean, String TABLENAME2) throws SQLException {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try {\r\n");
        sb.append("            sql = \"INSERT INTO \" + TABLENAME2 + \" (" + fields + ") VALUES (" + values + ")\";\r\n");
        sb.append("            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);\r\n");
        sb.append("            KeyHolder keyholder = new GeneratedKeyHolder();\r\n");
        sb.append("            _np.update(sql, ps, keyholder);\r\n");
        sb.append("            return keyholder.getKey().longValue();\r\n");
        sb.append("        } catch (Exception e) {\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"insert\", e);").append("\r\n");
        sb.append("            throw new SQLException(\"insert is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        fields = getFields(rsmd, key, true);
        values = getValues(rsmd, key, true);

        sb.append("    //添加数据\r\n");
        sb.append("    public long insertPrimaryKey(" + entityName + " bean) throws SQLException {\r\n");
        sb.append("        return insertPrimaryKey(bean, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //添加数据\r\n");
        sb.append("    public long insertPrimaryKey(" + entityName + " bean, String TABLENAME2) throws SQLException {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try {\r\n");
        sb.append("            sql = \"INSERT INTO \" + TABLENAME2 + \" (" + fields + ") VALUES (" + values + ")\";\r\n");
        sb.append("            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);\r\n");
        sb.append("            return _np.update(sql, ps);\r\n");
        sb.append("        } catch(Exception e) {\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"insertPrimaryKey\", e);").append("\r\n");
        sb.append("            throw new SQLException(\"insert2 is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateBatchInsert(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String entityName = StringExecutor.removeUnderline(sqlTableName);

        String fields = getFields(rsmd, key, false);
        String values = getQValues(rsmd, key, false);
        sb.append("    //批量添加数据\r\n");
        sb.append("    public int[] insert(List<" + entityName + "> beans) throws SQLException {\r\n");
        sb.append("        return insert(beans, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("    //批量添加数据\r\n");
        sb.append("    public int[] insert(final List<" + entityName + "> beans, String TABLENAME2) throws SQLException{\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql = \"INSERT INTO \" + TABLENAME2 + \" (" + fields + ") VALUES (" + values + ")\";\r\n");
        sb.append("            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public int getBatchSize() {\r\n");
        sb.append("                    return beans.size();\r\n");
        sb.append("                }\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public void setValues(PreparedStatement ps, int i) throws SQLException {\r\n");
        sb.append("                    " + entityName + " bean = beans.get(i);\r\n");
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String sqlColumnName = rsmd.getColumnName(i);
            String propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(sqlColumnName));
            if (sqlColumnName.equals(key))
                continue;
            String s = "bean." + propertyName;
            int columnType = rsmd.getColumnType(i);
            if (columnType == 93)
                s = "new Timestamp(bean." + propertyName + ".getTime())";
            else if (columnType == 91) {
                s = "new java.sql.Date(bean." + propertyName + ".getTime())";
            }
            sb.append("                    ps.").append(BatchOP.setOP(rsmd, i))
                    .append("(")
                    .append(i - 1).append(", " + s + ");\r\n");
        }
        sb.append("                }\r\n");
        sb.append("            });\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"int[] insert\", e);").append("\r\n");

        sb.append("            throw new SQLException(\"insert is error\", e);\r\n");
        sb.append("        }\r\n");

        sb.append("    }\r\n");
        sb.append("\r\n");

        return sb.toString();
    }

    static String generateDelete(ResultSetMetaData rsmd, String tableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String javaType = JavaType.getType(rsmd, key);
        sb.append("    //删除单条数据\r\n");
        sb.append("    public int deleteByKey(" + javaType + " " + key + ") throws SQLException{\r\n");
        sb.append("        return deleteByKey(" + key + ", TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //删除单条数据\r\n");
        sb.append("    public int deleteByKey(" + javaType + " " + key + ", String TABLENAME2) throws SQLException{\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql = \"DELETE FROM \"+TABLENAME2+\" WHERE " + key + "=:" + key + "\";\r\n");
        sb.append("            Map<String,Object> param = new HashMap<String,Object>();\r\n");
        sb.append("            param.put(\"" + key + "\", " + key + ");\r\n");
        sb.append("            return _np.update(sql, param);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"deleteByKey\", e);").append("\r\n");

        sb.append("            throw new SQLException(\"deleteByKey is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateBatchDelete(ResultSetMetaData rsmd, String tableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String javaType = JavaType.getType(rsmd, key);
        sb.append("    //批量删除数据\r\n");
        sb.append("    public int[] deleteByKey(" + javaType + "[] keys) throws SQLException{\r\n");
        sb.append("        return deleteByKey(keys, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //批量删除数据\r\n");
        sb.append("    public int[] deleteByKey(final " + javaType + "[] keys, String TABLENAME2) throws SQLException{\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql = \"DELETE FROM \"+TABLENAME2+\" WHERE " + key + "=?\";\r\n");
        sb.append("            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public int getBatchSize() {\r\n");
        sb.append("                    return keys.length;\r\n");
        sb.append("                }\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public void setValues(PreparedStatement ps, int i) throws SQLException {\r\n");
        sb.append("                    ps." + BatchOP.setOP(rsmd, key) + "(1 , keys[i]);\r\n");
        sb.append("                }\r\n");
        sb.append("            });\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"int[] deleteByKey\", e);").append("\r\n");

        sb.append("            throw new SQLException(\"deleteByKey is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateSelectAll(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        String fields = getFields(rsmd, key, true);
        sb.append("    //查询所有数据\r\n");
        sb.append("    public List<" + entityName + "> selectAll() {\r\n");
        sb.append("        return selectAll(TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //查询所有数据\r\n");
        sb.append("    public List<" + entityName + "> selectAll(String TABLENAME2) {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try {\r\n");
        sb.append("            sql = \"SELECT " + fields + " FROM \"+ TABLENAME2 +\" ORDER BY " + key + "\";\r\n");
        sb.append("            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        sb.append("        } catch (Exception e) {\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"selectAll\", e);").append("\r\n");
        sb.append("            return new ArrayList<" + entityName + ">();\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //查询最新数据\r\n");
        sb.append("    public List<" + entityName + "> selectLast(int num) {\r\n");
        sb.append("        return selectLast(num, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //查询所有数据\r\n");
        sb.append("    public List<" + entityName + "> selectLast(int num ,String TABLENAME2) {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql = \"SELECT " + fields + " FROM \"+ TABLENAME2 +\" ORDER BY " + key + " DESC LIMIT \"+ num +\"\" ;\r\n");
        sb.append("            return _np.getJdbcOperations().query(sql, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"selectLast\", e);").append("\r\n");
        sb.append("            return new ArrayList<" + entityName + ">();\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        return sb.toString();
    }

    static String generateSelect(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        if (key == null) {
            key = primaryKey;
        }
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        String fields = getFields(rsmd, key, true);
        String keyJavaType = JavaType.getType(rsmd, key);
        String propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(key));
        sb.append("    //根据主键查询\r\n");
        sb.append("    public List<" + entityName + "> selectGtKey(" + keyJavaType + " " + propertyName + ") {\r\n");
        sb.append("        return selectGtKey(" + propertyName + ", TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //根据主键查询\r\n");
        sb.append("    public List<" + entityName + "> selectGtKey(" + keyJavaType + " " + propertyName + ", String TABLENAME2) {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql = \"SELECT " + fields + " FROM \" + TABLENAME2 + \" WHERE " + key + " = :" + propertyName + "\";\r\n");
        sb.append("            Map<String,Object> param = new HashMap<String,Object>();\r\n");
        sb.append("            param.put(\"" + key + "\", " + propertyName + ");\r\n");
        sb.append("            return _np.query(sql, param, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"selectGtKey\", e);").append("\r\n");
        sb.append("            return new ArrayList<" + entityName + ">();\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("    //根据主键查询\r\n");
        sb.append("    public " + entityName + " selectByKey(" + keyJavaType + " " + propertyName + ") {\r\n");
        sb.append("        return selectByKey(" + propertyName + ", TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //根据主键查询\r\n");
        sb.append("    public " + entityName + " selectByKey(" + keyJavaType + " " + propertyName + ", String TABLENAME2) {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql=\"SELECT " + fields + " FROM \"+TABLENAME2+\" WHERE " + key + " = :" + propertyName + "\";\r\n");
        sb.append("            Map<String,Object> param = new HashMap<String,Object>();\r\n");
        sb.append("            param.put(\"" + key + "\", " + propertyName + ");\r\n");
        sb.append("            List<" + entityName + "> list =  _np.query(sql, param, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        sb.append("            return (list == null || list.size() == 0) ? null : list.get(0);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"selectByKey " + key + "=\"+" + propertyName + ",e);").append("\r\n");
        sb.append("            return null;\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateSelectByIndex(ResultSetMetaData rsmd, String sqlTableName, List<String> indexs, MyIndex mi) throws SQLException {
        if (indexs.size() < 1)
            return "";
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        StringBuffer sb = new StringBuffer();
        String pk = AutoIncrement.getAutoIncrement(rsmd);
        if (pk == null)
            pk = "";
        StringBuffer ukey = new StringBuffer();
        int ii = 0;
        for (String key : indexs) {
            key = (String) indexs.get(ii++);
            ukey.append(StringExecutor.upperFirstChar(PinYin.getShortPinYin(key)));
        }
        boolean wy = false;
        if (mi != null) {
            wy = mi.wy;
        }
        String fname = "selectBy" + ukey;
        String fields = getFields(rsmd, pk, true);

        sb.append("    //根据索引" + ukey + "查询\r\n");
        if (wy)
            sb.append("    public " + entityName + " ");
        else {
            sb.append("    public List<" + entityName + "> ");
        }
        sb.append(fname);
        sb.append("(");
        ii = 0;
        for (String key : indexs) {
            key = (String) indexs.get(ii++);
            String keyJavaType = JavaType.getType(rsmd, key);

            if (keyJavaType.equals("java.util.Date")) {
                return "";
            }
            if (ii <= 1)
                sb.append("");
            else {
                sb.append(", ");
            }
            sb.append(keyJavaType);
            sb.append(" ");
            sb.append(PinYin.getShortPinYin(key));
        }
        sb.append(") {\r\n");
        sb.append("        return " + fname + "(");
        ii = 0;
        for (String key : indexs) {
            key = (String) indexs.get(ii++);
            if (ii <= 1)
                sb.append("");
            else {
                sb.append(", ");
            }
            sb.append(PinYin.getShortPinYin(key));
        }

        sb.append(", TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("    //根据索引" + ukey + "查询\r\n");
        if (wy)
            sb.append("    public " + entityName + " ");
        else {
            sb.append("    public List<" + entityName + "> ");
        }
        sb.append(fname);
        sb.append("(");
        ii = 0;
        for (String key : indexs) {
            key = (String) indexs.get(ii++);
            String keyJavaType = JavaType.getType(rsmd, key);

            if (keyJavaType.equals("java.util.Date")) {
                return "";
            }
            if (ii <= 1)
                sb.append("");
            else {
                sb.append(", ");
            }
            sb.append(keyJavaType);
            sb.append(" ");
            sb.append(PinYin.getShortPinYin(key));
        }
        sb.append(", String TABLENAME2) {\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql=\"SELECT " + fields + " FROM \"+TABLENAME2+\" WHERE ");
        ii = 0;
        for (String key : indexs) {
            key = (String) indexs.get(ii++);
            if (ii > 1) {
                sb.append(" AND ");
            }
            sb.append(key);
            sb.append("=:");
            sb.append(key);
        }

        sb.append("\";\r\n");
        sb.append("            Map<String,Object> param = new HashMap<String,Object>();\r\n");
        for (String key : indexs) {
            String shortKey = PinYin.getShortPinYin(key);
            sb.append("            param.put(\"" + key + "\", " + shortKey + ");\r\n");
        }

        if (wy) {
            sb.append("            return (" + entityName + ")_np.queryForObject(sql, param, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        } else {
            sb.append("            return _np.query(sql, param, new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        }

        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        if (wy) {
            sb.append("            return null;\r\n");
        } else {
            sb.append("            log.error(\"generateSelectByIndex\",e);").append("\r\n");
            sb.append("            return new Vector<" + entityName + ">();\r\n");
        }
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateCount(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    //所有数据总数\r\n");
        sb.append("    public int count() {\r\n");
        sb.append("        return count(TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //所有数据总数\r\n");

        sb.append("    public int count(String TABLENAME2) {\r\n");
        sb.append("        String sql;\r\n");
        sb.append("        try{\r\n");
        sb.append("            sql=\"SELECT COUNT(*) FROM \"+TABLENAME2+\"\";\r\n");
        sb.append("            return _np.getJdbcOperations().queryForObject(sql,Integer.class);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"count\",e);").append("\r\n");
        sb.append("            return 0;\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

//    static String generateIndexCount(ResultSetMetaData rsmd, String tableName, List<String> indexes, MyIndex mi) throws SQLException {
//        StringBuffer uKey = new StringBuffer();
//        int ii = 0;
//        String key;
//        for (Iterator localIterator = indexes.iterator(); localIterator.hasNext(); ) {
//            key = (String) localIterator.next();
//            key = (String) indexes.get(ii++);
//            uKey.append(StringExecutor.upperFirstChar(PinYin.getShortPinYin(key)));
//        }
//        StringBuffer sb = new StringBuffer();
//
//        sb.append("    //根据索引" + uKey + "统计数据\r\n");
//
//        sb.append("    public int countBy" + uKey + "(");
//        ii = 0;
//        for (String key : indexes) {
//            key = (String) indexes.get(ii++);
//            String shortKey = PinYin.getShortPinYin(key);
//            String keyJavaType = JavaType.getType(rsmd, key);
//
//            if (keyJavaType.equals("java.util.Date")) {
//                return "";
//            }
//            if (ii <= 1)
//                sb.append("");
//            else {
//                sb.append(", ");
//            }
//            sb.append(keyJavaType);
//            sb.append(" ");
//            sb.append(shortKey);
//        }
//        sb.append(") {\r\n");
//        sb.append("        return  countBy" + uKey + "(");
//        ii = 0;
//        for (String key : indexes) {
//            key = (String) indexes.get(ii++);
//            String shortKey = PinYin.getShortPinYin(key);
//            if (ii <= 1)
//                sb.append("");
//            else {
//                sb.append(", ");
//            }
//            sb.append(shortKey);
//        }
//        sb.append(", TABLENAME);\r\n");
//        sb.append("    }\r\n");
//        sb.append("\r\n");
//        sb.append("    //根据索引" + uKey + "统计数据\r\n");
//        sb.append("    @SuppressWarnings(\"deprecation\")\r\n");
//        sb.append("    public int countBy" + uKey + "(");
//        ii = 0;
//        for (String key : indexes) {
//            key = (String) indexes.get(ii++);
//            String shortKey = PinYin.getShortPinYin(key);
//            String keyJavaType = JavaType.getType(rsmd, key);
//
//            if (keyJavaType.equals("java.util.Date")) {
//                return "";
//            }
//            if (ii <= 1)
//                sb.append("");
//            else {
//                sb.append(", ");
//            }
//            sb.append(keyJavaType);
//            sb.append(" ");
//            sb.append(shortKey);
//        }
//        sb.append(", String TABLENAME2) {\r\n");
//        sb.append("        try{\r\n");
//        sb.append("            String sql;\r\n");
//        sb.append("            sql=\"SELECT COUNT(*) FROM \"+TABLENAME2+\" WHERE ");
//        ii = 0;
//        for (String key : indexes) {
//            key = (String) indexes.get(ii++);
//            String shortKey = PinYin.getShortPinYin(key);
//            String jType = JavaType.getType(rsmd, key);
//            if (ii > 1) {
//                sb.append(" + \" AND ");
//            }
//            sb.append(key);
//            if (jType.equals("String")) {
//                sb.append(" LIKE '%\" + ");
//                sb.append(shortKey);
//                sb.append(" + \"%'");
//                sb.append("\"");
//            } else {
//                sb.append("=\" + ");
//                sb.append(shortKey);
//                sb.append("");
//            }
//        }
//        sb.append(";\r\n");
//        sb.append("            return _np.getJdbcOperations().queryForInt(sql);\r\n");
//        sb.append("        }catch(Exception e){\r\n");
//        sb.append("            //createTable(TABLENAME2);\r\n");
//        sb.append("            log.error(\"generateIndexCount\",e);").append("\r\n");
//        sb.append("            return 0;\r\n");
//        sb.append("        }\r\n");
//        sb.append("    }\r\n");
//        sb.append("\r\n");
//        return sb.toString();
//    }

    static String generateSelectByPage(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String pk = AutoIncrement.getAutoIncrement(rsmd);
        if (pk == null)
            pk = "";
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        String fields = getFields(rsmd, pk, true);
        sb.append("    //分页查询\r\n");
        sb.append("    public List<" + entityName + "> selectByPage(int begin, int num) {\r\n");
        sb.append("        return selectByPage(begin, num, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //分页查询\r\n");
        sb.append("    public List<" + entityName + "> selectByPage(int begin, int num, String TABLENAME2) {\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql = \"SELECT " + fields + " FROM \"+TABLENAME2+\" LIMIT \"+begin+\", \"+num+\"\";\r\n");
        sb.append("            return _np.getJdbcOperations().query(sql,new BeanPropertyRowMapper<" + entityName + ">(" + entityName + ".class));\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            //createTable(TABLENAME2);\r\n");
        sb.append("            log.error(\"selectByPage\", e);").append("\r\n");
        sb.append("            return new ArrayList<" + entityName + ">();\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateUpdate(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        String propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(key));
        if (key == null) {
            key = primaryKey;
        }
        sb.append("    //修改数据\r\n");
        sb.append("    public int updateByKey(" + entityName + " bean) {\r\n");
        sb.append("        return updateByKey(bean, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //修改数据\r\n");
        sb.append("    public int updateByKey(" + entityName + " bean, String TABLENAME2) {\r\n");
        sb.append("        try {\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql = \"UPDATE \" + TABLENAME2 + \" SET ");
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String sqlColumnName = rsmd.getColumnName(i);
//            String _propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline());
            if (sqlColumnName.equals(key))
                continue;
            sb.append(sqlColumnName);
            sb.append(" =: ");
            sb.append(propertyName);
            if (i < count) {
                sb.append(",");
            }
        }
        sb.append(" WHERE " + key + " =: " + propertyName + "\";\r\n");
        sb.append("            SqlParameterSource ps = new BeanPropertySqlParameterSource(bean);\r\n");
        sb.append("            return _np.update(sql, ps);\r\n");
        sb.append("        } catch(Exception e) {\r\n");
        sb.append("            log.error(\"updateByKey\", e);").append("\r\n");
        sb.append("            return 0;\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateBatchUpdate(ResultSetMetaData rsmd, String sqlTableName) throws SQLException {
        StringBuffer sb = new StringBuffer();
        String key = AutoIncrement.getAutoIncrement(rsmd);
        String entityName = StringExecutor.removeUnderline(sqlTableName);
        if (key == null) {
            key = primaryKey;
        }
        sb.append("    //批量修改数据\r\n");
        sb.append("    public int[] updateByKey(final List<" + entityName + "> beans) throws SQLException {\r\n");
        sb.append("        return updateByKey(beans, TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //批量修改数据\r\n");
        sb.append("    public int[] updateByKey(final List<" + entityName + "> beans, String TABLENAME2) throws SQLException{\r\n");
        sb.append("        try {\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql = \"UPDATE \" + TABLENAME2 + \" SET ");
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String sqlColumnName = rsmd.getColumnName(i);
            if (sqlColumnName.equals(key))
                continue;
            sb.append(sqlColumnName);
            sb.append(" = ?");
            if (i < count) {
                sb.append(", ");
            }
        }
        sb.append(" WHERE " + key + " = ? \";\r\n");
        sb.append("            return _np.getJdbcOperations().batchUpdate(sql, new BatchPreparedStatementSetter() {\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public int getBatchSize() {\r\n");
        sb.append("                    return beans.size();\r\n\r\n");
        sb.append("                }\r\n");
        sb.append("                //@Override\r\n");
        sb.append("                public void setValues(PreparedStatement ps, int i) throws SQLException {\r\n");
        sb.append("                    " + entityName + " bean = beans.get(i);\r\n");
        for (int i = 1; i <= count; i++) {
            String sqlColumnName = rsmd.getColumnName(i);
            String propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(sqlColumnName));
            if (sqlColumnName.equals(key))
                continue;
            String s = "bean." + propertyName;
            int columnType = rsmd.getColumnType(i);
            if (columnType == 93) {
                s = "new Timestamp(bean." + propertyName + ".getTime())";
            } else if (columnType == 91) {
                s = "new java.sql.Date(bean." + propertyName + ".getTime())";
            }
            sb.append("                    ps." + BatchOP.setOP(rsmd, i) + "(").append(i - 1).append(", " + s + ");\r\n");
        }
        String s = "bean." + StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(key));
        sb.append("                    ps." + BatchOP.setOP(rsmd, key) + "(" + count + ", " + s + ");\r\n");
        sb.append("                }\r\n");
        sb.append("            });\r\n");
        sb.append("        } catch(Exception e) {\r\n");
        sb.append("            log.error(\"int[] updateByKey\", e);").append("\r\n");

        sb.append("            throw new SQLException(\"updateByKey is error\", e);\r\n");
        sb.append("        }\r\n");

        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateCreateTable(Connection conn, ResultSet rs, ResultSetMetaData rsmd, String tableName, Map<String, String> map_comment)
            throws Exception {
        String createSql = SqlExecutor.createMysqlTable_Comment(conn, rs, tableName, map_comment, 1, 1);
        String[] ss = createSql.split("\n");
        StringBuffer sb2 = new StringBuffer();
        int i = 0;
        for (String s : ss) {
            if (i > 0)
                sb2.append("                ");
            sb2.append("\"");
            sb2.append(s);
            sb2.append("\"");
            i++;
            if (i < ss.length) {
                sb2.append(" +");
                sb2.append("\n ");
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("    //创建表\r\n");
        sb.append("    public void createTable(String TABLENAME2) throws SQLException {\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql = " + sb2.toString() + ";\r\n");
        sb.append("            Map<String,String> params = new HashMap<String,String>();\r\n");
        sb.append("            params.put(\"TABLENAME\", TABLENAME2);\r\n");
        sb.append("            sql  = EasyTemplate.make(sql, params);\r\n");
        sb.append("            _np.getJdbcOperations().execute(sql);\r\n");
        sb.append("        } catch(Exception e) {\r\n");
        sb.append("            log.error(\"createTable\", e);").append("\r\n");
        sb.append("            throw new SQLException(\"createTable is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateTruncate(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    //清空表\r\n");
        sb.append("    public void truncate() throws SQLException{\r\n");
        sb.append("        truncate(TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //清空表\r\n");
        sb.append("    public void truncate(String TABLENAME2) throws SQLException{\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql=\"TRUNCATE TABLE \"+TABLENAME2+\"\";\r\n");
        sb.append("            _np.getJdbcOperations().execute(sql);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"truncate\", e);").append("\r\n");
        sb.append("            throw new SQLException(\"truncate is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateRepair(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    //修复表\r\n");
        sb.append("    public void repair(){\r\n");
        sb.append("        repair(TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        sb.append("    //修复表\r\n");
        sb.append("    public void repair(String TABLENAME2){\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql=\"REPAIR TABLE \"+TABLENAME2+\"\";\r\n");
        sb.append("            _np.getJdbcOperations().execute(sql);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"repair\", e);").append("\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateOptimize(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    //优化表\r\n");
        sb.append("    public void optimize(){\r\n");
        sb.append("        optimize(TABLENAME);\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");

        sb.append("    //优化表\r\n");
        sb.append("    public void optimize(String TABLENAME2){\r\n");
        sb.append("        try{\r\n");
        sb.append("            String sql;\r\n");
        sb.append("            sql=\"OPTIMIZE TABLE \"+TABLENAME2+\"\";\r\n");
        sb.append("            _np.getJdbcOperations().execute(sql);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"optimize\", e);").append("\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    static String generateExecute(ResultSetMetaData rsmd, String tableName) {
        StringBuffer sb = new StringBuffer();
        sb.append("    //执行sql\r\n");
        sb.append("    public void execute(String sql) throws SQLException{\r\n");
        sb.append("        try{\r\n");
        sb.append("            _np.getJdbcOperations().execute(sql);\r\n");
        sb.append("        }catch(Exception e){\r\n");
        sb.append("            log.error(\"execute\", e);").append("\r\n");
        sb.append("            throw new SQLException(\"execute is error\", e);\r\n");
        sb.append("        }\r\n");
        sb.append("    }\r\n");
        sb.append("\r\n");
        return sb.toString();
    }

    public static String getFields(ResultSetMetaData rsmd, String key, boolean bkey)
            throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            if ((key != null) && (key.equals(columnName)) && (!bkey))
                continue;
            fields.append(columnName);
            if (i < count) {
                fields.append(", ");
            }
        }
        return fields.toString();
    }

    public static String[] getFieldArrays(ResultSetMetaData rsmd, String key, boolean bkey) throws SQLException {
        List fields = new Vector();
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            if ((key != null) && (key.equals(columnName)) && (!bkey))
                continue;
            fields.add(columnName);
        }
        return (String[]) fields.toArray(new String[1]);
    }

    public static String getFieldArrayString(ResultSetMetaData rsmd, String key) throws SQLException {
        StringBuffer fields = new StringBuffer();
        int count = rsmd.getColumnCount();
        fields.append("{");
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            fields.append("\"");
            fields.append(columnName);
            fields.append("\"");
            if (i < count) {
                fields.append(", ");
            }
        }
        fields.append("}");
        return fields.toString();
    }

    public static String getValues(ResultSetMetaData rsmd, String key, boolean bkey) throws SQLException {
        StringBuffer values = new StringBuffer();
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String sqlColumnName = rsmd.getColumnName(i);
            String propertyName = StringExecutor.lowerFirstChar(StringExecutor.removeUnderline(sqlColumnName));
            if ((key != null) && (key.equals(sqlColumnName)) && (!bkey))
                continue;
            values.append(":").append(propertyName);
            if (i < count) {
                values.append(", ");
            }
        }
        return values.toString();
    }

    public static String getQValues(ResultSetMetaData rsmd, String key, boolean bkey) throws SQLException {
        StringBuffer values = new StringBuffer();
        int count = rsmd.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String columnName = rsmd.getColumnName(i);
            if ((key != null) && (key.equals(columnName)) && (!bkey))
                continue;
            values.append("?");
            if (i < count) {
                values.append(",");
            }
        }
        return values.toString();
    }
}