package com.example.baltazar.core.core.interceptors

import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.managers.CacheManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationInterceptor @Inject constructor(
    private val cacheManager: CacheManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val defaultLang = Locale.getDefault().language
        val defaultReg = Locale.getDefault().country

        val language = runBlocking {
            cacheManager.getString(CacheKeys.APP_LANGUAGE, defaultLang).first().ifBlank { defaultLang }
        }
        val region = runBlocking {
            cacheManager.getString(CacheKeys.APP_REGION, defaultReg).first().ifBlank { defaultReg }
        }

        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Accept-Language", language)
            .header("X-Region", region)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
