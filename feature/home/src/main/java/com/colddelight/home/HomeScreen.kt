package com.colddelight.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.component.StepButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStartButtonClick: (Int) -> Unit
) {
    val token = homeViewModel.token.collectAsStateWithLifecycle(initialValue = "")

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "Home",
            )
            StepButton(onClick = {
                onStartButtonClick(1)
            }) {
                Text(
                    text = stringResource(R.string.start),
                )
            }

            StepButton(onClick = {
                homeViewModel.updateToken("테스트용입니다")
            }) {
                Text(
                    text = token.value,
                )
            }

        }
    }
}