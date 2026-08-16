package com.example.baltazar.feature.explore.core.exception

import com.example.baltazar.core.core.exceptions.BaseApiException

sealed class ExploreException(httpCode: Int, message: String) : BaseApiException(httpCode, message) {
    object NotFound : ExploreException(404, "Requested resource not found")
    object ServerError : ExploreException(500, "Internal server error")
    object Unknown : ExploreException(0, "An unknown error occurred")

    companion object {
        val allErrors = listOf(
            NotFound,
            ServerError
        )
    }
}
