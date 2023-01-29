package item4;

public abstract class UtilityClass {

    public UtilityClass() {
        System.out.println("utility class constructor");
    }

    public static String hello() {
        return "hello";
    }

    public static void main(String[] args) {
        String hello = UtilityClass.hello();
//        UtilityClass utilityClass = new UtilityClass();
    }
}
