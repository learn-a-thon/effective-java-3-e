package item3.enumtype;

import java.lang.reflect.Constructor;

public class EnumElvisReflectionTest {

    public static void main(String[] args) {
        try {
            Constructor<Elvis> declaredConstructor = Elvis.class.getDeclaredConstructor();
            // java.lang.NoSuchMethodException: item3.enumtype.Elvis.<init>() ...
            System.out.println(declaredConstructor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
