package com.colddelight.history

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.R
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.DarkGray
import com.colddelight.designsystem.theme.HortaTypography
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.Red
import com.colddelight.designsystem.theme.TextGray
import com.colddelight.model.DayHistory
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.kizitonwose.calendar.compose.CalendarLayoutInfo
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.core.nextMonth
import com.kizitonwose.calendar.core.previousMonth
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val historyUiState by historyViewModel.historyUiState.collectAsStateWithLifecycle()
    val dayHistoryExerciseList by historyViewModel.dayHistoryExerciseList.collectAsStateWithLifecycle()

    Log.e("TAG", "HistoryScreen: ${historyUiState}")

    Scaffold(
        modifier = Modifier.fillMaxSize(), containerColor = BackGray
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            HistoryContentWithState(
                uiState = historyUiState,
                dayHistoryExerciseList = dayHistoryExerciseList,
                onMonthChanged = { historyViewModel.upDateMonth(it) },
                onDaySelected = {
                    historyViewModel.getDayHistoryExercise(it)
                    Log.e("TAG", "HistoryScreen:${dayHistoryExerciseList} ")
                },
            )
        }
    }
}

@Composable
private fun HistoryContentWithState(
    uiState: HistoryUiState,
    dayHistoryExerciseList: List<Exercise>,
    onMonthChanged: (LocalDate) -> Unit,
    onDaySelected: (Int) -> Unit,
) {
    when (uiState) {
        is HistoryUiState.Loading -> {}
        is HistoryUiState.Error -> Text(text = uiState.msg)
        is HistoryUiState.Success -> HistoryContent(
            uiState.historyList,
            dayHistoryExerciseList,
            onMonthChanged,
            onDaySelected,
        )
    }
}

@Composable
private fun HistoryContent(
    historyList: List<DayHistory>,
    dayHistoryExerciseList: List<Exercise>,
    onMonthChanged: (LocalDate) -> Unit,
    onDaySelected: (Int) -> Unit,
) {
    var initMonth = YearMonth.now()
    var currentMonth = remember { initMonth }
    val startMonth = remember { initMonth.minusMonths(100) } // Adjust as needed
    val endMonth = remember { initMonth.plusMonths(100) } // Adjust as needed
    val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

    val state = rememberCalendarState(
        startMonth = startMonth,
        endMonth = endMonth,
        firstVisibleMonth = currentMonth,
        firstDayOfWeek = firstDayOfWeek
    )

    val daysOfWeek = daysOfWeek(firstDayOfWeek = DayOfWeek.MONDAY)
    val coroutineScope = rememberCoroutineScope()
    val visibleMonth = rememberFirstMostVisibleMonth(state, viewportPercent = 90f)

    var isDaySelected by remember { mutableStateOf(false) }
    var selectedDay by remember {
        mutableStateOf(
            DayHistory(
                LocalDate.now(),
                emptyList()
            )
        )
    }

    LazyColumn {
        item {
            Log.e("TAG", "HistoryContent: $historyList")
            SimpleCalendarTitle(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        isDaySelected = false
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                        onMonthChanged(state.firstVisibleMonth.yearMonth.atDay(1))
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        isDaySelected = false
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                        onMonthChanged(state.firstVisibleMonth.yearMonth.atDay(1))
                    }
                },
            )
            HorizontalCalendar(state = state, dayContent = { calendarDay ->
                val hasHistory = historyList.any { it.createdTime == calendarDay.date }
                val matchingHistories = historyList.find { it.createdTime == calendarDay.date }
                Day(
                    calendarDay,
                    hasHistory,
                    matchingHistories ?: DayHistory(calendarDay.date, emptyList(), 0)
                ) {
                    isDaySelected = true
                    onDaySelected(it.id)
                    selectedDay = it
                }
            }, monthHeader = {
                DaysOfWeekTitle(daysOfWeek = daysOfWeek)
            })
        }
        if (isDaySelected) item {
            Divider(color = DarkGray, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = selectedDay.createdTime.toString(), style = HortaTypography.headlineSmall)
                CategoryIconList(selectedDay.categoryList.map { ExerciseCategory.fromId(it)!! })
                dayHistoryExerciseList.map {
                    Text(text = it.name)
                    it.setInfoList.map { setInfo ->
                        Text(text = "${setInfo.kg} X ${setInfo.reps}")
                    }
                }
            }
        }
    }
}

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


@Composable
fun Day(
    day: CalendarDay,
    hasHistory: Boolean,
    dayHistory: DayHistory,
    onClick: (DayHistory) -> Unit,
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(
                if (hasHistory) Main else Color.Transparent, CircleShape
            )
            .clickable(enabled = (day.position == DayPosition.MonthDate) && hasHistory, onClick = {
                onClick(dayHistory)
            }), contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = NotoTypography.labelLarge,
            color = if (day.position == DayPosition.MonthDate) {
                if (hasHistory) BackGray else TextGray
            } else LightGray
        )
    }
}

@Composable
fun DaysOfWeekTitle(daysOfWeek: List<DayOfWeek>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = NotoTypography.bodySmall,
                text = dayOfWeek.getDisplayName(TextStyle.NARROW, Locale.getDefault()),
                color = if (dayOfWeek == DayOfWeek.SUNDAY || dayOfWeek == DayOfWeek.SATURDAY) Red else TextGray
            )
        }
    }
}


@Composable
fun SimpleCalendarTitle(
    modifier: Modifier,
    currentMonth: YearMonth,
    goToPrevious: () -> Unit,
    goToNext: () -> Unit,
) {
    Row(
        modifier = modifier.height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CalendarNavigationIcon(
            icon = Icons.Filled.KeyboardArrowLeft,
            contentDescription = "Previous",
            onClick = goToPrevious,
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .testTag("MonthTitle"),
            text = currentMonth.toString(),
            style = NotoTypography.headlineSmall,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
        )
        CalendarNavigationIcon(
            icon = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Next",
            onClick = goToNext,
        )
    }
}

@Composable
fun rememberFirstMostVisibleMonth(
    state: CalendarState,
    viewportPercent: Float = 50f,
): CalendarMonth {
    val visibleMonth = remember(state) { mutableStateOf(state.firstVisibleMonth) }
    LaunchedEffect(state) {
        snapshotFlow { state.layoutInfo.firstMostVisibleMonth(viewportPercent) }.filterNotNull()
            .collect { month -> visibleMonth.value = month }
    }
    return visibleMonth.value
}

private fun CalendarLayoutInfo.firstMostVisibleMonth(viewportPercent: Float = 50f): CalendarMonth? {
    return if (visibleMonthsInfo.isEmpty()) {
        null
    } else {
        val viewportSize = (viewportEndOffset + viewportStartOffset) * viewportPercent / 100f
        visibleMonthsInfo.firstOrNull { itemInfo ->
            if (itemInfo.offset < 0) {
                itemInfo.offset + itemInfo.size >= viewportSize
            } else {
                itemInfo.size - itemInfo.offset >= viewportSize
            }
        }?.month
    }
}

@Composable
private fun CalendarNavigationIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) = Box(
    modifier = Modifier
        .fillMaxHeight()
        .aspectRatio(1f)
        .clip(shape = CircleShape)
        .clickable(role = Role.Button, onClick = onClick),
) {
    Icon(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .align(Alignment.Center),
        imageVector = icon,
        tint = TextGray,
        contentDescription = contentDescription,
    )
}