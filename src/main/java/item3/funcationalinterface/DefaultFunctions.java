package item3.funcationalinterface;

import item3.methodreference.Person;

import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultFunctions {
    public static void main(String[] args) {
        // 인자와 반환 타입을 지정하여 반환
        Function<Integer, String> intToString = (i) -> "hello";

        // 인자는 안받고 지정 타입으로 반환
        Supplier<Integer> integerSupplier;
        Supplier<Person> personSupplier = Person::new;
        Function<LocalDate, Person> localDateToPerson = Person::new;

        // 인자를 지정하고 void 타입으로 반환
        Consumer<Integer> integerConsumer = System.out::println;

        // 인자를 지정하고 boolean 타입으로 반환
        Predicate<Integer> predicate;
    }
}
