package com.colddelight.routine

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.contentPaddingWithoutLabel
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.component.CategoryChip
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo

@Composable
fun RoutineScreen(
    viewModel: RoutineViewModel = hiltViewModel(),
){
    val routineInfoUiState by viewModel.routineInfoUiState.collectAsStateWithLifecycle()
    val routineDayInfoUiState by viewModel.routineDayInfoUiState.collectAsStateWithLifecycle()

    Log.e("TAG", "RoutineScreen: ${routineInfoUiState}", )
    Log.e("TAG", "RoutineScreen: ${routineDayInfoUiState}", )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ){ padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            RoutineContentWithState(
                routineInfoUiState = routineInfoUiState,
                routineDayInfoUiState = routineDayInfoUiState
                )
        }
    }
}

@Preview
@Composable
fun PreviewUnit(){
    RoutineContent(
        Routine("Test",3),
        listOf(
            RoutineDayInfo(0,1, listOf(0),
                listOf(ExerciseInfo(0,1,"벤치프레스",0,0, listOf(20,40), listOf(12,12)))
            ),
            RoutineDayInfo(0,1, listOf(0),
                listOf(ExerciseInfo(0,2,"플라이",1,1, listOf(20,40), listOf(12,12)))
            )
        )
    )
}

@Composable
private fun RoutineContentWithState(
    routineInfoUiState: RoutineInfoUiState,
    routineDayInfoUiState: RoutineDayInfoUiState
) {
    when{
        routineInfoUiState is RoutineInfoUiState.Success &&
                routineDayInfoUiState is RoutineDayInfoUiState.Success -> RoutineContent(routineInfoUiState.routine,routineDayInfoUiState.routineDayInfo)
        routineInfoUiState is RoutineInfoUiState.Loading -> RoutineLoading()
        routineInfoUiState is RoutineInfoUiState.Error -> RoutineLoading()
        routineDayInfoUiState is RoutineDayInfoUiState.Loading -> RoutineLoading()
        routineDayInfoUiState is RoutineDayInfoUiState.Error -> RoutineLoading()
    }
}

@Composable
private fun RoutineLoading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun RoutineContent(routine: Routine, routineDayList: List<RoutineDayInfo>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            RoutineList(routine.name)
            CountDate(routine.cnt)
        }
        ExerciseCardView("월요일", listOf("벤치프레스","플라이","스쿼트"),listOf("가슴","운동"))
    }
}

@Composable
fun CountDate(
    cnt: Int
){
    val date = getTodayDate()
    Column {
        Text(
            text = "$date ~",
            style = HortaTypography.labelMedium
            )
        Row{
            Text(text = "+ ", style = HortaTypography.headlineSmall)
            Text(text = cnt.toString(), style = HortaTypography.headlineSmall, color = Main)
            Text(text = " days ", style = HortaTypography.headlineSmall)
        }
    }
    
}

@Composable
fun RoutineList(name: String){
    Text(text = name, style = NotoTypography.headlineMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownRoutineList(routineList: List<String>, modifier: Modifier){

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(routineList[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ){
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(0.4f),
            readOnly = true,
            textStyle= NotoTypography.headlineSmall,
            value = selectedOptionText,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextGray,
                unfocusedTextColor = TextGray,
                disabledContainerColor =  Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor =   Color.Transparent,
                focusedTrailingIconColor = TextGray,
                unfocusedTrailingIconColor = TextGray,
                focusedIndicatorColor =  TextGray,
                unfocusedIndicatorColor= TextGray,
            ),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            routineList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dayOfWeek,
                style = NotoTypography.bodyMedium,
                modifier = Modifier
                    .padding(start = 20.dp)
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
        Text(text = exercise, style = NotoTypography.labelSmall, modifier = Modifier.padding(start = 8.dp) )
    }
}
