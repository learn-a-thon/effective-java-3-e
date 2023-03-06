package com.example.effective.Item18;

import java.util.*;

/**
 * 핵심은 임의의 Set에 계측 기능을 더해 새로운 Set 으로 만듦
 *  - Set 모든 하위 구현체에 계측 기능을 더해 사용할 수 있다.
 *  - 다른 Set 계측 기능을 덧씌운 데코레이터 패턴
 *  - Set 과 CompositionInstrumentedSet 사이에 ForwardingSet 을 두어 명세되지 않은 '자기사용(self-use)' 으로 인한 예기치 못한 상황을 회피할 수 있다.
 * @param <E>
 */
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;

    ForwardingSet(Set<E> s) {
        this.s = s;
    }

    @Override
    public void clear() {
        s.clear();
    }

    @Override
    public int size() {
        return s.size();
    }

    @Override
    public boolean isEmpty() {
        return s.isEmpty();
    }

    public boolean contains(Object o) {
        return s.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    public boolean add(E e) {
        return s.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return s.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    public static void main(String[] args) {
        // 컴포지션 방식은 어떤 Set 구현체라도 계측할 수 있다.
        Set<String> stringSet = new CompositionInstrumentedSet<String>(new TreeSet<String>());
        Set<Object> s = new CompositionInstrumentedSet<>(new HashSet<>(10));
    }
}
