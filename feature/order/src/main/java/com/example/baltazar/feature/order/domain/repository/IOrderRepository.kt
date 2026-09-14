package com.example.baltazar.feature.order.domain.repository

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.feature.order.domain.model.NextScreenResult
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus
import com.example.baltazar.feature.order.domain.model.PaymentSummary

interface IOrderRepository {
    suspend fun createOrder(
        serviceType: ServiceType,
        serviceId: String,
        subItemId: String? = null
    ): Result<Order>

    suspend fun getNextScreen(orderId: String): Result<NextScreenResult>

    suspend fun patchPaymentMethod(orderId: String, paymentMethodId: String): Result<Order>

    suspend fun patchDeliveryAddress(orderId: String, lat: Double, lng: Double, addressName: String): Result<Order>

    suspend fun getOrders(
        serviceType: ServiceType? = null,
        status: OrderStatus? = null,
        limit: Int = 20,
        cursor: String? = null
    ): Result<PaginatedList<Order>>

    suspend fun getOrderDetails(orderId: String): Result<Order>

    suspend fun cancelOrder(orderId: String): Result<Order>

    suspend fun getPaymentSummary(orderId: String): Result<PaymentSummary>
}
