package com.example.effectivejava3e.week1.item1;

import java.util.EnumSet;

public enum OrderStatus {

    PREPARING, SHIPPED, DELIVERING, DELIVERED;

    public static final EnumSet<OrderStatus> completed = EnumSet.of(DELIVERING, DELIVERED);
}
