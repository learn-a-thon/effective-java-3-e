# item 24. 멤버 클래스는 되도록 static으로 만들라

1. 정적 멤버 클래스
- 바깥 클래스와 함께 쓰일 때만 유용한 public 도우미 클래스. 예) Calculator.Operation.PLUS (Enum)
- Outter 클래스와 독립적이다. 
- Outter 클래스를 참조하지 않는 경우에 사용한다. 
  - 참조하는 곳이 있다면, 비정적 멤버 클래스로 만들 수 밖에 없다.
  - 비정적 멤버 클래스는 암뭄적으로 바깥 인스턴스에 대한 참조가 생긴다.
    - 이 말은 즉슨, Outter class 인스턴스 없이는 자기 자신을 생성할 수 없다.
    - `InnerClass innerClass = new OutterClass().new InnerClass();`

2. 비정적 멤버 클래스
- 바깥 클래스의 인스턴스와 암묵적으로 연결된다.
- 어댑터를 정의할 때 자주 쓰인다.
- 멤버 클래스에서 바깥 인스턴스를 참조할 필요가 없다면 무조건 정적 멤버 클래스로 만들자.

3. 익명 클래스
- 바깥 클래스의 멤버가 아니며, 쓰이는 시점과 동시에 인스턴스가 만들어진다.
- 비정적인 문맥에서 사용될 때만 바깥 클래스의 인스턴스를 참조할 수 있다.
- 자바에서 람다를 지원하기 전에 즉석에서 작은 함수 객체나 처리 객체를 만들 때 사용했다.
- 정적 팩터리 메서드를 만들 때 사용할 수도 있다.

4. 지역 클래스
- 가장 드물게 사용된다.
- 지역 변수를 선언하는 곳이면 어디든 지역 클래스를 정의해 사용할 수 있다.
- 가독성을 위해 짧게 작성해야 한다.


#### 어댑터 패턴
기존 코드를 클라이언트가 사용하는 인터페이스의 구현체로 바꿔주는 패턴
 - 클라이언트가 사용하는 인터페이스를 따르지 않는 기존 코드를 재사용할 수 있게 해준다.

```java
try(InputStream is = new FileInputStream("number.txt");
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader reader = new BufferedReader(isr)) {
    while(reader.ready()) {
        System.out.println(reader.readLine());
    }
} catch (IOException e) {
    throw new RuntimeException(e);
}
```
- InputStream을 InputStreamReader로 사용할 수 있게끔