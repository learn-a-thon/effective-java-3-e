package item5.dependencyinjection;

import item5.Dictionary;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class SpellCheckerTest {

    @Test
    void create_supplier() {
        // dictionary 객체를 외부에서 주입함으로써 유연함이 증가한다.
        SpellChecker spellChecker = new SpellChecker(() -> new MockDictionary());

        // method reference로 축약 가능하다.
        // SpellChecker spellChecker2 = new SpellChecker(MockDictionary::new);

        // 람다를 사용하면 팩토리 클래스를 활용할 수 있다.
        // SpellChecker spellChecker3 = new SpellChecker(DictionaryFactory::getKor);
        assertFalse(spellChecker.isValid("bye"));
    }

    @Test
    void spellChecker() {
        // dictionary 객체를 외부에서 주입함으로써 유연함이 증가한다.
        SpellChecker spellChecker = new SpellChecker(new MockDictionary());
        assertFalse(spellChecker.isValid("bye"));
    }

    static class MockDictionary implements Dictionary {
        @Override
        public boolean contains(String word) {
            return false;
        }

        @Override
        public List<String> closeWordsTo(String typo) {
            return null;
        }
    }
}
