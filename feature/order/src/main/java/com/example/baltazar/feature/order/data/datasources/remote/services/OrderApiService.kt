package com.example.baltazar.feature.order.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.core.data.model.response.PaginatedResponse
import com.example.baltazar.feature.order.data.model.dto.NextScreenDataDto
import com.example.baltazar.feature.order.data.model.dto.OrderDto
import com.example.baltazar.feature.order.data.model.dto.OrderItemDto
import com.example.baltazar.feature.order.data.model.dto.PaymentSummaryDto
import com.example.baltazar.feature.order.data.model.request.CreateOrderRequest
import com.example.baltazar.feature.order.data.model.request.PatchDeliveryAddressRequest
import com.example.baltazar.feature.order.data.model.request.PatchPaymentMethodRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface OrderApiService {

    @POST("api/orders")
    suspend fun createOrder(
        @Body request: CreateOrderRequest
    ): ApiResponse<OrderDto>

    @GET("api/orders/{id}/next-screen")
    suspend fun getNextScreen(
        @Path("id") id: String
    ): ApiResponse<NextScreenDataDto>

    @PATCH("api/orders/{id}/payment-method")
    suspend fun patchPaymentMethod(
        @Path("id") id: String,
        @Body request: PatchPaymentMethodRequest
    ): ApiResponse<OrderDto>

    @PATCH("api/orders/{id}/delivery-address")
    suspend fun patchDeliveryAddress(
        @Path("id") id: String,
        @Body request: PatchDeliveryAddressRequest
    ): ApiResponse<OrderDto>

    @GET("api/orders")
    suspend fun getOrders(
        @Query("serviceType") serviceType: String? = null,
        @Query("status") status: String? = null,
        @Query("limit") limit: Int = 20,
        @Query("cursor") cursor: String? = null
    ): PaginatedResponse<OrderItemDto>

    @GET("api/orders/{id}")
    suspend fun getOrderDetails(
        @Path("id") id: String
    ): ApiResponse<OrderDto>

    @PUT("api/orders/{id}/cancel")
    suspend fun cancelOrder(
        @Path("id") id: String
    ): ApiResponse<OrderDto>

    @GET("api/orders/{id}/payment-summary")
    suspend fun getPaymentSummary(
        @Path("id") id: String
    ): ApiResponse<PaymentSummaryDto>
}
