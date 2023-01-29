package item5.factorymethod;

import item5.Dictionary;

public class SpellChecker {
    private final Dictionary dictionary;

    public SpellChecker(DictionaryFactory dictionaryFactory) {
        this.dictionary = dictionaryFactory.getDictionary();
    }
}
