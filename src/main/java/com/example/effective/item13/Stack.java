package com.example.effective.item13;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack implements Cloneable {
    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack() {
        this.elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e) {
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        Object result = elements[--size];
        elements[size] = null;
        return result;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Stack clone() {
        try {
            Stack result = (Stack) super.clone();
            result.elements = elements.clone();
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void ensureCapacity() {
        if (elements.length == size) {
            elements = Arrays.copyOf(elements, 2 * size + 1);
        }
    }

    public static void main(String[] args) {
        Object[] values = new Object[2];
        values[0] = new PhoneNumber(123, 456, 8790);
        values[1] = new PhoneNumber(123, 456, 1111);

        Stack stack = new Stack();
        for (Object arg : values) {
            stack.push(arg);
        }

        Stack copy = stack.clone();

        System.out.println("pop from stack");
        while(!stack.isEmpty()) {
            System.out.println(stack.pop() + " ");
        }

        System.out.println("pop from copy");
        while(!copy.isEmpty()) {
            System.out.println(copy.pop() + " ");
        }

        System.out.println(stack.elements[0] == copy.elements[0]);
    }

}
