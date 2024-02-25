package com.colddelight.domain.usecase.routineday

import com.colddelight.data.repository.HistoryRepository
import com.colddelight.data.repository.RoutineDayRepository
import com.colddelight.data.util.getDayOfWeekEn
import com.colddelight.model.ExerciseDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject


class GetExerciseDayListUseCase @Inject constructor(
    private val routineDayRepository: RoutineDayRepository,
    private val historyRepository: HistoryRepository
) {
    operator fun invoke() : Flow<List<ExerciseDay>> {
        val routineDayFlow = routineDayRepository.getExerciseDayList()
        val historyFlow = historyRepository.getHistoryDateForThisWeek()
        return routineDayFlow.combine(historyFlow) { routineDays, historyDates ->
            generateExerciseDays(routineDays.map { it.dayOfWeek }, historyDates)
        }
    }
}
//TODO GetRoutineDayListUseCase와 일주일중 빈날 만드는 로직 같게
private fun generateExerciseDays(
    routineDays: List<Int>,
    historyDates: List<LocalDate>
): List<ExerciseDay> {
    val startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY)
    val exerciseDays = mutableListOf<ExerciseDay>()
    val allWeek = listOf(1, 2, 3, 4, 5, 6, 7)
    val myWeek = allWeek.map { !routineDays.contains(it) }
    for (dayOfWeekId in 0 until 7) {
        val isRestDay = myWeek[dayOfWeekId]
        val isExerciseDone = historyDates.contains(startOfWeek.plusDays(dayOfWeekId.toLong()))
        exerciseDays.add(
            ExerciseDay(
                dayOfWeekId + 1,
                getDayOfWeekEn(dayOfWeekId + 1),
                isRestDay,
                isExerciseDone
            )
        )
    }
    return exerciseDays
}