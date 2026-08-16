package com.example.baltazar.feature.explore.data.datasources.remote.services

import com.example.baltazar.feature.explore.data.model.response.BannerResponse
import com.example.baltazar.feature.explore.data.model.response.ExploreResponse
import retrofit2.http.GET

interface ExploreApiService {
    @GET("api/home/banner")
    suspend fun getBanners(): BannerResponse

    @GET("api/home/explore")
    suspend fun getExploreSections(): ExploreResponse
}
