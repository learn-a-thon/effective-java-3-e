package com.example.effective.item6;

public class Deprecation {

    private String name;

    /**
     * @deprecated in favor of
     * {@link #Deprecation(String}
     */
    @Deprecated(forRemoval = true)
    public Deprecation() {}

    public Deprecation(String name) {
        this.name = name;
    }

}
