package com.example.baltazar.feature.company.core.di

import com.example.baltazar.feature.company.data.datasources.remote.services.CompanyApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CompanyNetworkModule {

    @Provides
    @Singleton
    fun provideCompanyApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): CompanyApiService {
        return retrofit.create(CompanyApiService::class.java)
    }
}
