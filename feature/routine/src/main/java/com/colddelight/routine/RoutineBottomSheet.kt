package com.colddelight.routine

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.colddelight.designsystem.component.ExerciseDetailItem
import com.colddelight.designsystem.component.FilterChip
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.RedButton
import com.colddelight.designsystem.component.SetAction
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Topback
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.DayExercise
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.RoutineDayInfo
import com.colddelight.model.SetInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertDayExerciseBottomSheet(
    onDismissSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    insertDayExercise: (DayExercise) -> Unit,
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
                ExerciseDetailItem(
                    item.kg,
                    item.reps,
                    index,
                    exercise.category != ExerciseCategory.CALISTHENICS
                ) { setAction ->
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
    var setInfoList by remember { mutableStateOf(List(5) { SetInfo(20, 12) }) }

    val lazyColumnState = rememberLazyListState()
    val density = LocalDensity.current
    val itemSizePx = with(density) { 100.dp.toPx() }
    val coroutineScope = rememberCoroutineScope()
    var insertCategory by remember { mutableStateOf(ExerciseCategory.CHEST) }

    ModalBottomSheet(
        onDismissRequest = { onDismissSheet(false) },
        sheetState = sheetState,
        containerColor = BackGray,
    ) {

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
                        ExerciseDetailItem(
                            item.kg,
                            item.reps,
                            index,
                            selectedExercise.category != ExerciseCategory.CALISTHENICS
                        ) { setAction ->
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

                    item {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 45.dp),

                            ) {
                            AddCategoryTextBox(onCategorySelected = {
                                insertCategory = it
                            })
                            ExerciseNameOutlineTextField(
                                initKeyboardOpen = false,
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
    }
}

