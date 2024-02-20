package com.colddelight.exercisedetail

import android.content.Context
import android.os.CountDownTimer
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.component.BigSetButton
import com.colddelight.designsystem.component.ui.CategoryIconList
import com.colddelight.designsystem.component.ui.DoneExerciseDetailItem
import com.colddelight.designsystem.component.EditText
import com.colddelight.designsystem.component.ui.ExerciseDetailItem
import com.colddelight.designsystem.component.ui.ExerciseProgress
import com.colddelight.designsystem.component.MainButton
import com.colddelight.designsystem.component.ui.SetAction
import com.colddelight.designsystem.component.ui.TitleText
import com.colddelight.designsystem.icons.IconPack
import com.colddelight.designsystem.icons.iconpack.Minus
import com.colddelight.designsystem.icons.iconpack.Plus
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.Red
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.exercise.ExerciseDetailUiState
import com.colddelight.exercise.ExerciseUiState
import com.colddelight.exercise.ExerciseViewModel
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo

@Composable
fun ExerciseDetailScreen(
    onDoneButtonClick: () -> Unit,
    viewModel: ExerciseViewModel = hiltViewModel()
) {
    val dataUiState by viewModel.exerciseUiState.collectAsStateWithLifecycle()
    val uiState by viewModel.exerciseDetailUiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = BackGray
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
                },
                {
                    viewModel.setDone()
                    onDoneButtonClick()
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
    onDoneButtonClick: () -> Unit,

    ) {
    when (dataUiState) {
        is ExerciseUiState.Loading -> {}
        is ExerciseUiState.Error -> Text(text = dataUiState.msg)
        is ExerciseUiState.Success -> ExerciseDetailContent(
            uiState,
            dataUiState.exerciseList[dataUiState.curIndex],
            uiState.curSet,
            setAction,
            updateUiState,
            onDoneButtonClick
        )
    }
}

@Composable
private fun ExerciseDetailContent(
    uiState: ExerciseDetailUiState,
    exercise: Exercise,
    curSet: Int,
    setAction: (SetAction) -> Unit,
    updateUiState: (newState: ExerciseDetailUiState) -> Unit,
    onDoneButtonClick: () -> Unit,
) {
    val lazyColumnState = rememberLazyListState()
    val density = LocalDensity.current
    val itemSizePx = with(density) { 100.dp.toPx() }
    val coroutineScope = rememberCoroutineScope()


    val focusManager = LocalFocusManager.current

    fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
        clickable(indication = null,
            interactionSource = remember { MutableInteractionSource() }) {
            onClick()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 16.dp)
            .noRippleClickable {
                focusManager.clearFocus()
            },
        state = lazyColumnState
    ) {


        item {
            ExerciseInfo(exercise, Modifier)
        }
        item {
            TitleText(text = "Set", modifier = Modifier.padding(top = 8.dp))
        }
        item {
            val curCnt =
                if (curSet + 1 >= exercise.setInfoList.size) exercise.setInfoList.size else curSet + 1

            Row(Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = curCnt.toString(),
                    style = NotoTypography.bodyMedium,
                    color = Main,
                )
                Text(
                    text = " / ${exercise.setInfoList.size}",
                    style = NotoTypography.bodyMedium,
                )
            }
        }
        item {
            ExerciseProgress(Modifier.fillMaxWidth(), curSet, exercise.setInfoList.size, true)
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                Alignment.Center,
            ) {
                when (uiState) {
                    is ExerciseDetailUiState.Default -> CurrentSetButtons(
                        exercise.category != ExerciseCategory.CALISTHENICS,
                        exercise.setInfoList[curSet],
                        true,
                        setAction,
                        curSet,
                        focusManager
                    )

                    is ExerciseDetailUiState.During -> CurrentSetButtons(
                        exercise.category != ExerciseCategory.CALISTHENICS,
                        exercise.setInfoList[curSet],
                        false,
                        setAction,
                        curSet,
                        focusManager
                    )

                    is ExerciseDetailUiState.Resting -> Timer()

                    is ExerciseDetailUiState.Done -> DoneSetButton(onDoneButtonClick)
                }
            }
        }
        item {
            SetButtonWithState(uiState, updateUiState)
        }

        itemsIndexed(exercise.setInfoList) { index, item ->
            if (curSet > index) {
                DoneExerciseDetailItem(
                    item.kg,
                    item.reps,
                    exercise.category != ExerciseCategory.CALISTHENICS
                )
            } else {
                ExerciseDetailItem(
                    item.kg,
                    item.reps,
                    index,
                    exercise.category != ExerciseCategory.CALISTHENICS,
                    focusManager,
                    setAction,
                )
            }
            if (curSet == index || curSet - 1 == index) {
                Divider(color = Main, thickness = 2.dp)
            } else {
                Divider(
                    color = DarkGray,
                    thickness = 2.dp
                )
            }
        }
//        item {
//            ClickableText(
//                text = AnnotatedString("+ 세트 추가"),
//                style = NotoTypography.headlineSmall.copy(color = DarkGray),
//                onClick = {
//                    setAction(SetAction.AddSet)
//                    coroutineScope.launch {
//                        lazyColumnState.animateScrollBy(
//                            value = itemSizePx * lazyColumnState.layoutInfo.totalItemsCount.toFloat(),
//                            animationSpec = tween(durationMillis = 2000)
//                        )
//                    }
//                })
//        }

    }
}


@Composable
fun DoneSetButton(onDoneButtonClick: () -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = Main,
        ),
        onClick = { onDoneButtonClick() },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
            .background(Main, shape = CircleShape)

    ) {
        Text(text = "세트종료", style = NotoTypography.headlineMedium, color = Color.White)
    }
}

@Composable
fun SetButtonWithState(
    uiState: ExerciseDetailUiState,
    updateUiState: (newState: ExerciseDetailUiState) -> Unit,
) {
    when (uiState) {
        is ExerciseDetailUiState.Default -> SetButton("세트시작") {
            updateUiState(ExerciseDetailUiState.During(uiState.curSet))
        }

        is ExerciseDetailUiState.During -> SetButton("세트종료") {
            updateUiState(ExerciseDetailUiState.Resting(uiState.curSet))
        }

        is ExerciseDetailUiState.Resting -> SetButton("휴식종료") {
            updateUiState(ExerciseDetailUiState.Default(uiState.curSet + 1))
        }

        is ExerciseDetailUiState.Done -> {}
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
fun Timer() {
    var timerSeconds by remember { mutableIntStateOf(90) }
    var progress by remember { mutableFloatStateOf(0f) }

    var newTime by remember { mutableIntStateOf(90) }


    val context = LocalContext.current

    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BigSetButton(IconPack.Minus) {
            if (timerSeconds - 30 > 0) {
                timerSeconds -= 30
                newTime = timerSeconds

            }
        }
        CircularTimer(timerSeconds, progress)

        BigSetButton(IconPack.Plus) {
            timerSeconds += 30
            newTime = timerSeconds

        }
    }

    DisposableEffect(newTime) {
        val timer = object : CountDownTimer(newTime * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                timerSeconds = (millisUntilFinished / 1000).toInt()
                progress = 1f - timerSeconds.toFloat() / newTime.toFloat()
            }

            override fun onFinish() {
                vibrator.vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(200, 500),
                        0
                    )
                )
            }
        }
        timer.start()

        onDispose {
            vibrator.cancel()
            timer.cancel()
        }

    }

}

@Composable
fun CircularTimer(timerSeconds: Int, progress: Float) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(0.7f)
            .aspectRatio(1f)
            .background(BackGray, shape = CircleShape),
        Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawArc(
                color = DarkGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 5.dp.toPx())
            )
            drawArc(
                color = if (timerSeconds > 5) Main else Red,
                startAngle = -90f,
                sweepAngle = 360 * progress,
                useCenter = false,
                style = Stroke(width = 5.dp.toPx())
            )
        }
        Text(
            text = "${timerSeconds / 60}:${(timerSeconds % 60).toString().padStart(2, '0')}",
            style = NotoTypography.headlineMedium
        )
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
@Preview
@Composable
fun SetPreview() {
    ExerciseDetailContent(curSet = 0,
        exercise = Exercise.Weight(
            "벤치 프레스", 888, 888, 1, true,
            setInfoList = listOf(SetInfo(20, 12), SetInfo(40, 12), SetInfo(60, 12))
        ),
        uiState = ExerciseDetailUiState.Default(0),
        setAction = {},
        updateUiState = {},
        onDoneButtonClick = {})
}

@Composable
private fun CurrentSetButtons(
    isWeight: Boolean,
    setInfo: SetInfo,
    showSetButton: Boolean,
    setAction: (SetAction) -> Unit,
    curIndex: Int,
    focusManager: FocusManager,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        if (isWeight) {
            SingleSetButtons(
                setInfo.kg,
                10,
                true,
                showSetButton,
                150,
                setAction,
                curIndex,
                focusManager
            )
        }
        SingleSetButtons(
            setInfo.reps,
            1,
            false,
            showSetButton,
            150,
            setAction,
            curIndex,
            focusManager
        )
    }
}

@Composable
private fun SingleSetButtons(
    target: Int,
    base: Int,
    isKg: Boolean,
    showSetButton: Boolean,
    size: Int,
    setAction: (SetAction) -> Unit,
    curIndex: Int,
    focusManager: FocusManager,

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
            Row {
                EditText(
                    data = target.toString(),
                    focusManager = focusManager,
                    style = NotoTypography.headlineMedium,
                    modifier = Modifier.width(70.dp),
                    color = TextGray
                ) { new ->
                    if (isKg) setAction(SetAction.UpdateKg(new, curIndex))
                    else setAction(SetAction.UpdateReps(new, curIndex))
                }
                Text(text = if (isKg) "kg" else "회", style = NotoTypography.headlineMedium)
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