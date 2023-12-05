package com.colddelight.data.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val tokenDataSource: TokenPreferencesDataSource
) : LoginHelper {
    override val isLogin: Flow<Boolean>
        get() {
            TODO()
        }


}