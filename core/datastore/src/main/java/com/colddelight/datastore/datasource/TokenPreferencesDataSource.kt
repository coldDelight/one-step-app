package com.colddelight.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface TokenPreferencesDataSource {
    val token: Flow<String>

    suspend fun saveToken(token:String)
    suspend fun delToken()

}