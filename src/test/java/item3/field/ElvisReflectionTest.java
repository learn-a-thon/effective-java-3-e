package item3.field;

import java.lang.reflect.Constructor;

public class ElvisReflectionTest {

    public static void main(String[] args) {
        try {
            Constructor<Elvis> declaredConstructor = Elvis.class.getDeclaredConstructor();
            // accessible 설정을 하면 리플렉션으로 인스턴스를 생성할 수 있다.
            declaredConstructor.setAccessible(true);
            Elvis elvis = declaredConstructor.newInstance();
            Elvis elvis2 = declaredConstructor.newInstance();
            elvis.sing();
            elvis2.sing();
            System.out.println(elvis == elvis2);
            System.out.println(elvis == Elvis.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
