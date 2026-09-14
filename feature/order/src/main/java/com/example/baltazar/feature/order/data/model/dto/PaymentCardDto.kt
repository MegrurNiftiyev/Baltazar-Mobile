package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.PaymentCard
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PaymentCardDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("paymentMethodId") val paymentMethodId: String? = null,
    @SerialName("brand") val brand: String? = null,
    @SerialName("last4") val last4: String? = null,
    @SerialName("expiryMonth") val expiryMonth: Int? = null,
    @SerialName("expiryYear") val expiryYear: Int? = null
) {
    fun toDomain(): PaymentCard {
        val uniquePmId = paymentMethodId.takeIf { !it.isNullOrBlank() }
            ?: id.takeIf { !it.isNullOrBlank() }
            ?: mongoId.takeIf { !it.isNullOrBlank() }
            ?: UUID.randomUUID().toString()

        return PaymentCard(
            id = id ?: mongoId,
            paymentMethodId = uniquePmId,
            brand = brand ?: "Card",
            last4 = last4 ?: "****",
            expiryMonth = expiryMonth ?: 1,
            expiryYear = expiryYear ?: 2030
        )
    }
}
