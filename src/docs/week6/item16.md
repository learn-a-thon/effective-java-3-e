# item 16. public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라

```java
class Point {
    private double x;
    private double y;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void getX() { return x; }
    public void getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}
```

- 패키지 바깥에서 접근할 수 있는 클래스라면 접근자를 제공함으로써 클래스 내부 표현 방식을 언제든 바꿀 수 있는 유연성을 얻을 수 있다. public 클래스가 필드를 공개하면 이를 사용하는 클라이언트가 생겨날 것이므로 내부 표현 방식을 마음대로 바꿀 수 없게 된다. 
- 하지만 **package-private 클래스 혹은 private 중첩 클래스라면 데이터 필드를 노출한다 해도 하등 문제가 없다.** 그 클래스가 표현하려는 추상 개념만 올바르게 표현하면 된다. 클라이언트 코드가 이 클래스 내부 표현에 묶이기는 하나, 클라이언트도 어차피 이 클래스를 포함하는 패키지 안에서 동작하는 코드일 뿐이다. 따라서 패키지 바깥 코드는 전혀 손대지 않고도 데이터 표현 방식을 바꿀 수 있다. 
- private 중첩 클래스의 경우라면 수정 범위가 더 좁아져서 이 클래스를 포함하는 외부 클래스까지로 제한된다.

#### public 클래스의 필드가 불변이라면?
```java
public final class Time {
    private static final int HOURS_PER_DAY    = 24;
    private static final int MINUTES_PER_HOUR = 60;

    public final int hour;
    public final int minute; 

    public Time(int hour, int minute) {
        if (hour < 0 || hour >= HOURS_PER_DAY) {
            throw new ...
        }

        if (minute < 0 || minute >= MINUTES_PER_HOUR) {
            throw new ...
        }
        this.hour = hour;
        this.minute = minute;
    }
}
```
- 직접 노출할 때의 단점이 조금은 줄어들지만, 좋은 생각은 X
- API를 변경하지 않고는 표현 방식을 바꿀 수 없고, 필드를 읽을 때 부수적인 작업을 수행할 수 없다는 단점은 여전함
- 단, 불변식은 보장할 수 있게 된다.

#### 정리
- public 클래스는 절대 가변 필드를 직접 노출해서는 안된다. 
- 불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 순 없다. 
- 하지만 package-private 클래스나 private 중첩 클래스에서는 불변이든 가변이든 종종 필드를 노출하는 편이 나을 때도 있다.