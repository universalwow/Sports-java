package com.astra.actionconfig.utils;

import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class A {

    public static void main(String[] args) {
        Set<Integer> a = new HashSet<>();
        List<Integer> b = Lists.newArrayList(1,2,3);
        a.removeAll(b);
        a.remove(1);
        System.out.println(a.size());
    }
}
