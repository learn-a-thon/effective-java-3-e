package item1;

import java.util.EnumSet;

public class Foo {

    String name;

    String address;

    /* 장점 2. 호출될 때마다 인스턴스를 생성하지 않아도 된다.
     Foo.INSTANCE 와 getInstance()*/
    private static final Foo INSTANCE = new Foo();

    public static Foo getInstance() {
        return INSTANCE;
    }

    Foo() {
    }

    public Foo(String name) {
        this.name = name;
    }

    // 장점 1. 이름을 가질 수 있다.
    public static Foo withName(String name) {
        return new Foo(name);
    }

    /* 장점 1.
     아래와 같이 동일 개수의 생성자가 존재하는 경우(생성 조차 안됨)
     public Foo(String address) {
        this.address = address;
     }
     각 생성자가 어떤 역할을 하는지 정확히 기억하기 어렵고 엉뚱한 것을 호출하여 실수할 수 있습니다.
     코드를 읽는 사람도 클래스 설명 문서를 찾아보지 않고 의미를 알지 못합니다.

     그러나, 이름을 가질 수 있는 정적 팩터리 메소드에는 위와 같은 제약이 없고
     아래와 같이 각각 차이를 잘 드러내는 이름을 가진 메소드를 만들 수 있습니다.*/
    public static Foo withAddress(String address) {
        Foo foo = new Foo();
        foo.address = address;
        return foo;
    }

    /* 장점 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
     장점 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
     아래와 같이 Foo의 하위 타입 객체인 SubFoo를 반환하는 것이 가능합니다.
     또한, getInstance() 를 오버로딩(입력 파라미터를 달리)하여
     다른 객체를 반환할 수 있습니다.*/
    public static Foo getInstance(boolean flag) {
        return flag ? new Foo() : new SubFoo();
    }

    /* 장점 5. 정적 팩터리 메소드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.
     JDBC, 서비스 제공자에게 공급을 받는다.
     */
    public static Foo getInstance2(boolean flag) {
        Foo foo = new Foo();
        // 어떤 특정 약속 되어 있는 텍스트 파일에서 Foo의 구현체의 FQCN(foo qualified class name) 을 읽어온다.
        // FQCN 에 해당하는 인스턴스를 생성한다.
        // foo 변수를 해당 인스턴스를 가리키도록 수정한다.
        return foo;
    }

    public static void main(String[] args) {
        Foo foo1 = new Foo("name");
        Foo foo2 = Foo.withName("name");// 장점 1. 명확한 이름을 가지고 있다.

        //장점 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
        EnumSet<Color> colors = EnumSet.allOf(Color.class);
        EnumSet<Color> redAndBlue = EnumSet.of(Color.RED, Color.BLUE);
    }

    enum Color {
        RED, BLUE, GREEN
    }

    /* private static method가 필요한 이유
     private 메소드의 존재 이유와 동일합니다.
     study 안에 gitCommit 가 노출되기를 원하지 않는 경우에
     private 로 감출 수 있다.*/
    public static void study() {
        gitCommit();
    }

    private static void gitCommit() {

    }
}
