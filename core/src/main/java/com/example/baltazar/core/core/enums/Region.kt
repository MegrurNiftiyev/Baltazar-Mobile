package com.example.baltazar.core.core.enums

enum class Region(val code: String, val displayName: String) {
    AZ("AZ", "Azərbaycan"),
    TR("TR", "Türkiyə"),
    US("US", "ABŞ"),
    RU("RU", "Rusiya");

    companion object {
        fun fromCode(code: String?): Region {
            return entries.firstOrNull { it.code.equals(code, ignoreCase = true) } ?: AZ
        }
    }
}
