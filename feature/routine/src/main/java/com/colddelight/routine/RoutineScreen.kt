package com.colddelight.routine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.TextGray

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
            countDate(cnt = 3)
        }
    }
}

@Composable
fun countDate(
    cnt: Int
){
    val date = getTodayDate()
    Column {
        Text(
            text = "$date ~",
            fontSize = 16.sp,
            color = TextGray
            )
        Row{
            Text(
                text = "+ ",
                fontSize = 24.sp,
                color = TextGray,
                )
            Text(
                text = cnt.toString(),
                fontSize = 24.sp,
                color = Main,
            )
            Text(
                text = " days",
                fontSize = 24.sp,
                color = TextGray,
            )
        }
    }
    
}

@Preview
@Composable
fun previewCountDate(){
    countDate(cnt = 3)
}
