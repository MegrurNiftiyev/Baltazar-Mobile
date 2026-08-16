package com.example.baltazar.core.core.di

import com.example.baltazar.core.BuildConfig
import com.example.baltazar.core.core.interceptors.AuthInterceptor
import com.example.baltazar.core.core.interceptors.LocalizationInterceptor
import com.example.baltazar.core.core.interceptors.TokenAuthenticator
import com.example.baltazar.core.data.datasources.remote.services.RefreshTokenApiService
import com.example.baltazar.core.data.datasources.remote.services.UserApiService
import com.example.baltazar.core.data.datasources.remote.services.WishlistApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        explicitNulls = false
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Provides
    @Singleton
    @Named("AuthOkHttpClient")
    fun provideAuthOkHttpClient(
        localizationInterceptor: LocalizationInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(localizationInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("NormalOkHttpClient")
    fun provideNormalOkHttpClient(
        authInterceptor: AuthInterceptor,
        localizationInterceptor: LocalizationInterceptor,
        tokenAuthenticator: TokenAuthenticator,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(localizationInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(tokenAuthenticator)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @Named("AuthRetrofit")
    fun provideAuthRetrofit(
        @Named("AuthOkHttpClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @Named("AppRetrofit")
    fun provideAppRetrofit(
        @Named("NormalOkHttpClient") okHttpClient: OkHttpClient,
        json: Json
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideRefreshTokenApiService(
        @Named("AuthRetrofit") retrofit: Retrofit
    ): RefreshTokenApiService {
        return retrofit.create(RefreshTokenApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): UserApiService {
        return retrofit.create(UserApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideWishlistApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): WishlistApiService {
        return retrofit.create(WishlistApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideReviewApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): com.example.baltazar.core.data.datasources.remote.services.ReviewApiService {
        return retrofit.create(com.example.baltazar.core.data.datasources.remote.services.ReviewApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideIncludedServiceApiService(
        @Named("AppRetrofit") retrofit: Retrofit
    ): com.example.baltazar.core.data.datasources.remote.services.IncludedServiceApiService {
        return retrofit.create(com.example.baltazar.core.data.datasources.remote.services.IncludedServiceApiService::class.java)
    }
}
