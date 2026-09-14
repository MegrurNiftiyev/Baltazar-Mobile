package com.example.baltazar.feature.order.data.datasources.remote.services

import com.example.baltazar.feature.order.data.model.dto.TokenizeCardResponseDto
import com.example.baltazar.feature.order.data.model.request.TokenizeCardRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface PaymentGatewayApiService {

    @POST("api/payments/methods")
    suspend fun tokenizeCard(
        @Body request: TokenizeCardRequest
    ): TokenizeCardResponseDto
}
