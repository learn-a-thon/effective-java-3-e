package item5.springioc;

import item5.Dictionary;

import java.util.List;

//@Component
public class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public boolean isValid(String word) {
        // todo isValid 부가코드
        return dictionary.contains(word);
    }

    public List<String> suggestions(String typo) {
        // todo suggestions 부가코드
        return dictionary.closeWordsTo(typo);
    }
}
