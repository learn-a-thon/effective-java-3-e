package item5.factorymethod;

import item5.Dictionary;
import item5.KorDictionary;

public class DefaultDictionaryFactory implements DictionaryFactory {
    @Override
    public Dictionary getDictionary() {
        return new KorDictionary();
    }
}
