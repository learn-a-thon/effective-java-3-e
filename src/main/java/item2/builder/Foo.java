package item2.builder;

public class Foo {
    private Long id;
    private String name;
    private int age;
    private int weight;
    private int height;

    public Foo(Long id, String name, int age, int weight, int height) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.height = height;
    }

    public static FooBuilder builder(Long id, String name) {
        return new FooBuilder(id, name);
    }

    public static class FooBuilder {
        private Long id;
        private String name;
        private int age;
        private int weight;
        private int height;

        public FooBuilder(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public FooBuilder age(int age) {
            this.age = age;
            return this;
        }
        public FooBuilder weight(int weight) {
            this.weight = weight;
            return this;
        }
        public FooBuilder height(int height) {
            this.height = height;
            return this;
        }

        public Foo build() {
            return new Foo(id, name, age, weight, height);
        }
    }

    public static void main(String[] args) {
        Foo foo = Foo.builder(1L, "홍길동")
                .age(20)
                .height(200)
                .weight(1000)
                .build();
    }
}
