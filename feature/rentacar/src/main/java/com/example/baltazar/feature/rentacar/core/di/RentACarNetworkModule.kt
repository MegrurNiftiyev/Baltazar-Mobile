package com.example.baltazar.feature.rentacar.core.di

import com.example.baltazar.feature.rentacar.data.datasources.remote.services.RentACarApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RentACarNetworkModule {

    @Provides
    @Singleton
    fun provideRentACarApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): RentACarApiService {
        return retrofit.create(RentACarApiService::class.java)
    }
}
