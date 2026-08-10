package com.example.baltazar.feature.auth.domain.model

import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region

data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String = "",
    val region: Region = Region.AZ,
    val language: Language = Language.AZ
)
