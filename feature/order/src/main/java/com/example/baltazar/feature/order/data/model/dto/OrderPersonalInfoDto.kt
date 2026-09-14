package com.example.baltazar.feature.order.data.model.dto

import com.example.baltazar.feature.order.domain.model.OrderDriverLicense
import com.example.baltazar.feature.order.domain.model.OrderPassportInfo
import com.example.baltazar.feature.order.domain.model.OrderPersonalInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderPersonalInfoDto(
    @SerialName("name") val name: String? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("dateOfBirth") val dateOfBirth: String? = null,
    @SerialName("address") val address: String? = null,
    @SerialName("idNumber") val idNumber: String? = null
) {
    fun toDomain(): OrderPersonalInfo {
        return OrderPersonalInfo(
            name = name.orEmpty(),
            phone = phone.orEmpty(),
            dateOfBirth = dateOfBirth,
            address = address,
            idNumber = idNumber
        )
    }
}

@Serializable
data class OrderDriverLicenseDto(
    @SerialName("licenseNumber") val licenseNumber: String? = null,
    @SerialName("expiryDate") val expiryDate: String? = null
) {
    fun toDomain(): OrderDriverLicense {
        return OrderDriverLicense(
            licenseNumber = licenseNumber.orEmpty(),
            expiryDate = expiryDate.orEmpty()
        )
    }
}

@Serializable
data class OrderPassportInfoDto(
    @SerialName("passportNumber") val passportNumber: String? = null,
    @SerialName("expiryDate") val expiryDate: String? = null
) {
    fun toDomain(): OrderPassportInfo {
        return OrderPassportInfo(
            passportNumber = passportNumber.orEmpty(),
            expiryDate = expiryDate.orEmpty()
        )
    }
}
