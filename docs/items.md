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
