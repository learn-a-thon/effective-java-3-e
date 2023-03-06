package com.example.effective.Item18;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class InstrumentedHashSet<E> extends HashSet<E> {

    // 추가된 원소의 수
    private int addCount = 0;

    public InstrumentedHashSet() {}

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    public int getAddCount() {
        return addCount;
    }


    public static void main(String[] args) {
        InstrumentedHashSet instrumentedHashSet = new InstrumentedHashSet<String>();
        instrumentedHashSet.addAll(List.of("e1", "e2", "e3"));

        // 3을 기대했지만 6이 출력된다.
        // HashSet 내부적으로 addAll 메서드는 add() 함수를 반복 호출하도록 구현이 되어있다.
        System.out.println(instrumentedHashSet.getAddCount());
    }
}
