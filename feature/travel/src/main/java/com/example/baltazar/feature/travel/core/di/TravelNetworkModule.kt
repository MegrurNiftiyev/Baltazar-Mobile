package com.example.baltazar.feature.travel.core.di

import com.example.baltazar.feature.travel.data.datasources.remote.services.TravelApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TravelNetworkModule {

    @Provides
    @Singleton
    fun provideTravelApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): TravelApiService {
        return retrofit.create(TravelApiService::class.java)
    }
}
