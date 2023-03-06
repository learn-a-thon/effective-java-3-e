package com.example.effective.Item18;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositionInstrumentedSet<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public CompositionInstrumentedSet(Set<E> e) {
        super(e);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }

    public static void main(String[] args) {
        CompositionInstrumentedSet compositionInstrumentedSet = new CompositionInstrumentedSet<String>(new HashSet<>());
        compositionInstrumentedSet.addAll(List.of("e1", "e2", "e3"));

        System.out.println(compositionInstrumentedSet.getAddCount()); // 3

    }
}
