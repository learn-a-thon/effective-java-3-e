package item2.builder;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FooLombok {
    private Long id;
    private String name;
    private int age;
    private int weight;
    private int height;
}
