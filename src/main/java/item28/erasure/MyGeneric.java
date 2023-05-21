package item28.erasure;

import java.util.ArrayList;
import java.util.List;

public class MyGeneric {

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("foo");
        String name = names.get(0);
        System.out.println(name);
    }
}
