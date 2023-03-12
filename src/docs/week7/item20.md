# item 20. 추상 클래스보다 인터페이스를 우선하라.

- 추상 클래스는 상속이 한 클래스당 1개 밖에 안됨, 인터페이스는 여러개 구현 가능 
  - 추상 클래스를 사용하면 제약이 발생할 수 밖에 없음
- default 메서드를 사용할 수 없는 경우에는 추상 클래스를 사용해야 한다.
  - 인스턴스 필드를 사용해야 하는 경우

1. 자바 8부터 인터페이스도 디폴트 메서드를 제공할 수 있다.
- 만약 public interface라면, 새로운 메서드를 추가 할때마다 구현한 모든 클래스에 오류가 발생하게 된다.
- 이럴때 default 메서드를 사용하면 손쉽게 기능을 확장시킬 수 있다.
- static 메서드도 추가할 수 있다.
2. 기존 클래스도 손쉽게 새로운 인터페이스를 구현해 넣을 수 있다.
3. 인터페이스는 믹스인 정의에 안성맞춤이다. (선택적인 기능 추가)
4. 계층구조가 없는 타임 프레임워크를 만들 수 있다.
- 계층구조가 아닌 조합을 통해 새로운 형태의 인터페이스를 만들 수 있다. 
  - 예를 들어 가수와 작곡가가 있고, 이를 이용해 가수와 작곡을 동시에 하는 가수겸 작곡가를 표현할 때 추상 클래스를 활용한다면 계층 구조로 구현을 해야한다.
  - 하지만 인터페이스를 활용한다면 계층 구조가 아닌 단순한 조합을 통해 표현할 수 있다. 
  ```java
  public interface SingerSongwriter extends Singer, Songwriter{
    AudioClip strum();
    void actSensitive();
  }
  ```
5. 래퍼 클래스와 함께 사용하면 인터페이스는 기능을 향상 시키는 안전하고 강력한 수단이 된다.
- 추상 클래스를 활용하면 상위 클래스에 의해서 하위 클래스의 행동이 깨지는 상황 (캡슐화가 깨짐)이 발생할 수 있다.
- 추상 클래스는 상위 클래스가 새로운 메서드가 추가된 것을 알 수 없고, 인터페이스는 오류에 의해 알 수 있다.
6. 구현이 명백한 것은 인터페이스의 디폴트 메서드를 사용해 프로그래머의 일감을 덜어 줄 수 있다.


#### 인터페이스와 추상 골격 클래스
1. 인터페이스와 추상 클래스의 장점을 모두 취할 수 있다.
- 인터페이스 - 디폴트 메서드 구현 
- 추상 골격 클래스 - 나머지 메서드 구현 (인터페이스에서 채워넣을 수 없는 것들)
  - 추상 클래스로 인터페이스를 구현하는데, 일부 로직은 완전한 구현체를 제공하고, 완전한 구현체 사이 사이에 끼워넣을 수 있는 템플릿 부분을 남겨놓는다. 
  ```java
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        return new AbstractList<>() {
            @Override public Integer get(int i) {
                return a[i];  // 오토박싱(아이템 6)
            }

            @Override public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // 오토언박싱
                return oldVal;  // 오토박싱
            }

            @Override public int size() {
                return a.length;
            }
        };
    }
  ```
- 만약 List를 구현하려면 수많은 메서드를 구현해야함.
  - 추상 구현클래스인 AbstractList를 사용하면 일부만 재정의해서 사용할 수 있다.
- 템플릿 메서드 패턴
2. 다중 상속을 시뮬레이트 할 수 있다.
```java
public class MyCat extends AbstractCat implements Flyable {

    private MyFlyable myFlyable = new MyFlyable();

    @Override
    protected String sound() {
        return "인싸 고양이 두 마리가 나가신다!";
    }

    @Override
    protected String name() {
        return "유미";
    }

    public static void main(String[] args) {
        MyCat myCat = new MyCat();
        System.out.println(myCat.sound());
        System.out.println(myCat.name());
        myCat.fly();
    }

    @Override
    public void fly() {
        this.myFlyable.fly();
    }

    private class MyFlyable extends AbstractFlyable {
        @Override
        public void fly() {
            System.out.println("날아라.");
        }
    } // 다중 상속 시뮬레이트
}
```
3. 골격 구현은 상속용 클래스이기 때문에 아이템 19를 따라야 한다.

#### 템플릿 메서드 패턴
알고리즘 구조를 서브 클래스가 확장할 수 있도록 템플릿으로 제공하는 방법
- 추상 클래스는 템플릿을 제공하고 하위 클래스는 구체적인 알고리즘을 제공한다.

### 디폴트 메서드와 Object 메서드
인터페이스의 디폴트 메서드로 Object 메서드를 재정의 할 수 없는 이유
- default 메서드의 용도가 재정의를 위한 용도가 아니기 때문이다. (not poor man's traits)
  - 인터페이스에 간단한 추가 기능을 제공하기 위한 용도 (인터페이스의 진화)
- 만약 재정의가 가능하다면, 클래스의 재정의를 따라야 하는지 인터페이스의 재정의를 따라야 하는지 복잡한 상황이 발생한다. 
- 만약 재정의가 가능하다면, 구현 클래스의 동작이 디폴트 메서드에 의해서 깨질 수 있다.