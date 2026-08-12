package com.example.baltazar.core.core.network

import com.example.baltazar.core.core.exceptions.BaseApiException
import com.example.baltazar.core.core.exceptions.NetworkException
import retrofit2.HttpException
import java.io.IOException

suspend inline fun <T> executeRequest(
    crossinline apiCall: suspend () -> T,
    expectedErrors: List<BaseApiException> = emptyList()
): T {
    return try {
        apiCall()
    } catch (e: HttpException) {
        val code = e.code()

        val matchedException = expectedErrors.find { it.httpCode == code }

        if (matchedException != null) {
            throw matchedException
        }

        throw NetworkException.ServerError(code, e.message())
    } catch (e: IOException) {
        throw NetworkException.NoInternet
    } catch (e: Exception) {
        throw Exception(e.message ?: "An unexpected error occurred")
    }
}

