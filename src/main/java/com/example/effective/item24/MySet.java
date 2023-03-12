package com.example.effective.item24;

import java.util.AbstractSet;
import java.util.Iterator;

public class MySet<E> extends AbstractSet<E> {
    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    @Override
    public int size() {
        return 0;
    }
    private class MyIterator implements Iterator<E> { // 비정적 멤버 클래스로 iterator 구현

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            return null;
        }
    }
}
