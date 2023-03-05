package com.example.effectivejava3e.week6.item18;

import java.util.Collection;
import java.util.Set;

public class InstrumentedSet<E> extends ForwardingSet<E> { // 기존 Set이아닌 새로운 클래스로 확장
    private int addCount = 0;

    public InstrumentedSet(Set<E> s) {
        super(s);
    }


    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }


    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount++;
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
