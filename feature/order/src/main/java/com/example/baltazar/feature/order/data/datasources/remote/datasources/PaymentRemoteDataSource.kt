package com.example.baltazar.feature.order.data.datasources.remote.datasources

import com.example.baltazar.core.data.model.response.ApiResponse
import com.example.baltazar.feature.order.data.datasources.remote.services.PaymentApiService
import com.example.baltazar.feature.order.data.datasources.remote.services.PaymentGatewayApiService
import com.example.baltazar.feature.order.data.model.dto.PaymentCardDto
import com.example.baltazar.feature.order.data.model.dto.PaymentTransactionDto
import com.example.baltazar.feature.order.data.model.dto.TokenizeCardResponseDto
import com.example.baltazar.feature.order.data.model.request.AddCardRequest
import com.example.baltazar.feature.order.data.model.request.ProcessPaymentRequest
import com.example.baltazar.feature.order.data.model.request.TokenizeCardRequest
import javax.inject.Inject

class PaymentRemoteDataSource @Inject constructor(
    private val paymentApiService: PaymentApiService,
    private val paymentGatewayApiService: PaymentGatewayApiService
) {
    suspend fun tokenizeCard(request: TokenizeCardRequest): TokenizeCardResponseDto {
        return paymentGatewayApiService.tokenizeCard(request)
    }

    suspend fun addCard(request: AddCardRequest): ApiResponse<PaymentCardDto> {
        return paymentApiService.addCard(request)
    }

    suspend fun getAllCards(): ApiResponse<List<PaymentCardDto>> {
        return paymentApiService.getAllCards()
    }

    suspend fun processPayment(orderId: String, request: ProcessPaymentRequest): ApiResponse<PaymentTransactionDto> {
        return paymentApiService.processPayment(orderId, request)
    }
}
