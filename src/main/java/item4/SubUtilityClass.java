package item4;

public class SubUtilityClass extends UtilityClass {

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        SubUtilityClass subUtilityClass = new SubUtilityClass();
        String hello = SubUtilityClass.hello();
    }
}
