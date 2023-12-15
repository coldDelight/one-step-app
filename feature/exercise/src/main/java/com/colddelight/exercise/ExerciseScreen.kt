package com.colddelight.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.R
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.model.RoutineInfo
import com.colddelight.designsystem.theme.Main
import com.colddelight.model.ExerciseCategory

@Composable
fun ExerciseScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
) {
    val sessionUiState by viewModel.exerciseUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            SessionDetailContent(uiState = sessionUiState)
        }
    }
}

@Composable
private fun SessionDetailContent(uiState: ExerciseUiState) {
    when (uiState) {
        is ExerciseUiState.Loading -> SessionDetailLoading()
        is ExerciseUiState.Error -> SessionDetailLoading()
        is ExerciseUiState.Success -> todayRoutineInfo("2023.11.15 Thu", uiState.routineInfo)
    }
}

@Composable
private fun SessionDetailLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun todayRoutineInfo(date: String, routineInfo: RoutineInfo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        dateAndCnt(date, routineInfo.cnt)
        Text(text = routineInfo.name, style = NotoTypography.headlineLarge)
        categoryIcon(routineInfo.categoryIdList)
    }
}

@Composable
fun dateAndCnt(date: String, cnt: Int) {
    Row {
        Text(text = "$date [", style = HortaTypography.bodyMedium)
        Text(text = "$cnt", style = HortaTypography.bodyMedium, color = Main)
        Text(text = "]", style = HortaTypography.bodyMedium)
    }
}

@Composable
fun categoryIcon(categoryList: List<ExerciseCategory>) {
    LazyRow {
        items(categoryList) { item ->

            Image(
                painter = painterResource(id = R.drawable.chest),
                contentDescription = "가슴",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun RoutineInfoPreview() {
    val routineInfo =
        RoutineInfo("3분할", cnt = 5, listOf(ExerciseCategory.CHEST, ExerciseCategory.BACK))
    todayRoutineInfo(date = "2023.11.15 Thu", routineInfo = routineInfo)

}
