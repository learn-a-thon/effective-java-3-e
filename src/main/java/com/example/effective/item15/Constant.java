package com.example.effective.item15;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constant {

    // 참조된 객체 자체는 수정될 수 있다.
    public static final String[] VALUES = {"A", "B", "C"};

    // 1. public 배열을 private 으로 만들고 public 불변 리스트를 사용한다.
    private static final String[] PRIVATE_VALUES = {"A", "B", "C"};
    public static final List<String> VALUES2 = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

    // 2. 복사본을 반환하는 public 메소드를 추가한다. (방어적 복사)
    public static final String[] values() {
        return PRIVATE_VALUES.clone();
    }
}
