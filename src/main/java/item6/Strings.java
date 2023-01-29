package item6;

public class Strings {
    public static void main(String[] args) {
        String hello = "hello";
        // 권장하지 않는 방법
        String hello2 = new String("hello");
        String hello3 = "hello";

        System.out.println(hello == hello2);
        System.out.println(hello.equals(hello2));
    }
}
