package item2.hierarchicalbuilder;

public class Calzone extends Pizza {
    private final boolean sauceInside;

    Calzone(Builder builder) {
        super(builder);
        this.sauceInside = builder.sauceInside;
    }

    @Override
    public String toString() {
        return toppings + "로 토핑한 소스 포함이 " + sauceInside + " 인 Calzone 피자";
    }

    public static class Builder extends Pizza.Builder<Calzone.Builder> {
        private boolean sauceInside = false;

        public Builder sauceInside() {
            this.sauceInside = true;
            return this;
        }

        @Override
        Pizza build() {
            return new Calzone(this);

        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
