package item11.hashtable;


import item11.hashcode.PhoneNumber;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    public static void main(String[] args) {
        Map<PhoneNumber, String> map = new HashMap<>();

        PhoneNumber number1 = new PhoneNumber(123, 456, 7890);
//        PhoneNumber number2 = new PhoneNumber(123, 456, 7890);
        PhoneNumber number2 = new PhoneNumber(456, 789, 1111);

        // 같은 인스턴스인데 다른 hashCode
        //  hashMap에 값을 저장할 때 저장할 객체의 hashCode를 기반으로 어느 해쉬 버킷에 저장할지 결정하게 된다.
        //  같은 인스턴스지만 서로 다른 hashcode를 반환한다면 각기 다른 버킷에 들어가게 된다.
        // 다른 인스턴스인데 같은 hashCode를 쓴다면?
        //  해당 경우가 해시 충돌에 해당하는데
        //  값을 저장하는 경우 동일한 해쉬 버킷에 링크드 리스트 형태로 인스턴스들이 들어가게 된다.
        //  때문에 해당 값을 맵에서 가져오는 경우 링크드 리스트의 크기인 O(n)만큼 소요되게 된다.
        System.out.println(number1.equals(number2));
        System.out.println(number1.hashCode());
        System.out.println(number2.hashCode());

        map.put(number1, "hello");
        map.put(number2, "world");

        String s = map.get(number1);
        String s1 = map.get(number2);
        String s2 = map.get(new PhoneNumber(123, 456, 7890));
        System.out.println(s);
        System.out.println(s1);
        System.out.println(s2);
    }
}
