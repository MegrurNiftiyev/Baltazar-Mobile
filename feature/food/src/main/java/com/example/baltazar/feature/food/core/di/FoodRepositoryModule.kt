package com.example.baltazar.feature.food.core.di

import com.example.baltazar.feature.food.data.repository.FoodRepository
import com.example.baltazar.feature.food.domain.repository.IFoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FoodRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFoodRepository(
        foodRepository: FoodRepository
    ): IFoodRepository
}
