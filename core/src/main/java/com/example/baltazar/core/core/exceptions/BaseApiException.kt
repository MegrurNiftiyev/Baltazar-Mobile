package com.example.baltazar.core.core.exceptions

abstract class BaseApiException(val httpCode: Int, message: String) : Exception(message)

