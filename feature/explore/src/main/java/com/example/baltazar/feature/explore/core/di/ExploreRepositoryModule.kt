package com.example.baltazar.feature.explore.core.di

import com.example.baltazar.feature.explore.data.repository.ExploreRepository
import com.example.baltazar.feature.explore.domain.repository.IExploreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ExploreRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExploreRepository(
        exploreRepository: ExploreRepository
    ): IExploreRepository
}
