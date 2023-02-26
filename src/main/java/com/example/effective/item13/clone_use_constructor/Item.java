package com.example.effective.item13.clone_use_constructor;

public class Item implements Cloneable {

    private String name;

    @Override
    public Item clone() {
        try {
            Item result = (Item) super.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
