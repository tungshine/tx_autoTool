package com.tanglover.design.patterns.adapter;

/**
 * @author TangXu
 * @create 2019-06-30 17:09
 * @description: 类的适配器模式
 */
public interface Targetable {

    /**
     * 与原类中方法一致
     */
    public void method1();

    /**
     * 新类的方法
     */
    public void method2();
}