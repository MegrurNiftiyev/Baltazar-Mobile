package com.example.baltazar.core.core.interceptors

import com.example.baltazar.core.core.preferences.AppPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalizationInterceptor @Inject constructor(
    private val appPreferences: AppPreferences
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val language = appPreferences.getSavedLanguage() ?: Locale.getDefault().language
        val region = appPreferences.getSavedRegion() ?: Locale.getDefault().country

        val originalRequest = chain.request()
        val modifiedRequest = originalRequest.newBuilder()
            .header("Accept-Language", language)
            .header("X-Region", region)
            .build()

        return chain.proceed(modifiedRequest)
    }
}
