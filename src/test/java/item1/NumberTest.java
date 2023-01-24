package item1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class NumberTest {
    @Test
    void sort() {
        //질문1
        List<Integer> numbers = new ArrayList<>(List.of(49, 42, 64, 4, 31, 73, 77));
        Comparator<Integer> desc = (o1, o2) -> o2 - o1;
        numbers.sort(desc);
        System.out.println(numbers);

        //질문2
        numbers.sort(desc.reversed());
        System.out.println(numbers);
    }
}
