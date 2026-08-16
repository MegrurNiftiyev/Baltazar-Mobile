package com.example.baltazar.core.data.repository

import com.example.baltazar.core.core.constants.CacheKeys
import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import com.example.baltazar.core.core.managers.CacheManager
import com.example.baltazar.core.core.managers.EncryptedCacheManager
import com.example.baltazar.core.core.managers.SessionManager
import com.example.baltazar.core.core.preferences.AppPreferences
import com.example.baltazar.core.domain.repository.ISettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository @Inject constructor(
    private val cacheManager: CacheManager,
    private val encryptedCacheManager: EncryptedCacheManager,
    private val appPreferences: AppPreferences,
    private val sessionManager: SessionManager
) : ISettingsRepository {

    override val isDarkMode: Flow<Boolean>
        get() = cacheManager.getBoolean(CacheKeys.IS_DARK_MODE, false)

    override val language: Flow<Language>
        get() = cacheManager.getString(
            CacheKeys.APP_LANGUAGE,
            appPreferences.getSavedLanguage() ?: Language.AZ.code
        ).map { Language.fromCode(it) }

    override val region: Flow<Region>
        get() = cacheManager.getString(
            CacheKeys.APP_REGION,
            appPreferences.getSavedRegion() ?: Region.AZ.code
        ).map { Region.fromCode(it) }

    override val cardViewMode: Flow<CardViewMode>
        get() = cacheManager.getString(CacheKeys.CARD_VIEW_MODE, CardViewMode.GRID.name).map { modeName ->
            try {
                CardViewMode.valueOf(modeName)
            } catch (_: Exception) {
                CardViewMode.GRID
            }
        }

    override suspend fun setDarkMode(enabled: Boolean) {
        cacheManager.setBoolean(CacheKeys.IS_DARK_MODE, enabled)
    }

    override suspend fun setLanguage(language: Language) {
        setLanguage(language.code)
    }

    override suspend fun setLanguage(languageCode: String) {
        val lang = Language.fromCode(languageCode)
        cacheManager.setString(CacheKeys.APP_LANGUAGE, lang.code)
        appPreferences.saveLanguage(lang.code)
    }

    override suspend fun setRegion(region: Region) {
        setRegion(region.code)
    }

    override suspend fun setRegion(regionCode: String) {
        val reg = Region.fromCode(regionCode)
        cacheManager.setString(CacheKeys.APP_REGION, reg.code)
        appPreferences.saveRegion(reg.code)
    }

    override suspend fun setCardViewMode(mode: CardViewMode) {
        cacheManager.setString(CacheKeys.CARD_VIEW_MODE, mode.name)
    }

    override suspend fun logout() {
        encryptedCacheManager.removeSecureKey(CacheKeys.ACCESS_TOKEN)
        encryptedCacheManager.removeSecureKey(CacheKeys.REFRESH_TOKEN)
        cacheManager.setBoolean(CacheKeys.IS_LOGIN_FINISHED, false)
        sessionManager.clear()
    }
}
