package item3.enumtype;

import java.io.*;

public class EnumElvisSerializationTest {
    public static void main(String[] args) {
        String file = "elvis.obj";
        // write
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Elvis.INSTANCE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // read
        try (ObjectInput in = new ObjectInputStream(new FileInputStream(file))) {
            Elvis elvis = (Elvis) in.readObject();
            System.out.println(elvis == Elvis.INSTANCE); // true
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
