package com.example.effective.item8;

public class Adult {
    public static void main(String[] args) throws Exception {
        try (Room room = new Room(7)) {
            System.out.println("hi~");
        }
    }
}
