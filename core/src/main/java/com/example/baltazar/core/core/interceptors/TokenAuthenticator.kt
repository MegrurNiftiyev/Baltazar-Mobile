package com.example.baltazar.core.core.interceptors

import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.data.datasources.remote.RefreshTokenDataSource
import com.example.baltazar.core.data.model.request.RefreshTokenRequest
import com.example.baltazar.core.core.managers.EncryptedCacheManager
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val refreshTokenDataSource: Provider<RefreshTokenDataSource>,
    private val encryptedCacheManager: EncryptedCacheManager
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (getRetryCount(response) >= 2) {
            return null
        }

        synchronized(this) {
            val oldToken = response.request.header("Authorization")?.removePrefix("Bearer ")
            val savedToken = encryptedCacheManager.getSecureString(CacheKeys.ACCESS_TOKEN)

            if (savedToken != null && savedToken != oldToken) {
                return response.request.newBuilder()
                    .header("Authorization", "Bearer $savedToken")
                    .build()
            }

            val refreshToken = encryptedCacheManager.getSecureString(CacheKeys.REFRESH_TOKEN) ?: return null

            return runBlocking {
                try {
                    val refreshResponse = refreshTokenDataSource.get().refresh(RefreshTokenRequest(refreshToken))
                    val tokenData = refreshResponse.data ?: return@runBlocking null

                    encryptedCacheManager.saveSecureString(CacheKeys.ACCESS_TOKEN, tokenData.accessToken)
                    encryptedCacheManager.saveSecureString(CacheKeys.REFRESH_TOKEN, tokenData.refreshToken)

                    response.request.newBuilder()
                        .header("Authorization", "Bearer ${tokenData.accessToken}")
                        .build()
                } catch (e: Exception) {
                    encryptedCacheManager.removeSecureKey(CacheKeys.ACCESS_TOKEN)
                    encryptedCacheManager.removeSecureKey(CacheKeys.REFRESH_TOKEN)
                    null
                }
            }
        }
    }

    private fun getRetryCount(response: Response): Int {
        var count = 1
        var prior = response.priorResponse
        while (prior != null) {
            count++
            prior = prior.priorResponse
        }
        return count
    }
}

