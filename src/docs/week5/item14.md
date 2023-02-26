# item 14. Comparable을 구현할지 고민하라

### Comparable? 
- comaprable은 Object의 순서를 정의해줄 수 있다.
- Object.equals에 더해서 순서까지 비교할 수 있으며 Generic을 지원한다.
- 자기 자신이 compareTo에 전달된 객체보다 작으면 음수, 같으면 0, 크다면 양수를 리턴한다.
- 반사성, 대칭성, 추이성이 성립한다.
- 반드시 따라야 하는 것은 아니지만 x.compareTo(y) == 0이라면, x.equals(y)가 true여야 한다.
  - 지켜주는 것이 좋다.
  - BigDecimal은 equals는 소수점까지 비교하지만, compareTo는 아니다.


### Comparable 구현 방법
1. Comparable 인터페이스를 구현해야 한다.
   - 만약 Comparable 인터페이스를 구현한 부모 클래스를 상속한 클래스의 경우에는? 
     - 별도로 Comparator를 제공하는 방식으로 구현한다.
     - Composition을 사용해라
     - View 메서드를 제공해라
     ```java
     public Point getPoint() {
        return this.point;
     }
     ```


### Comparable 구현 방법2
```java
    private static final Comparator<PhoneNumber> COMPARATOR =
            Comparator.comparingInt((PhoneNumber pn) -> pn.areaCode)
                    .thenComparingInt(pn -> pn.getPrefix())
                    .thenComparingInt(pn -> pn.lineNum);

    @Override
    public int compareTo(PhoneNumber pn) {
        return COMPARATOR.compare(this, pn);
    }
```
- JAVA8부터는 Comparator가 제공하는 static method를 활용해서 Comparator를 생성할 수 있다.