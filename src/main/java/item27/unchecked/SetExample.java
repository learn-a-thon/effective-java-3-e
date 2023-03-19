package item27.unchecked;

import java.util.HashSet;
import java.util.Set;

public class SetExample {

    public static void main(String[] args) {
        Set names = new HashSet(); // 컴파일 경고!

        Set<String> strings = new HashSet<>();
    }
}
