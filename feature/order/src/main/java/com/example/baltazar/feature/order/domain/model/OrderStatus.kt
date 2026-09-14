package com.example.baltazar.feature.order.domain.model

enum class OrderStatus {
    PENDING,
    AWAITING_PAYMENT,
    PROCESSING,
    CONFIRMED,
    CANCELLED,
    EXPIRED;

    companion object {
        fun fromRaw(raw: String?): OrderStatus {
            return try {
                raw?.let { valueOf(it.uppercase()) } ?: PENDING
            } catch (e: Exception) {
                PENDING
            }
        }
    }
}
