package item2;

public class Foo {
    private Long id;
    private String name;
    private int age;
    private int weight;
    private int height;

    public Foo() {
    }

    public Foo(Long id, String name, int age) {
        this(id, name, age, 0, 0);
    }

    public Foo(Long id, String name, int age, int weight) {
        this(id, name, age, weight, 0);
    }

    public Foo(Long id, String name, int age, int weight, int height) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public static void main(String[] args) {
        // 자바빈스 패턴 방식을 사용하는 경우
        // 기본 생성자가 사용후 바로 사용된 위험성(초기값이 설정되지 않은 상태)이 있어서 권장하지 않는다.
        Foo foo = new Foo();
        foo.setId(1L);
    }
}
