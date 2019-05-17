package com.tanglover;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TangXu
 * @create 2019-03-05 11:20
 * @description:
 */
public class StreamTest {

    @Test
    public void steam() {
        Student a = new Student(1, "A", "M", 184);
        Student b = new Student(2, "B", "G", 163);
        Student c = new Student(3, "C", "M", 175);
        Student d = new Student(4, "D", "G", 158);
        Student e = new Student(5, "E", "M", 170);
        List<Student> list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);
        list.add(d);
        list.add(e);
        list.stream()
                .filter(student -> student.getSex().equals("G"))
                .forEach(student -> System.out.println(student.getName()));
    }
}

class Student {

    int no;
    String name;
    String sex;
    float height;

    public Student(int no, String name, String sex, float height) {
        this.no = no;
        this.name = name;
        this.sex = sex;
        this.height = height;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }
}