package com.example.baltazar.core.core.preferences

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("baltazar_app_prefs", Context.MODE_PRIVATE)

    fun saveLanguage(language: String) {
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }

    fun getSavedLanguage(): String? {
        return prefs.getString(KEY_LANGUAGE, null)
    }

    fun saveRegion(region: String) {
        prefs.edit().putString(KEY_REGION, region).apply()
    }

    fun getSavedRegion(): String? {
        return prefs.getString(KEY_REGION, null)
    }

    companion object {
        private const val KEY_LANGUAGE = "key_saved_language"
        private const val KEY_REGION = "key_saved_region"
    }
}
