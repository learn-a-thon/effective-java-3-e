package com.example.effective.item5.dependencyinjection;

import com.example.effective.item5.DefaultDictionary;
import org.junit.jupiter.api.Test;

class SpellCheckerTest {

    @Test
    void isValid() {
        SpellChecker spellChecker = new SpellChecker(new DefaultDictionary()); // 의존 객체의 주입
        spellChecker.isValid("test");
    }

    @Test
    void isValid2() {
//        SpellChecker spellChecker = new SpellChecker(() -> new DefaultDictionary());
        SpellChecker spellChecker = new SpellChecker(DefaultDictionary::new);
        spellChecker.isValid("test");
    }
}