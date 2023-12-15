package com.colddelight.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.colddelight.data.util.getDayOfWeek
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.component.CategoryChip
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray
import java.time.DayOfWeek

@Composable
fun RoutineScreen(
    routineViewModel: RoutineViewModel = hiltViewModel(),
){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ){ padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Text(
                text = "Routine",
            )
            CountDate(cnt = 3)
        }
    }
}

@Composable
fun CountDate(
    cnt: Int
){
    val date = getTodayDate()
    val defaultStyle = SpanStyle(
        color = TextGray,
        fontSize = 24.sp,
    )
    Column {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text ="test",
                style = NotoTypography.headlineLarge
            )
            Text(
                text ="TYest",
                style = HortaTypography.headlineLarge
            )
        }
        Row(verticalAlignment = Alignment.Bottom){
            Text(
                text ="test",
                style = NotoTypography.headlineMedium
            )
            Text(
                text ="test",
                style = HortaTypography.headlineMedium
            )

        }

        Text(
            text = "$date ~",
            fontSize = 16.sp,
            color = TextGray
            )
        Row{
            Text(text = buildAnnotatedString {
                withStyle(style = defaultStyle) { append("+ ") }
                withStyle(
                    style = SpanStyle(
                        fontSize = 24.sp,
                        color = Main,
                    ),
                ) { append(cnt.toString()) }
                withStyle(style = defaultStyle,) { append(" days") }
            })
        }
    }
    
}

@Preview
@Composable
fun PreviewUnit(){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(BackGray)) {
        CountDate(cnt = 3)
        ExerciseCardView(getDayOfWeek(1),exerciseList = listOf("플라이","벤치프레스"), categoryList = listOf("가슴","이두"))
    }
}



@Composable
fun ExerciseCardView(
    dayOfWeek: String,
    exerciseList: List<String>,
    categoryList: List<String>
){
    Column(
        modifier = Modifier
            .fillMaxWidth(0.45f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayOfWeek,
                color = TextGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(0.3f)
            )
            CategoryList(categoryList)
        }
        Box(
            modifier = Modifier
                .aspectRatio(8f / 7f)
                .background(LightGray, RoundedCornerShape(20.dp))
                .padding(16.dp),
        ) {
            ExerciseList(exerciseList = exerciseList)
        }
    }
}
@Composable
fun CategoryList(
    categoryList : List<String>
){
    Row(
    ){
        categoryList.forEach{
            CategoryChip(it,8)
        }
    }
}


@Composable
fun ExerciseList(
    exerciseList : List<String>
){
    Column{
        exerciseList.forEach {
            ExerciseText(it)
        }
    }
}

@Composable
fun ExerciseText(
    exercise: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(LightGray)
    ) {
        Box (modifier = Modifier
            .size(3.dp)
            .background(Color.White, CircleShape)
        )
        Text(text = exercise, color = TextGray, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp) )
    }
}
