package com.example.baltazar.core.domain.repository

import com.example.baltazar.core.core.enums.CardViewMode
import com.example.baltazar.core.core.enums.Language
import com.example.baltazar.core.core.enums.Region
import kotlinx.coroutines.flow.Flow

interface ISettingsRepository {
    val isDarkMode: Flow<Boolean>
    val language: Flow<Language>
    val region: Flow<Region>
    val cardViewMode: Flow<CardViewMode>

    suspend fun setDarkMode(enabled: Boolean)
    suspend fun setLanguage(language: Language)
    suspend fun setLanguage(languageCode: String)
    suspend fun setRegion(region: Region)
    suspend fun setRegion(regionCode: String)
    suspend fun setCardViewMode(mode: CardViewMode)
    suspend fun logout()
}
