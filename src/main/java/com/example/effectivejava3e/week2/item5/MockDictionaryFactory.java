package com.example.effectivejava3e.week2.item5;

public class MockDictionaryFactory implements DictionaryFactory {

    @Override
    public Dictionary getDictionary() {

        return new MockDictionary();
    }
}
