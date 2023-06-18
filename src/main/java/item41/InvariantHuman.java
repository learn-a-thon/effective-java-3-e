package item41;

final class InvariantHuman {
    private int age;
    private String name;

    public InvariantHuman(String name) {
        this(0, name);
    }

    public InvariantHuman(int age, String name) {
        if (age < 0) {
            throw new IllegalArgumentException("나이는 0보다 작을 수 없다는 것은 불변한 사실이다.");
        }
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}
