package com.example.baltazar.feature.travel.di

import com.example.baltazar.feature.travel.data.source.remote.TravelApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TravelNetworkModule {
    @Provides
    @Singleton
    fun provideTravelApi(retrofit: Retrofit): TravelApi {
        return retrofit.create(TravelApi::class.java)
    }
}
