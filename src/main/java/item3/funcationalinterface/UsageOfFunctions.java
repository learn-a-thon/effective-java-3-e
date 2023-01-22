package item3.funcationalinterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class UsageOfFunctions {
    public static void main(String[] args) {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(1933, 2, 19));
        dates.add(LocalDate.of(2033, 3, 29));
        dates.add(LocalDate.of(2003, 4, 15));

        Predicate<LocalDate> localDatePredicate = d -> d.isBefore(LocalDate.of(2000, 1, 1));
        Function<LocalDate, Integer> getYear = LocalDate::getYear;
        List<Integer> years = dates.stream()
                .filter(localDatePredicate)
                .map(getYear)
                .collect(Collectors.toList());
    }
}
