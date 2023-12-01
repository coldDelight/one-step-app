package com.colddelight.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
){

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ){ padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            Text(
                text = "History",
            )
        }
    }
}