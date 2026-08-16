package com.example.baltazar.core.core.extensions

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.baltazar.core.core.enums.Language

fun Context.changeLanguage(languageCode: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val localeManager = this.getSystemService(Context.LOCALE_SERVICE) as? LocaleManager
        localeManager?.applicationLocales = LocaleList.forLanguageTags(languageCode)
    } else {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
    }
}

fun Context.changeLanguage(language: Language) {
    changeLanguage(language.code)
}

val Context.getCurrentLanguage: String
    get() {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val localeManager = this.getSystemService(Context.LOCALE_SERVICE) as? LocaleManager
            val appLocales = localeManager?.applicationLocales?.toLanguageTags()
            if (!appLocales.isNullOrEmpty()) {
                appLocales
            } else {
                LocaleList.getDefault()[0]?.language ?: "az"
            }
        } else {
            val appLocales = AppCompatDelegate.getApplicationLocales().toLanguageTags()
            if (appLocales.isNotEmpty()) {
                appLocales
            } else {
                LocaleListCompat.getDefault()[0]?.language ?: "az"
            }
        }
    }

val Context.currentLanguage: Language
    get() = Language.fromCode(getCurrentLanguage)
