package com.example.baltazar.feature.order.data.repository

import com.example.baltazar.feature.order.data.datasources.remote.datasources.PaymentRemoteDataSource
import com.example.baltazar.feature.order.data.model.request.AddCardRequest
import com.example.baltazar.feature.order.data.model.request.ProcessPaymentRequest
import com.example.baltazar.feature.order.data.model.request.TokenizeCardRequest
import com.example.baltazar.feature.order.domain.model.PaymentCard
import com.example.baltazar.feature.order.domain.model.PaymentTransaction
import com.example.baltazar.feature.order.domain.model.TokenizedCard
import com.example.baltazar.feature.order.domain.repository.IPaymentRepository
import javax.inject.Inject

class PaymentRepository @Inject constructor(
    private val remoteDataSource: PaymentRemoteDataSource
) : IPaymentRepository {

    private fun parseHttpError(throwable: Throwable, defaultMsg: String): Exception {
        if (throwable is retrofit2.HttpException) {
            try {
                val errorJsonStr = throwable.response()?.errorBody()?.string()
                if (!errorJsonStr.isNullOrBlank()) {
                    val jsonObject = org.json.JSONObject(errorJsonStr)
                    val msg = jsonObject.optString("message").takeIf { it.isNotBlank() }
                        ?: jsonObject.optString("errorCode").takeIf { it.isNotBlank() }
                    if (msg != null) {
                        return Exception(msg)
                    }
                }
            } catch (_: Exception) {}
        }
        return Exception(throwable.message ?: defaultMsg)
    }

    override suspend fun tokenizeCard(
        cardNumber: String,
        cardHolder: String,
        expiryMonth: String,
        expiryYear: String,
        cvv: String
    ): Result<TokenizedCard> {
        return runCatching {
            val response = remoteDataSource.tokenizeCard(
                TokenizeCardRequest(
                    cardNumber = cardNumber,
                    cardHolder = cardHolder,
                    expiryMonth = expiryMonth,
                    expiryYear = expiryYear,
                    cvv = cvv
                )
            )
            if (response.success) {
                response.toDomain()
            } else {
                throw Exception(response.message ?: response.errorCode ?: "Kart tokenizasiyası xətası")
            }
        }.recoverCatching { throw parseHttpError(it, "Kart tokenizasiyası xətası") }
    }

    override suspend fun addPaymentMethod(
        paymentMethodId: String,
        brand: String,
        last4: String,
        expiryMonth: Int,
        expiryYear: Int
    ): Result<PaymentCard> {
        return runCatching {
            val response = remoteDataSource.addCard(
                AddCardRequest(
                    paymentMethodId = paymentMethodId,
                    brand = brand,
                    last4 = last4,
                    expiryMonth = expiryMonth,
                    expiryYear = expiryYear
                )
            )
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Kart əlavə edilmədi")
            }
        }.recoverCatching { throw parseHttpError(it, "Kart əlavə edilmədi") }
    }

    override suspend fun getAllCards(): Result<List<PaymentCard>> {
        return runCatching {
            val response = remoteDataSource.getAllCards()
            val data = response.data
            if (response.success && data != null) {
                data.map { it.toDomain() }
            } else {
                throw Exception(response.message ?: "Failed to fetch payment cards")
            }
        }.recoverCatching { throw parseHttpError(it, "Failed to fetch payment cards") }
    }

    override suspend fun processPayment(
        orderId: String,
        paymentMethodId: String
    ): Result<PaymentTransaction> {
        return runCatching {
            val response = remoteDataSource.processPayment(
                orderId = orderId,
                request = ProcessPaymentRequest(paymentMethodId = paymentMethodId)
            )
            val data = response.data
            if (response.success && data != null) {
                data.toDomain()
            } else {
                throw Exception(response.message ?: "Failed to process payment")
            }
        }.recoverCatching { throw parseHttpError(it, "Failed to process payment") }
    }
}
