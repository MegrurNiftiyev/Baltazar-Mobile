package com.example.baltazar.feature.explore.core.di

import com.example.baltazar.feature.explore.data.datasources.remote.services.ExploreApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExploreNetworkModule {

    @Provides
    @Singleton
    fun provideExploreApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): ExploreApiService {
        return retrofit.create(ExploreApiService::class.java)
    }
}

