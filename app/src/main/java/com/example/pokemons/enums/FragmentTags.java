package com.example.pokemons.enums;

import androidx.annotation.NonNull;

public enum FragmentTags {
    HOME("home"), SETTINGS("settings");

    private final String stringValue;

    FragmentTags(final String s) {
        stringValue = s;
    }

    @NonNull
    public String toString() {
        return stringValue;
    }
}
