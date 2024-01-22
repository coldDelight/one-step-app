package com.colddelight.routine

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.data.util.getDayOfWeek
import com.colddelight.designsystem.component.CategoryChip
import com.colddelight.designsystem.component.ExerciseDetailItem
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.component.FilterChip
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.RedButton
import com.colddelight.designsystem.component.SetAction
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Close
import com.colddelight.designsystem.icons.iconpack.Topback
import com.colddelight.designsystem.icons.iconpack.Trash
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.Red
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.DayExercise
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import com.colddelight.model.SetInfo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar


@Composable
fun RoutineScreen(
    viewModel: RoutineViewModel = hiltViewModel(),
) {
    val routineInfoUiState by viewModel.routineInfoUiState.collectAsStateWithLifecycle()
    val routineDayInfoUiState by viewModel.routineDayInfoUiState.collectAsStateWithLifecycle()
    val exerciseListState by viewModel.exerciseListState.collectAsStateWithLifecycle()


    Log.e("RoutineInfo", "RoutineScreen: ${routineInfoUiState}")
    Log.e("RoutineDayInfo", "RoutineScreen: ${routineDayInfoUiState}")
    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            RoutineContentWithState(routineInfoUiState = routineInfoUiState,
                routineDayInfoUiState = routineDayInfoUiState,
                insertRoutineDay = { newRoutineDayInfo ->
                    viewModel.insertRoutineDay(newRoutineDayInfo)
                },
                exerciseListState = exerciseListState,
                insertRoutine = {
                    viewModel.insertRoutine(it)
                },
                insertExercise = { exerciseInfo ->
                    viewModel.insertExercise(exerciseInfo)
                },
                deleteRoutineDay = {
                    viewModel.deleteRoutineDay(it)
                },
                deleteExercise = {
                    viewModel.deleteExercise(it)
                },
                deleteDayExercise = {
                    viewModel.deleteDayExercise(it)
                },
                insertDayExercise = {
                    viewModel.insertDayExercise(it)
                })
        }
    }
}

@Composable
private fun RoutineContentWithState(
    routineInfoUiState: RoutineInfoUiState,
    routineDayInfoUiState: RoutineDayInfoUiState,
    exerciseListState: ExerciseListState,
    insertRoutine: (Routine) -> Unit,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
    insertExercise: (ExerciseInfo) -> Unit,
    insertDayExercise: (DayExercise) -> Unit,
    deleteRoutineDay: (Int) -> Unit,
    deleteExercise: (Int) -> Unit,
    deleteDayExercise: (Int) -> Unit,
) {
    when {
        routineInfoUiState is RoutineInfoUiState.Success && routineDayInfoUiState is RoutineDayInfoUiState.Success && exerciseListState is ExerciseListState.NotNone -> RoutineContent(
            routineInfoUiState.routine,
            routineDayInfoUiState.routineDayInfo,
            exerciseListState.exerciseList,
            insertRoutine,
            insertRoutineDay,
            insertExercise,
            insertDayExercise,
            deleteRoutineDay,
            deleteExercise,
            deleteDayExercise,
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
private fun RoutineContent(
    routine: Routine,
    routineDayList: List<RoutineDayInfo>,
    exerciseList: List<ExerciseInfo>,
    insertRoutine: (Routine) -> Unit,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
    insertExercise: (ExerciseInfo) -> Unit,
    insertDayExercise: (DayExercise) -> Unit,
    deleteRoutineDay: (Int) -> Unit,
    deleteExercise: (Int) -> Unit,
    deleteDayExercise: (Int) -> Unit,
) {
    var screenWidth by remember { mutableStateOf(0) }
    val density = LocalDensity.current.density

    var currentDayOfWeek by remember { mutableStateOf(getDayOfWeekNumber()) }


    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .onGloballyPositioned {
            screenWidth = ((it.size.width) / density * 0.45).toInt()
        }
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RoutineName(routine.name, insertRoutine ={ insertRoutine(Routine(id = routine.id, name = it, cnt = routine.cnt ))})
                CountDate(routine.cnt)
            }
        }
        item {
            ExerciseCardRow(
                currentDayOfWeek,routineDayList, screenWidth
            ) { selectedDayOfWeek ->
                // ExerciseCardView가 선택될 때마다 currentDayOfWeek를 업데이트
                currentDayOfWeek = selectedDayOfWeek
            }
        }
        item {
            DayOfWeekAndDot(routineDayList[currentDayOfWeek - 1], deleteRoutineDay)
            LazyRow {
                item {
                    AddCategoryBtn(
                        routineDayInfo = routineDayList[currentDayOfWeek - 1],
                        insertRoutineDay = insertRoutineDay
                    )
                    CategoryList(
                        true,
                        routineDayList[currentDayOfWeek - 1],
                        16,
                        insertRoutineDay = insertRoutineDay
                    )
                }
            }
        }
        item {
            ExerciseGridList(
                routineDayList[currentDayOfWeek - 1],
                routineDayList[currentDayOfWeek - 1].exerciseList,
                exerciseList,
                insertRoutineDay,
                insertExercise,
                insertDayExercise,
                deleteExercise,
                deleteDayExercise,
                screenWidth
            )
        }
    }
}

fun getDayOfWeekNumber(): Int {
    val calendar = Calendar.getInstance()
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    return when (dayOfWeek) {
        Calendar.MONDAY -> 1
        Calendar.TUESDAY -> 2
        Calendar.WEDNESDAY -> 3
        Calendar.THURSDAY -> 4
        Calendar.FRIDAY -> 5
        Calendar.SATURDAY -> 6
        Calendar.SUNDAY -> 7
        else -> 0
    }
}

@Composable
fun DayOfWeekAndDot(
    routineDayInfo: RoutineDayInfo, deleteRoutineDay: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,

        ) {
        val modifier = Modifier.padding(end = 20.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = modifier,
                text = getDayOfWeek(routineDayInfo.dayOfWeek),
                style = NotoTypography.headlineSmall
            )
            routineDayInfo.exerciseList?.let {
                if (it.size > 7) {
                    ExerciseProgress(modifier = modifier, 7, 7)
                    Text(text = "7+", style = NotoTypography.bodyMedium)
                } else if (it.isNotEmpty()) {
                    ExerciseProgress(modifier = modifier, it.size, it.size)
                    Text(text = it.size.toString(), style = NotoTypography.bodyMedium)
                }
            }
        }
        RoutineDayDeleteBtn(routineDayInfo.routineDayId, deleteRoutineDay)
    }
}

@Composable
fun RoutineDayDeleteBtn(
    routineDayId: Int,
    deleteRoutineDay: (Int) -> Unit,
) {
    var openAlertDialog by remember { mutableStateOf(false) }

    IconButton(onClick = { openAlertDialog = true }) {
        Icon(imageVector = IconPack.Trash, contentDescription = "루틴데이삭제", tint = LightGray)
    }
    if (openAlertDialog) {
        RoutineDayDeleteDialog(onDismissRequest = {
            openAlertDialog = it
        }, onConfirmation = {
            if (it) deleteRoutineDay(routineDayId)
        })
    }
}

@Composable
fun RoutineDayDeleteDialog(
    onDismissRequest: (Boolean) -> Unit,
    onConfirmation: (Boolean) -> Unit,
) {
    AlertDialog(containerColor = BackGray, text = {
        Text(
            text = "해당 요일의 모든 데이터를 삭제하시겠습니까?",
            style = NotoTypography.bodyMedium,
            color = TextGray
        )
    }, onDismissRequest = {
        onDismissRequest(true)
    }, confirmButton = {
        TextButton(onClick = {
            onConfirmation(true)
            onDismissRequest(false)
        }) {
            Text(text = "삭제", style = NotoTypography.bodyMedium, color = Red)
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissRequest(false)
        }) {
            Text("취소", style = NotoTypography.bodyMedium, color = TextGray)
        }
    })
}


@Composable
fun ExerciseGridList(
    routineDayInfo: RoutineDayInfo,
    exerciseList: List<Exercise>,
    allExerciseList: List<ExerciseInfo>,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
    insertExercise: (ExerciseInfo) -> Unit,
    insertDayExercise: (DayExercise) -> Unit,
    deleteExercise: (Int) -> Unit,
    deleteDayExercise: (Int) -> Unit,
    screenWidth: Int,
) {
    val height = screenWidth * (exerciseList.size / 2 + 2)
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .heightIn(max = height.dp)
                .wrapContentHeight(),        // wrapContent 설정
            userScrollEnabled = false        // 스크롤 막기
        ) {
            if (exerciseList.isNotEmpty()) {
                item {
                    ExerciseItem(
                        routineDayInfo = routineDayInfo,
                        exercise = exerciseList[0],
                        insertDayExercise = insertDayExercise,
                        deleteDayExercise = deleteDayExercise
                    )
                }
                item {
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(2f / 1f)
                    )

                }
                if (exerciseList.size >= 2) {
                    val itemsList = exerciseList.subList(1, exerciseList.size)
                    items(itemsList) {
                        ExerciseItem(
                            routineDayInfo = routineDayInfo,
                            exercise = it,
                            insertDayExercise = insertDayExercise,
                            deleteDayExercise = deleteDayExercise
                        )
                    }
                }
            }
            item {
                AddExerciseToRoutineDayBtn(
                    routineDayInfo,
                    allExerciseList,
                    insertExercise,
                    insertRoutineDay,
                    insertDayExercise,
                    deleteExercise,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExerciseToRoutineDayBtn(
    routineDayInfo: RoutineDayInfo,
    exerciseList: List<ExerciseInfo>,
    insertExercise: (ExerciseInfo) -> Unit,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
    insertDayExercise: (DayExercise) -> Unit,
    deleteExercise: (Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .aspectRatio(1f / 1f)
        .padding(16.dp)
        .border(4.dp, DarkGray, CircleShape)
        .background(BackGray, CircleShape)
        .clip(CircleShape)
        .clickable {
            if (routineDayInfo.routineDayId == 0) {
                insertRoutineDay(
                    RoutineDayInfo(
                        routineDayId = 0,
                        dayOfWeek = routineDayInfo.dayOfWeek,
                        routineId = routineDayInfo.routineId
                    )
                )
            }
            showBottomSheet = true
        }
        .padding(16.dp), contentAlignment = Alignment.Center) {
        Icon(
            imageVector = Icons.Rounded.Add, contentDescription = null, tint = TextGray
        )
    }

    if (showBottomSheet) {
        ExerciseListBottomSheet(
            routineDayInfo = routineDayInfo,
            onDismissSheet = {
                showBottomSheet = it
            },
            sheetState = sheetState,
            exerciseList = exerciseList,
            insertExercise = insertExercise,
            deleteExercise = deleteExercise,
            insertDayExercise = insertDayExercise
        )

    }
}

//야가 기존 운동 수정하는 거여 chan
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertDayExerciseBottomSheet(
    onDismissSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    insertDayExercise: (DayExercise) -> Unit,  //야여 야
    deleteDayExercise: (Int) -> Unit,
    routineDayInfo: RoutineDayInfo,
    exercise: Exercise,
) {
    val categoryList = (1..6).toList()
    var setInfoList by remember { mutableStateOf(exercise.setInfoList) }

    val lazyColumnState = rememberLazyListState()
    val density = LocalDensity.current
    val itemSizePx = with(density) { 100.dp.toPx() }
    val coroutineScope = rememberCoroutineScope()


    ModalBottomSheet(
        onDismissRequest = { onDismissSheet(false) },
        sheetState = sheetState,
        containerColor = BackGray,
    ) {
        LazyRow(modifier = Modifier.padding(horizontal = 10.dp)) {
            items(categoryList) { categoryIndex ->
                FilterChip(
                    ExerciseCategory.toName(categoryIndex),
                    {},
                    false,
                    if (categoryIndex == exercise.category.id) Main else LightGray
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp),
            state = lazyColumnState
        ) {

            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Main, RoundedCornerShape(10.dp))
                    ) {
                        Text(
                            text = exercise.name,
                            style = NotoTypography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
            itemsIndexed(setInfoList) { index, item ->
                ExerciseDetailItem(item.kg, item.reps, index) { setAction ->
                    setInfoList = performSetAction(
                        setInfoList, setAction
                    )
                }
                Divider(
                    color = DarkGray, thickness = 2.dp
                )

            }
            item {
                ClickableText(modifier = Modifier.padding(top = 8.dp),
                    text = AnnotatedString("+ 세트 추가"),
                    style = NotoTypography.headlineSmall.copy(color = DarkGray),
                    onClick = {
                        setInfoList = performSetAction(setInfoList, SetAction.AddSet)
                        coroutineScope.launch {
                            lazyColumnState.animateScrollBy(
                                value = itemSizePx * lazyColumnState.layoutInfo.totalItemsCount.toFloat(),
                                animationSpec = tween(durationMillis = 2000)
                            )
                        }
                    })
            }
            item {
                Row(
                    Modifier.padding(vertical = 45.dp),

                    ) {
                    RedButton(modifier = Modifier.padding(end = 8.dp), onClick = {
                        deleteDayExercise(exercise.dayExerciseId)
                        onDismissSheet(false)
                    }, content = { Text("삭제", style = NotoTypography.bodyMedium) })
                    MainButton(modifier = Modifier.fillMaxWidth(), onClick = {
                        insertDayExercise(
                            DayExercise(
                                routineDayId = routineDayInfo.routineDayId,
                                exerciseId = exercise.exerciseId,
                                kgList = setInfoList.map { it.kg },
                                repsList = setInfoList.map { it.reps },
                                id = exercise.dayExerciseId
                            )
                        )
                        onDismissSheet(false)
                    }) {
                        Text(text = "완료", style = NotoTypography.bodyMedium, color = Color.White)
                    }
                }
            }
        }
    }
}

//아따 여기에서도 쓴다잉 chan
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListBottomSheet(
    routineDayInfo: RoutineDayInfo,
    onDismissSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    exerciseList: List<ExerciseInfo>,
    insertExercise: (ExerciseInfo) -> Unit,
    deleteExercise: (Int) -> Unit,
    insertDayExercise: (DayExercise) -> Unit,
) {

    ModalBottomSheet(
        onDismissRequest = { onDismissSheet(false) },
        sheetState = sheetState,
        containerColor = BackGray,
    ) {
        val categoryList = (1..6).toList()
        var selectedChipIndices by remember { mutableStateOf(emptyList<ExerciseCategory>()) }
        var bottomSheetState by remember { mutableStateOf(ExerciseBottomSheetState.List) }
        var selectedExercise by remember {
            mutableStateOf(
                ExerciseInfo(
                    -1, "", ExerciseCategory.CHEST
                )
            )
        }
        var setInfoList by remember {
            mutableStateOf(List(5) { SetInfo(20, 12) })
        }

        val lazyColumnState = rememberLazyListState()
        val density = LocalDensity.current
        val itemSizePx = with(density) { 100.dp.toPx() }
        val coroutineScope = rememberCoroutineScope()


        when (bottomSheetState) {
            ExerciseBottomSheetState.Selected -> {
                LazyRow(modifier = Modifier.padding(horizontal = 10.dp)) {
                    items(categoryList) { categoryIndex ->
                        FilterChip(
                            ExerciseCategory.toName(categoryIndex),
                            {},
                            false,
                            if (categoryIndex == selectedExercise.category.id) Main else LightGray
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(top = 16.dp),
                    state = lazyColumnState
                ) {

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            IconButton(onClick = {
                                bottomSheetState = ExerciseBottomSheetState.List
                            }) {
                                Icon(
                                    imageVector = IconPack.Topback,
                                    contentDescription = "운동 선택",
                                    tint = TextGray
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Main, RoundedCornerShape(10.dp))
                            ) {
                                Text(
                                    text = selectedExercise.name,
                                    style = NotoTypography.bodyMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }
                    }
                    itemsIndexed(setInfoList) { index, item ->
                        ExerciseDetailItem(item.kg, item.reps, index) { setAction ->
                            setInfoList = performSetAction(
                                setInfoList, setAction
                            )
                        }
                        Divider(
                            color = DarkGray, thickness = 2.dp
                        )

                    }
                    item {
                        ClickableText(text = AnnotatedString("+ 세트 추가"),
                            style = NotoTypography.headlineSmall.copy(color = DarkGray),
                            onClick = {
                                setInfoList = performSetAction(setInfoList, SetAction.AddSet)
                                coroutineScope.launch {
                                    lazyColumnState.animateScrollBy(
                                        value = itemSizePx * lazyColumnState.layoutInfo.totalItemsCount.toFloat(),
                                        animationSpec = tween(durationMillis = 2000)
                                    )
                                }
                            })
                    }
                    item {
                        MainButton(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 45.dp),
                            onClick = {
                                insertDayExercise(
                                    DayExercise(
                                        routineDayId = routineDayInfo.routineDayId,
                                        exerciseId = selectedExercise.id,
                                        kgList = setInfoList.map { it.kg },
                                        repsList = setInfoList.map { it.reps },
                                        id = 0
                                    )
                                )
                                onDismissSheet(false)
                            }) {
                            Text(
                                text = "완료",
                                style = NotoTypography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                }

            }

            else -> {
                LazyRow(modifier = Modifier.padding(horizontal = 10.dp)) {
                    items(categoryList) { categoryIndex ->
                        FilterChip(ExerciseCategory.toName(categoryIndex), { isSelected ->
                            selectedChipIndices = if (isSelected) {
                                selectedChipIndices + ExerciseCategory.fromId(categoryIndex)!!
                            } else {
                                selectedChipIndices - ExerciseCategory.fromId(categoryIndex)!!
                            }
                        }, true, LightGray)
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .heightIn(min = 300.dp)
                ) {
                    if (selectedChipIndices.isEmpty()) {
                        itemsIndexed(exerciseList) { index, it ->
                            AllExerciseListItem(it,
                                insertExercise = insertExercise,
                                deleteExercise = deleteExercise,
                                isItemSelected = {
                                    selectedExercise = it
                                    bottomSheetState = ExerciseBottomSheetState.Selected
                                })
                            Divider(
                                color = LightGray, thickness = 1.dp
                            )
                        }
                        if (exerciseList.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1.5f)
                                ) {
                                    Text(
                                        text = "운동을 추가해주세요",
                                        style = NotoTypography.headlineSmall,
                                        color = LightGray,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    } else {
                        val filteredList =
                            exerciseList.filter { it.category in selectedChipIndices }
                        itemsIndexed(filteredList) { index, it ->
                            AllExerciseListItem(it,
                                insertExercise = insertExercise,
                                deleteExercise = deleteExercise,
                                isItemSelected = {
                                    selectedExercise = it
                                    bottomSheetState = ExerciseBottomSheetState.Selected
                                })
                            Divider(
                                color = LightGray, thickness = 1.dp
                            )
                        }
                        if (filteredList.isEmpty()) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1.5f)
                                ) {
                                    Text(
                                        text = "운동을 추가해주세요",
                                        style = NotoTypography.headlineSmall,
                                        color = LightGray,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }

                var insertCategory by remember { mutableStateOf(ExerciseCategory.CHEST) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .padding(bottom = 105.dp),

                    ) {
                    AddCategoryTextBox(onCategorySelected = {
                        insertCategory = it
                    })
                    ExerciseNameOutlineTextField(
                        isIconVisible = true,
                        originExerciseName = "",
                        insertExerciseName = {
                            insertExercise(ExerciseInfo(id = 0, name = it, insertCategory))
                        },
                        containerColor = BackGray
                    )
                }
            }
        }
    }
}

fun performSetAction(setInfoList: List<SetInfo>, action: SetAction): List<SetInfo> {
    when (action) {
        is SetAction.UpdateKg -> return upDateNewKgList(
            setInfoList,
            action.updatedKg,
            action.toChange
        )

        is SetAction.UpdateReps -> return upDateNewRepsList(
            setInfoList,
            action.updatedReps,
            action.toChange
        )

        is SetAction.DeleteSet -> return deleteSet(setInfoList, action.toChange)

        is SetAction.AddSet -> return addSet(setInfoList)
    }
}

fun upDateNewKgList(setInfoList: List<SetInfo>, updatedKg: Int, toChange: Int): List<SetInfo> {
    if (updatedKg > 0) {
        return setInfoList.mapIndexed { index, setInfo ->
            if (index >= toChange) {
                setInfo.copy(kg = updatedKg)
            } else {
                setInfo
            }
        }
    } else {
        return setInfoList
    }
}

fun upDateNewRepsList(setInfoList: List<SetInfo>, updatedReps: Int, toChange: Int): List<SetInfo> {
    return if (updatedReps > 0) {
        setInfoList.mapIndexed { index, setInfo ->
            if (index >= toChange) {
                setInfo.copy(reps = updatedReps)
            } else {
                setInfo
            }
        }
    } else {
        setInfoList
    }
}

fun deleteSet(setInfoList: List<SetInfo>, toChange: Int): List<SetInfo> {
    val setInfoList = setInfoList.filterIndexed { index, _ -> index != toChange }
    return setInfoList
}

private fun addSet(setInfoList: List<SetInfo>): List<SetInfo> {
    val setInfoList = setInfoList.toMutableList()
    setInfoList.add(SetInfo(20, 12))
    return setInfoList
}

@Composable
fun ExerciseNameOutlineTextField(
    isIconVisible: Boolean,
    originExerciseName: String,
    insertExerciseName: (String) -> Unit,
    containerColor: Color,
) {
    var textState by remember { mutableStateOf(originExerciseName) }
    val focusRequester = remember { FocusRequester() }
    val keyboard = LocalSoftwareKeyboardController.current


    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp)
//            .focusRequester(focusRequester)
            ,
        value = textState,
        onValueChange = {
            textState = it
            if (!isIconVisible) insertExerciseName(it)
        },
        placeholder = {
            Text(
                text = "새 운동 추가", style = NotoTypography.bodyMedium, color = DarkGray
            )
        },
        singleLine = true,
        textStyle = NotoTypography.bodyMedium,
        trailingIcon = {
            if (textState != "" && isIconVisible) Icon(imageVector = Icons.Rounded.Add,
                tint = TextGray,
                contentDescription = "추가",
                modifier = Modifier
                    .border(
                        1.dp, Main, CircleShape
                    )
                    .clickable {
                        insertExerciseName(textState)
                        textState = ""
                    })

        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = TextGray,
            unfocusedTextColor = TextGray,
            focusedBorderColor = Main,
            unfocusedBorderColor = LightGray,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
        ),

        )

//    LaunchedEffect(focusRequester) {
//            focusRequester.requestFocus()
//            keyboard?.show()
//    }
}

@Composable
fun AllExerciseListItem(
    exerciseInfo: ExerciseInfo,
    insertExercise: (ExerciseInfo) -> Unit,
    deleteExercise: (Int) -> Unit,
    isItemSelected: (ExerciseInfo) -> Unit,
) {
    var dialogState by remember { mutableStateOf(ExerciseDialogState.None) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                isItemSelected(exerciseInfo)
            }, onLongPress = {
                dialogState = ExerciseDialogState.Basic
            })
        }) {
        Text(
            text = exerciseInfo.name,
            style = NotoTypography.bodyMedium,
            modifier = Modifier.padding(vertical = 22.dp)
        )
    }

    when (dialogState) {
        ExerciseDialogState.Basic -> ExerciseDialog { dialogState = it }
        ExerciseDialogState.Edit -> EditExerciseDialog(
            exerciseInfo, { dialogState = it }, insertExercise
        )

        ExerciseDialogState.Delete -> DeleteExerciseDialog(
            exerciseInfo, { dialogState = it }, deleteExercise
        )

        else -> {}
    }
}


@Composable
fun ExerciseDialog(
    changeDialogState: (ExerciseDialogState) -> Unit,
) {

    Dialog(onDismissRequest = { changeDialogState(ExerciseDialogState.None) }) {
        Card(
            modifier = Modifier.fillMaxWidth(0.5f),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = BackGray, contentColor = TextGray
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextButton(onClick = {
                    changeDialogState(ExerciseDialogState.Edit)
                }) {
                    Text(
                        text = "수정", style = NotoTypography.bodyMedium,
                    )
                }
                Divider(
                    modifier = Modifier.height(1.dp), color = LightGray
                )
                TextButton(onClick = {
                    changeDialogState(ExerciseDialogState.Delete)
                }) {
                    Text(
                        text = "삭제", style = NotoTypography.bodyMedium,
                    )
                }
            }

        }
    }


}

@Composable
fun EditExerciseDialog(
    exerciseInfo: ExerciseInfo,
    onDismissDialog: (ExerciseDialogState) -> Unit,
    insertExercise: (ExerciseInfo) -> Unit,
) {
    var insertCategory by remember { mutableStateOf(exerciseInfo.category) }
    var insertName by remember { mutableStateOf(exerciseInfo.name) }

    AlertDialog(containerColor = DarkGray,
        onDismissRequest = { onDismissDialog(ExerciseDialogState.None) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),

                ) {
                AddCategoryTextBox(
                    onCategorySelected = {
                        insertCategory = it
                    }
                )
                ExerciseNameOutlineTextField(
                    false,
                    originExerciseName = insertName,
                    insertExerciseName = {
                        insertName = it
                    },
                    containerColor = DarkGray
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                insertExercise(
                    ExerciseInfo(
                        id = exerciseInfo.id, name = insertName, category = insertCategory
                    )
                )
                onDismissDialog(ExerciseDialogState.None)
            }) {
                Text(text = "완료", style = NotoTypography.bodyMedium, color = Main)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissDialog(ExerciseDialogState.None)
            }) {
                Text("취소", style = NotoTypography.bodyMedium, color = TextGray)
            }
        })
}


@Composable
fun DeleteExerciseDialog(
    exerciseInfo: ExerciseInfo,
    onDismissDialog: (ExerciseDialogState) -> Unit,
    deleteExercise: (Int) -> Unit,
) {
    AlertDialog(containerColor = BackGray, text = {
        Text(
            text = "운동을 삭제하면 관련 데이터가 전부 삭제됩니다.",
            style = NotoTypography.bodyMedium,
            color = TextGray
        )
    }, onDismissRequest = {
        onDismissDialog(ExerciseDialogState.None)
    }, confirmButton = {
        TextButton(onClick = {
            deleteExercise(exerciseInfo.id)
            onDismissDialog(ExerciseDialogState.None)
        }) {
            Text(text = "삭제", style = NotoTypography.bodyMedium, color = Red)
        }
    }, dismissButton = {
        TextButton(onClick = {
            onDismissDialog(ExerciseDialogState.None)
        }) {
            Text("취소", style = NotoTypography.bodyMedium, color = TextGray)
        }
    })


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseItem(
    routineDayInfo: RoutineDayInfo,
    insertDayExercise: (DayExercise) -> Unit,
    exercise: Exercise,
    deleteDayExercise: (Int) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var recentId by remember { mutableStateOf(0) }
    var isDeleteMode by remember {
        mutableStateOf(false)
    }
    var selectedExercise by remember {
        mutableStateOf<Exercise>(
            Exercise.Calisthenics(
                "",
                0,
                0,
                0,
                false,
                emptyList(),
                ExerciseCategory.CHEST,
                0,
            )
        )
    }
    if (recentId != exercise.dayExerciseId) {
        isDeleteMode = false
        recentId = exercise.dayExerciseId
    }
    val boxModifier = Modifier
        .fillMaxSize()
        .aspectRatio(1f / 1f)
        .padding(16.dp)
        .background(BackGray, CircleShape)
        .border(4.dp, if (isDeleteMode) Red else Main, CircleShape)
        .padding(16.dp)
        .clip(CircleShape)

    LaunchedEffect(showBottomSheet) {
        selectedExercise = exercise
    }

    when (exercise) {
        is Exercise.Weight -> {
            Box(
                boxModifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (!isDeleteMode) showBottomSheet = true
                        },
                        onLongPress = {
                            isDeleteMode = true
                        }
                    )
                }
            ) {
                if (!isDeleteMode) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = exercise.name,
                            style = NotoTypography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        if (exercise.min != exercise.max) {
                            Text(
                                text = "min : ${exercise.min}",
                                style = NotoTypography.labelMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = "max : ${exercise.max}",
                                style = NotoTypography.labelMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        } else {
                            Text(
                                text = "${exercise.min}kg",
                                style = NotoTypography.labelMedium,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                        }
                    }
                } else {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        IconButton(onClick = { deleteDayExercise(exercise.dayExerciseId) }) {
                            Icon(
                                imageVector = IconPack.Close,
                                contentDescription = "취소",
                                tint = TextGray
                            )
                        }
                    }
                }
            }
        }

        is Exercise.Calisthenics -> {
            Box(boxModifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    showBottomSheet = true
                }, onLongPress = {
                    isDeleteMode = true
                })
            }) {
                if (!isDeleteMode) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Text(
                            text = exercise.name,
                            style = NotoTypography.bodyMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "${exercise.reps}",
                            style = NotoTypography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "${exercise.set}set",
                            style = NotoTypography.labelMedium,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        IconButton(onClick = { deleteDayExercise(exercise.dayExerciseId) }) {
                            Icon(
                                imageVector = IconPack.Close,
                                contentDescription = "취소",
                                tint = TextGray
                            )
                        }
                    }
                }
            }
        }
    }

    if (showBottomSheet) {
        InsertDayExerciseBottomSheet(
            onDismissSheet = { showBottomSheet = it },
            sheetState = sheetState,
            insertDayExercise = insertDayExercise,
            routineDayInfo = routineDayInfo,
            exercise = selectedExercise,
            deleteDayExercise = deleteDayExercise
        )
    }

    LaunchedEffect(isDeleteMode) {
        if (isDeleteMode) {
            delay(3000)
            isDeleteMode = false
        }
    }
}

@Composable
fun ExerciseCardRow(
    selectedDayOfWeek: Int,
    routineDayList: List<RoutineDayInfo>,
    widthDp: Int,
    onCardClicked: (Int) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow (state = LazyListState(firstVisibleItemIndex = selectedDayOfWeek-1)){
        itemsIndexed(routineDayList) { index, routineDayInfo ->
            Box(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(top = 16.dp, bottom = 24.dp)
            ) {
                ExerciseCardView(
                    isSelected = (index+1 == selectedDayOfWeek),
                    routineDayInfo = routineDayInfo,
                    widthDp = widthDp,
                    onCardClicked = {
                        // 클릭 시 선택한 dayOfWeek를 콜백으로 전달
                        onCardClicked(it)
                    })
            }
        }
//        coroutineScope.launch {
//            scrollState.scrollToItem(getDayOfWeekNumber()-1)
//        }
    }
//    val todayDayOfWeek = getDayOfWeekNumber()-1
//
//    LaunchedEffect(todayDayOfWeek){
//    }
}

@Composable
fun CountDate(
    cnt: Int,
) {
    Row {
        Text(text = "+ ", style = HortaTypography.headlineSmall)
        Text(text = cnt.toString(), style = HortaTypography.headlineSmall, color = Main)
        Text(text = " days ", style = HortaTypography.headlineSmall)
    }
}

@Composable
fun AddCategoryTextBox(
    onCategorySelected: (ExerciseCategory) -> Unit,
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(1) }
    Row(
        Modifier
            .width(80.dp)
            .border(1.dp, LightGray, RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = ExerciseCategory.toName(selectedCategory), style = NotoTypography.bodySmall,
            modifier = Modifier
                .padding(vertical = 16.dp)
                .padding(start = 16.dp)
        )
        Row {
            Icon(
                Icons.Filled.ArrowDropDown,
                null,
                tint = TextGray,
                modifier = Modifier
                    .rotate(if (isDropDownMenuExpanded) 180f else 0f)
                    .clickable {
                        isDropDownMenuExpanded = !isDropDownMenuExpanded
                    }
            )
            Spacer(modifier = Modifier.padding(end = 8.dp))
        }
    }
    DropdownMenu(modifier = Modifier
        .width(80.dp)
        .background(DarkGray),
        expanded = isDropDownMenuExpanded,
        onDismissRequest = {
            isDropDownMenuExpanded = false
        }) {
        val list = (1..6).toList()
        list.forEachIndexed { index, it ->
            DropdownMenuItem(modifier = Modifier.wrapContentSize(), text = {
                Text(
                    ExerciseCategory.toName(it),
                    style = NotoTypography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }, onClick = {
                onCategorySelected(ExerciseCategory.fromId(it)!!)
                selectedCategory = it // 선택된 카테고리를 저장
                isDropDownMenuExpanded = false // 드롭다운 닫기
            })
            if (index < list.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                        .height(1.dp),
                    color = LightGray
                )
            }
        }
    }
}


@Composable
fun AddCategoryBtn(
    routineDayInfo: RoutineDayInfo,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
) {
    var isDropDownMenuExpanded by remember { mutableStateOf(false) }
    var selectedCategory: Int by remember { mutableStateOf(-1) } // 추가

    Box(modifier = Modifier
        .padding(end = 8.dp)
        .border(1.dp, DarkGray, CircleShape)
        .padding(
            start = 24.dp, end = 24.dp, top = 15.dp, bottom = 15.dp
        )
        .clickable {
            isDropDownMenuExpanded = true
        }) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = null,
            tint = TextGray,
            modifier = Modifier.size(10.dp)
        )
    }

    DropdownMenu(modifier = Modifier
        .width(60.dp)
        .background(DarkGray),
        expanded = isDropDownMenuExpanded,
        onDismissRequest = {
            isDropDownMenuExpanded = false
        }) {
        val list = (1..6).toList() - routineDayInfo.categoryList.toSet()

        list.forEachIndexed { index, it ->
            DropdownMenuItem(modifier = Modifier.wrapContentSize(), text = {
                Text(
                    ExerciseCategory.toName(it),
                    style = NotoTypography.labelMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }, onClick = {
                selectedCategory = it // 선택된 카테고리를 저장
                isDropDownMenuExpanded = false // 드롭다운 닫기
            })
            if (index < list.size - 1) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp)
                        .height(1.dp),
                    color = LightGray
                )
            }
        }
    }

    DisposableEffect(selectedCategory) {
        if (selectedCategory != -1 && !routineDayInfo.categoryList.contains(selectedCategory)) {
            val newCategoryList = routineDayInfo.categoryList + selectedCategory
            val newRoutineDayInfo = routineDayInfo.copy(categoryList = newCategoryList)
            insertRoutineDay(newRoutineDayInfo)
        }
        onDispose { }
    }
}

@Composable
fun RoutineName(name: String, insertRoutine: (String) -> Unit) {
    var showEditDialog by remember {
        mutableStateOf(false)
    }
    Row {
        Text(text = name, style = NotoTypography.headlineMedium)
        IconButton(onClick = { showEditDialog = true }) {
            Icon(imageVector = Icons.Rounded.Edit, contentDescription = "루틴 이름 변경", tint = LightGray)
        }
    }
    if (showEditDialog) {
        EditRoutineNameDialog(name, { showEditDialog = it }, {insertRoutine(it)})
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditRoutineNameDialog(
    routineName: String,
    onDismissDialog: (Boolean) -> Unit,
    insertRoutine: (String) -> Unit,
) {
    var insertName by remember {
        mutableStateOf(routineName)
    }
    var errText by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(insertName) {
        if (insertName.isNotEmpty()||insertName.length < 9)
            errText = false
    }

    AlertDialog(containerColor = DarkGray,
        onDismissRequest = { onDismissDialog(false) },
        text = {
            Column {
                ExerciseNameOutlineTextField(
                    false,
                    originExerciseName = insertName,
                    insertExerciseName = {
                        insertName = it
                    },
                    containerColor = DarkGray
                )
                if (errText)
                    Text(
                        text = "루틴 이름을 1글자 이상 8글자 이하로 설정해주세요",
                        style = NotoTypography.labelMedium,
                        color = Red,
                        modifier = Modifier.padding(top = 4.dp, start = 10.dp)
                    )
            }
        },
        confirmButton = {
            TextButton(onClick =
            {
                if (insertName.isNotEmpty() && insertName.length < 9) {
                    insertRoutine(
                        insertName
                    )
                    onDismissDialog(false)
                } else errText = true
            }) {
                Text(text = "완료", style = NotoTypography.bodyMedium, color = Main)
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismissDialog(false)
            }) {
                Text("취소", style = NotoTypography.bodyMedium, color = TextGray)
            }
        })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownRoutineList(routineList: List<String>, modifier: Modifier) {

    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(routineList[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded, onExpandedChange = { expanded = it }, modifier = modifier
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
    isSelected:Boolean,
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Text(
                text = getDayOfWeek(routineDayInfo.dayOfWeek),
                style = NotoTypography.bodyMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
            LazyRow {
                item {
                    CategoryList(false, routineDayInfo, 8, {})
                }
            }

        }
        Box(
            modifier = Modifier
                .aspectRatio(8f / 7f)
                .background(DarkGray, RoundedCornerShape(10.dp))
                .border(
                    2.dp,
                    if (isSelected) Main else Color.Transparent,
                    RoundedCornerShape(10.dp)
                )
                .clickable {
                    onCardClicked(routineDayInfo.dayOfWeek) // 클릭 시 콜백 호출
                }
                .padding(16.dp),
        ) {
            if (routineDayInfo.exerciseList.isNotEmpty()) {
                LazyColumn(
                    Modifier.heightIn(max = (routineDayInfo.exerciseList.size * 30).dp)
                ) {
                    items(routineDayInfo.exerciseList) {
                        ExerciseList(exercise = it)
                    }
                }
            } else {
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
    }
}


@Composable
fun CategoryList(
    isCanDelete: Boolean,
    routineDayInfo: RoutineDayInfo,
    size: Int,
    insertRoutineDay: (RoutineDayInfo) -> Unit,
) {
    Row{
        routineDayInfo.categoryList?.forEach {
            CategoryChip(
                isCanDelete, ExerciseCategory.toName(it), it, size
            ) { selectedCategory ->
                if (routineDayInfo.categoryList.contains(selectedCategory)) {
                    val newCategoryList = routineDayInfo.categoryList - selectedCategory
                    val newRoutineDayInfo = routineDayInfo.copy(categoryList = newCategoryList)
                    insertRoutineDay(newRoutineDayInfo)
                }
            }
        }
    }
}

@Composable
fun ExerciseList(
    exercise: Exercise,
) {
    Column {
        val name = when (exercise) {
            is Exercise.Weight -> exercise.name
            is Exercise.Calisthenics -> exercise.name
        }
        ExerciseText(name)
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

