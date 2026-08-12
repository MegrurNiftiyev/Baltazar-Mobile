package com.example.baltazar.core.domain.model

data class UserInfo(
    val name: String,
    val phone: String? = null,
    val region: String? = null,
    val language: String = "en"
)
