package com.tanglover.study;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;

/**
 * @author TangXu
 * @create 2019-06-10 15:20
 * @description:
 */
public class Test {

    @org.junit.Test
    public void test() {
        System.out.println(2 >>> 2);
    }

    @org.junit.Test
    public void test2() {
        int a = 9;//定义一个变量；
        boolean b = (a < 4) && (a++ < 10);
        System.out.println("使用短路逻辑运算符的结果为" + b);
        System.out.println("a的结果为" + a);
    }

    @org.junit.Test
    public void test3() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
    }
}