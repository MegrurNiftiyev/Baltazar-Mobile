package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.TokenizedCard
import kotlinx.serialization.Serializable

@Serializable
data class TokenizeCardResponseDto(
    val success: Boolean = false,
    val paymentMethodId: String? = null,
    val brand: String? = null,
    val last4: String? = null,
    val expiryMonth: String? = null,
    val expiryYear: String? = null,
    val errorCode: String? = null,
    val message: String? = null
) {
    fun toDomain(): TokenizedCard {
        return TokenizedCard(
            paymentMethodId = paymentMethodId.orEmpty(),
            brand = brand.orEmpty(),
            last4 = last4.orEmpty(),
            expiryMonth = expiryMonth?.toIntOrNull() ?: 0,
            expiryYear = expiryYear?.toIntOrNull() ?: 0
        )
    }
}
