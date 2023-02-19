# item 15. 클래스와 멤버의 접근 권한을 최소화하라.

- 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다.

### 정보 은닉의 장점
1. 시스템 개발 속도를 높인다, 여러 컴포넌트를 병렬로 개발할 수 있기 때문이다.
2. 시스템 관리 비용을 낮춘다, 각 컴포넌트를 더 빨리 파악할 수 있다.
3. 성능 최적화에 도움을 준다, 다른 컴포넌트에 영향을 주지 않고 최적화할 수 있기 때문이다.
4. 재사용성을 높인다, 외부에 거의 의존하지 않기 때문이다.
5. 큰 시스템을 제작하는 난이도를 낮춰준다, 시스템 전체가 아직 완성되지 않은 상태에서도 개별 컴포넌트의 동작을 검증할 수 있기 때문이다.

### package-private(default) vs public 
톱레벨 클래스와 인터페이스에 부여할 수 있는 접근 수준은 package-private와 public 
    - 만약 public을 부여하면 공개 API가 되어 영원히 하위 호환을 생각해야 한다.
    - package-private을 부여하면, 내부 구현이 되어 수정에 용이하다.

### 설계 방법
1. 클래스의 공개 API를 세심히 설계한 후, 그 외의 모든 멤버는 private으로 만들자. 그런 다음 오직 같은 패키지의 다른 클래스가 접근해야 하는 경우 package-private으로 풀어주자.
2. 권한을 풀어주는 일을 자주 한다면, 시스템에서 컴포넌트를 더 분해해야 하는 것은 아닌지 다시 고민한다. 
3. private와 package-private 멤버는 모두 해당 클래스의 구현에 해당하므로 보통은 공개 API에 영향을 주지 않는다. 단, Serializable을 구현한 클래스에서는 그 필드들도 의도치 않게 공개 API가 될 수 있다.
4. public class에서는 멤버의 접근 수준을 package-private에서 protected로 바꾸는 순간 그 멤버에 접근할 수 있는 대상 범위가 엄청나게 넓어진다.
    - public class의 protected 멤버는 공개 API이므로 영원히 지원되어야 하며, 내부 동작 방식을 API 문서에 적어 사용자에게 공개해야 할 수도 있다.
    - 따라서 protected는 적을수록 좋다.

5. public 클래스의 인스턴스 필드는 되도록 public이 아니여야 한다. 
   - 이렇게 되면 그 필드가 가질 수 있는 값을 제한할 힘을 잃게 된다.
   - 즉, 불변식을 보장할 수 없게 된다.
   - 그리고 수정될 때 락 힉득 같은 다른 작업을 할 수 없어서, public 가변 필드를 갖는 클래스는 스레드 안전하지 않다.
   - 심지어 필드가 final 이면서 불변객체라도 문제가 발생한다, 왜냐하면 다른곳에서 사용할수도 있기 때문에 내부 구현을 바꾸고 싶어도 그 public 필드를 없애는 방식으로는 리팩터링 할 수 없다.
   - 물론, 해당 클래스가 표현하는 추상 개념을 완성하는 데 꼭 필요한 구성요소로써의 상수라면 public static final 필드로 공개해도 좋다.
   - 길이가 0이 아닌 배열은 모두 변경 가능하지 주의하자, 따라서 클래스에서 public static final 배열 필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공해서는 안된다.
     - 클라이언트에서 값을 수정할 수 있게 된다.
     - 대신 아래와 같은 방법으로 제공하자.
     ```java
     private static final Thing[] PRIVATE_VALUES = {...};
     public static final LIST<Thing> VALUES = 
        Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

     private static final Thing[] PRIVATE_VALUES = {...};
     public static final Thing[] values() {
        return PRIVATE_VALUES.clone();
     }
     ```

### 모듈
자바 9에서는 모듈 시스템이라는 개념이 도입되면서 두 가지 압묵적 접근 수준이 추가되었다. 
1. 모듈은 패키지의 묶음이다, 모듈은 자신에 속하는 패키지 중 공개할 것들을 `module-info.java`에 선언한다. 
   - protected, public 멤버라도 해당 패키지를 공개하지 않았다면 모듈 외부에서는 접근할 수 없다. 
   - 물론 모듈 안에서는 exports로 선언했는지 여부에 아무런 영향도 받지 않는다.
   - 모듈 시스템을 활용한다면 클래스를 외부에 공개하지 않으면서도 같은 모듈을 이루는 패키지 사이에서는 자유롭게 공유할 수 있다.
2. 이 암묵적 접근 수준들은 각각 public 수준과 protected 수준과 같으나 그 효과가 모듈 내부로 한정되는 변종인 것이다. 

주의해야할 점은 다음과 같다. 
1. 모듈의 JAR 파일을 자신의 모듈 경로가 아닌 애플리케이션의 클래스 패스에 두면 그 모듈 안의 모든 패키지는 마치 모듈이 없는 것처럼 행동한다.
    - 즉, 모듈이 공개했는지 여부와 상관없이, public 클래스가 선언한 모든 public ghrdms protected 멤버를 모듈 밖에서도 접근할 수 없게 된다. 