package com.example.baltazar.core.managers

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.baltazar.core.constants.CacheKeys
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CacheManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = _root_ide_package_.com.example.baltazar.core.constants.CacheKeys.APP_CACHE)


    fun getBoolean(key: String, default: Boolean = false): Flow<Boolean> =
        context.dataStore.data.map { it[booleanPreferencesKey(key)] ?: default }

    suspend fun setBoolean(key: String, value: Boolean) {
        context.dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    fun getString(key: String, default: String = ""): Flow<String> =
        context.dataStore.data.map { it[stringPreferencesKey(key)] ?: default }

    suspend fun setString(key: String, value: String) {
        context.dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    fun getInt(key: String, default: Int = 0): Flow<Int> =
        context.dataStore.data.map { it[intPreferencesKey(key)] ?: default }

    suspend fun setInt(key: String, value: Int) {
        context.dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    suspend fun removeKey(key: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
        }
    }

    suspend fun clearAllCache() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}