package com.example.effective.item10;

import java.util.Objects;

public class CaseInsensitiveString {
    private final String s;

    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }

    /**
     * 한 방향으로만 작동하는 잘못된 예
     * 컬렉션을 사용한 contains등의 비교를 사용해도 구현에 따라 다른 결과가 나올 수 있다.
     * @param o
     * @return
     */
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof String) {
//            return s.equalsIgnoreCase((String) o);
//        }
//        return false;
//    }

    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveString &&
                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
    }

}
