package com.colddelight.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.colddelight.designsystem.theme.BackGray
import com.colddelight.designsystem.theme.LightGray
import com.colddelight.designsystem.theme.Main
import com.colddelight.designsystem.theme.NotoTypography
import com.colddelight.designsystem.theme.Red
import com.colddelight.designsystem.theme.TextGray
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun HistoryScreen(
    historyViewModel: HistoryViewModel = hiltViewModel(),
) {
    val historyList by historyViewModel.historyList.collectAsStateWithLifecycle()

    Log.e("TAG", "HistoryScreen: ${historyList}")

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



    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(BackGray)
        ) {
            Row {
                IconButton(onClick = { currentMonth = currentMonth.minusMonths(1) }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "이전",
                        tint = TextGray,
                        modifier = Modifier.rotate(180f)
                    )
                }
                Text(text = currentMonth.toString(), style = NotoTypography.headlineMedium)
                IconButton(onClick = { currentMonth = currentMonth.plusMonths(1) }) {
                    Icon(
                        imageVector = Icons.Filled.PlayArrow,
                        contentDescription = "다음",
                        tint = TextGray
                    )
                }
            }
            HorizontalCalendar(
                state = state,
                dayContent = {
                    val hasHistory = historyList.contains(it.date)
                    Day(it, hasHistory)
                },
                monthHeader = {
                    DaysOfWeekTitle(daysOfWeek = daysOfWeek)
                }
            )
        }
    }
}

@Composable
fun Day(day: CalendarDay, hasHistory: Boolean) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .background(if (hasHistory) Main else Color.Transparent, CircleShape), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            style = NotoTypography.labelLarge,
            color = if (day.position == DayPosition.MonthDate){ if(hasHistory) BackGray else TextGray}else LightGray
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