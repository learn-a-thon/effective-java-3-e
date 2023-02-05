package com.example.effective.item7.executor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutorsExampleTest {

    @Test
    public void test() {
        System.out.println(Runtime.getRuntime().availableProcessors());
    }
}