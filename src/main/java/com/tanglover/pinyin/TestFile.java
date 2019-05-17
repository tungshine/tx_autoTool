package com.tanglover.pinyin;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

/**
 * @author TangXu
 * @create 2019-05-17 16:13
 * @description:
 */
public class TestFile {

    public static void readTxt(String filePath) {

        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    System.out.println(lineTxt);
                }
                br.close();
            } else {
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }

    }


    public static void main(String[] args) {
//        System.out.println(TestFile.class.getResource("").getPath());
//        System.out.println(TestFile.class.getResource("").toString());
//        String filePath = "classpath:test";
        String absolutePath = TestFile.class.getResource("").toString();
        String path = absolutePath.replace("file:/", "").split("classes")[0] + "resources/" + "pinyin.db";
        System.out.println(path);
//        readTxt(filePath);
        readTxt(path);
    }

}