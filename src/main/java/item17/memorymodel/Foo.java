package item17.memorymodel;

public class Foo {
    private final int x;
    private final int y;

    public Foo() {
        this.x = 1;
        this.y = 2;
    }

    public static void main(String[] args) {
        // 필드에 final 키워드가 없다면 아래 실행 순서가 다를 수 있다.
        // Object w = new Foo()
        // foo = w
        // w.x = 1
        // w.y = 2

        Foo foo = new Foo();
    }
}
