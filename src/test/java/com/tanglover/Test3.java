package com.tanglover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author TangXu
 * @create 2019-04-01 13:43
 * @description:
 */
public class Test3 {

    private static Logger LOGGER = LoggerFactory.getLogger(Test3.class);

    public static void main(String[] args) {
        String a = "301353.0499999999883584678173065185546875";
        double c = 301353.0499999999883584678173065185546875d;
        double e = 92.9999999999999999999999999d;
        double f = 93.0000000000000000001d;
        BigDecimal sa = new BigDecimal(a);
        BigDecimal sc = new BigDecimal(String.valueOf(c));
        BigDecimal dc = new BigDecimal(Double.toString(c));
        BigDecimal ec = new BigDecimal(e);
        BigDecimal fc = new BigDecimal(f);

        System.out.println("sa : " + sa);
        System.out.println("sc : " + sc);
        System.out.println("dc : " + dc);
        System.out.println("ec : " + ec.divide(new BigDecimal(1), 2, 5));
        System.out.println("fc : " + fc.divide(new BigDecimal(1), 2, 5));


        BigDecimal m = new BigDecimal(232.5d);
        BigDecimal n = new BigDecimal(0.03d);
        BigDecimal multiply = m.multiply(n);
        System.out.println(multiply);
        System.out.println(multiply.multiply(new BigDecimal(100)).divide(new BigDecimal(1), 0, 5));
    }
}