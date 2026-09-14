package com.example.baltazar.feature.hotel.core.di

import com.example.baltazar.feature.hotel.data.datasources.remote.services.HotelApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HotelNetworkModule {

    @Provides
    @Singleton
    fun provideHotelApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): HotelApiService {
        return retrofit.create(HotelApiService::class.java)
    }
}
