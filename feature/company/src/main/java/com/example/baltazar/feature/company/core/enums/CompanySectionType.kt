package com.example.baltazar.feature.company.core.enums

enum class CompanySectionType {
    HEADER,
    ABOUT,
    GALLERY,
    ITEMS,
    REVIEWS,
    UNKNOWN;

    companion object {
        fun fromString(value: String): CompanySectionType {
            return try {
                valueOf(value.uppercase())
            } catch (_: Exception) {
                UNKNOWN
            }
        }
    }
}
