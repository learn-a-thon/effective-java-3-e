package com.example.effective.item5.factorymethod;

import com.example.effective.item5.DefaultDictionary;
import com.example.effective.item5.Dictionary;

public class DefaultDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new DefaultDictionary();
    }
}
