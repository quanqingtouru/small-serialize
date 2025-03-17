package com.quanqingtouru.serialize.small;

import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> items = new LinkedList<>();

        Class<? extends List> aClass = items.getClass();

        System.out.println(aClass);
    }
}