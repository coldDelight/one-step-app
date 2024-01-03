package com.colddelight.data.repository

import android.util.Log
import com.colddelight.data.util.getDayOfWeekEn
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.ExerciseDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val routineDayDao: RoutineDayDao,
) : HomeRepository {
    override fun getExerciseWeek(): Flow<List<ExerciseDay>> {
        val now = LocalDate.now()
        val startOfWeek = now.with(DayOfWeek.MONDAY)
        val routineDayFlow = routineDayDao.getAllRoutineDay(1)
        val historyFlow = historyDao.getHistoryForThisWeek(startOfWeek)

        return routineDayFlow.combine(historyFlow) { routineDays, historyDates ->
            generateExerciseDays(startOfWeek, routineDays.map { it.dayOfWeek }, historyDates)
        }
    }

    private fun generateExerciseDays(
        startOfWeek: LocalDate,
        routineDays: List<Int>,
        historyDates: List<LocalDate>
    ): List<ExerciseDay> {
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

}