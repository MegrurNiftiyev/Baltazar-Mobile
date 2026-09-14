package com.example.baltazar.feature.order.core.di

import com.example.baltazar.core.BuildConfig
import com.example.baltazar.feature.order.data.datasources.remote.services.GeocodingApiService
import com.example.baltazar.feature.order.data.datasources.remote.services.OrderApiService
import com.example.baltazar.feature.order.data.datasources.remote.services.PaymentApiService
import com.example.baltazar.feature.order.data.datasources.remote.services.PaymentGatewayApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OrderNetworkModule {

    private val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideOrderApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): OrderApiService {
        return retrofit.create(OrderApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePaymentApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): PaymentApiService {
        return retrofit.create(PaymentApiService::class.java)
    }

    @Provides
    @Singleton
    @Named("PaymentGatewayRetrofit")
    fun providePaymentGatewayRetrofit(
        @Named("NormalOkHttpClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.PAYMENT_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun providePaymentGatewayApiService(
        @Named("PaymentGatewayRetrofit") retrofit: Retrofit
    ): PaymentGatewayApiService {
        return retrofit.create(PaymentGatewayApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGeocodingApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): GeocodingApiService {
        return retrofit.create(GeocodingApiService::class.java)
    }
}
