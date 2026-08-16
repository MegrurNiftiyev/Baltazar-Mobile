package com.example.baltazar.feature.travel.core.di

import com.example.baltazar.feature.travel.data.repository.TravelRepository
import com.example.baltazar.feature.travel.domain.repository.ITravelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TravelRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTravelRepository(
        travelRepository: TravelRepository
    ): ITravelRepository
}
