package item26.raw;

public class UseRawType<E> {
    private E e;

    public static void main(String[] args) {
        // UseRawType<String>.class 이러한 클래스는 존재하지 않는다.
        // 컴파일 시점에 소거되기 때문에
        // 반드시 로 타입의 클래스만 존재한다.
        System.out.println(UseRawType.class);

        UseRawType<String> stringType = new UseRawType<>();

        System.out.println(stringType instanceof UseRawType);
        // 컴파일은 되지만 소거되기 때문에 의미없다.
        //System.out.println(stringType instanceof UseRawType<String>);
    }
}
