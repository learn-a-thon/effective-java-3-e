package item5.dependencyinjection;

import item5.Dictionary;
import item5.KorDictionary;

public class DictionaryFactory {

    public Dictionary get() {
        return new KorDictionary();
    }

    public static Dictionary getKor() {
        return new KorDictionary();
    }
}
