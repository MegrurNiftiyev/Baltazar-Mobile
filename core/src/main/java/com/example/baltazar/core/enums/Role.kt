package com.example.baltazar.core.enums

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Role {
    @SerialName("USER") USER,
    @SerialName("ADMIN") ADMIN,
    @SerialName("GUEST") GUEST,
    @SerialName("UNKNOWN") UNKNOWN
}
