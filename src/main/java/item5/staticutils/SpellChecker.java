package item5.staticutils;

import item5.KorDictionary;

import java.util.List;

public class SpellChecker {
    // 자원을 직접 명시하는 예시
    // 테스트 코드 작성시 의존 객체인 Dictionary 활용 대한 유연함이 떨어진다.
    private static final KorDictionary dic = new KorDictionary();

    public SpellChecker() {
    }

    public static boolean isValid(String word) {
        // todo isValid 부가코드
        return dic.contains(word);
    }

    public static List<String> suggestions(String typo) {
        // todo suggestions 부가코드
        return dic.closeWordsTo(typo);
    }
}
