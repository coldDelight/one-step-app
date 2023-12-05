package com.colddelight.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class TokenPreferencesDataSourceImpl @Inject constructor(
    @Named("token") private val dataStore: DataStore<Preferences>,
) : TokenPreferencesDataSource {
    object PreferencesKey {
        val SUPABASE_TOKEN = stringPreferencesKey("SUPABASE_TOKEN")
    }

    override val token = dataStore.data.map { preferences ->
        preferences[PreferencesKey.SUPABASE_TOKEN] ?: ""
    }


    override suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SUPABASE_TOKEN] = token
        }
    }


    override suspend fun delToken() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.SUPABASE_TOKEN] = ""
        }
    }

}

