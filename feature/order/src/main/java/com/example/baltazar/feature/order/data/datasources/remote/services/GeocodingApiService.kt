package com.example.baltazar.feature.order.data.datasources.remote.services

import com.example.baltazar.feature.order.data.model.dto.NominatimReverseResultDto
import com.example.baltazar.feature.order.data.model.dto.NominatimSearchResultDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Url

interface GeocodingApiService {

    @Headers("User-Agent: BaltazarMobileApp/1.0")
    @GET
    suspend fun searchLocation(
        @Query("q") query: String,
        @Url url: String = "https://nominatim.openstreetmap.org/search",
        @Query("format") format: String = "json",
        @Query("addressdetails") addressDetails: Int = 1,
        @Query("limit") limit: Int = 5
    ): List<NominatimSearchResultDto>

    @Headers("User-Agent: BaltazarMobileApp/1.0")
    @GET
    suspend fun reverseGeocode(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Url url: String = "https://nominatim.openstreetmap.org/reverse",
        @Query("format") format: String = "json"
    ): NominatimReverseResultDto
}
