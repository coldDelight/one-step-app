package com.colddelight.data.repository

import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.model.DayHistory
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao
) : HistoryRepository {

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
            isDone = historyExerciseEntity.isDone,
            category = ExerciseCategory.CALISTHENICS,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    historyExerciseEntity.repsList[index]
                )
            }
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
            isDone = historyExerciseEntity.isDone,
            category = exercise.category,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg, historyExerciseEntity.repsList[index]
                )
            }
        )
    }

    override fun getAllDoneHistory(): Flow<List<LocalDate>> {
        return historyDao.getAllDoneHistory()
    }

    override suspend fun insertHistory() {
        historyExerciseDao.insertHistoryExercise(
            HistoryExerciseEntity(
                historyId = 1,
                exerciseId = 2,
                isDone = true,
                kgList = listOf(20,60,80),
                repsList = listOf(15,15,15),
                dayExerciseId = 0
            )
        )
//        historyDao.insertHistory(
//            HistoryEntity(
//                LocalDate.of(2024, 1, 3),
//                listOf(1, 2),
//                isDone = true,
//            )
//        )
//        historyDao.insertHistory(
//            HistoryEntity(
//                LocalDate.of(2024, 1, 9),
//                listOf(1, 2),
//                isDone = true,
//            )
//        )
//        historyDao.insertHistory(
//            HistoryEntity(
//                LocalDate.of(2023, 12, 6),
//                listOf(1, 2),
//                isDone = true,
//            )
//        )
//        historyDao.insertHistory(
//            HistoryEntity(
//                LocalDate.of(2024, 2, 1),
//                listOf(1, 2),
//                isDone = true,
//            )
//        )
    }
}