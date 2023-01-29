package item5.singleton;

import item5.KorDictionary;

import java.util.List;

public class SpellChecker {
    private final KorDictionary dic = new KorDictionary();

    private SpellChecker() {
    }

    public static final SpellChecker INSTANCE = new SpellChecker();

    public boolean isValid(String word) {
        // todo isValid 부가코드
        return dic.contains(word);
    }

    public List<String> suggestions(String typo) {
        // todo suggestions 부가코드
        return dic.closeWordsTo(typo);
    }
}
