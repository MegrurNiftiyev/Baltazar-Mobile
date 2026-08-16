package com.example.baltazar.feature.auth.data.model.dto

import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.feature.auth.domain.model.User
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("email") val email: String,
    @SerialName("phone") val phone: String? = null,
    @SerialName("region") val region: String? = null,
    @SerialName("language") val language: String? = null
) {
    fun toDomain(): User {
        val parsedRegion = region?.let { r ->
            try { Region.valueOf(r.uppercase()) } catch (e: Exception) { Region.AZ }
        } ?: Region.AZ

        val parsedLanguage = language?.let { l ->
            Language.entries.firstOrNull { it.code.equals(l, ignoreCase = true) }
                ?: try { Language.valueOf(l.uppercase()) } catch (e: Exception) { Language.AZ }
        } ?: Language.AZ

        return User(
            id = id,
            name = name,
            email = email,
            phone = phone ?: "",
            region = parsedRegion,
            language = parsedLanguage
        )
    }
}
