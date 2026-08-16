package com.example.baltazar.feature.company.core.di

import com.example.baltazar.feature.company.data.repository.CompanyRepository
import com.example.baltazar.feature.company.domain.repository.ICompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CompanyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyRepository(
        impl: CompanyRepository
    ): ICompanyRepository
}
