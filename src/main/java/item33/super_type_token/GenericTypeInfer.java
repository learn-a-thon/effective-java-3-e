package item33.super_type_token;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeInfer {

    static class Super<T> {
        T value;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        Super<String> stringSuper = new Super<>();
        System.out.println(stringSuper.getClass().getDeclaredField("value").getType());

        // (new Super<String>(){}): 익명 내부 클래스
        Type type = (new Super<String>(){}).getClass().getGenericSuperclass();
        ParameterizedType pType = (ParameterizedType) type;
        Type actualTypeArgument = pType.getActualTypeArguments()[0];
        System.out.println(actualTypeArgument);
    }
}
