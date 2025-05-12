package com.quanqingtouru.serialize.small.list;

import com.quanqingtouru.serialize.small.SmallSerialize;
import lombok.Data;

import java.util.ArrayList;

public class CodecListTest {

    public static void main(String[] args) {

        SmallSerialize.registerClass((short) 1, Teacher.class);
        SmallSerialize.registerClass((short) 2, Item.class);


        Teacher teacher = new Teacher();
        teacher.setName("teacher");
        teacher.setAge(18);

        for (int i = 0; i < 5; i++) {
            Item item = new Item();
            item.setName("item" + i);
            item.setAge(i);
            teacher.getItems().add(item);
        }

        byte[] serialize = SmallSerialize.serialize(teacher);
        Teacher deserialize = SmallSerialize.deserialize(serialize);
        System.out.println(deserialize);
    }


    @Data
    public static class Teacher {
        private String name;
        private int age;
        private ArrayList<Item> items = new ArrayList<>();
    }

    @Data
    public static class Item {
        private String name;
        private int age;
    }
}



