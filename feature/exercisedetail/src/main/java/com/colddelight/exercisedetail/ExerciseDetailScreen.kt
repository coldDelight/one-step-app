package com.colddelight.exercisedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.component.BigSetButton
import com.colddelight.designsystem.component.ExerciseProgress
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.SmallSetButton
import com.colddelight.designsystem.component.TitleText
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Minus
import com.colddelight.designsystem.icons.iconpack.Plus
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.exercise.CategoryIconList
import com.colddelight.exercise.ExerciseDetailUiState
import com.colddelight.exercise.ExerciseUiState
import com.colddelight.exercise.ExerciseViewModel
import com.colddelight.exercise.SetAction
import com.colddelight.model.Exercise
import com.colddelight.model.SetInfo

@Composable
fun ExerciseDetailScreen(
    viewModel: ExerciseViewModel = hiltViewModel(),
) {
    val dataUiState by viewModel.exerciseUiState.collectAsStateWithLifecycle()
    val uiState by viewModel.exerciseDetailUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            ExerciseDetailContentWithState(
                dataUiState = dataUiState,
                uiState = uiState,
                { setAction -> viewModel.performSetAction(setAction) },
                { newState ->
                    viewModel.updateDetailUiState(newState)
                }
            )
        }
    }
}

@Composable
private fun ExerciseDetailContentWithState(
    dataUiState: ExerciseUiState,
    uiState: ExerciseDetailUiState,
    setAction: (SetAction) -> Unit,
    updateUiState: (newState: ExerciseDetailUiState) -> Unit,
) {
    when (dataUiState) {
        is ExerciseUiState.Loading -> {}
        is ExerciseUiState.Error -> Text(text = dataUiState.msg)
        is ExerciseUiState.Success -> ExerciseDetailContent(
            uiState,
            dataUiState.exerciseList[dataUiState.curIndex],
            dataUiState.curSetIndex,
            setAction,
            updateUiState
        )
    }
}

@Composable
private fun ExerciseDetailContent(
    uiState: ExerciseDetailUiState,
    exercise: Exercise,
    curSetIndex: Int,
    setAction: (SetAction) -> Unit,
    updateUiState: (newState: ExerciseDetailUiState) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        item {
            ExerciseInfo(exercise, Modifier)
        }
        item {
            ExerciseProgress(Modifier.fillMaxWidth(), curSetIndex, exercise.setInfoList.size)
        }
        item {
            TitleText(text = "Set", modifier = Modifier.padding(top = 8.dp))
        }
        item {
            when (uiState) {
                is ExerciseDetailUiState.Default -> CurrentSetButtons(
                    exercise.setInfoList[curSetIndex],
                    true,
                    setAction,
                    curSetIndex
                )

                is ExerciseDetailUiState.During -> CurrentSetButtons(
                    exercise.setInfoList[curSetIndex],
                    false,
                    setAction,
                    curSetIndex
                )

                is ExerciseDetailUiState.Resting -> {}
                is ExerciseDetailUiState.Done -> {}
            }
        }
        item {
            SetButtonWithState(uiState, updateUiState)
        }
        itemsIndexed(exercise.setInfoList) { index, item ->
            ExerciseDetailItem(item.kg, item.reps, index, setAction)
            if (curSetIndex == index || curSetIndex - 1 == index) {
                Divider(color = Main, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
            } else {
                Divider(
                    color = DarkGray,
                    modifier = Modifier.padding(top = 16.dp),
                    thickness = 2.dp
                )
            }
        }
        item {
            MainButton(onClick = { setAction(SetAction.AddSet) }) {
                Text(text = "세트 추가")
            }
        }
    }
}

@Composable
fun SetButtonWithState(
    uiState: ExerciseDetailUiState,
    updateUiState: (newState: ExerciseDetailUiState) -> Unit,
) {
    when (uiState) {
        is ExerciseDetailUiState.Default -> SetButton("세트시작") {
            updateUiState(ExerciseDetailUiState.During)
        }

        is ExerciseDetailUiState.During -> SetButton("세트종료") {
            updateUiState(ExerciseDetailUiState.Resting)
        }

        is ExerciseDetailUiState.Resting -> SetButton("휴식종료") {
            updateUiState(ExerciseDetailUiState.Default)
        }

        is ExerciseDetailUiState.Done -> SetButton("마지막") {

        }
    }

}

@Composable
fun SetButton(text: String, onClick: () -> Unit) {
    MainButton(
        onClick, content = {
            Text(
                text,
                style = NotoTypography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(2.dp)
            )
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Composable
fun ExerciseDetailItem(
    kg: Int, reps: Int, index: Int,
    setAction: (SetAction) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SmallSetButton(Icons.Filled.Clear) {
                setAction(SetAction.DeleteSet(index))
            }
            SmallSetButton(IconPack.Minus) {
                setAction(SetAction.UpdateKg(kg - 10, index))
            }
            Text(text = "${kg}kg", style = HortaTypography.bodyMedium)
            SmallSetButton(IconPack.Plus) {
                setAction(SetAction.UpdateKg(kg + 10, index))
            }
            SmallSetButton(IconPack.Minus) {
                setAction(SetAction.UpdateReps(reps - 1, index))
            }
            Text(text = "${reps}reps", style = HortaTypography.bodyMedium)
            SmallSetButton(IconPack.Plus) {
                setAction(SetAction.UpdateReps(reps + 1, index))
            }
        }
    }
}

@Composable
private fun ExerciseInfo(exercise: Exercise, modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = exercise.name, style = NotoTypography.headlineMedium)
        CategoryIconList(listOf(exercise.category))
    }
}


//@Preview(name = "SMALL", device = Devices.PIXEL)
////@Preview(name = "BIG", device = Devices.PIXEL_4_XL)
////@Preview(name = "FOLDABLE", device = Devices.FOLDABLE)
//@Composable
//fun SetPreview() {
//    ExerciseDetailContent(curSetIndex = 0, exercise = Exercise.Weight(
//        "벤치 프레스", 20, 40, 1, true,
//        setInfoList = listOf(SetInfo(20, 12), SetInfo(40, 12), SetInfo(60, 12))
//    ), uiState = ExerciseDetailUiState.Default, setAction = {}, updateUiState = {})
//}

@Composable
private fun CurrentSetButtons(
    setInfo: SetInfo,
    showSetButton: Boolean,
    setAction: (SetAction) -> Unit,
    curIndex: Int
) {
//    val fixedItemSize = with(LocalDensity.current) { 40.dp.toPx() }
//    val fixedHeightSize = with(LocalDensity.current) { 80.dp.toPx() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
//            .height(fixedHeightSize.dp)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SingleSetButtons(setInfo.kg, 10, true, showSetButton, 150, setAction, curIndex)
        SingleSetButtons(setInfo.reps, 1, false, showSetButton, 150, setAction, curIndex)
    }
}

@Preview
@Composable
fun SetPreview() {
    CurrentSetButtons(SetInfo(50, 20), true, {}, 0)
}

@Composable
private fun SingleSetButtons(
    target: Int,
    base: Int,
    isKg: Boolean,
    showSetButton: Boolean,
    size: Int,
    setAction: (SetAction) -> Unit,
    curIndex: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    )
    {
        if (showSetButton) {
            BigSetButton(IconPack.Plus) {
                if (isKg) {
                    setAction(SetAction.UpdateKg(target + base, curIndex))
                } else {
                    setAction(SetAction.UpdateReps(target + base, curIndex))
                }
            }
        }
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(BackGray, shape = CircleShape)
                .border(
                    width = 3.dp,
                    color = Main,
                    shape = CircleShape
                ),
            Alignment.Center
        ) {
            if (isKg) {
                Text("${target}kg", style = NotoTypography.headlineMedium)
            } else {
                Text("${target}회", style = NotoTypography.headlineMedium)
            }
        }
        if (showSetButton) {
            BigSetButton(IconPack.Minus) {
                if (isKg) {
                    setAction(SetAction.UpdateKg(target - base, curIndex))
                } else {
                    setAction(SetAction.UpdateReps(target - base, curIndex))
                }
            }
        }
    }
}