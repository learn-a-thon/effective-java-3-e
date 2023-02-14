# item 10. equals는 일반 규약을 지켜 재정의하라

equals 메서드는 재정의하기 쉬워 보이지만 곳곳에 함정이 도사리고 있다. 문제를 회피하는 가장 쉬운 길은 아예 재정의 하지 않는 것인데, 그냥 두면 그 클래스의 인스턴스는 오직 자기 자신과만 같게 된다. 아래와 같은 상황 중 하나에 해당한다면 재정의하지 않는 것이 최선이다. 

### 재정의하지 않아야 하는 상황

1. 각 인스턴스가 본질적으로 고유하다. 
2. 인스턴스의 논리적 동치성을 검사할 일이 없다. 
- java.util.regex.Pattern은 equals를 재정의해서 두 Pattern의 인스턴스가 같은 정규표현식을 나타내는지를 검사하는 논리적 동치성을 검사하는 방법도 있다.
3. 상위 클래스에서 재정의한 equals가 하위 클래스에도 딱 들어맞는다.
4. 클래스가 private이거나 package-private이고 equals 메서드를 호출할 일이 없다.

### 재정의해야 할 때 
1. 객체 식별성이 아니라 논리적 동치성을 확인해야 하는데, 상위 클래스의 equals가 논리적 동치성을 비교하도록 재정의되지 않았을 때
- 주로 Integer와 String처럼 값을 표현하는 클래스를 말한다. 두 값 객체를 equals로 비교하는 프로그래머는 객체가 같은지가 아니라 값이 같은지를 알고 싶어 한다. 
- 물론 값 클래스더라도, 값이 같은 인스턴스가 둘 이상 만들어지지 않음을 보장하는 인스턴스 통제 클래스라면 equals를 재정의 하지 않아도 된다. (Enum) 

### equals 메서드를 재정의할 때 반드시 따라야하는 규약
1. 반사성 : null아닌 모든 참조 값 x에 대해, x.equals(x)는 true다.
2. 대칭성 : null이 아닌 모든 참조 값 x,y에 대해 x.equals(y)가 true면 y.equals(x)도 true다.
3. 추이성 : null이 아닌 모든 참조 값 x,y,z에 대해 x.equals(y)가 true이고, y.equals(z)도 true면 x.equals(z)도 true다. 
4. 일관성 : null이 아닌 모든 참조 값 x,y에 대해 x.equals(y)를 반복해서 호출하면 항상 true를 반환하거나 항상 false를 반환한다.
5. null-아님 : null이 아닌 모든 참조 값 x에 대해, x.equals(null)은 false다.

수많은 클래스들은 전달받은 객체가 이 규약을 지킨다고 가정하고 동작하기 때문에, 꼭 지켜야 한다. 

```java
public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        Point p = (Point) o;
        return p.x == x && p.y == y;
    }
}

```
만약, 여기에 색상을 추가 한다고 생각해보자. 

```java

public class ColorPoint extends Point {
    private final Color color; 

    public ColorPoint(int x, int y, Color color)...
} 
```
만약 equals를 구현하지 않고 그대로 둔다면, color는 무시한 채 비교를 수행한다. 규약을 무시하진 않지만, 옳지 않은 코드이다.


```java
@Override 
public boolean equals(Object o) {
    if (!(o instanceof ColorPoint)) return false;
    return super.equals(o) && ((ColorPoint) o).color = color;
}
```
위처럼, 위치와 색상이 같을 때만 true를 반환하는 equals를 생각해보자, 이 메서드는 일반 Point를 ColorPoint에 비교한 결과와 그 둘을 바꿔 비교한 결과는 다를 수 있다. 
> 왜냐면, Point에서 ColorPoint를 비교하면 좌표가 같기 때문에 true를 반환하지만 ColorPoint에서 Point를 비교하면 무조건 false를 반환함 

그렇다면 만약 ColorPoint에서 Point를 equals 했을 때, 색상을 무시하도록 하면? 
```java
@Override
public boolean equals(Object o) {
    if (!(o instanceof Point)) return false;

    // o가 일반 point면 o의 Equals를 호출한다. --> 무한 재귀 호출 발생 
    if (!(o instanceof ColorPoint)) return o.equals(this);

    return super.equals(o) && ((ColorPoint) o).color == color;
}
```
> 대칭성은 지켜주지만, 추이성을 깨버린다.

```java
ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
Point p2 = new Point(1, 2);
ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE); 
```
> p1이랑 p2는 같고, p2와 p3도 같지만 p1과 p3는 다르다!

또한 이 방식은 무한 재귀에 빠질 우려도 있다. 만약 Point의 또다른 하위 클래스인 SmellPoint 만들고, equals를 동일한 방식으로 구현하고, myColorPoint.equals(mySmellPoint)를 호츨하면 StackOverFlowError를 일으킨다. 

사실 이 현상은 모든 객체 지향 언어의 동치관계에서 나타나는 근본적인 문제다, 구체 클래스를 확장해 값을 추가하며 equals 규약을 만족시킬 방법은 존재하지 않는다. 추상화 이점을 포기하지 않는 한은 말이다.  이 말은 얼핏 equals 안의 instanceof 검사를 getClass로 바꾸면 규약도 지키고 값도 추가하면서 구체 클래스를 상속할 수 있다는 뜻으로 들린다.

```java
@Override
public boolean equals(Object o) {
    if (o == null || o.getClass != getClass()) return false;
    ...
}
```

만약 주어진 점이 단위 원 안에 있는지를 판별하는 메서드가 필요하다고 해보자. 
```java
private static final Set<Point> unitCircle = Set.of(new Point...)

public static boolean onUnitCircle(Point p) {
    return unitCircle.contains(p);
}
```
```java
public class CounterPoint extends Point {
    private static final AtomicInteger counter = ....;

    public CounterPoint(int x, int y) {
        super(x,y);
        counter.incrementAndGet();
    }

    public static int numberCreated() {
        return counter.get();
    }
}
```

리스코브 치환 원칙에 따르면, 어떤 타입에 있어서 중요한 속성이라면 그 하위 타입에서도 중요하다, 따라서 그 타입의 모든 메서드가 하위 타입에서도 똑같이 잘 작동해야 한다. 즉 Point의 하위 클래스는 정의상 여전히 Point이므로 어디서는 Point로써 활용될 수 있어야 한다. 

그런데 위의 CounterPoint 인스턴스를 onUnitCircle 메서드에 넘기면 어떻게 될까? Point 클래스의 equals를 getClass를 사용해 작성했다면 onUnitCircle은 false를 반환한다. x,y와 무관하게 말이다. 왜? Set을 포함하여 대 부분의 컬렉션들은 데이터가 있는지 확인하는 작업에 equals 메서드를 사용하는데, CounterPoint 인스턴스는 절대로 Point와 같을 수 없기 때문이다. 반면 Point equals를 instanceof 기반으로 올바로 구현했다면 CounterPoint를 건네줘도 onUnitCircle이 올바르게 동작할 것이다. 

괜찮은 우회방법이 있다. 상속 대신 컴포지션을 사용하라는 아이템 18의 조언을 따르면 된다. 
- Point를 상속하는 대신 ColorPoint의 private 필드로 두고, 
- ColorPoint와 같은 위치의 일반 Point를 반환하는 뷰 메서드를 public으로 추가하는 것

```java
public class ColorPoint {
    private final Point point;
    private final Color color;

    public ColorPoint(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }

    /**
     * 이 ColorPoint의 Point 뷰를 반환한다.
     */
    public Point asPoint() {
        return point;
    }

    @Override public boolean equals(Object o) {
        if (!(o instanceof ColorPoint))
            return false;
        ColorPoint cp = (ColorPoint) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    @Override public int hashCode() {
        return 31 * point.hashCode() + color.hashCode();
    }
}
```

일관성은 두 객체가 같다면, 영원히 같다는 것이다. 즉, equals의 판단에 신뢰할 수 없는 자원이 끼어서는 안된다.

null-아님은 모든 객체들은 null과 같지 않아야 한다는 것인데, o.equals(null)이 true를 반환하는 상황은 상상하기 어렵지만, 실수로 NullPointerException을 던지는 경우는 많다. 

```java
@Override
public boolean equals(Object o) {
    if (o == null) return false; => 얘는 필요없다!!
}
```

동치성을 검사하려면 equals는 건네받은 객체를 적절히 형변환 후, 필수 필드의 값들을 알아내는데 그러려면 형변환 앞에 instanceof를 통해 타입 체크를 한다. 얘는 null이면 false를 반환한다. 
