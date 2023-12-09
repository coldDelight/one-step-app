package com.colddelight.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.data.repository.ExerciseRepository
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasource.UserDataSource
import com.colddelight.network.datasourceImpl.UserDataSourceImpl
import com.colddelight.network.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import io.github.jan.supabase.compose.auth.composable.NativeSignInResult
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenDataSource: TokenPreferencesDataSource,
    private val exerciseRepository: ExerciseRepository
//    private val userDataSource: UserDataSource
) : ViewModel() {
    private val _userState = mutableStateOf<UserState>(UserState.Loading)
    val userState: State<UserState> = _userState

    val token: Flow<String> = tokenDataSource.token


    private val _exerciseState = MutableStateFlow<ExerciseUiState>(ExerciseUiState.Loading)
    val exerciseState: StateFlow<ExerciseUiState> = _exerciseState

    init {
        viewModelScope.launch {
            exerciseRepository.getExerciseResourcesStream().combine(_exerciseState) { ex, _ ->
                ExerciseUiState.Success(
                    exerciseList = ex
                )
            }.catch {
            }.collect { combinedUiState ->
                _exerciseState.value = combinedUiState
            }
        }
    }
    fun addItem(){
        viewModelScope.launch {
            exerciseRepository.addItem()

        }
    }


    fun updateToken(token: String) {
        viewModelScope.launch {
            tokenDataSource.saveToken(token)
        }
    }

    fun getUser() {
        viewModelScope.launch {
            Log.e("TAG", "getUser: ${UserDataSourceImpl().getUserInfo()}")
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
                client.gotrue.currentAccessTokenOrNull()
                updateToken(client.gotrue.currentAccessTokenOrNull() ?: "0")
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

sealed interface ExerciseUiState {
    object Loading : ExerciseUiState

    data class Success(
        val exerciseList: List<Exercise>,
    ) : ExerciseUiState
}


