package com.colddelight.designsystem.component.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main

@Composable
fun ExerciseProgress(
    modifier: Modifier,
    current: Int,
    exerciseCnt: Int,
    isExercise: Boolean = false
) {
    LazyRow(
        state = LazyListState(firstVisibleItemIndex = current),
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically

    ) {
        items(exerciseCnt) { index ->
            val circleColor = if (index <= current) Main else LightGray

            if (isExercise) {
                if ((index + 1) % 5 == 0) {
                    BigCircleDot(color = circleColor)
                } else {
                    CircleDot(color = circleColor)
                }
            } else {
                CircleDot(color = circleColor)

            }
        }
    }
}

@Composable
fun CircleDot(color: Color) {
    Box(
        modifier = Modifier
            .size(14.dp)
            .background(color, shape = CircleShape)
    )
}

@Composable
fun BigCircleDot(color: Color) {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(color, shape = CircleShape)
    )
}