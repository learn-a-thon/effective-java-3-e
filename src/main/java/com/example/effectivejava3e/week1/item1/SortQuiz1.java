package com.example.effectivejava3e.week1.item1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SortQuiz1 {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(20);
        numbers.add(44);
        numbers.add(10);

        Comparator<Integer> desc = (o1, o2) -> o2 - o1;

        numbers.sort(desc);

        desc.reversed(); // default method
    }
}
