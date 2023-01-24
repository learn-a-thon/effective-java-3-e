package item3.field;

public class MetaElvis<T> implements IElvis {
    private static final MetaElvis<Object> INSTANCE = new MetaElvis<>();

    // 메소드에 정의된 static <T>의 경우 클래스에 선언된 MetaElvis<T> 제네릭과의 스코프가 다르다.
    // 따라서 해당 메소드에 정의된 MetaElvis<T>는 메소드의 제네릭을 사용하는 것이다.
    @SuppressWarnings("unchecked")
    public static <E> MetaElvis<E> getInstance() {
        return (MetaElvis<E>) INSTANCE;
    }

    // 해당 메소드에 사용된 제네릭 타입은
    // 클래스에 정의된 class MetaElvis<T> 제네릭 타입을 사용한다.
    public void say(T t) {
        System.out.println("say " + t);
    }

    @Override
    public void sing() {
        System.out.println("singing~");
    }

    public static void main(String[] args) {
        MetaElvis<String> elvis1 = MetaElvis.getInstance();
        MetaElvis<Integer> elvis2 = MetaElvis.getInstance();

        System.out.println(elvis1.equals(elvis2));// true
        // 동일 인스턴스는 맞지만 제네릭 타입이 달라서 불가능
        // System.out.println(elvis1 == elvis2);
        elvis1.say("hello");
        elvis2.say(1235);
    }
}
