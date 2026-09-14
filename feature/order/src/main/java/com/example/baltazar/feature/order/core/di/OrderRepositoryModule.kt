package com.example.baltazar.feature.order.core.di

import com.example.baltazar.feature.order.data.repository.LocationRepository
import com.example.baltazar.feature.order.data.repository.OrderRepository
import com.example.baltazar.feature.order.data.repository.PaymentRepository
import com.example.baltazar.feature.order.domain.repository.ILocationRepository
import com.example.baltazar.feature.order.domain.repository.IOrderRepository
import com.example.baltazar.feature.order.domain.repository.IPaymentRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepository: OrderRepository
    ): IOrderRepository

    @Binds
    @Singleton
    abstract fun bindPaymentRepository(
        paymentRepository: PaymentRepository
    ): IPaymentRepository

    @Binds
    @Singleton
    abstract fun bindLocationRepository(
        locationRepository: LocationRepository
    ): ILocationRepository
}
