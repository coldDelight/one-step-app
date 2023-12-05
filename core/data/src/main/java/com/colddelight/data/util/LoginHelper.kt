package com.colddelight.data.util

import kotlinx.coroutines.flow.Flow

interface LoginHelper {
    val isLogin: Flow<Boolean>
//    suspend fun isLogin(): Flow<Boolean>
}