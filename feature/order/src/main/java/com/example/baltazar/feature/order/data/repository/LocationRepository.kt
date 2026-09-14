package com.example.baltazar.feature.order.data.repository

import com.example.baltazar.feature.order.data.datasources.remote.services.GeocodingApiService
import com.example.baltazar.feature.order.domain.model.LocationSearchResult
import com.example.baltazar.feature.order.domain.repository.ILocationRepository
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val geocodingApiService: GeocodingApiService
) : ILocationRepository {

    override suspend fun searchLocation(query: String): Result<List<LocationSearchResult>> {
        return runCatching {
            val response = geocodingApiService.searchLocation(query)
            response.mapNotNull { item ->
                val lat = item.lat?.toDoubleOrNull()
                val lng = item.lon?.toDoubleOrNull()
                val name = item.displayName
                if (lat != null && lng != null && !name.isNullOrBlank()) {
                    LocationSearchResult(addressName = name, lat = lat, lng = lng)
                } else null
            }
        }
    }

    override suspend fun reverseGeocode(lat: Double, lng: Double): Result<LocationSearchResult> {
        return runCatching {
            val response = geocodingApiService.reverseGeocode(lat, lng)
            val name = response.displayName ?: "$lat, $lng"
            LocationSearchResult(addressName = name, lat = lat, lng = lng)
        }
    }
}
