# item2. 생성자에 매개변수가 많다면 빌더를 고려하라. 

### 대안 1. 점층적 생성자 패턴 또는 생성자 체이닝 
- 생성자 내부의 중복 코드를 제거할 수 있다.
- 하지만 각 파라미터에 어떤 타입이 들어가는지 파악하기가 쉽지 않다.

```java
public NutritionFacts(int servingSize, int servings) {
    this(servingSize, servings, 0);
}

public NutritionFacts(int servingSize, int servings, int calories) {
    this(servingSize, servings, calories, 0);
}

public NutritionFacts(int servingSize, int servings, int calories, int fat) {
   this(servingSize, servings, calories, fat, 0);
}

public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium) {
    this(servingSize, servings, calories, fat, sodium, 0);
}

public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
    this.servingSize = servingSize;
    this.servings = servings;
    this.calories = calories;
    this.fat = fat;
    this.sodium = sodium;
    this.carbohydrate = carbohydrate;
}
```

### 대안 2. 자바빈즈 패턴 
- getter/setter 패턴을 정의 해놓은 표준 스펙 
- 필수값이 세팅이 되었는지 체크를 할 수가 없다. -> 불완전한 상태로 사용될 여지가 있다.
- immutable한 객체를 만들 수 없다. 
  - 불변 객체를 만들기 위해 객체 프리징을 사용해보자?

### 빌더
```java
NutritionFacts cocaCola = new Builder(240, 8)
        .calories(100)
        .sodium(35)
        .carbohydrate(27)
        .build();
```

- 모든 장점을 취한 패턴 
- 하지만 코드가 매우 더러워지기 때문에, Immutable한 객체가 아니면 다른 방법을 선택하는게 좋을수도 있다.
  - Lombok을 사용하는 방식 
    - 필수값을 지정해줄 수 없다. (기존의 빌더의 장점을 버리는 것)
    - 전체 생성자가 공존한다. (Lombok의 AllArgsConstructor의 access level을 private으로 주면 됨)

  