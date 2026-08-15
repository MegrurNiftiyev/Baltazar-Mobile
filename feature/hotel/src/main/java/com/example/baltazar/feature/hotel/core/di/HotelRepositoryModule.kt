package com.example.baltazar.feature.hotel.core.di

import com.example.baltazar.feature.hotel.data.repository.HotelRepository
import com.example.baltazar.feature.hotel.domain.repository.IHotelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HotelRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHotelRepository(
        hotelRepository: HotelRepository
    ): IHotelRepository
}
