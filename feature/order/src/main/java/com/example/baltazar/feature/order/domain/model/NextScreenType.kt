package com.example.baltazar.feature.order.domain.model

enum class NextScreenType {
    PERSONAL_INFO_SCREEN,
    DRIVER_LICENSE_SCREEN,
    PASSPORT_INFO_SCREEN,
    DELIVERY_ADDRESS_SCREEN,
    PAYMENT_SCREEN,
    CONFIRM_SCREEN,
    UNKNOWN;

    companion object {
        fun fromRaw(raw: String?): NextScreenType {
            return try {
                raw?.let { valueOf(it.uppercase()) } ?: UNKNOWN
            } catch (e: Exception) {
                UNKNOWN
            }
        }
    }
}
