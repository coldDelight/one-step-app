package com.colddelight.data.util

import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LoginHelperImpl @Inject constructor(
    private val tokenDataSource: TokenPreferencesDataSource
) : LoginHelper {

    //    override suspend fun isLogin(): Flow<Boolean> {
//        val tokenFlow = tokenDataSource.token.first()
//        return if(tokenFlow.isNotEmpty()){
//            flow { emit(true) }
//        } else flow { emit(false) }
//    }
    override val isLogin: Flow<Boolean>
        get() = tokenDataSource.token.map {
            it.isNotEmpty()
        }
//    override val isLogin: Flow<Boolean> = callbackFlow {
//        channel.trySend(true)
//
//    }.conflate()
//        get() {
//            return flow {
//                emit(true)
//                tokenDataSource.token.collectLatest {
//                    emit(it.isNotEmpty())
//                    Log.e("TAG", "${it.isNotEmpty()} 이다: ", )
//
//                    Log.e("TAG", "지금토큰 $it 이다: ", )
//                }
//            }
//        }
}