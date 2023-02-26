# item 18. 상속보다는 컴포지션을 사용하라

상속은 코드를 재사용하는 강력한 수단이지만, 항상 최선은 아니다. 잘못 사용하면 오류를 내기 쉬운 소프트웨어를 만들게 된다. 상위 클래스와 하위 클래스를 모두 같은 프로그래머가 통제하는 패키지 안에서라면 상속도 안전한 방법이다. 확장할 목적으로 설계되었고 문서화도 잘 된 클래스도 마찬가지로 안전하다. 하지만 일반적인 구체 클래스를 패키지 경계를 넘어, 즉 다른 패키지의 구체 클래스를 상속하는 일은 위험하다. 이 책에서의 상속은 클래스가 다른 클래스를 상속하는 것을 말하고, 인터페이스 상속(클래스가 인터페이스를 구현하거나 인터페이스가 다른 인터페이스를 확장) 과는 무관하다. 

1. 메서드 호출과 달리 상속은 캡슐화를 깨뜨린다.
- 상위 클래스는 릴리스마다 내부 구현이 달라질 수 있으며, 그 여파로 코드 한 줄 건드리지 않은 하위 클래스가 오동작할 수 있다.
- 이러한 이유로 상위 클래스 설계자가 확장을 충분히 고려하고 문서화도 제대로 해두지 않으면 하위 클래스는 상위 클래스의 변하에 발맞춰 수정돼야만 한다.


#### 문제를 피해가는 방법 : 컴포지션 
클래스를 확장하는 대신, 새로운 클래스를 만들고 private 필드로 기존 클래스의 인스턴스를 참조하게 하자. 기존 클래스가 새로운 클래스의 요소로 쓰인다는 뜻에서 이러한 설계를 컴포지션이라고 한다. 새 클래스의 인스턴스 메서드들은 기존 클래스의 대응하는 메서드를 호출해 그 결과를 반환한다. 이 방식을 전달이라 하며, 새 클래스의 메서드들을 전달 메서드라고 한다.

그 결과 새로운 클래스는 기존 클래스의 내부 구현 방식의 영향에서 벗어나며, 심지어 기존 클래스에 새로운 메서드가 추가되더라도 전혀 영향을 받지 않는다.

```java
public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;


    public ForwardingSet(Set<E> s) {
        this.s = s;
    }

    public void clear() {
        s.clear();
    }

    public boolean contains(Object o) {
        return s.contains(o);
    }


    @Override
    public Iterator<E> iterator() {
        return s.iterator();
    }


    public boolean isEmpty() {
        return s.isEmpty();
    }

    public int size() {
        return s.size();
    }

    public boolean add(E e) {
        return s.add(e);
    }

    public boolean remove(Object o) {
        return s.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    public Object[] toArray() {
        return s.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(!(o instanceof ForwardingSet))
            return false;

        ForwardingSet<?> that = (ForwardingSet<?>) o;

        return s != null ? s.equals(that.s) : that.s == null;
    }

    @Override
    public String toString() {
        return "ForwardingSet{" + "s=" + s + '}';
    }

    @Override
    public int hashCode() {
        return s != null ? s.hashCode() : 0;
    }
}


public class InstrumentedSet<E> extends ForwardingSet<E> {
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
```
임의의 Set에 계측 기능을 덧씌워 새로운 Set으로 만드는 것이 이 클래스의 핵심이다.
상속 방식은 구체 클래스를 각각을 따로 확장해야 하며, 지원하고 싶은 상위 클래스의 생성자 각각에 대응하는 생성자를 별도로 정의해줘야 한다. 하지만 지금 선보인 컴포지션 방식은 한 번만 구현해두면 어떠한 Set 구현체라도 계측할 수 있으며, 기존 생성자들과도 함께 사용할 수 있다.

InstrumentSet을 이용하면 대상 Set 인스턴스를 특정 조건하에서만 임시로 계측할 수 있다.
```java
static void walk(Set<Dog> dogs) {
    InstrumentSet<Dog> iDogs = new InstrumentSet<>(dogs);
    ...
}
```

다른 Set 인스턴스를 감싸고 있다는 뜻에서 IntstrumentedSet 같은 클래스를 래퍼 클래스라고 하며, 다른 Set에 계측 기능을 덧씌운다는 뜻에서 데코레이터 패턴이라고 한다. 컴포지션과 전달의 조합은 넓은 의미로 위임이라고 부른다. 단, 엄밀히 따지면 래퍼 객체가 내부 객체에 자기 자신의 참조를 넘기는 경우만 위임에 해당한다.

래퍼 클래스는 단점이 거의 없다. 
- 래퍼 클래스가 콜백 프레임워크와는 어울리지 않다는 점만 주의하면 된다.
- 콜백 프레임워크에서는 자기 자신의 참조를 다른 객체에 넘겨서 다음 호출(콜백)때 사용하도록 한다. 내부 객체는 자신을 감싸고 있는 래퍼의 존재를 모르니 대신 자신(this)를 넘기고, 콜백 때는 래퍼가 아닌 내부 객체를 호출하게 된다. 이를 SELF 문제라고 한다.
  - 전달 메서드가 성능에 주는 영향이나, 래퍼 객체가 메모리 사용량에 주는 여향을 걱정하는 사람도 있지만, 실무에서는 큰 영향이 없다고 밝혀졌다.
- 전달 메서드를 만드는 것이 지루하지만, 재사용할 수 있는 전달 클래스를 인터페이스당 하나씩만 만들어두면 원하는 기능을 덧씌우는 전달 클래스들을 아주 손쉽게 구현할 수 있다.

상속은 반드시 하위클래스가 상위 클래스의 진짜 하위 타입인 상황에서만 쓰여야 한다. 다르게 말하면, 클래스 B가 클래스 A와 is-a 관계일 때만 클래스 A를 상속해야 한다. 만약 상속해야 하면, 'B가 정말 A인가'라고 자문해보고, 확실히 "그렇다"일 때만 상속해야 한다. 만약 "아니다"면, A를 private 인스턴스로 두고 A와는 다른 API를 제공해야 하는 상황이 대다수다.

컴포지션을 사용해야 하는 상황에서 상속을 사용하는 건 내부 구현을 불필요하게 노출하는 꼴이다. 그 결과 API가 내부 구현에 묶이고 그 클래스의 성능도 영원히 제한된다. 더 심각한 문제는 클라이언트가 노출된 내부에 직접 접근할 수 있다는 점이다. 

컴포지션 대신 상속을 사용하기로 결정하기 전에 마지막으로 자문해야할 질문이 있다.
1. 확장하려는 클래스의 API에 아무런 결함이 없는가? 
2. 결함이 있다면, 이 결함이 내 클래스의 API까지 전파돼도 괜찮은가?
> 컴포지션으로 이런 결함을 숨기는 새로운 API를 설계할 수 있지만 상속은 상위 클래스의 API를 그 결함까지도 그대로 승계한다.

