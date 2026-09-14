package com.example.baltazar.feature.order.domain.model

import com.example.baltazar.core.core.enums.ServiceType

data class PaymentSummary(
    val orderId: String,
    val serviceType: ServiceType,
    val serviceId: String,
    val status: OrderStatus,
    val totalPrice: Double,
    val serviceItemSnapshot: OrderServiceItemSnapshot?,
    val transactions: List<PaymentTransaction>
)
