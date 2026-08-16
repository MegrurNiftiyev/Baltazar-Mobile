package com.example.baltazar.feature.auth.core.exception

import com.example.baltazar.core.core.exceptions.BaseApiException

sealed class AuthException(httpCode: Int, message: String) : BaseApiException(httpCode, message) {

    object InvalidCredentials : AuthException(401, "Invalid email or password")
    object ValidationError : AuthException(400, "Invalid input data format")
    object UserAlreadyExists : AuthException(409, "An account with this email already exists")
    object RateLimitExceeded : AuthException(429, "Too many requests, please try again later")

    companion object {
        val allErrors = listOf(
            InvalidCredentials,
            ValidationError,
            UserAlreadyExists,
            RateLimitExceeded
        )
    }
}
