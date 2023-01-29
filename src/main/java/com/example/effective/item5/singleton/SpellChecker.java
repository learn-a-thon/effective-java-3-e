package com.example.effective.item5.singleton;


import com.example.effective.item5.DefaultDictionary;
import com.example.effective.item5.Dictionary;

import java.util.List;

// 유연성과 재사용성이 떨어진다.
// korean dict, english dict 등 변경 시 문제
public class SpellChecker {

    private final Dictionary defaultDictionary = new DefaultDictionary();

    private SpellChecker() {}

    public static final SpellChecker INSTANCE = new SpellChecker();

    public boolean isValid(String word) {
        return defaultDictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        return defaultDictionary.closeWordsTo(typo);
    }
}
