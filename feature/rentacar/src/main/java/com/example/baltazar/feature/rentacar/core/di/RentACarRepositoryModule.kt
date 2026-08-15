package com.example.baltazar.feature.rentacar.core.di

import com.example.baltazar.feature.rentacar.data.repository.RentACarRepository
import com.example.baltazar.feature.rentacar.domain.repository.IRentACarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RentACarRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRentACarRepository(
        rentACarRepository: RentACarRepository
    ): IRentACarRepository
}
