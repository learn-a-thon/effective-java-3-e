package item2.hierarchicalbuilder;

public class PizzaTest {

    public static void main(String[] args) {
        Pizza nyPizza = new NyPizza.Builder(NyPizza.Size.MEDIUM)
                .addTopping(Pizza.Topping.MUSHROOM)
                .addTopping(Pizza.Topping.ONION)
                .build();

        Pizza calzone = new Calzone.Builder()
                .sauceInside()
                .addTopping(Pizza.Topping.SAUSAGE)
                .build();

        System.out.println("nyPizza = " + nyPizza);
        System.out.println("calzone = " + calzone);
    }
}
