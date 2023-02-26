# item 13. clone 재정의는 주의해서 진행하라

### Cloneable의 문제점
  1. Cloneable은 복제해도 되는 클래스임을 명시하는 용도의 믹스인 인터페이스지만, 아쉽게도 의도한 목적을 제대로 이루지 못했다. 
  2. 가장 큰 문제는 clone 메서드가 선언된 곳이 Cloneable이 아닌 Object이고, 그마저도 protected라는 데 있다. 그래서 Cloneable을 구현하는 것만으로는 외부 객체에서 clone 메서드를 호출할 수 없다. (리플렉션을 사용하면 가능하지만, 100% 보장 X)


하지만 이러한 문제점이 있더라도, Cloneable은 널리 쓰이고 있어 잘 알아두는 것이 좋다. 

### Cloneable 인터페이스가 하는 일
- Cloneable은 아무것도 구현되어 있지 않음
- 그런데 clone을 사용하려면 Cloneable 인터페이스를 구현하고, clone 메서드를 정의해줘야한다.
- clone 메서드로 생성하는 애는 생성자를 통해 만들어지지 않는다.
- Cloneable을 구현안하면, 호출은 가능하지만 Exception이 발생한다.

### clone 메서드의 일반 규약
- x.clone() != x 반드시 true (x를 클론했을때, 클론한 객체와 다른 인스턴스여야 한다.)
- x.clone().getClass() == x.getClass() 반드시 true
- x.cone().equals(x)는 true가 아닐 수도 있다. (애매하네)
    - 복사해도 그 객체를 식별해야하는 id는 유니크해야하는 경우, 복사하는 과정에서 다른 값을 넣어주는 경우도 있음

### super.clone()을 사용해야 하는 이유
- 만약, super.clone()을 사용하지 않고, 생성자를 통해 반환하도록 정의를 한다면 오류가 발생한다. 
    - 생성자를 통해 반환하면 부모 타입인 클래스가 반환된다. (ClassCastException 발생!)
    - 정상적으로 구현하면 super.clone()을 호출하면 부모 클래스 타입이 아닌 호출한 클래스의 타입인 오브젝트가 반환된다. (????)
      - super.clone()을 호출했을때 나오는 오브젝트가 비결정적이다. (super.clone()을 호출했지만 부모 클래스 타입의 인스턴스를 반환하지 않음)

### 가변 객체 clone 정의하는 법
1. 접근 제한자는 public, 반환 타입은 자신의 클래스로 변경한다.
2. super.clone()을 호출한 뒤 필요한 필드를 적절히 수정한다.
    - 배열을 복제할 때는 배열의 clone 메서드를 사용하라.
    - 경우에 따라 final을 사용할 수 없을지도 모른다.
    - 필요한 경우 deep copy를 해야한다. (배열은 clone()을 통해 새로 생성되지만, 배열 안에 있는 인스턴스들은 shallow copy 된다.)
    - 객체만 clone을 통해 만들고, 고수준 API(공개된 public 메서드 따위)를 통해 후처리를 하는 방식을 사용한다.

### 주의
1. clone() 메서드 내에 오버라이딩할 수 있는 메서드가 사용되서는 안된다. (주의)
    - 객체 생성 행위가 하위 클래스에 의해 변화가 발생해서는 안된다.
      - 굳이 사용해야 한다면, 굉장히 스트레이트한 룰을 지키게끔 해야한다.
2. 상속용 클래스에 Cloneable 인터페이스 사용을 권장하지 않는다. (주의)
    - 상속해야하는 프로그래머에게 많은 짐을 주는 행위이다.
    - 부담을 덜기 위해서는 기본 clone() 구현체를 제공하거나, Cloneable 구현을 막을 수도 있다(Throw Exception). (선택권을 하위클래스에게)
3. Thread-safe한 clone 메서드를 만들어줘야 한다면, synchronized를 붙여줘야 한다. (주의)

### 대신하는 법
1. 생성자를 통해 copy (복사 생성자)
```java
public final class PhoneNumber {
    public PhoneNumber(PhoneNumber phoneNumber) {
        this(phoneNumber.areaCode, phonenumber.prefix);
    }
}
```
- clone은 사용자가 원하는 하위 타입으로 변환이 불가능하다. 
- clone은 어떻게 복사되는지 불분명하다.
- clone은 모호한 규약을 가지고 있다.
- clone은 생성자를 거치지 않아서, 전처리 코드를 뛰어넘을 수 있다.
- clone은 final을 못쓴다.
  - 생성자 copy는 이 단점을 모두 상쇄할 수 있다.

2. 팩토리 메서드를 활용하기 (복사 팩터리)