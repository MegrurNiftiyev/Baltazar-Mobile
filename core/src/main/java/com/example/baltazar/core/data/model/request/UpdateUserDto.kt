package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.UserInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserDto(
    @SerialName("name") val name: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("language") val language: String? = null
)

fun UserInfo.toDto(): UpdateUserDto {
    return UpdateUserDto(
        name = name,
        phone = phone,
        region = region,
        language = language
    )
}
