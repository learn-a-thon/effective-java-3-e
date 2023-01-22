package item2.hierarchicalbuilder;

import java.util.Objects;

public class NyPizza extends Pizza {

    private final Size size;

    NyPizza(Builder builder) {
        super(builder);
        this.size = builder.size;
    }

    @Override
    public String toString() {
        return toppings + "로 토핑한 " + size + "크기의 뉴욕 피자";
    }

    public enum Size {
        SMALL, MEDIUM, BIG
    }

    public static class Builder extends Pizza.Builder<NyPizza.Builder> {
        private final Size size;

        public Builder(Size size) {
            this.size = Objects.requireNonNull(size);
        }

        @Override
        Pizza build() {
            return new NyPizza(this);

        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
