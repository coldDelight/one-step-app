package com.colddelight.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
//    private val tokenDataSource: TokenPreferencesDataSource,
    private val repository: ExerciseRepository,

    ) : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    init {
        viewModelScope.launch{
//            repository.addTmp()

        }
    }

//    val token: Flow<String> = tokenDataSource.token


//    fun updateToken(token: String) {
//        viewModelScope.launch {
//            tokenDataSource.saveToken(token)
//        }
//    }

//    fun getUser() {
//        viewModelScope.launch {
//            Log.e("TAG", "getUser: ${UserDataSourceImpl().getUserInfo()}")
//        }
//    }

//    fun delToken() {
//        viewModelScope.launch {
//            tokenDataSource.delToken()
//        }
//    }

//    fun checkGoogleLoginStatus(result: NativeSignInResult) {
//        _userState.value = UserState.Loading
//        when (result) {
//            is NativeSignInResult.Success -> {
//                client.gotrue.currentAccessTokenOrNull()
//                updateToken(client.gotrue.currentAccessTokenOrNull() ?: "0")
//                _userState.value = UserState.Success("Logged in via Google")
//            }
//
//            is NativeSignInResult.ClosedByUser -> {}
//            is NativeSignInResult.Error -> {
//                val message = result.message
//                _userState.value = UserState.Error(message)
//            }
//
//            is NativeSignInResult.NetworkError -> {
//                val message = result.message
//                _userState.value = UserState.Error(message)
//            }
//        }
//    }
}


