package com.example.baltazar.core.core.di

import com.example.baltazar.core.data.repository.UserRepositoryImpl
import com.example.baltazar.core.domain.repository.IUserRepository
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
}
