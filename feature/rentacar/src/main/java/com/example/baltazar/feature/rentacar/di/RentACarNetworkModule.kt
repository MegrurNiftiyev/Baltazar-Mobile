package com.example.baltazar.feature.rentacar.di

import com.example.baltazar.feature.rentacar.data.source.remote.RentACarApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RentACarNetworkModule {
    @Provides
    @Singleton
    fun provideRentACarApi(retrofit: Retrofit): RentACarApi {
        return retrofit.create(RentACarApi::class.java)
    }
}
