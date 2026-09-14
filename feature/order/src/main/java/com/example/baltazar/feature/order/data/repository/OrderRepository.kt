package com.example.baltazar.feature.order.data.repository

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.core.domain.model.PaginatedList
import com.example.baltazar.core.domain.model.PaginationInfo
import com.example.baltazar.feature.order.data.datasources.remote.datasources.OrderRemoteDataSource
import com.example.baltazar.feature.order.data.model.request.CreateOrderRequest
import com.example.baltazar.feature.order.data.model.request.PatchDeliveryAddressRequest
import com.example.baltazar.feature.order.data.model.request.PatchPaymentMethodRequest
import com.example.baltazar.feature.order.domain.model.NextScreenResult
import com.example.baltazar.feature.order.domain.model.Order
import com.example.baltazar.feature.order.domain.model.OrderStatus
import com.example.baltazar.feature.order.domain.model.PaymentSummary
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val remoteDataSource: OrderRemoteDataSource
) : IOrderRepository {

    override suspend fun createOrder(
        serviceType: ServiceType,
        serviceId: String,
        subItemId: String?
    ): Result<Order> {
        return runCatching {
            val response = remoteDataSource.createOrder(
                CreateOrderRequest(
                    serviceType = serviceType.name,
                    serviceId = serviceId,
                    subItemId = subItemId
                )
            )
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to create order")
            }
        }
    }

    override suspend fun getNextScreen(orderId: String): Result<NextScreenResult> {
        return runCatching {
            val response = remoteDataSource.getNextScreen(orderId)
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to resolve next screen")
            }
        }
    }

    override suspend fun patchPaymentMethod(
        orderId: String,
        paymentMethodId: String
    ): Result<Order> {
        return runCatching {
            val response = remoteDataSource.patchPaymentMethod(
                id = orderId,
                request = PatchPaymentMethodRequest(paymentMethodId = paymentMethodId)
            )
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to attach payment method")
            }
        }
    }

    override suspend fun patchDeliveryAddress(
        orderId: String,
        lat: Double,
        lng: Double,
        addressName: String
    ): Result<Order> {
        return runCatching {
            val response = remoteDataSource.patchDeliveryAddress(
                id = orderId,
                request = PatchDeliveryAddressRequest(lat = lat, lng = lng, addressName = addressName)
            )
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to update delivery address")
            }
        }
    }

    override suspend fun getOrders(
        serviceType: ServiceType?,
        status: OrderStatus?,
        limit: Int,
        cursor: String?
    ): Result<PaginatedList<Order>> {
        return runCatching {
            val response = remoteDataSource.getOrders(
                serviceType = serviceType?.name,
                status = status?.name,
                limit = limit,
                cursor = cursor
            )
            if (response.success) {
                val domainItems = response.data.map { it.toDomain() }
                val paginationInfo = response.pagination?.toDomain() ?: PaginationInfo(
                    nextCursor = null,
                    hasMore = false,
                    limit = limit
                )
                PaginatedList(
                    items = domainItems,
                    pagination = paginationInfo
                )
            } else {
                throw Exception("Failed to fetch orders")
            }
        }
    }

    override suspend fun getOrderDetails(orderId: String): Result<Order> {
        return runCatching {
            val response = remoteDataSource.getOrderDetails(orderId)
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to fetch order details")
            }
        }
    }

    override suspend fun cancelOrder(orderId: String): Result<Order> {
        return runCatching {
            val response = remoteDataSource.cancelOrder(orderId)
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to cancel order")
            }
        }
    }

    override suspend fun getPaymentSummary(orderId: String): Result<PaymentSummary> {
        return runCatching {
            val response = remoteDataSource.getPaymentSummary(orderId)
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to fetch payment summary")
            }
        }
    }
}
