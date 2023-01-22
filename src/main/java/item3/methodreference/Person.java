package item3.methodreference;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Person {
    LocalDate birthday;

    public Person() {
    }

    public Person(LocalDate birthday) {
        this.birthday = birthday;
    }

    public static int compareByAge(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    public int compareByAgeMethodRef(Person a, Person b) {
        return a.birthday.compareTo(b.birthday);
    }

    public int compareByAgeMethodRef(Person b) {
        return this.birthday.compareTo(b.birthday);
    }

    public int getAge() {
        return LocalDate.now().getYear() - birthday.getYear();
    }

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person(LocalDate.of(2011, 3, 11)));
        personList.add(new Person(LocalDate.of(2031, 8, 7)));
        personList.add(new Person(LocalDate.of(2001, 4, 21)));

        personList.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.birthday.compareTo(o2.birthday);
            }
        });
        // 람다 표현식 줄여쓰기
        personList.sort((o1, o2) -> o1.birthday.compareTo(o2.birthday));
        // static method reference
        personList.sort(Person::compareByAge);
        // 인스턴스 메소드 레퍼런스
        Person person = new Person(null);
        personList.sort(person::compareByAgeMethodRef);
        // 임의 객체의 인스턴스 메소드 레퍼런스
        personList.sort(Person::compareByAgeMethodRef);
        // 생성자 레퍼런스
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(1933, 2, 19));
        dates.add(LocalDate.of(2033, 3, 29));
        dates.add(LocalDate.of(2003, 4, 15));
        List<Person> newPersonList = dates.stream()
                .map(Person::new)
                .collect(Collectors.toList());
        System.out.println(personList);
    }
}
