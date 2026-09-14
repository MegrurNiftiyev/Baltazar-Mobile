package com.example.baltazar.feature.order.domain.repository

import com.example.baltazar.feature.order.domain.model.LocationSearchResult

interface ILocationRepository {
    suspend fun searchLocation(query: String): Result<List<LocationSearchResult>>
    suspend fun reverseGeocode(lat: Double, lng: Double): Result<LocationSearchResult>
}
