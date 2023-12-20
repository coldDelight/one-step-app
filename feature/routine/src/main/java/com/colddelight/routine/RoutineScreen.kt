package com.colddelight.routine

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getCategoryStringFromInt
import com.colddelight.data.util.getDayOfWeek
import com.colddelight.data.util.getTodayDate
import com.colddelight.designsystem.component.CategoryChip
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.designsystem.theme.notosanskr
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import kotlin.math.log

@Composable
fun RoutineScreen(
    viewModel: RoutineViewModel = hiltViewModel(),
) {
    val routineInfoUiState by viewModel.routineInfoUiState.collectAsStateWithLifecycle()
    val routineDayInfoUiState by viewModel.routineDayInfoUiState.collectAsStateWithLifecycle()

    Log.e("RoutineInfo", "RoutineScreen: ${routineInfoUiState}")
    Log.e("RoutineDayInfo", "RoutineScreen: ${routineDayInfoUiState}")
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
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
fun PreviewUnit() {
//    RoutineContent(
//        Routine(1,"Test",3),
//        listOf(
//            RoutineDayInfo(0,0,1, listOf(0),
//                listOf(ExerciseInfo(0,1,"벤치프레스",0, listOf(20,40), listOf(12,12)))
//            ),
//            RoutineDayInfo(0,0,1, listOf(0),
//                listOf(ExerciseInfo(0,2,"플라이",1, listOf(20,40), listOf(12,12)))
//            )
//        )
//    )
}

@Composable
private fun RoutineContentWithState(
    routineInfoUiState: RoutineInfoUiState,
    routineDayInfoUiState: RoutineDayInfoUiState,
) {
    when {
        routineInfoUiState is RoutineInfoUiState.Success &&
                routineDayInfoUiState is RoutineDayInfoUiState.Success -> RoutineContent(
            routineInfoUiState.routine,
            routineDayInfoUiState.routineDayInfo
        )

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
    var screenWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current.density

    var currentDayOfWeek by remember { mutableStateOf(1) }

    LaunchedEffect(currentDayOfWeek) {
        // currentDayOfWeek가 업데이트될 때마다 호출되는 부분
        // 필요한 처리를 여기에 추가
        Log.e("TAG", "RoutineContent: $currentDayOfWeek", )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned {
                screenWidth = ((it.size.width) / density * 0.45).toInt()
            }
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
        ExerciseCardRow(routineDayList, screenWidth,currentDayOfWeek
            ){ selectedDayOfWeek ->
            // ExerciseCardView가 선택될 때마다 currentDayOfWeek를 업데이트
            currentDayOfWeek = selectedDayOfWeek
        }
        RoutineDayScreen(routineDayList[currentDayOfWeek-1])
        GridViewWithDifferentItemSizes()
    }
}

@Composable
fun DayOfWeekAndDot(routineDayInfo: RoutineDayInfo){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val modifier = Modifier.padding(end = 20.dp)
        Text(modifier = modifier,text = getDayOfWeek(routineDayInfo.dayOfWeek), style = NotoTypography.headlineSmall)
        routineDayInfo.exerciseList?.let {
            if(it.isNotEmpty()){
                ExerciseProgress(modifier = modifier, it.size,it.size)
                Text(text = it.size.toString(), style =  NotoTypography.bodyMedium)
            }
        }
    }
}

@Preview
@Composable
fun GridViewWithDifferentItemSizes() {
    // 아이템 목록 생성
    val itemsList = (0..5).toList()

    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2)
        ) {
            item {
                ExerciseItem(
                    exercise = Exercise.Weight(name = "벤치프레스", 20, 40, "", 1),
                )
            }
            item {
                Text(
                    "Single item", modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 1f)
                )

            }
            items(itemsList) {
                var currentItemCenter = Offset.Zero
                ExerciseItem(exercise = Exercise.Weight(name = "벤치프레스${it + 1}", 20, 40, "", 1),)
            }
        }
    }
}


@Composable
fun ExerciseItem(
    exercise: Exercise,
){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1f / 1f)
            .padding(16.dp)
            .background(BackGray, CircleShape)
            .border(4.dp, Main, CircleShape)
            .padding(16.dp)
        ,
        contentAlignment = Alignment.Center
    ){
        val name = when (exercise) {
            is Exercise.Weight -> exercise.name
            is Exercise.Calisthenics -> exercise.name
        }
        Column {
            Text(text = name, style = NotoTypography.bodySmall)
        }
    }
}




@Composable
fun RoutineDayScreen( routineDayInfo: RoutineDayInfo ){
    DayOfWeekAndDot(routineDayInfo)
}

@Composable
fun ExerciseCardRow(
    routineDayList: List<RoutineDayInfo>,
    widthDp: Int,
    currentDayOfWeek: Int,
    onCardClicked: (Int) -> Unit) {
    Log.e("TAG", "받았슈: $widthDp")

    LazyRow {
            items(routineDayList) { routineDayInfo ->
                Box(
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                        .padding(top = 16.dp, bottom = 24.dp)
                ) {
                    ExerciseCardView(
                        routineDayInfo = routineDayInfo,
                        widthDp = widthDp,
                        onCardClicked = {
                            // 클릭 시 선택한 dayOfWeek를 콜백으로 전달
                            onCardClicked(it)
                            Log.e("TAG", "ExerciseCardRow: ${it}", )
                        }
                    )
                }
            }
        }
}

@Composable
fun CountDate(
    cnt: Int,
) {
    val date = getTodayDate()
    Column {
        Text(
            text = "$date ~",
            style = HortaTypography.labelMedium
        )
        Row {
            Text(text = "+ ", style = HortaTypography.headlineSmall)
            Text(text = cnt.toString(), style = HortaTypography.headlineSmall, color = Main)
            Text(text = " days ", style = HortaTypography.headlineSmall)
        }
    }

}

@Composable
fun RoutineList(name: String) {
    Text(text = name, style = NotoTypography.headlineMedium)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownRoutineList(routineList: List<String>, modifier: Modifier) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(routineList[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(0.4f),
            readOnly = true,
            textStyle = NotoTypography.headlineSmall,
            value = selectedOptionText,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = TextGray,
                unfocusedTextColor = TextGray,
                disabledContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTrailingIconColor = TextGray,
                unfocusedTrailingIconColor = TextGray,
                focusedIndicatorColor = TextGray,
                unfocusedIndicatorColor = TextGray,
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
    routineDayInfo: RoutineDayInfo,
    widthDp: Int,
    onCardClicked: (Int) -> Unit, // 클릭 이벤트를 처리할 콜백 함수
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 16.dp)
            .width(widthDp.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = getDayOfWeek(routineDayInfo.dayOfWeek),
                style = NotoTypography.bodyMedium,
                modifier = Modifier
                    .padding(start = 10.dp)
            )
            val categoryList: List<String> = routineDayInfo.categoryList?.map {
                getCategoryStringFromInt(it)
            } ?: emptyList()

            CategoryList(categoryList)

        }
        Box(
            modifier = Modifier
                .aspectRatio(8f / 7f)
                .background(DarkGray, RoundedCornerShape(10.dp))
                .clickable {
                    onCardClicked(routineDayInfo.dayOfWeek) // 클릭 시 콜백 호출
                    Log.e("TAG", "ExerciseCardView: ${routineDayInfo.dayOfWeek}",)
                }
                .padding(16.dp),
        ) {
            routineDayInfo.exerciseList?.let { ExerciseList(exerciseList = it) }
        }
    }
}


@Composable
fun CategoryList(
    categoryList: List<String>,
) {
    Row(
    ) {
        categoryList.forEach {
            CategoryChip(it, 8)
        }
    }
}

@Composable
fun ExerciseList(
    exerciseList: List<Exercise>,
) {
    if(exerciseList.isNotEmpty()){
        Column {
            exerciseList.forEach {
                val name = when (it) {
                    is Exercise.Weight -> it.name
                    is Exercise.Calisthenics -> it.name
                }
                ExerciseText(name)

            }
        }
    }
    else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "휴식", style = NotoTypography.headlineSmall, color = LightGray)
        }
    }

}

@Composable
fun ExerciseText(
    exercise: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
//            .background(DarkGray)
    ) {
        Box(
            modifier = Modifier
                .size(3.dp)
                .background(Color.White, CircleShape)
        )
        Text(
            text = exercise,
            style = NotoTypography.labelMedium,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
