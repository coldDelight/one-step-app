package com.colddelight.home

import androidx.compose.ui.graphics.Color
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseDay

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Error(val msg: String) : HomeUiState

    data class Success(
        val cnt: Int,
        val exerciseWeek: List<ExerciseDay>,
        val today: Int,
        val state: HomeState,
    ) : HomeUiState

}

sealed interface HomeState {
    val textColor: Color
    val text: String
    val strokeColor: Color
        get() = LightGray

    data class During(
        val categoryList: List<ExerciseCategory>,
        override val text: String,
        override val textColor: Color = TextGray,
        override val strokeColor: Color = Main
    ) : HomeState

    data class Resting(
        override val textColor: Color = DarkGray,
        override val text: String = "휴식일"
    ) : HomeState

    data class Done(
        override val textColor: Color = Main,
        override val text: String = "운동완료"
    ) : HomeState

}
