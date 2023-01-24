package item1;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflectionTest {

    @Test
    void reflection() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> clazz = Class.forName("item1.HelloService");
        Constructor<?> constructor = clazz.getConstructor();
        HelloService helloService = (HelloService) constructor.newInstance();
        System.out.println("helloService.hello() = " + helloService.hello());
    }
}
