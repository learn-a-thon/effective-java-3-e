package item3.staticfactory;

// p24. 정적 팩터리 방식의 싱글턴
public class Elvis implements Singer {
    private static final Elvis INSTANCE = new Elvis();

    public static Elvis getInstance() {
        return INSTANCE;
    }

    @Override
    public void sing() {
        System.out.println("singing!");
    }
}
