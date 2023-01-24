package item3.field;

import java.io.Serializable;

public class Elvis implements IElvis, Serializable {
    private static final Elvis INSTANCE = new Elvis();

    private static boolean created;

    private Elvis() {
        // 리플렉션 방어
        if (created) {
            throw new UnsupportedOperationException("can't be created by constructor");
        }
        created = true;
    }

    public static Elvis getInstance() {
        return INSTANCE;
        // 메소드를 통해 인스턴스를 제공하는 경우
        // 클라이언트 코드에 영향을 주지 않고
        // 새로운 인스턴스를 반환하게 할 수 있다.
        // return new Elvis();
    }

    @Override
    public void sing() {
        System.out.println("singing~");
    }

    // 해당 메소드를 정의하면 역직렬화로 싱글톤이 깨지는 것을 방어할 수 있다.
    private Object readResolve() {
        return INSTANCE;
    }
}
