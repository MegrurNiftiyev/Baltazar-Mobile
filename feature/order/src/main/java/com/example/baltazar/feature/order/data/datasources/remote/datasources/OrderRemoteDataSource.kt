package com.example.baltazar.feature.order.data.datasources.remote.datasources

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.order.data.datasources.remote.services.OrderApiService
import com.example.baltazar.feature.order.data.model.dto.NextScreenDataDto
import com.example.baltazar.feature.order.data.model.dto.OrderDto
import com.example.baltazar.feature.order.data.model.dto.OrderItemDto
import com.example.baltazar.feature.order.data.model.dto.PaymentSummaryDto
import com.example.baltazar.feature.order.data.model.request.CreateOrderRequest
import com.example.baltazar.feature.order.data.model.request.PatchDeliveryAddressRequest
import com.example.baltazar.feature.order.data.model.request.PatchPaymentMethodRequest
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val apiService: OrderApiService
) {
    suspend fun createOrder(request: CreateOrderRequest): ApiResponse<OrderDto> {
        return apiService.createOrder(request)
    }

    suspend fun getNextScreen(id: String): ApiResponse<NextScreenDataDto> {
        return apiService.getNextScreen(id)
    }

    suspend fun patchPaymentMethod(id: String, request: PatchPaymentMethodRequest): ApiResponse<OrderDto> {
        return apiService.patchPaymentMethod(id, request)
    }

    suspend fun patchDeliveryAddress(id: String, request: PatchDeliveryAddressRequest): ApiResponse<OrderDto> {
        return apiService.patchDeliveryAddress(id, request)
    }

    suspend fun getOrders(
        serviceType: String?,
        status: String?,
        limit: Int,
        cursor: String?
    ): PaginatedResponse<OrderItemDto> {
        return apiService.getOrders(serviceType, status, limit, cursor)
    }

    suspend fun getOrderDetails(id: String): ApiResponse<OrderDto> {
        return apiService.getOrderDetails(id)
    }

    suspend fun cancelOrder(id: String): ApiResponse<OrderDto> {
        return apiService.cancelOrder(id)
    }

    suspend fun getPaymentSummary(id: String): ApiResponse<PaymentSummaryDto> {
        return apiService.getPaymentSummary(id)
    }
}
