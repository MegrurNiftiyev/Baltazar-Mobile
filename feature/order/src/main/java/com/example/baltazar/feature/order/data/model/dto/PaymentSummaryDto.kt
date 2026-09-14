package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.order.domain.model.OrderStatus
import com.example.baltazar.feature.order.domain.model.PaymentSummary
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentSummaryDto(
    @SerialName("orderId") val orderId: String? = null,
    @SerialName("serviceType") val serviceType: String? = null,
    @SerialName("serviceId") val serviceId: String? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("totalPrice") val totalPrice: Double? = null,
    @SerialName("serviceItemSnapshot") val serviceItemSnapshot: OrderServiceItemSnapshotDto? = null,
    @SerialName("transactions") val transactions: List<PaymentTransactionDto> = emptyList()
) {
    fun toDomain(): PaymentSummary {
        return PaymentSummary(
            orderId = orderId.orEmpty(),
            serviceType = ServiceType.fromRaw(serviceType),
            serviceId = serviceId.orEmpty(),
            status = OrderStatus.fromRaw(status),
            totalPrice = totalPrice ?: 0.0,
            serviceItemSnapshot = serviceItemSnapshot?.toDomain(),
            transactions = transactions.map { it.toDomain() }
        )
    }
}
