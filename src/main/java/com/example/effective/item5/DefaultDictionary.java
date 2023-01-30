package com.example.effective.item5;

import java.util.List;

public class DefaultDictionary implements Dictionary {

    public boolean contains(String word) {
        return true;
    }

    public List<String> closeWordsTo(String typo) {
        return null;
    }

}
