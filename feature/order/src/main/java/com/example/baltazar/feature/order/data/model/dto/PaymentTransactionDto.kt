package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.PaymentTransaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentTransactionDto(
    @SerialName("id") val id: String? = null,
    @SerialName("_id") val mongoId: String? = null,
    @SerialName("userId") val userId: String? = null,
    @SerialName("orderId") val orderId: String? = null,
    @SerialName("paymentMethodId") val paymentMethodId: String? = null,
    @SerialName("amount") val amount: Double? = null,
    @SerialName("currency") val currency: String? = null,
    @SerialName("providerEventId") val providerEventId: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("createdAt") val createdAt: String? = null
) {
    fun toDomain(): PaymentTransaction {
        return PaymentTransaction(
            id = id ?: mongoId ?: "",
            userId = userId.orEmpty(),
            orderId = orderId.orEmpty(),
            paymentMethodId = paymentMethodId,
            amount = amount ?: 0.0,
            currency = currency.orEmpty(),
            providerEventId = providerEventId,
            status = status ?: "SUCCESS",
            createdAt = createdAt.orEmpty()
        )
    }
}
