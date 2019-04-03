package com.tanglover.sql.jdbc.bean;

/**
 * @author TangXu
 * @create 2019-03-28 15:44
 * @description: 数据库类型与Java类型的相互映射Bean
 */
public class Field {

    private String tableFiled; // 数据库字段名
    private String tableType; // 数据库类型
    private String tableKey; // 数据库是否是主要关键字段

    private String javaField; // java字段名
    private String javaType; // java类型

    public String getTableFiled() {
        return tableFiled;
    }

    public void setTableFiled(String tableFiled) {
        this.tableFiled = tableFiled;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableKey() {
        return tableKey;
    }

    public void setTableKey(String tableKey) {
        this.tableKey = tableKey;
    }

    public String getJavaField() {
        return javaField;
    }

    public void setJavaField(String javaField) {
        this.javaField = javaField;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}