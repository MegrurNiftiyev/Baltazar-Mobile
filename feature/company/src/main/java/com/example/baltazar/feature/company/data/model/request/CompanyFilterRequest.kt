package com.example.baltazar.feature.company.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class CompanyFilterRequest(
    val serviceType: String? = null,
    val search: String? = null,
    val limit: Int? = 20,
    val cursor: String? = null
)
