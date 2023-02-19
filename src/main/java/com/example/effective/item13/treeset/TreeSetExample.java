package com.example.effective.item13.treeset;

import com.example.effective.item13.PhoneNumber;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class TreeSetExample {

    public static void main(String[] args) {
//        TreeSet<Integer> numbers = new TreeSet<>();
//        numbers.add(10);
//        numbers.add(4);
//        numbers.add(6);

        // natural order 를 알기 위해 comparable 로 캐스팅을 시도하기 때문에 구현해야함
        TreeSet<PhoneNumber> numbers = new TreeSet<>(Comparator.comparingInt(PhoneNumber::hashCode));
        Set<PhoneNumber> phoneNumberSet = Collections.synchronizedSet(numbers); // 동기화 블록이 적용되어있는 treeset
        numbers.add(new PhoneNumber(123, 456, 8877));
        numbers.add(new PhoneNumber(123, 456, 887));
        numbers.add(new PhoneNumber(123, 456, 881));

        for (PhoneNumber number : numbers) {
            System.out.println(number);
        }
    }
}
