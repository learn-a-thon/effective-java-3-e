# item1. 생성자 대신 정적 팩터리 메서드를 고려하라. 

### 장점
1. 생성자의 시그니처가 중복되는 경우 고려할만 하다.
2. 인스턴스를 항상 새로 만들 필요가 없다.
- 기본 생성자를 막아버리고, 정적 메서드를 제공함으로써 클래스가 객체 생성을 완전히 컨트롤할 수 있다.

**플라이웨이트 패턴?**    
```text
자주 사용하는 값들을 미리 캐싱해서 넣어놓고, 꺼내서 쓰는 디자인 패턴    
왜 언급? 정적 메서드를 통해 객체 생성을 컨트롤
```

3. 인터페이스 타입을 활용할 수 있다.
- 정적 팩토리 메서드를 통해 인터페이스의 하위 클래스들을 반환할 수 있다. (유연성)
```java
publics tatic HelloService of(String lang) {
   if(~~) {
        return aService();
   }
   else {
        return bService();
   }
}
```

**Java 8부터는 인터페이스에도 STATIC METHOD를 선언할 수 있다.**

4. 인터페이스 기반의 프레임웤을 사용할 수 있게 해준다.
- 3번과 이어지는 장점, 정적 팩토리 메서드를 통해 구체적인 타입을 숨기고 인터페이스 타입을 사용하게끔 강제할 수 있음

5. 정적 팩토리 메서드를 작성하는 시점에 굳이 구현체가 없어도 된다.
- 정적 팩토리 메서드를 작성하는 시점에 서비스 제공자 프레임워크(ServiceLoader)가 있다면 인터페이스만 있어도 된다. (구현체가 없어도 된다.)
```java

public class HelloServiceFactory {

    public static void main(String[] args) {
        ServiceLoader<HelloService> loader = ServiceLoader.load(HelloService.class); // 모든 HelloService 구현체 조회
        Optional<HelloService> helloServiceOptional = loader.findFirst();
        helloServiceOptional.ifPresent(h -> {
            System.out.println(h.hello());
        }); // ChineseHelloService에 의존적이지가 않다. 어떤 구현체가 올지 모름 
    }
}
```

### 단점 
1. 상속이 불가능하다. (기본 생성자를 private으로 막은 후, 정적 팩토리 메서드를 제공하기 때문)
- 물론 기본 생성자를 제공하면서 정적 팩토리 메서드도 제공하는 경우도 있다. 

2. 자바 docs에서 한눈에 파악하기가 쉽지가 않다. 
- constructors에 들어가지 않고, method 탭에 들어가기 때문에
- 그렇기 때문에 정적 팩토리 메서드의 네이밍이 일관적이여야 한다. (of와 같이)
- 좋은 방법 중 하나는 정적 팩토리 메서드에 대한  주석을  작성하는 것

### 완벽 공략 1. 열거 타입 (Enum)
- 가질 수 있는 값을 제한할 수 있기 때문에 Type-Safety를 보장할 수 있다. 
- 싱글톤 패턴을 안전하게 구현할 수 있다.
- Enum은 JVM 레벨에서 오직 하나의 인스턴스만을 보장하기 때문에 equals 말고 '==' 사용해라. (NPE 방지 차원)

**Enum을 Key로 사용하는 Map을 정의하세요, Enum을 담고 있는 Set을 만들어보세요**
```text
## EnumMap

Javadoc에 따르면 

"when the map is created. Enum maps are represented internally as arrays. This representation is extremely compact and efficient." 

-> hashmap 은 key를 bucket에 저장하고각 bucket이 linked list를 참조 하고 있음. (linkedlist에는 hash(key)가 같은 element가 들어감) 그런데 enummap 의 경우 key로 사용할 값이 제한되어 있으므로, 그 갯수만큼 길이를 가진 array를 선언하고. 해당 index에 값을 넣으면 됨. 

 ## EnumSet

Javadoc에 따르면 

"when the set is created. Enum sets are represented internally as bit vectors."

-> hashset은 hashmap 과 같은데 map의 value가 있다 없다를 표현하는 지시자 같은 값이 들어감. enumset은 값이 있다 없다만 표시하면 되니까 enummap 처럼 array로 구현하지 않고 10101011 같은 bitvector로 구현이 가능.
```

### 완벽 공략 2. 플라이웨이트 패턴 
- 같은 객체가 자주 사용될 때 플라이웨이트 패턴을 사용할 수 있다. 
    - 매번 객체를 생성해서 줄 필요가 있을까? -> 캐싱하면 된다.
    - 즉 플라이웨이트 패턴이란, 객체를 캐싱하여 메모리 사용을 줄이는 패턴이다.
    - 자주 변하는 속성과 변하지 않는 속성을 분리하고 재사용하여 메모리 사용을 줄이는 것 

### 완벽 공략 3. 인터페이스와 정적 메서드 
- 자바 8 이전에는 인터페이스에 정적 메서드를 정의하는 것은 불가능 했었음
  - 인스턴스 생성 후에 사용할 수 있는 것은 default 메서드를 활용하면 됨 
- 자바 9 부터는 private한 정적 메서드를 정의할 수 있음, 단 private 필드는 아직도 불가

### 완벽 공략 4. 서비스 제공자 프레임워크 
- 서비스 제공자 인터페이스 : 다양한 형태로 만들어질 수 있는 서비스 제공자 인터페이스 (여러 구현체로 만들어질 수 있는)
- 서비스 제공자 : 서비스 제공자 인터페이스의 구현체들
- 서비스 제공 API :  구현체를 등록하는 방법을 제공하는 것, SPRING 같은 경우는 Configuration 에노테이션을 정의한 클래스내에 @Bean 메서드를 통해 구현체를 등록함 
- 서비스 접근 API : 서비스를 가져오는 방법, Spring에서는 Bean을 가져오는 방법들

**브릿지 패턴?** 
- 구체적인 것과 추상적인 것을 분리해서 사이에 다리를 놓는 것, 서로 영향을 주지 않으면 개별적인 계층구조로 발전할 수 있게끔 사용되는 구조 
  - 만약 챔피언과 스킨이 구분되어 있지 않으면, 스킨이 추가될 때마다 챔피언 클래스도 계속 추가해야 함
  - 챔피언에 스킨 필드를 가지게 해서 서로 구분 시키는 것 (브릿지 패턴)
- 서비스 제공자 프레임워크를 사용할때도 서비스 제공자 프레임워크를 활용하는 코드는 그대로 두고 서비스 구현체만 수정하면서 확장된 기능을 제공할 수 있었다. 
    - 즉, 이래서 브릿지 패턴이 언급된 것
    

### 완벽 공략 5. 리플렉션 
- 서비스 제공자 인터페이스가 없다면 각 구현체를 인터페이스로 만들 때 리플렉션을 사용해야 한다.
