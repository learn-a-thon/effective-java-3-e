package item5;

import java.util.Collections;
import java.util.List;

public class KorDictionary implements Dictionary {

    public boolean contains(String word) {
        return true;
    }

    public List<String> closeWordsTo(String typo) {
        return Collections.emptyList();
    }
}
