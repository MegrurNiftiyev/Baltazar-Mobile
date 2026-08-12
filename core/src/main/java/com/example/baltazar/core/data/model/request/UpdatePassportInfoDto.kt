package com.example.baltazar.core.data.model.request

import com.example.baltazar.core.domain.model.PassportInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePassportInfoDto(
    @SerialName("passportNumber") val passportNumber: String,
    @SerialName("citizenship") val citizenship: String,
    @SerialName("issueDate") val issueDate: String? = null,
    @SerialName("expiryDate") val expiryDate: String? = null
)

fun PassportInfo.toDto(): UpdatePassportInfoDto {
    return UpdatePassportInfoDto(
        passportNumber = passportNumber,
        citizenship = citizenship,
        issueDate = issueDate,
        expiryDate = expiryDate
    )
}
