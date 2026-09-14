package com.example.baltazar.feature.order.data.datasources.remote.services

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.feature.order.data.model.dto.PaymentCardDto
import com.example.baltazar.feature.order.data.model.dto.PaymentTransactionDto
import com.example.baltazar.feature.order.data.model.request.AddCardRequest
import com.example.baltazar.feature.order.data.model.request.ProcessPaymentRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {

    @GET("api/payment/all-cards")
    suspend fun getAllCards(): ApiResponse<List<PaymentCardDto>>

    @POST("api/payment/add-card")
    suspend fun addCard(
        @Body request: AddCardRequest
    ): ApiResponse<PaymentCardDto>

    @POST("api/payment/pay/{id}")
    suspend fun processPayment(
        @Path("id") orderId: String,
        @Body request: ProcessPaymentRequest
    ): ApiResponse<PaymentTransactionDto>
}
