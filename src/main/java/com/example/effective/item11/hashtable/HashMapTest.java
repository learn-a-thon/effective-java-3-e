package com.example.effective.item11.hashtable;


import com.example.effective.item11.hashcode.PhoneNumber;
import java.util.HashMap;
import java.util.Map;

// map put, get 시 .hashCode() 메소드를 수행해 값을 찾을 버킷을 지정한다.
public class HashMapTest {

    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>();
        PhoneNumber number1 = new PhoneNumber(123, 456, 7890);
        PhoneNumber number2 = new PhoneNumber(456, 789, 1111);

        // 같은 인스턴스인데 다른 hashCode
        System.out.println(number1.equals(number2));
        System.out.println(number1.hashCode());
        System.out.println(number2.hashCode());

        map.put(number1, "test1");
        map.put(number2, "test122");

        String s1 = map.get(number1);
        String s2 = map.get(number2);
        String s3 = map.get(new PhoneNumber(123, 456, 7890));

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);

    }
}
