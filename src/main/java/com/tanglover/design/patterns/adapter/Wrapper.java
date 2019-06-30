package com.tanglover.design.patterns.adapter;

/**
 * @author TangXu
 * @create 2019-06-30 17:15
 * @description: 对象的适配器模式
 */
public class Wrapper implements Targetable {

    private Source source;

    public Wrapper(Source source) {
        super();
        this.source = source;
    }

    @Override
    public void method2() {
        System.out.println("this is the targetable method!");
    }

    @Override
    public void method1() {
        source.method1();
    }
}