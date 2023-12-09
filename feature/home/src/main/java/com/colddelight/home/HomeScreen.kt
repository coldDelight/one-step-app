package com.colddelight.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.component.StepButton
import com.colddelight.network.SupabaseClient.client
import com.colddelight.network.datasourceImpl.UserDataSourceImpl
import io.github.jan.supabase.compose.auth.composable.rememberLoginWithGoogle
import io.github.jan.supabase.compose.auth.composeAuth

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStartButtonClick: (Int) -> Unit
) {
    val token = homeViewModel.token.collectAsStateWithLifecycle(initialValue = "")
    val exerciseUiState by homeViewModel.exerciseState.collectAsStateWithLifecycle()
    val action = client.composeAuth.rememberLoginWithGoogle(
        onResult = { result -> homeViewModel.checkGoogleLoginStatus(result) },
        fallback = {}
    )
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
                action.startFlow()
            }) {
                Text(
                    text = "구글 로그인하기",
                )
            }

            StepButton(onClick = {
                homeViewModel.delToken()
            }) {
                Text(
                    text = "토큰 삭제",
                )
            }


            StepButton(onClick = {
//                homeViewModel.getUser()
            }) {
                Text(
                    text = "t사용자 정보",
                )
            }

            StepButton(onClick = {
                homeViewModel.addItem()
            }) {
                Text(
                    text = "room임시 데이터 추가",
                )
            }
            when(exerciseUiState){
                is ExerciseUiState.Loading->{
                    CircularProgressIndicator(
                        Modifier.size(100.dp,100.dp),
                        color = Color.Red,
                        strokeWidth = 5.dp
                    )
                }
                is ExerciseUiState.Success->{
                    LazyColumn(
                    ){
                        items((exerciseUiState as ExerciseUiState.Success).exerciseList.size){
                            Text(text = (exerciseUiState as ExerciseUiState.Success).exerciseList[it].name, color = Color.White)
                            Text(text = (exerciseUiState as ExerciseUiState.Success).exerciseList[it].categoryName,color = Color.White)
                        }
                    }
                }
            }

        }
    }
}