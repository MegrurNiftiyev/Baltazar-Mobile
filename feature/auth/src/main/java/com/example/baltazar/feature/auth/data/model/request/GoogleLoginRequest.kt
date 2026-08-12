package com.example.baltazar.feature.auth.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    @SerialName("idToken") val idToken: String
)
