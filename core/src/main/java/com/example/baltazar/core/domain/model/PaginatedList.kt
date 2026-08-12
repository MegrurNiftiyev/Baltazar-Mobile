package com.example.baltazar.core.domain.model

data class PaginatedList<T>(
    val items: List<T>,
    val pagination: PaginationInfo
)
