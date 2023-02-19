package item14.decimal;

public class IntOverflow {

    public static void main(String[] args) {
        System.out.println(-2147483648 - 10); // 음의 정수의 끝은 양의 정수의 최대값이다.

        // 다음 방식을 사용하는 것을 권장한다.
        System.out.println(Integer.compare(-2147483648, 10));
    }
}
