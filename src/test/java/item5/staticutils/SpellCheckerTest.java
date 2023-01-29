package item5.staticutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SpellCheckerTest {

    @Test
    void spellChecker() {
        boolean valid = SpellChecker.isValid("hello");
        assertTrue(valid);
    }
}
