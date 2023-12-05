package com.colddelight.home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import com.colddelight.network.SupabaseClient.client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.gotrue


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenDataSource: TokenPreferencesDataSource
) : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    val token: Flow<String> = tokenDataSource.token


    fun updateToken(token: String) {
        viewModelScope.launch {
            tokenDataSource.saveToken(token)
        }
    }

    fun delToken() {
        viewModelScope.launch {
            tokenDataSource.delToken()
        }
    }

    fun checkGoogleLoginStatus(result: NativeSignInResult) {
        _userState.value = UserState.Loading
        when (result) {
            is NativeSignInResult.Success -> {
                Log.e("TAG", "성공이요: $result", )
                client.gotrue.currentAccessTokenOrNull()
                updateToken(client.gotrue.currentAccessTokenOrNull()?:"0")
                _userState.value = UserState.Success("Logged in via Google")
            }
            is NativeSignInResult.ClosedByUser -> {}
            is NativeSignInResult.Error -> {
                val message = result.message
                _userState.value = UserState.Error(message)
            }
            is NativeSignInResult.NetworkError -> {
                val message = result.message
                _userState.value = UserState.Error(message)
            }
        }
    }


}