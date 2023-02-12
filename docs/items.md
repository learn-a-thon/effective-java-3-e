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
  