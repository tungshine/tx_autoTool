package com.tanglover.sql.jdbc.builder;

import com.tanglover.sql.jdbc.util.StringExecutor;

public class DaoFactoryBuilder {

    public String build(String[] tableNames, String pkg) throws Exception {
        StringBuffer sb = new StringBuffer();
        if ((pkg != null) && (pkg.length() > 0)) {
            sb.append("package " + pkg + ";");
            sb.append("\r\n");
            sb.append("\r\n");
        }
        sb.append("import org.springframework.stereotype.Repository;");
        sb.append("\r\n");
        sb.append("import org.springframework.beans.factory.annotation.Autowired;");
        sb.append("\r\n");
        sb.append("\r\n");

        sb.append("//DAO Factory\r\n");
        sb.append("@Repository(\"daoFactory\")");
        sb.append("\r\n");
        sb.append("public class ");
        sb.append("DaoFactory {");
        sb.append("\r\n");
        sb.append("\r\n");

        for (String tableName : tableNames) {
            String daoName = StringExecutor.removeUnderline(tableName) + "Dao";
            String dao_quote = StringExecutor.lowerFirstChar(daoName) + "Dao";
            sb.append("    @Autowired");
            sb.append("\r\n");
            sb.append("    protected " + daoName + " " + dao_quote + ";");
            sb.append("\r\n");
        }
        sb.append("\r\n");
        sb.append("    /*******************************下面是GET方法**************************************/");
        sb.append("\r\n");

        for (String tableName : tableNames) {
            String daoName = StringExecutor.removeUnderline(tableName) + "Dao";
            String dao_quote = StringExecutor.lowerFirstChar(daoName) + "Dao";

            sb.append("    public " + daoName + " get" + daoName + "() {");
            sb.append("\r\n");
            sb.append("\t    return " + dao_quote + ";");
            sb.append("\r\n");
            sb.append("    }");
            sb.append("\r\n");
            sb.append("\r\n");
        }

        sb.append("}");
        sb.append("\r\n");
        return sb.toString();
    }
}