package com.tanglover.study.myabstract;

/**
 * @author TangXu
 * @create 2019-06-10 15:08
 * @description:
 */
public abstract class SuperClass {

    abstract void test();

    void test2() {

    }
}

class SubClass extends SuperClass {

    @Override
    void test() {

    }
}