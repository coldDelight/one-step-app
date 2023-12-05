package com.colddelight.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.colddelight.datastore.datasource.TokenPreferencesDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenDataSource: TokenPreferencesDataSource
) : ViewModel() {

    val token: Flow<String> = tokenDataSource.token


    fun updateToken(token: String) {
        viewModelScope.launch {
            tokenDataSource.saveToken(token)
        }
    }


}