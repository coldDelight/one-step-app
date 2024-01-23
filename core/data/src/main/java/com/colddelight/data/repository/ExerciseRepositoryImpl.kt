package com.colddelight.data.repository

import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.SetInfo
import com.colddelight.model.TodayRoutine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val routineDao: RoutineDao,
    private val dayExerciseDao: DayExerciseDao,
    private val userDataSource: UserPreferencesDataSource
) : ExerciseRepository {

    private val todayHistoryId = historyDao.getHistoryForToday(LocalDate.now())


    private val dayOfWeek = LocalDate.now().dayOfWeek.value
    override fun getTodayExerciseList(): Flow<List<Exercise>> {
        return todayHistoryId.flatMapLatest {
            historyExerciseDao.getTodayHistoryExercises(it)
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
            isDone = historyExerciseEntity.isDone,
            category = exercise.category,
            setInfoList = historyExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg, historyExerciseEntity.repsList[index]
                )
            },
            dayExerciseId = historyExerciseEntity.dayExerciseId
        )
    }

    override fun getTodayRoutineInfo(): Flow<TodayRoutine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDayDao.getTodayRoutineInfo(routineId, dayOfWeek)
                    .map { routineDayInfoMap ->
                        val routine = routineDayInfoMap.keys.firstOrNull()
                        val routineInfo = TodayRoutine(
                            name = routine?.name ?: "",
                            cnt = routine?.cnt ?: 0,
                            categoryIdList = routineDayInfoMap[routine]?.split(",")
                                ?.mapNotNull {
                                    it.toIntOrNull()
                                        ?.let { id -> ExerciseCategory.fromId(id) }
                                } ?: listOf()
                        )
                        routineInfo
                    }
            }

    }

    override suspend fun upDateKgList(historyExerciseId: Int, kgList: List<Int>) {
        historyExerciseDao.updateKgList(historyExerciseId, kgList)
    }

    override suspend fun upDateRepsList(historyExerciseId: Int, repsList: List<Int>) {
        historyExerciseDao.updateRepsList(historyExerciseId, repsList)
    }

    override suspend fun upDateSetInfo(
        historyExerciseId: Int,
        kgList: List<Int>,
        repsList: List<Int>
    ) {
        historyExerciseDao.updateSetInfoList(historyExerciseId, kgList, repsList)
    }

    override suspend fun initExercise() {
        val id = todayHistoryId.firstOrNull() ?: -1

        when (id) {
            -1 -> {
                val todayRoutine = routineDayDao.geTodayRoutineDay(dayOfWeek).firstOrNull()
                if (todayRoutine != null) {
                    historyDao.insertHistory(
                        HistoryEntity(
                            LocalDate.now(),
                            todayRoutine.categoryList,
                            isDone = false,
                        )
                    )
                    val dayExercises =
                        dayExerciseDao.getDayExercisesByRoutineDayId(todayRoutine.id).first()
                    val historyExercises = dayExercises.map { dayExercise ->
                        HistoryExerciseEntity(
                            id = dayExercise.id,
                            historyId = todayHistoryId.firstOrNull() ?: -1,
                            exerciseId = dayExercise.exerciseId,
                            isDone = false,
                            kgList = dayExercise.kgList,
                            repsList = dayExercise.repsList,
                            dayExerciseId = dayExercise.id
                        )
                    }
                    historyExerciseDao.insertAll(historyExercises)
                }
            }

            else -> {}
        }
    }

    override suspend fun updateHistoryExercise(id: Int, isDone: Boolean) {
        historyExerciseDao.updateHistoryExercise(id, isDone)

    }

    override suspend fun updateDayExercise(id: Int, setInfoList: List<SetInfo>) {
        dayExerciseDao.updateKgRepsById(
            id,
            kgList = setInfoList.map { it.kg },
            repsList = setInfoList.map { it.reps })
    }

    override suspend fun finHistory() {
        historyDao.finToday(todayHistoryId.firstOrNull() ?: -1)
        routineDao.updateCountById(1)
    }
}

