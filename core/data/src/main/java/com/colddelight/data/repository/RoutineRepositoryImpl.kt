package com.colddelight.data.repository

import android.util.Log
import com.colddelight.database.dao.DayExerciseDao
import com.colddelight.database.dao.ExerciseDao
import com.colddelight.database.dao.HistoryDao
import com.colddelight.database.dao.HistoryExerciseDao
import com.colddelight.database.dao.RoutineDao
import com.colddelight.database.dao.RoutineDayDao
import com.colddelight.database.model.DayExerciseEntity
import com.colddelight.database.model.ExerciseEntity
import com.colddelight.database.model.HistoryExerciseEntity
import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.datastore.datasource.UserPreferencesDataSource
import com.colddelight.model.DayExercise
import com.colddelight.model.Exercise
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.ExerciseInfo
import com.colddelight.model.Routine
import com.colddelight.model.RoutineDayInfo
import com.colddelight.model.SetInfo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import java.time.LocalDate
import javax.inject.Inject

class RoutineRepositoryImpl @Inject constructor(
    private val routineDao: RoutineDao,
    private val dayExerciseDao: DayExerciseDao,
    private val exerciseDao: ExerciseDao,
    private val routineDayDao: RoutineDayDao,
    private val userDataSource: UserPreferencesDataSource,
    private val historyDao: HistoryDao,
    private val historyExerciseDao: HistoryExerciseDao,

    ) : RoutineRepository {

    override fun getRoutine(): Flow<Routine> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDao.getRoutine(routineId)
                    .map {
                        val routine = Routine(
                            id = it.id,
                            name = it.name,
                            cnt = it.cnt
                        )
                        routine
                    }
            }
    }

    override fun getRoutineWeekInfo(): Flow<List<RoutineDayInfo>> {
        return userDataSource.currentRoutineId
            .flatMapLatest { routineId ->
                routineDayDao.getAllRoutineDay(routineId)
                    .combine(dayExerciseDao.getAllDayExercise()) { routineDayList, dayExerciseMapList ->
                        val resultList = mutableListOf<RoutineDayInfo>()

                        val missingDays =
                            (1..7).toSet() - routineDayList.map { it.dayOfWeek }.toSet()

                        for (routineDayEntity in routineDayList) {
                            val exerciseList = mutableListOf<Exercise>()

                            val routineDayMap = dayExerciseMapList.filterKeys {
                                it.routineDayId == routineDayEntity.id
                            }

                            routineDayMap.forEach { (dayExercise, exercise) ->
                                when (exercise.category) {
                                    ExerciseCategory.CALISTHENICS ->
                                        transformCalisthenicsExercise(dayExercise, exercise)

                                    else -> transformWeightExercise(dayExercise, exercise)
                                }.apply {
                                    exerciseList.add(this)
                                }
                            }

                            val routineDayInfo = RoutineDayInfo(
                                routineId = routineDayEntity.routineId,
                                routineDayId = routineDayEntity.id,
                                dayOfWeek = routineDayEntity.dayOfWeek,
                                categoryList = routineDayEntity.categoryList,
                                exerciseList = exerciseList
                            )
                            resultList.add(routineDayInfo)
                        }

                        missingDays.forEach { missingDay ->
                            val nullRoutineDayInfo = RoutineDayInfo(
                                routineId = routineId,
                                routineDayId = 0,
                                dayOfWeek = missingDay,
                                categoryList = emptyList(),
                                exerciseList = emptyList()
                            )
                            resultList.add(nullRoutineDayInfo)
                        }

                        // dayOfWeek로 정렬
                        val sortedRoutineDayInfoList =
                            resultList.sortedBy { it.dayOfWeek }

                        sortedRoutineDayInfoList
                    }
            }
    }

    private fun transformCalisthenicsExercise(
        dayExerciseEntity: DayExerciseEntity,
        exercise: ExerciseEntity,
    ): Exercise.Calisthenics {
        return Exercise.Calisthenics(
            exerciseId = exercise.id,
            dayExerciseId = dayExerciseEntity.id,
            name = exercise.name,
            reps = dayExerciseEntity.repsList.maxOrNull() ?: 0,
            set = dayExerciseEntity.repsList.size,
            setInfoList = dayExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    dayExerciseEntity.repsList[index]
                )
            }
        )
    }

    private fun transformWeightExercise(
        dayExerciseEntity: DayExerciseEntity,
        exercise: ExerciseEntity,
    ): Exercise.Weight {
        return Exercise.Weight(
            exerciseId = exercise.id,
            dayExerciseId = dayExerciseEntity.id,
            name = exercise.name,
            min = dayExerciseEntity.kgList.minOrNull() ?: 0,
            max = dayExerciseEntity.kgList.maxOrNull() ?: 0,
            setInfoList = dayExerciseEntity.kgList.mapIndexed { index, kg ->
                SetInfo(
                    kg,
                    dayExerciseEntity.repsList[index]
                )
            }

        )
    }


    override suspend fun insertRoutineDay(routinDay: RoutineDayInfo) {
        val routineDayEntity = RoutineDayEntity(
            routineId = routinDay.routineId,
            categoryList = routinDay.categoryList,
            dayOfWeek = routinDay.dayOfWeek,
            id = routinDay.routineDayId
        )
        routineDayDao.insertRoutineDay(routineDayEntity)
    }

    override suspend fun insertExercise(exercise: ExerciseInfo) {
        val exerciseEntity = ExerciseEntity(
            id = exercise.id,
            category = exercise.category,
            name = exercise.name
        )
        exerciseDao.insertExercise(exerciseEntity)
    }

    override suspend fun insertDayExercise(dayExercise: DayExercise) {

        val todayHistoryId = historyDao.getHistoryForToday(LocalDate.now()).firstOrNull()

        val dayExerciseEntity = DayExerciseEntity(
            id = dayExercise.id,
            routineDayId = dayExercise.routineDayId,
            exerciseId = dayExercise.exerciseId,
            kgList = dayExercise.kgList,
            repsList = dayExercise.repsList
        )
        val id = dayExerciseDao.insertDayExercise(dayExerciseEntity)

        if (todayHistoryId != null) {
            val historyExerciseEntity = HistoryExerciseEntity(
                historyId = todayHistoryId,
                exerciseId = dayExercise.exerciseId,
                isDone = false,
                kgList = dayExercise.kgList,
                repsList = dayExercise.repsList,
                dayExerciseId = id.toInt()
            )
            historyExerciseDao.insertHistoryExercise(historyExerciseEntity)
        }
        Log.e("TAG", "insertDayExercise: 했숩니다${dayExerciseEntity}")
    }


    override suspend fun addRoutine(): List<ExerciseEntity> {
        //routineDayDao.deleteRoutineDayAndRelatedData(9)

        //1. Exercise Add
        exerciseDao.insertExercise(ExerciseEntity("벤치프레스", ExerciseCategory.CHEST))
        exerciseDao.insertExercise(ExerciseEntity("덤벨 컬", ExerciseCategory.ARM))
        exerciseDao.insertExercise(ExerciseEntity("턱걸이", ExerciseCategory.CALISTHENICS))

        //2. RoutineDay Add
        routineDayDao.insertRoutineDay(RoutineDayEntity(1, 2, listOf(1, 2)))

        //3. DayExercise Add
        dayExerciseDao.insertDayExercise(
            DayExerciseEntity(
                1,
                0,
                listOf(20, 40),
                listOf(12, 12)
            )
        )
        dayExerciseDao.insertDayExercise(
            DayExerciseEntity(
                1,
                1,
                listOf(40, 60),
                listOf(10, 10)
            )
        )
        dayExerciseDao.insertDayExercise(DayExerciseEntity(1, 1, listOf(0, 0), listOf(20, 20)))
        Log.e("TAG", "나 로그찍는다?: ")
        return exerciseDao.getExercise().first()
    }

    override suspend fun deleteRoutineDay(routineDayId: Int) {
        routineDayDao.deleteRoutineDayById(routineDayId)
    }

    override suspend fun deleteExercise(exerciseId: Int) {
        exerciseDao.deleteExerciseAndRelatedData(exerciseId)
    }

    override suspend fun deleteDayExercise(dayExerciseId: Int) {
        dayExerciseDao.deleteDayExerciseById(dayExerciseId)

        val todayHistoryId = historyDao.getHistoryForToday(LocalDate.now()).firstOrNull()
        if (todayHistoryId != null) {
            historyExerciseDao.deleteHistoryExerciseById(dayExerciseId)
        }
    }

    override fun getAllExerciseList(): Flow<List<ExerciseInfo>> {
        return exerciseDao.getExercise().mapNotNull {
            it.map { exerciseEntity ->
                val exerciseInfo = ExerciseInfo(
                    id = exerciseEntity.id,
                    name = exerciseEntity.name,
                    category = exerciseEntity.category
                )
                exerciseInfo
            }
        }
    }
}