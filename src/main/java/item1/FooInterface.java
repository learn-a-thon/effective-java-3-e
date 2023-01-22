package item1;

/* 장점 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
 java8 부터는 인터페이스가 정적 메서드를 가질 수 없다는 제한이 풀렸습니다.
 java9 부터는 private 정적 메서드까지 허용합니다.

 정적 메서드들을 구현하기 위한 코드 중 많은 부분이 여전히 별도의
 package-private 클래스에 두어야 할 부분이 존재합니다.
 또한, 정적 필드와 정적 멤버 클래스는 여전히 public 이어야 합니다.*/
public interface FooInterface {

    static Foo getInstance() {
        return new Foo();
    }
}
