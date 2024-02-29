package com.colddelight.designsystem.component.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.R
import com.colddelight.model.ExerciseCategory

@Composable
fun CategoryIconList(categoryList: List<ExerciseCategory>) {
    LazyRow {
        items(categoryList) { item ->
            Box(modifier = Modifier.padding(4.dp)) {
                when (item) {
                    ExerciseCategory.CHEST -> Image(
                        painter = painterResource(id = R.drawable.chest),
                        contentDescription = "가슴",
                    )

                    ExerciseCategory.SHOULDER -> Image(
                        painter = painterResource(id = R.drawable.shoulder),
                        contentDescription = "어깨",
                    )

                    ExerciseCategory.BACK -> Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "등",
                    )

                    ExerciseCategory.ARM -> Image(
                        painter = painterResource(id = R.drawable.arm),
                        contentDescription = "팔",
                    )

                    ExerciseCategory.LEG -> Image(
                        painter = painterResource(id = R.drawable.leg),
                        contentDescription = "하체",
                    )

                    ExerciseCategory.CALISTHENICS -> Image(
                        painter = painterResource(id = R.drawable.calisthenics),
                        contentDescription = "맨몸",
                    )
                }

            }
        }
    }
}