package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.DayHistory
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao
) : HistoryRepository {
    override fun getHistoryForToday(): Flow<Int> {
        return historyDao.getHistoryForToday()
    }

    override suspend fun deleteHistory(historyId: Int) {
        historyDao.deleteHistory(historyId)
    }

    override fun getHistoryDateForThisWeek(): Flow<List<LocalDate>> {
        val startOfWeek = LocalDate.now().with(DayOfWeek.MONDAY)
        return historyDao.getHistoryForThisWeek(startOfWeek)
    }

    //----------------------------------------------------------------------------
    override fun getHistoryForSelectedMonth(selectedDate: LocalDate): Flow<List<DayHistory>> {
        val firstDayOfMonth = selectedDate.with(TemporalAdjusters.firstDayOfMonth())
        val lastDayOfMonth = selectedDate.with(TemporalAdjusters.lastDayOfMonth())
        return historyDao.getHistoryForSelectedMonth(firstDayOfMonth, lastDayOfMonth)
            .map { historyList ->
                historyList.map {
                    DayHistory(
                        id = it.id,
                        createdTime = it.createdTime,
                        categoryList = it.categoryList
                    )
                }
            }
    }

    override fun getHistoryForSelectedDay(historyId: Int): Flow<List<Exercise>> {
        return historyExerciseDao.getDoneHistoryExercises(historyId)
            .map { historyExercises ->
                val exerciseList = mutableListOf<Exercise>()
                historyExercises.forEach { (dayExerciseEntity, exercise) ->
                    val exerciseItem = when (exercise.category) {
                        ExerciseCategory.CALISTHENICS ->
                            transformCalisthenicsExercise(dayExerciseEntity, exercise)

                        else -> transformWeightExercise(dayExerciseEntity, exercise)
                    }
                    exerciseList.add(exerciseItem)
                }
                exerciseList
        }
    }


    private fun transformCalisthenicsExercise(
        historyExerciseEntity: HistoryExerciseEntity,
        exercise: ExerciseEntity
    ): Exercise.Calisthenics {
        return Exercise.Calisthenics(
            exerciseId = historyExerciseEntity.id,
            name = exercise.name,
            reps = historyExerciseEntity.repsList.maxOrNull() ?: 0,
            set = historyExerciseEntity.repsList.size,
//            isDone = historyExerciseEntity.isDone,
            category = ExerciseCategory.CALISTHENICS,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    historyExerciseEntity.repsList[index]
                )
            },
            dayExerciseId = historyExerciseEntity.dayExerciseId
        )
    }

    private fun transformWeightExercise(
        historyExerciseEntity: HistoryExerciseEntity,
        exercise: ExerciseEntity
    ): Exercise.Weight {
        return Exercise.Weight(
            exerciseId = historyExerciseEntity.id,
            name = exercise.name,
            min = historyExerciseEntity.kgList.minOrNull() ?: 0,
            max = historyExerciseEntity.kgList.maxOrNull() ?: 0,
//            isDone = historyExerciseEntity.isDone,
            category = exercise.category,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg, historyExerciseEntity.repsList[index]
                )
            },
            dayExerciseId = historyExerciseEntity.dayExerciseId
        )
    }

    override fun getAllDoneHistory(): Flow<List<LocalDate>> {
        return historyDao.getAllDoneHistory()
    }

}