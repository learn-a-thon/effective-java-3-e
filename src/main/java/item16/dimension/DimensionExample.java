package item16.dimension;

import java.awt.*;

public class DimensionExample {
    public static void main(String[] args) {
        Button button = new Button("hello button");
        button.setBounds(0, 0, 20, 10);

        Dimension size = button.getSize();
        System.out.println("size.height = " + size.height);
        System.out.println("size.width = " + size.width);
    }
}
