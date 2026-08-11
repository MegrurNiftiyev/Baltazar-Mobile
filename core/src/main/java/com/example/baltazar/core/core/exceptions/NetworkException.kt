package com.example.baltazar.core.core.exceptions

sealed class NetworkException(message: String) : Exception(message) {
    object NoInternet : NetworkException("No internet connection")
    class ServerError(val httpCode: Int, override val message: String = "Server error occurred") : NetworkException(message)
}

