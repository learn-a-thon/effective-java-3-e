# 이펙티브 자바 

# Item 1: 생성자 대신 정적 팩터리 메서드를 고려하라.

> 정적 팩토리 메소드는 디자인패턴이 아니다.

### 장점
 - 이름을 가질 수 있다.
 - 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.  
   (생성 비용이 큰 객체가 자주 요청되는 상황이라면 성능을 상당히 올려준다.)
 - 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 생긴다. 
 - 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다. 
 - 정적 팩토리 메소드를 작성하는 시점에 반환할 객체의 클래스가 존재하지 않아도 된다. 

### 단점
 - 상속을 위해 public이나 protected 생성자가 필요하기 때문에 정적 팩토리 메소드만 제공하면 하위 클래스를 만들 수 없다.
 - 프로그래머가 찾기 어렵다.

 **정적 팩토리 메소드와 public 생성자는 각자의 쓰임새가 있다. 하지만 대부분의 경우 정적 팩토리가 유리한 점이 많기 때문에 무작성 public 생성자를 사용한 부분이 있다면 수정하자.**

 # Item 2: 생성자에 매개변수가 많다면 빌더를 고려하라.

 ### 점층적 생성자 패턴
 ```java
 public class NutritionFacts {
    private final int servingsSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public NutritionFacts(int servingSize, int servings) {
        this(servingSize, servings, 0);
    }

    public NutritionFacts(int servingsSize, int servings, int calories) {
        this(servingsSize, servings, calories, 0);
    }

    public NutritionFacts(int servingsSize, int servings, int calories, int fat) {
        this(servingsSize, servings, calories, fat, 0);
    }

    public NutritionFacts(int servingsSize, int servings, int calories, int fat, int sodium) {
        this(servingsSize, servings, calories, fat, sodium, 0);
    }

    public NutritionFacts(int servingsSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
        this.servingsSize = servingsSize;
        this.servings = servings;
        this.calories = calories;
        this.fat = fat;
        this.sodium = sodium;
        this.carbohydrate = carbohydrate;
    }
}
```

점층적 생성자 패턴을 이용해 인스턴스를 만들려면 원하는 매개변수를 모두 포함한 생성자 중에 가장 짧은 것을 골라 호출하면된다.

### 단점
 - 매개변수가 많아지면 클라이언트 코드를 작성하기 번거롭고, 읽기 어렵다.
 - 타입이 같은 매개변수를 전달할 때 순서가 잘못되어도 컴파일 시점에 알 수 없다.

 ## 자바빈즈 패턴
 ```java
public class NutritionFacts2 {
    private int servingsSize = -1;
    private int servings = -1;
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public NutritionFacts2() {}
    
    public void setServingsSize(int val) { this.servingsSize = val; }
    public void setServings(int val) { this.servings = val; }
    public void setCalories(int val) { this.calories = val; }
    public void setFat(int val) { this.fat = val; }
    public void setSodium(int val) { this.sodium = val; }
    public void setCarbohydrate(int val) { this.carbohydrate = val; }
}
 ```
### 단점
 - 하나의 인스턴스를 만들기 위해 세터를 여러 번 호출해야하낟.
 - 마지막 setter까지 완벽하게 호출되기 전까지는 객체의 일관성이나 완전성이 무너진 상태에 놓인다.
 - 불변 클래스를 사용하기 애매하다

 ### 빌더 패턴
 ```java
public class NutritionFacts3 {
    private final int servingsSize;
    private final int servings;
    private final int calories;
    private final int fat;
    private final int sodium;
    private final int carbohydrate;

    public static class Builder {
        // required
        private final int servingSize;
        private final int servings;

        // optional
        private int calories = 0;
        private int fat = 0;
        private int sodium = 0;
        private int carbohydrate = 0;

        public Builder(int servingSize, int servings) {
            this.servingSize = servingSize;
            this.servings = servings;
        }

        public Builder calories(int val) {
            calories = val;
            return this;
        }

        public Builder fat(int val) {
            fat = val;
            return this;
        }

        public Builder sodium(int val) {
            sodium = val;
            return this;
        }

        public Builder carbohydrate(int val) {
            carbohydrate = val;
            return this;
        }

        public NutritionFacts3 build() {
            return new NutritionFacts3(this);
        }
    }
    
    private NutritionFacts3(Builder builder) {
        servingsSize = builder.servingSize;
        servings = builder.servings;
        calories = builder.calories;
        fat = builder.fat;
        sodium = builder.sodium;
        carbohydrate = builder.carbohydrate;
    }
}

// 사용
NutritionFacts cocaCola = new NutritionFacts.Builder(240, 9)
                                            .calories(10);
                                            .fat(50)
                                            .build();
 ```

### 장점
 - 쓰기 쉽고, 읽기 쉽다.
 - 계층적으로 설계된 클래스와 함께 쓰기에 좋다. (Pizza, NyPizza, Calzone.java 참고)

### 단점
 - 코드가 장황해진다. 매개 변수가 많아야 값어치가 있다.

> 매개변수가 많다면 빌더 패턴을 선택하는게 더 낫다.   
점층적 생성자 패턴보다 읽고 쓰기 쉽고, 자바빈즈 패턴보다 훨씬 안전하다. 

# Item 3:  private 생성자나 열거 타입으로 싱글톤임을 보증하라. 
```java
// 정적 팩토리 방식의 싱글톤
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
    
    public static Elvis getInstance() {
        return INSTANCE;
    }
}
```
### 장점
 - 쉽게 변경 가능 (싱글톤이 아니게)
 - 정적 팩토리를 제네릭 싱글톤 팩토리로 만들 수 있다 (item 30)
 - 참조 공급자로 사용할 수 있다. (`Supplier<Elvis>`)

 ```java
 // 열거타입 방식의 싱글톤
 public enum Elvis2 {
    INSTANCE
}
```

### 장점
 - 간결하다
 - 추가노력 없이 직렬화 가능
 - 복잡한 직렬화 상황이나 리플렉션 공격에도 제 2의 instance 생성을 완벽하게 막아준다.
 - 대부분 상황에서는 원소가 하나 뿐인 열거 타입이 싱글톤을 만드는 가장 좋은 방법이다.

> 만드려고하는 싱글톤이 Enum 외의 클래스를 상속해야 한다면 이 방법은 사용할 수 없다.  


 # Item 4: 인스턴스화를 막으려거든 private 생성자를 사용하라

### 1. 정적 메서드만 담은 유틸리티 클래스는 인스턴스로 만들어 쓰려고 설계한 클래스가 아니다.  
### 2. 추상 클래스로 만드는 것으로는 인스턴스화를 막을 수 없다.  
### 3. private 생성자를 추가하면 클래스의 인스턴스화를 막을 수 있다.  
```java
// 추상 클래스로 생성을 제한하는 방법은 충분하지 않다. 
public class UtilityClass {

    /**
     * 이 클래스는 인스턴스를 만들 수 없다.
     */
    private UtilityClass() {
        throw new AssertionError();
    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        String hello = UtilityClass.hello();

        UtilityClass utilityClass = new UtilityClass();
    }
}

```
#### 4. 생성자에 주석으로 인스턴스화 불가한 이유를 설명하는 것이 좋다.  
### 5. 상속을 방지할 때도 같은 방법을 사용할 수 있다. 

# Item 5: 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라
  

### 1. 사용하는 자원에 따라 동작이 달라지는 클래스는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다. (의존 객체 주입을 사용하라)  
### 2. 의존 객체 주입이란 인스턴스를 생성할 때 필요한 자원을 넘겨주는 방식이다.  
### 3. 이 방식의 변형으로 생성자에 자원 팩터리를 넘겨줄 수 있다.   
### 4. 의존 객체 주입을 사용하면 클래스의 유연성, 재사용성, 테스트 용이성을 개선할 수 있다.   
```java
public class SpellChecker {

    // Dictionary 가 interface 라면 SpellChecker는 재사용이 가능해진다.
    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        return dictionary.closeWordsTo(typo);
    }
}
```

### 이 패턴의 쓸만한 변형으로 생성자에 자원 팩토리를 넘겨주는 방식이 있다. 
사용하는 객체 의존성을 바로 주입받는 것이 아니라 자원을 생성하는 팩토리 객체를 주입받는 형태로 사용되는 것이다. 

> Java 8에서 소개한 Supplier<T> 인터페이스가 팩토리를 표현한 완벽한 예이다. 

# Item 6: 불필요한 객체 생성을 피하라
### 문자열
 - 동일한 객체를 매번 새로 만들 필요가 없다.
 - 문자열은 리터럴을 사용하는 것이 좋다.
### 정규식, pattern
 - 생성 비용이 비싼 객체라서 반복해서 생성하기 보다, 캐싱하여 재사용하는 것이 좋다.
### 오토박싱
 - 기본타입을 그게 상응하는 박싱된 기본타입으로 상호 변환해주는 기술
 - 기본타입과 박싱된 기본 타입을 섞어서 사용하면 변환하는 과정에서 불필요한 객체가 생성될 수 있다.
### 사용 자제 API
### 정규 표현식
### 한 번 쓰고 버려져서 가비지 컬렉션 대상이 된다.
### 초기화 지연 기법
### 방어적 복사
### 가비지 컬렉션
 - 개념 : Mark, Sweep, Compact
 - 알고리즘 : Serial, Parallel, CMS, G1, ZGC, Shenandoah
 - 참고) How to choose the best Java garbage collector

# Item 7: 다 쓴 객체 참조를 해제하라.
 - 어떤 객체에 대한 레퍼런스가 남아있다면 해당 객체는 가비지 컬렉션의 대상이 되지 않는다.
 - 자기 메모리를 직접 관리하는 클래스라면 메모리 누수에 주의해야한다.
 - 참조 객체를 null 처리하는 일은 예외적인 경우이며 가장 좋은 방법은 유효 범위 밖으로 밀어내는 것이다.

#### WeakHashMap
더 이상 사용하지 않는 객체를 GC할 때 자동으로 삭제해주는 Map
 - key가 더 이상 강하게 레퍼런스되는 곳이 없다면 해당 엔트리를 제거한다.
 - 레퍼런스 종류
    - String, Soft, Weak, Phantom
 - 맵의 엔트리를 맵의 Value가 아니라 Key에 의존해야하는 경우에 사용할 수 있다.
 - 캐시를 구현하는데 사용할 수 있지만, 캐시를 직접 구현하는 것은 권장하지 않는다.

# Item 8: finalizer와 cleaner 사용을 피하라
 - 즉시 수행된다는 보장이 없다.
 - 실제로 실행되지 않을 수도 있다.
 - 동작 중에 예외가 발생하면 정리 작업이 처리되지 않을 수 있다.
 - 심각한 성능 문제가 있다.
 - 보안 문제가 있다. 
 
# Item 9: try-finally 보다 try-with-resources를 사용하라
  - try-finally는 최선의 방법이 아니다.
  - try-with-resources를 사용하면 코드가 더 짧고 분명하다.
  - 예외 정보 또한 훨씬 유용하다.

# Item 10: equals는 일반 규약을 지켜 재정의하라
equals는 논리적 동치성을 가질 때 재정의하라.

> 반사성 - null이 아닌 모든 참조 값 x에 대해, x.equals(x)는 true  
대칭성 -  null이 아닌 모든 참조 값 x,y에 대해, xequals(y)가 true이면 y.equals(x)도 true이다.   
추이성 - null이 아닌 모든 참조값 x,y,z에 대해, x.equals(y)가 true이고, y.equals(z)도 true이면 y.equals(z)도 true이다.  
일관성 - null이 아닌 모든 참조 값 x, y에 대해, x.equals(y)를 반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.  
null-아님 - null이 아닌 모든 참조값 x에 대해, x.equals(null)은 false이다.

### 좋은 equals 메소드를 구현하는 방법
 - == 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다.
   - 단순한 성능 최적화용
 - instanceof 연산자로 입력이 올바른 타입인지 확인한다.
   - 인터페이스를 구현한 클래스들의 equals를 지원해야하는지에 대해 고려 
 - 입력을 올바른 탕비으로 형변환한다.
 - 입력 객체와 자기 자신에 대응되는 핵심 필드들이 모두 일치하는지 하나씩 검사한다. 


# Item 11. equals를 재정의하려거든 hashCode도 재정의하라.
 - equals 비교에 사용하는 정보가 변경되지 않았다면 hashCode는 매번 같은 값을 리턴해야 한다.
 - 두 객체에 대한 equals가 같다면, hashCode의 값도 같아야 한다.
 - 두 객체에 대한 equals가 다르더라도, hashCode의 값은 같을 수 있지만 해사태이블 성능을 고려해 다른 값을 리턴하는 것이 좋다. 

 - 해시코드 구현 방법
    ```java
    @Override
        public int hashCode() {
            int result = Short.hashCode(areaCode);
            result = 31 * result + Short.hashCode(prefix);
            result = 31 * result + Short.hashCode(lineNum);
            return result;
        }
    ```
# Item 12. toString을 항상 재정의하라
 - toString은 간결하면서 사람이 읽기 쉬운 형태의 유익한 정보를 반환해야 한다.
 - Object의 toString은 클래스이름@16진수로 표시한 해시코드로 표현된다.
 - 객체가 가진 모든 정보를 보여주는 것이 좋다.
 - 값 클래스라면 포맷을 문서에 명시하는 것이 좋으며 해당 포맷으로 객체를 생성할 수 있는 정적 팩토리나 생성자를 제공하는 것이 좋다.
 - toString이 반환한 값에 포함된 정보를 얻어올 수 있는 API를 제공하는 것이 좋다.
 - 경우에 따라 AutoValue, 롬복 또는 IDE를 사용하지 않는게 적절할 수 있다. 

# Item 13. clone 재정의는 주의해서 진행하라
 - clone 규약
  - x.clone() != 반드시 true
  - x.clone().getClass() == x.getClass() 반드시 true
  - x.clone().equals(x) true가 아닐수도 있다.
 - 불변 객체라면 다음으로 충분하다.
  - Cloneable 인터페이스를 구현하고
  - clone 메소드를 재정의한다.

### clone 대신 권장하는 방법
 - "복사 생성자" 또는 변환 생성자, "복사 팩토리" 또는 변환 팩토리
 - 생성자를 쓰지 않으며, 모호한 규약, 불필요한 검사 예외, final 용법 방해 등에서 벗어날 수 있다.
 - 또 다른 큰 장점 중 하나로 인터페이스 타입의 인스턴스를 리턴할 수 있다.
  - 클라이언트가 복제본의 타입을 결정할 수 있다.

# Item 14. Comparable을 구현할지 고민하라.
 - Object.equals에 더해서 순서까지 비교할 수 있으며 Generic을 지원한다.
 - 자기 자신(this)이 compareTo에 전달된 객체보다 작으면 음수, 같으면 O, 크다면 양수를 리턴한다.
 - 반사성, 대칭성, 추이성을 만족해야 한다.
 - 반드시 따라야 하는 것은 아니지만 x.compareTo(y) == 0 이라면 x.equals(y)가 true여야 한다.

### compareTo 구현 방법
  - 자연적인 순서를 제공할 클래스에 implements Comparatable<T> 를 선언한다.
  - compareTo 메서드를 재정의한다.
  -  compareTo 메서드 안에서 기본 타입은 박싱된 기본 타입의 compare을 사용해 비교한다.
  - 핵심 필드가 여러 개라면 비교 순서가 중요하다. 순서를 결정하는데 있어서 가장 중요한 필드를 비교하고 그 값이 0이라면 다음 필드를 비교한다.

# Item 15. 클래스와 멤버의 접근 권한을 최소화하라.
 - 기본 원칙은 하나다. 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다. 
 - public 클래스의 인스턴스 필드는 되도록 public이 아니어야 한다.
 - 클래스에서 public static final 배열 필드를 두거나 이 필드를 반환하는 접근자 메서드를 제공해서는 안된다. 

> 프로그램 요소의 접근성은 최소한으로 하자. 의도하지 않는 필드에 대해서는 클래스, 인터페이스, 멤버가 의도치 않게 api로 공개되는 일이 없어야 한다. public static final 필드가 참조하는 객체가 불변인지 확인하라.

# Item 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라. 
접근자를 지정하는 방식은 캡슐화의 이점을 제공하고, 불변식을 보장하는 등 객체지향적인 프로그래밍을 가능하게 한다. 하지만 **package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출한다 해도 문제가 없다.**

```java
public class ColorPoint {
    private static class Point {
        public double x;
        public double y;
    }

    public Point getPoint() {
        Point point = new Point();
        point.x = 1.0;
        point.y = 2.0;
        
        return point;
    }
}

```
 - ColorPoint 외부에서 Point를 조작하는 것은 불가능하다. ColorPoint에서는 가능하다.
 - 클래스를 중첩시키는 방식은 클래스를 선언하는 측면이나 이를 사용하는 측면에서도 접근자 방식보다 좋다.

### 정리
 > public 클래스는 절대 가변 필드를 직접 노출해서는 안 된다. 불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 수는 없다. 하지만 package-private 클래스나 private 중첩 클래스에서는 종종 필드를 노출하는 편이 나을 때도 있다. 

# Item 17. 변경 가능성을 최소화하라.
불변 클래스는 생성부터 소멸까지 절대변하지 않는다. 불변 클래스는 가변 클래스보다 설계하고 구현하고 사용하기 쉽다. 오류의 여지도 적고 훨씬 안전하다. 

## 불변 클래스를 만들기 위한 다섯 가지 규칙
 - 객체의 상태를 변경하는 메서드를 제공하지 않는다.
 - 클래스를 확장할 수 없도록 한다. (대표적으로 final)
 - 모든 필드를 final로 선언한다.
    - 신규 인스턴스를 동기화 없이 다른 스레드로 건네도 문제없도록 보장하는데도 필요하다.
 - 모든 필드를 private으로 선언한다.
 - 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 한다. 

불변 객체는 스레드 간에 안전하게 공유할 수 있다. 따라서 불변 클래스라면 한 번 만든 인스턴스를 최대한 재활용하기를 권한다. 가장 대표적인 것이 자주쓰이는 값을 상수로 제공하는 것이다. 
```java
public static final Complex ZERO = new Complex(0, 0);
public static final Complex ONE = new Complex(1, 0);
public static final Complex I = new Complex(0, 1);
```

불변 클래스는 자주 사용되는 인스턴스를 캐싱하여 같은 인스턴스를 중복 생성하지 않게 해주는 정적 팩토리를 제공할 수 있다. 이런 정적 팩토리를 사용하면 여러 클라이언트가 인스턴스를 공유하여 메모리 사용량과 가비지 컬렉션 비용이 줄어든다. 
**불변 객체는 자유롭게 공유할 수 있음은 물론, 불변 객체까리는 내부 데이터를 공유할 수 있다.**

반면에 단점도 있다. 값이 다르면 반드시 독립된 객체로 만들어야한다는 것이다. 크기가 큰 객체의 작은 일부를 변경하기 위해 큰 생성 비용을 매번 지불해야한다.

### 정리
 - getter가 있다고 무조건 setter를 만들지말자
 - 클래스는 꼭 필요한 경우가 아니라면 불변이어야 한다. 불변 클래스의 단점이라곤 특정 상황에서의 잠재적 성능저하 뿐이다.
 - 만약 성능 때문에 어쩔 수 없다면 불변 클래스와 쌍을 이루는 가변 동반 클래스를 public 클래스로 제공하도록 하자.
 - 생성자는 불변식 설정이 모두 완료된, 초기화가 완벽히 끝난 상태의 객체를 생성해야한다.

# Item 18. 상속보다는 컴포지션을 사용하라.
**메소드 호출과 달리 상속은 캡슐화를 깨뜨린다.** 상위 클래스가 어떻게 구현되느냐에 따라 하위 클래스의 동작에 이상이 생길 수 있다. 이런 상황에서는 중간 wrapper 클래스를 두고 확장할 수 있도록 할 수 있다. 

```java
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

    // ... other mehtod overriding

    
    public static void main(String[] args) {
        // 컴포지션 방식은 어떤 Set 구현체라도 계측할 수 있다.
        Set<String> stringSet = new CompositionInstrumentedSet<String>(new TreeSet<String>());
        Set<Object> s = new CompositionInstrumentedSet<>(new HashSet<>(10));
    }
}
```

### 정리
상속은 캡슐화를 해친다는 문제가 있다. 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 사용해야한다. 상속 대신 컴포지션과 전달을 사용하자. 특히 래퍼 클래스로 구현할 적당한 인터페이스가 있다면 더욱 그렇다. 래퍼 클래스는 하위 클래스보다 견고하고 강력하다.

# Item 19. 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라. 
> 상속용 클래스는 재정의할 수 있는 메서드들을 내부적으로 어떻게 이용하는지 (자기 사용) 문서로 남겨야 한다.  
효율적인 하위 클래스를 큰 어려움 없이 만들 수 있게 하려면 클래스의 내부 동작 과정 중간에 끼어들 수 있는 훅을 잘 선별하여 protected 메서드 형태로 공개해야할 수도 있다. 

```java
protected void removeRange(int fromIndex, int toIndex)
```
AbstractList의 removeRange 메서드의 명세를 살펴보면 fromIndex에서 시작하는 리스트 반복자를 얻어 모든 원소를 제거할 때까지 `ite.next`, `ite.remove`를 반복 호출하도록 구현되어있다는 내용이 설명되어있다.  
List 구현체의 최종 사용자는 removeRange 메서드에는 관심이 없다. 하지만 메서드의 상세 동작을 설명한 이유는 단지 하위 클래스에서 부분리스트의 삭제 메서드를 이미 잘 만들어진 고성능 api를 사용하기 쉽도록하기 위함이다.  

### 상속 시 고려해야할 사항
 - **상속용 클래스를 시험하는 방법은 직접 하위 클래스를 만들어보는 것이 유일하다.** 
 - protected 메서드와 필드를 구현하면서 선택한 개발 방향은 반드시 검증되어야한다. 
 - 상속용 클래스의 생성자는 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다.

 ```java
public class Super {
    // 잘못된 예 - 생성자가 재정의 가능 메서드를 호출한다.
    public Super() {
        overrideMe();
    }
    public void overrideMe() {

    }
}
 ```
 ```java
public class Sub extends Super {
    // 초기화되지 않은 final 필드, 생성자에서 초기화한다.
    private final Instant instant;

    Sub() {
        instant = Instant.now();
    }

    // 재정의 가능 메서드, 상위 클래스의 생성자가 호출한다.
    @Override
    public void overrideMe() {
        System.out.println(instant);
    }

    public static void main(String[] args) {
        Sub sub = new Sub();
        sub.overrideMe();
    }
}
 ```
 상위 클래스의 생성자는 하위 클래스의 생성자가 인스턴스 필드를 초기화하기도 전에 `overrideMe`를 호출하기 때문에 null이 출력된다.  
 상위 클래스에서 instant 객체의 메서드를 호출했다면 `NPE`가 발생했을 것이다. 

 `Cloneable` 과 `Serializable` 인터페이스는 상속용 설계의 어려움을 한층 더해주기 때문에 좋지 않다. 해당 클래스를 확장하려는 개발자에게 많은 부담을 줄 수 있다. 
 **clone과 readObject는 생성자와 비슷한 효과를 낸다. 즉, 해당 메서드를 수행할 때 새로운 객체를 만든다.** 따라서 두 메서드에서도 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다.  
  - clone은 하위 클래스의 복제본의 상태를 수정하기 전(불완전 상태)에 재정의 메서드를 호출한다.
  - readObject는 하위 클래스의 상태가 미처 다 역직렬화되기 전에 재정의한 메서드부터 호출하게 된다.

이렇게 의도하지 않은 확장으로 인해 발생하는 문제를 해결하는 가장 좋은 방법은 상속용으로 설계하지 않은 클래스는 상속을 금지시키는 것이다. 두 가지 방법을 활용할 수 있다.
- 클래스를 final로 선언하는 방법
- 모든 생성자를 private이나 package-private으로 선언하고 public 정적 팩터리를 만들어주는 방법

클래스 내부에서는 재정의 가능 메서드를 사용하지 않게 만들고 이 사실을 문서로 남기면 메서드를 재정의해도 다른 메서드의 동작에 아무런 영향을 주지 않는다. 

### 정리
상속용 클래스를 설계하기란 쉽지 않다. 클래스 내부에서 스스로를 어떻게 사용하는지 모두 문서로 남겨야 하며, 일단 문서화한 것은 그 클래스가 쓰이는 한 반드시 지켜야 한다. 그러지 않으면 그 내부 구현 방식을 믿고 활용하던 하위 클래스를 오동작하게 만들 수 있다. 다른 이가 효율 좋은 하위 클래스를 만들 수 있도록 일부 메서드를 protected로 제공해야할 수도 있다. 따라서 확장해야할 명확한 이유가 없다면 상속을 금지하는게 좋다.

# Item 20. 추상 클래스보다는 인터페이스를 우선하라. 
추상 클래스와 인터페이스의 가장 큰 차이는 추상클래스가 정의한 타입을 구현하는 클래스는 반드시 추상 클래스의 하위 클래스가 되어야한다는 것이다.  
 자바는 단일 상속만 지원하니, 추상 클래스 방식은 새로운 타입을 정의하는 데 커다란 제약을 준 것이다. 반면 인터페이스가 선언한 메서드를 모두 정의하고 그 일반 규약을 잘 지킨 클래스라면 다른 어떤 클래스를 상속했든 같은 타입으로 취급한다. 

 인터페이스는 추상클래스도 느슨한 결합이 가능하도록하고 **계층 구조가 없는 타입 프레임워크를 만들 수 있다.** 클래스는 두 부모를 섬길 수 없고, 클래스 계층 구조에 믹스인을 삽입하기에 합리적인 위치가 없다. 

 ```java
public interface Singer {
    AudioClip sing(Song s)
}
 ```

 ```java
public interface Songwriter {
    Song compose(int chargPosition);
}
 ```

 ```java
 public interface SingerSongwriter extends Singer, Songwriter {
    AudioClip strum();
    void actSenstive();
 }
 ```

타입을 인터페이스로 정의하면 가수 클래스가 Singer와 Songwriter 모두를 구현해도 전혀 문제되지 않는다. 거대한 클래스 계층구조만으로는 공통 기능을 정의해놓은 타입이 없기 때문에 파라미터만 다른 메서드들이 수 없이 많아질 수 있다.

> 인터페이스는 기능을 향상 시키는 안전하고 강력한 수단이 된다.   
상속을 통해 만든 클래스는 래퍼 클래스 보다 활용도가 떨어지고 깨지기는 더 쉽다.

### 템플릿 메서드 패턴
인터페이스와 추상 골격 구현 클래스를 함께 제공하는식으로 인터페이스와 추상 클래스의 장점을 모두 취하는 방법이 있다.  
인터페이스로는 타입을 정의하고, 필요하면 디폴트 메서드 몇 개도 함께 제공한다. 그리고 골격 구현 클래스는 나머지 메서드들까지 구현한다.  
**관례상 인터페이스 이름이 Interface라면 그 골격 구현 클래스의 이름은 AbstractInterface로 짓는다.** 
 - ex) AbstractCollection, AbstractSet, AbstractList .. 

### 골격 구현 작성
 - 인터페이스를 잘 살펴 다른 메서드들의 구현에 사용되는 기반 메서드들을 선정한다.
 - 골격 구현에서는 추상 메서드가 된다.
 - 직접 구현할 수 있는 메서드들은 모두 디폴트 메서드로 제공한다.
 - 인터페이스를 구현하는 골격 구현 클래스를 하나 만들어 남은 메서드들을 작성해 넣는다.
 - 해당 구현체에 필요하다면 public이 아닌 필드와 메서드를 추가할 수도 있다. 

 ```java
// 골격 구현 클래스
public class AbstractMapEntry<K, V> implements Map.Entry<K, V> {

    @Override
    public K getKey() {
        return null;
    }

    @Override
    public V getValue() {
        return null;
    }

    // 변경 가능한 엔트리는 이 메서드를 반드시 재정의해야한다.
    @Override
    public V setValue(V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Map.Entry)) {
            return false;
        }
        Map.Entry<?, ?> e= (Map.Entry) o;
        return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }

}
 ```

 ### 정리
 다중 구현용 타입으로는 인터페이스가 가장 적합하다. **복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 꼭 고려해보자.** 골격 구현은 "가능한 한" 인터페이스의 디폴트 메서드를 제공하여 모든 곳에서 활용하는 것이 좋다.  
 참고로 인터페이스에 걸려있는 구현상의 제약 때문에 골격 구현을 추상 클래스로 제공하는 경우가 더 흔하다. 

 # Item 12. 인터페이스는 구현하는 쪽을 생각해 설계하라. 
 디폴트 메서드는 구현 클래스에 대해 아무것도 모른 채 합의 없이 무작정 삽입될 뿐이다.  
 **생각할 수 있는 모든 상황에서 불변식을 해치지 않는 디폴트 메서드를 작성하기란 어렵다.**
 ```java
default boolean removeIf(Predicate<? super E> filter) {
        Objects.requireNonNull(filter);
        boolean result = false;
        for (Iterator<E> it = iterator(); it.hasNext(); ) {
            it.remove();
            result = true;
        }
        return result;
}
```
removeIf는 범용적으로 잘 작성되었지만 그렇다고 현존하는 모든 Collection 구현체와 어우러지는 것은 아니다. 

>SynchronizedCollection 인스턴스를 여러 스레드가 공유하는 환경에서 한 스레드가 removeIf를 호출하면 예외가 발생하거나 다른 예기치 못한 결과로 이어질 수 있다. Collections.synchronizedCollection이 반환하는 클래스들은 removeIf를 재정의하고 이를 호출하는 다른 메서드들은 디폴트 구현을 호출하기 전에 동기화를 하도록했다. 하지만 제 3의 기존 컬렉션들은 여전히 수정되지 않고 매번 발빠르게 수정될 수 없다. 

### 정리
 - 기존 인터페이스에 디폴트 메서드로 새 메서드를 추가하는 일은 꼭 필요한 경우가 아니면 피해야한다.  
 - 디폴트 메서드는 인터페이스로부터 메서드를 제거하거나 기존 메서드의 시그니처를 수정하는 용도가 아님을 명심해야 한다. 이런 방식으로 코드가 수정되면 기존 클라이언트에 큰 영향이 간다.
 - 디폴트 메서드라는 유용한 도구가 생겼더라도 인터페이스를 설계 할 때는 세심한 주의를 기울여야 한다. 

 # Item 22. 인터페이스는 타입을 정의하는 용도로만 사용하라.
 인터페이스는 자신을 구현한 클래스의 인스턴스를 참조할 수 있는 타입 역할을 한다. 인터페이스는 오직 이 용도로만 사용해야한다. **상수인터페이스는 안티패턴으로 인터페이스르 잘못 사용한 예이다.**  

 상수 인터페이스를 사용하는 것은 내부 구현을 클래스의 API로 노출하는 행위이다. 클라이언트 코드가 내부 구현에 대항하는 상수들에 종속되게 된다. 
**Java.io.ObjectStreamConstants** 등 자바 플랫폼 라이브러리에도 상수 인터페이스가 있으나 잘못된 예이다. 

특정 클래스나 인터페이스와 강하게 연관된 상수라면 그 클래스나 인터페이스 자체에 추가해야 한다. 대표적으로 Integer, Double에 선언된 `MIN_VALUE`, `MAX_VALUE`가 여기에 해당된다.  

### 정리
인터페이스는 타입을 정의하는 용도로만 사용해야한다. 상수 공개용 수단으로 사용하지 말자.

# Item 23. 태그 달린 클래스 보다는 클래스 계층구조를 활용하라. 
여러 정보를 나타내고 특정 필드로 어떤 정보를 나타내는지 설명하는 태그(필드 값 등)로 나타내는 클래스가 있다. 태그가 달린 클래스는 단점이 너무 많다.
```java
public class Figure {
    enum Shape {
        RECTANGLE, CIRCLE
    }
    
    // 태그 팔드 - 현재 모양을 나타낸다.
    final Shape shape;
    
    double length;
    double width;
    
    // 다음 필드는 모양이 원 Circle 일 때만 쓰인다. 
    double radius;
    
    // 원용 생성자
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }
    
    // 사각형 생성자
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }
    
    double area() {
        switch (shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
```

 - 한 클래스에 혼합돼있어서 가독성이 나쁘다. 다른 의미를 위한 코드도 언제나 함께 사용되어 메모리도 많이 사용한다. 
 - 필드를 final로 선언하려면 해당 의미에 쓰이지 않는 필드들까지 초기화해야한다.
 - 엉뚱한 필드를 초기화해도 런타임에야 문제가 드러난다. 
 - 새로운 의미를 나타내야하는 필요가 생기면 코드를 추가해야하는데 하나라도 빠뜨리면 런타임에 문제가 생긴다 (OCP도 아님)

**태그 달린 클래스는 장황하고, 오류를 내기 쉽고, 비효율적이다.** 이를 클래스 계층 구조로 바꾸면 다음과 같다. 
```java
public abstract class AbstractFigure {
    abstract double area();
}

public class Circle extends AbstractFigure {
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}

public class Rectangle extends AbstractFigure {
    final double length;
    final double width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}

public class Square extends Rectangle {
    Square(double side) {
        super(side, side);
    }
}
```


클래스 계층구조는 태그 달린 클래스의 단점을 모두 날려버린다. 필드는 모두 final이다. 실수로 빼먹은 case문을 걱정할 필요도 없다. 독립적으로 계층구조를 확장하고 함께 사용할 수 있다. 타입이 의미별로 존재하니 변수의 의미를 명시하거나 제한할 수 있고, 또 특정 의미만 매개변수로 받을 수 있다. 

### 정리
태그 달린 클래스를 써야하는 상황은 거의 없다. 새로운 클래스를 작성하는데 태그 필드가 등장한다면 태그를 없애고 계층 구조로 대체하는 방법을 생각해보자.
기존 클래스가 태그 필드를 사용하고 있다면 계층 구조로 리팩터링하는 걸 고민하자.

# Item 24. 멤버 클래스는 되도록 static으로 만들라.
중첩 클래스는 자신을 감싼 바깥 클래스에서만 쓰여야하며, 그 외의 쓰임새가 있다면 톱레벨 클래스로 만들어야 한다. 

### 중첩 클래스의 종류는 아래와 같다. 
 - 정적 멤버 클래스
 - 멤버 클래스
 - 익명 클래스
 - 지역 클래스 

## 정적 멤버 클래스

정적 멤버 클래스는 다른 클래스 안에 선언되고, 바깥 클래스의 private 멤버에도 접근할 수 있다는 점만 제외하고는 일반 클래스와 동일하다.
**Operation** 열거 타입은 Calculator 클래스의 public 정적 멤버 클래스가 되어야한다. 때문에 Calculator의 클라이언트에서 `Calculator.Operation.PLUS`나 `Calculator.Operation.MINUS` 같은 형태로 연산을 참조할 수 있다. 
개념상 중첩 클래스의 인스턴스가 바깥 인스터스와 독립적으로 존재할 수 있다면 정적 멤버 클래스로 만들어야 한다. 비정적 멤버 클래스는 바깥 인스턴스 없이는 생성할 수 없기 때문이다. 

## 비정적 멤버 클래스
비정적 멤버 클래스의 인스턴스와 바깥 인스턴스 사이의 관계는 멤버 클래스가 인스턴스화될 때 확립되며, 더 이상 변경할 수 없다. 이 관계는 바깥 클래스의 인스턴스 메서드에서 비정적 멤버 클래스의 생성자를 호출할 때 자동으로 만들어지는게 보통이지만, 드물게는 직접 바깥 인스턴스의 클래스.new MemberClass(args)를 호출해 수동으로 만들기도 한다. 이 방법은 메모리 공간을 차지하며, 생성 시간도 더 걸린다. 
> 비정적 멤버 클래스는 어댑터를 정의할 때 자주 쓰인다. 즉, 어떤 클래스의 인스턴스를 감싸 마치 다른 클래스의 인스턴스처럼 보이게하는 뷰로 사용하는 것이다. 

```java
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
```
**멤버 클래스에 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자. static을 생략하면 바깥 인스턴스로의 숨은 외부 참조를 갖게 된다. 
참조를 저장하려면 시간과 공간이 소비된다. 더 심각한 것은 가비지 컬렉션이 바깥 클래스의 인스턴스를 수거하지 못하는 메모리 누수가 생길 수 있다는 점이다.**

## 익명클래스
익명 클래스는 이름이 없고 멤버 클래스도 아니다. 멤버와 달리, 쓰이는 시점에 선언과 동시에 인스턴스가 만들어진다. 코드 어디서든 만들 수 있다. 그리고 오직 **비정적인 문맥에서 사용될 때만 바깥 클래스의 인스턴스를 참조할 수 있다.**  
익명 클래스는 선언한 지점에서만 인스턴스를 만들 수 있고, instanceof 검사나 클래스의 이름이 필요한 작업은 수행할 수 없다. 여러 인터페이스를 구현할 수 없고, 인터페이스를 구현하는 동시에 다른 클래스를 상속할 수도 없다. 익명 클래스를 사용하는 클라이언트는 그 익명 클래스가 상위 타입에서 상속한 멤버 외에는 호출할 수 없다. 익명 클래스는 짧지않으면 가독성이 떨어진다. 익명 클래스의 또다른 사용처는 정적 팩토리 메서드를 만들 때이다. 

```java
// 익명 클래스를 이용해 정적 팩토리 메서드 구현.
public interface IntListHelper {
    
    static List<Integer> intArrayAsList(int[] a) {
        return new AbstractList<Integer>() {
            @Override
            public Integer get(int index) {
                return a[index];
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }
}

```

## 지역 클래스
 - 가장 드물게 사용된다. 
 - 선언 위치도 유효 범위도 지역변수와 동일하다.
 - 이름이 있고 반복해서 사용할 수 있다. (지역 스코프 내에서)
 - 익명 클래스처럼 비정적 문맥에서 사용될 때만 바깥 인스턴스를 참조할 수 있다.
 - 정적 멤버는 가질 수 없다.
 - 가독성을 위해 짧게 작성한다. 

### 정리
중첩클래스는 쓰임에 따라 여러 형태로 구현할 수 있다. 
 - 메서드 밖에서도 사용하거나 너무 길다면 멤버 클래스로 만든다.
 - 멤버 클래스의 인스턴스 각각이 바깥 인스턴스를 참조한다면 비정적으로, 그렇지 않으면 정적으로 만들자.
 - 중첩 클래스가 한 메서드 안에서만 쓰이면서 그 인스턴스를 생성하는 지점이 단 한 곳이고 해당 타입으로 쓰기에 적합한 클래스나 인터페이스가 이미 있다면 익명 클래스로 만들고 아닌 경우 지역클래스로 만들자.