package item2;

import java.util.Arrays;

public class VarargsSample {

    public void printNumber(int... numbers) {
        System.out.println(numbers.getClass().getCanonicalName());
        System.out.println(numbers.getClass().getComponentType());
        System.out.println(Arrays.toString(numbers));
    }

    // Heap Pollution 참고
    public static void main(String[] args) {
        VarargsSample varargsSample = new VarargsSample();
        varargsSample.printNumber(2, 3, 4, 5);
    }
}
