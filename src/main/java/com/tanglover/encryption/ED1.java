/**
 * speedframework-core
 *
 * @(#) ED1.java 2010 2010-12-14 下午01:34:57
 * <p>
 * Copyright 2010 G51.ORG, Inc. All rights reserved.
 */
package com.tanglover.encryption;

import java.text.DecimalFormat;

/**
 * @author: TangXu
 * @date: 2018/10/29 19:35
 * @description: ED1私有加密算法实现
 */
public class ED1 {
    public static char SOMETHING = 0x5a;

    public static String encryption(String plain, char mKey) {
        char chX;
        char[] buf = plain.toCharArray();
        chX = ROR(mKey);
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (char) (buf[i] ^ chX ^ mKey);
            chX = ROR(buf[i]);
        }
        return (new String(buf));
    }

    public static String decryption(String notplain, char mKey) {
        char chX;
        char[] buf = notplain.toCharArray();

        for (int i = buf.length - 1; i >= 0; i--) {
            if (i == 0) {
                chX = mKey;
            } else chX = buf[i - 1];
            chX = ROR(chX);
            buf[i] = (char) (buf[i] ^ chX ^ mKey);
        }
        return (new String(buf));
    }

    protected static char RCL(char mValue) {
        mValue = (char) (mValue << 1);
        return (mValue);
    }

    protected static char ROR(char mValue) {
        mValue = (char) (mValue >> 1);
        return (mValue);
    }

    public static String numToString(String num) {
        StringBuffer str = new StringBuffer();
        char[] chAr = num.toCharArray();
        for (int i = 0; i < chAr.length; i += 3) {
            str.append((char) Integer.parseInt(chAr[i] + "" + chAr[i + 1] + "" + chAr[i + 2]));
        }
        return str.toString();
    }

    public static String stringToNum(String str) {
        StringBuffer ret = new StringBuffer();
        char[] chAr = str.toCharArray();
        for (int i = 0; i < chAr.length; i++) {
            ret.append(formatNumber((int) chAr[i], 3));
        }

        return ret.toString();
    }

    public final static String formatNumber(double value, int num) {
        DecimalFormat format = new DecimalFormat();
        format.setMaximumIntegerDigits(num);
        format.setMinimumIntegerDigits(num);
        format.setGroupingSize(0);
        return format.format(value);
    }
}
