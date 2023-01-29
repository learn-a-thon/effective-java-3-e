package com.example.effective.item5.staticutils;


import com.example.effective.item5.DefaultDictionary;
import com.example.effective.item5.Dictionary;

import java.util.List;

// 테스트하기 유연하지 않다.
// Dictionary에 대해서 mocking을 하고 싶을 수도 있고 다른 자원을 사용하고 싶을 수도 있다.
// static 변수에 대한 목킹도 가능하지만 권장되지 않는다.
public class SpellChecker {

    // 사용하는 자원을 직접 생성해서 사용하는 형태
    private static final Dictionary DEFAULT_DICTIONARY = new DefaultDictionary();

    private SpellChecker() {}

    public static boolean isValid(String word) {
        return DEFAULT_DICTIONARY.contains(word);
    }

    public static List<String> suggestions(String typo) {
        return DEFAULT_DICTIONARY.closeWordsTo(typo);
    }
}
