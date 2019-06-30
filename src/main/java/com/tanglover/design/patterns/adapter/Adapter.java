package com.tanglover.design.patterns.adapter;

/**
 * @author TangXu
 * @create 2019-06-30 17:11
 * @description: 类的适配器模式
 */
public class Adapter extends Source implements Targetable {

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }
}