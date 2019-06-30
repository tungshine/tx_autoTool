package com.tanglover.design.patterns.adapter;

/**
 * @author TangXu
 * @create 2019-06-30 17:13
 * @description: 类的适配器模式
 */
public class AdapterTest {

    public static void main(String[] args) {
        Targetable target = new Adapter();
        target.method1();
        target.method2();
    }
}