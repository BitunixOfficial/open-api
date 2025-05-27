package com.bitunix.openapi.enums;

public enum Language {
    English("en_US"),
    Traditional_Chinese("zh_TW"),
    Turkish("tr_TR"),
    French("fr_FR"),
    Russian("ru_RU"),
    Vietnamese("vi_VN"),
    Spanish("es_ES"),
    Portuguese_Portugal("pt_PT"),
    Portuguese_Brazil("pt_BR"),
    Italiano("it_IT"),
    Deutsch("de_DE"),
    Japanese("ja_JP"),
    Korean("ko_KR"),
    Polish("pl_PL"),
    Bahasa_Indonesia("id_ID"),
    Thai("th_TH"),
    Ukranian("uk_UA"),
    Persian("fa_IR"),
    Hindi("hi_IN"),
    Uzbek("uz_UZ"),
    ;
    String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
