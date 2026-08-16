package com.example.baltazar.core.core.enums

enum class Language(val code: String, val displayName: String) {
    AZ("az", "Azərbaycan"),
    EN("en", "English"),
    TR("tr", "Türkçe"),
    RU("ru", "Русский");

    companion object {
        fun fromCode(code: String?): Language {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: AZ
        }
    }
}
