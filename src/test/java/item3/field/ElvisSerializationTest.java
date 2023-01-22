package item3.field;

import java.io.*;

public class ElvisSerializationTest {
    public static void main(String[] args) {
        String file = "elvis.obj";
        // write
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(Elvis.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // read
        try (ObjectInput in = new ObjectInputStream(new FileInputStream(file))) {
            Elvis elvis = (Elvis) in.readObject();
            System.out.println(elvis == Elvis.getInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
