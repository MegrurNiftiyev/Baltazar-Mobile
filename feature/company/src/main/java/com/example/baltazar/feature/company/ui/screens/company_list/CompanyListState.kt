package com.example.baltazar.feature.company.ui.screens.company_list

import com.example.baltazar.core.core.enums.ServiceType
import com.example.baltazar.feature.company.domain.model.Company

data class CompanyListState(
    val companies: List<Company> = emptyList(),
    val serviceType: ServiceType? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val nextCursor: String? = null,
    val hasMore: Boolean = false
)
