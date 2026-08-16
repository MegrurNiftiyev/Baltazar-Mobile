package com.example.baltazar.feature.company.ui.screens.company_detail

import com.example.baltazar.core.core.utils.SnackbarMessage
import com.example.baltazar.feature.company.domain.model.CompanyDetail
import com.example.baltazar.feature.company.domain.model.RelatedItem

data class CompanyDetailState(
    val company: CompanyDetail? = null,
    val relatedItems: List<RelatedItem> = emptyList(),
    val isLoading: Boolean = false,
    val isRelatedItemsLoading: Boolean = false,
    val isFavorite: Boolean = false,
    val error: String? = null,
    val userMessage: SnackbarMessage? = null
)
