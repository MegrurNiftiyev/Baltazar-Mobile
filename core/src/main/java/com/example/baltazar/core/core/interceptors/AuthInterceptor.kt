package com.example.baltazar.core.core.interceptors

import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.managers.EncryptedCacheManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val encryptedCacheManager: EncryptedCacheManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()

        val token = encryptedCacheManager.getSecureString(CacheKeys.ACCESS_TOKEN)

        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}

