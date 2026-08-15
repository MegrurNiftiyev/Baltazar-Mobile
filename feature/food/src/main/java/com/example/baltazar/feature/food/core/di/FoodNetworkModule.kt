package com.example.baltazar.feature.food.core.di

import com.example.baltazar.feature.food.data.datasources.remote.services.FoodApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FoodNetworkModule {

    @Provides
    @Singleton
    fun provideFoodApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): FoodApiService {
        return retrofit.create(FoodApiService::class.java)
    }
}
