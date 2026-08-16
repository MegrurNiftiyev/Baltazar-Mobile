package com.example.baltazar.feature.explore.domain.repository

import com.example.baltazar.feature.explore.domain.model.BannerItem
import com.example.baltazar.feature.explore.domain.model.ExploreSection

interface IExploreRepository {
    suspend fun getBanners(): Result<List<BannerItem>>
    suspend fun getExploreSections(): Result<List<ExploreSection>>
}
