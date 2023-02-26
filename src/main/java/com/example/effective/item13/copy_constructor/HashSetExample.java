package com.example.effective.item13.copy_constructor;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class HashSetExample {
    public static void main(String[] args) {
        Set<String> hashSet = new HashSet<>();
        hashSet.add("test1");
        hashSet.add("oiok");
        System.out.println("HashSet : " + hashSet);

        Set<String> treeSet = new TreeSet<>(hashSet); // 클라이언트가 원하는 하위 타입으로 변환이 가능

        System.out.println("TreeSet: " + treeSet);
    }
}
