package com.example.baltazar.core.core.di

import com.example.baltazar.core.data.repository.SettingsRepository
import com.example.baltazar.core.data.repository.UserRepositoryImpl
import com.example.baltazar.core.data.repository.WishlistRepository
import com.example.baltazar.core.domain.repository.ISettingsRepository
import com.example.baltazar.core.domain.repository.IUserRepository
import com.example.baltazar.core.domain.repository.IWishlistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): IUserRepository

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        wishlistRepository: WishlistRepository
    ): IWishlistRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepository: SettingsRepository
    ): ISettingsRepository

    @Binds
    @Singleton
    abstract fun bindReviewRepository(
        reviewRepository: com.example.baltazar.core.data.repository.ReviewRepository
    ): com.example.baltazar.core.domain.repository.IReviewRepository

    @Binds
    @Singleton
    abstract fun bindIncludedServiceRepository(
        includedServiceRepository: com.example.baltazar.core.data.repository.IncludedServiceRepository
    ): com.example.baltazar.core.domain.repository.IIncludedServiceRepository
}
