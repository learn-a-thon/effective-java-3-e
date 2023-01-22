package item3.enumtype;

import item3.field.IElvis;

public enum Elvis implements IElvis {
    INSTANCE;

    public void sing() {
        System.out.println("enum elvis singing!");
    }

    public static void main(String[] args) {
        Elvis elvis = Elvis.INSTANCE;
        elvis.sing();
    }
}
