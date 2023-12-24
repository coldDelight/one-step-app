package com.colddelight.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    onStartButtonClick: () -> Unit
) {
//    val token = homeViewModel.token.collectAsStateWithLifecycle(initialValue = "")
//    val action = client.composeAuth.rememberLoginWithGoogle(
//        onResult = { result -> homeViewModel.checkGoogleLoginStatus(result) },
//        fallback = {}
//    )
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackGray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), Alignment.Center
            ) {
                HomeButton(onStartButtonClick)
            }

//            StepButton(onClick = {
//                action.startFlow()
//            }, color = Main) {
//                Text(
//                    text = "구글 로그인하기",
//                )
//            }

//            StepButton(onClick = {
//                homeViewModel.delToken()
//            }, color = Main) {
//                Text(
//                    text = "토큰 삭제",
//                )
//            }

        }
    }
}

@Composable
fun HomeButton(onStartButtonClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGray
        ),
        onClick = { onStartButtonClick() },
        modifier = Modifier
            .size(300.dp)
            .background(Main, shape = CircleShape)
            .border(
                width = 4.dp,
                color = Main,
                shape = CircleShape
            )
    ) {
        Box(
            modifier = Modifier
        ) {
            Text(text = "운동시작", style = NotoTypography.headlineMedium, color = Main)
        }
    }
}
