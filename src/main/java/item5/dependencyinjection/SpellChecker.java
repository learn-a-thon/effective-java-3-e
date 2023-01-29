package item5.dependencyinjection;

import item5.Dictionary;

import java.util.List;
import java.util.function.Supplier;

public class SpellChecker {
    private final Dictionary dic;

    public SpellChecker(Dictionary dic) {
        this.dic = dic;
    }

    // 팩토리 클래스로 의존 객체 주입
    public SpellChecker(DictionaryFactory dicFactory) {
        this.dic = dicFactory.get();
    }

    // Supplier로 팩토리 클래스 대체하여 의존 객체 주입
    public SpellChecker(Supplier<Dictionary> dictionarySupplier) {
        this.dic = dictionarySupplier.get();
    }

    public boolean isValid(String word) {
        // todo isValid 부가코드
        return dic.contains(word);
    }

    public List<String> suggestions(String typo) {
        // todo suggestions 부가코드
        return dic.closeWordsTo(typo);
    }
}
