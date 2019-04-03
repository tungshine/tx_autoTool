package com.tanglover;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

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

@Getter
@Setter
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
}