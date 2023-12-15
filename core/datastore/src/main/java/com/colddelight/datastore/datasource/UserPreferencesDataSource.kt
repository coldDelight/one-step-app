package com.colddelight.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class UserPreferencesDataSource @Inject constructor(
    @Named("user") private val dataStore: DataStore<Preferences>,
) {

    object PreferencesKey {
        val CURRENT_ROUTINE_ID = stringPreferencesKey("CURRENT_ROUTINE_ID")
        val CURRENT_WEIGHT = stringPreferencesKey("CURRENT_WEIGHT")
    }

    val currentRoutineId = dataStore.data.map { preferences ->
        preferences[PreferencesKey.CURRENT_ROUTINE_ID]?.toInt() ?: 1
    }

    val currentWeight = dataStore.data.map { preferences ->
        preferences[PreferencesKey.CURRENT_WEIGHT]?.toInt() ?: 0
    }

    suspend fun saveWeight(weight: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENT_WEIGHT] = weight.toString()
        }
    }

    suspend fun delWeight() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENT_WEIGHT] = "0"
        }
    }

    suspend fun saveCurrentRoutineId(id: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENT_ROUTINE_ID] = id.toString()
        }
    }

    suspend fun delCurrentRoutineId() {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.CURRENT_ROUTINE_ID] = "1"
        }
    }


}