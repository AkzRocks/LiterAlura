package com.wildproyect.literalura.Model;

public enum Language {
    SPANISH("es"),
    INGLISH("en"),
    PORTUGUESE("pt"),
    FRENCH("fr"),
    ITALIAN("it"),
    JAPANESE("ja"),
    CHINESE("zh"),
    KOREAN("ko");

    private String language;

   Language(String language) {
        this.language = language;
    }

    public static Language fromString(String language) {
        for (Language lengua : Language.values()) {
            if (lengua.language.equalsIgnoreCase(language))
                return lengua;
        }
        throw new IllegalArgumentException(language + " is not supported");
    }
    public String getLanguage() {
        return language;
    }
    
}
